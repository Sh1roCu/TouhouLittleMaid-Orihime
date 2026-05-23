/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.transfer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * An implementation of {@code ResourceHandler<ItemVariant>} for the {@link Inventory} of a {@link Player}.
 *
 * @see VanillaContainerWrapper
 * @see WorldlyContainerWrapper
 */
// TODO: do we want to "animate" client-side inserted items with setPopTime(POP_TIME_DURATION)?
public final class PlayerInventoryWrapper extends VanillaContainerWrapper {
    /**
     * Gets the inventory wrapper for a {@link Player}.
     */
    public static PlayerInventoryWrapper of(Player player) {
        return of(player.getInventory());
    }

    /**
     * Gets the inventory wrapper for a player's {@link Inventory}.
     */
    public static PlayerInventoryWrapper of(Inventory inventory) {
        return (PlayerInventoryWrapper) VanillaContainerWrapper.of(inventory);
    }

    private final DroppedItems droppedItems = new DroppedItems();
    private final Inventory inventory;

    PlayerInventoryWrapper(Inventory inventory) {
        super(inventory);
        this.inventory = inventory;
    }

    @Override
    void resize() {
        // We currently limit the player wrapper to main + armor + offhand. This can be changed later if needed.
        size = Inventory.SLOT_BODY_ARMOR;
        while (slotWrappers.size() < size) {
            int index = slotWrappers.size();
            if (Inventory.INVENTORY_SIZE <= index && index < Inventory.SLOT_OFFHAND) {
                var equipmentSlot = Inventory.EQUIPMENT_SLOT_MAPPING.get(index);
                slotWrappers.add(new ArmorSlotWrapper(index, equipmentSlot));
            } else {
                slotWrappers.add(new SlotWrapper(index));
            }
        }
    }

    @Override
    void onRootCommit() {
        super.onRootCommit();
        // This sends a ClientboundContainerSetSlotPacket for each changed slot,
        // which seems to be a good thing to do based on vanilla's Inventory#placeItemBackInInventory
        if (!inventory.player.level().isClientSide()) {
            inventory.player.containerMenu.broadcastChanges();
        }
    }

    /**
     * Retrieves a wrapper for a specific slot.
     */
    public ResourceHandler<ItemVariant> getSlotHandler(int slot) {
        return getSlotWrapper(slot);
    }

    /**
     * Retrieves a wrapper for the slot corresponding to the current main hand.
     */
    public ResourceHandler<ItemVariant> getMainHandSlot() {
        if (Inventory.isHotbarSlot(inventory.getSelectedSlot())) {
            return getSlotHandler(inventory.getSelectedSlot());
        } else {
            throw new RuntimeException("Unexpected player selected slot: " + inventory.getSelectedSlot());
        }
    }

    /**
     * Retrieves a wrapper for the slot corresponding to the given hand.
     */
    public ResourceHandler<ItemVariant> getHandSlot(InteractionHand hand) {
        return switch (hand) {
            case MAIN_HAND -> getMainHandSlot();
            case OFF_HAND -> getSlotHandler(Inventory.SLOT_OFFHAND);
        };
    }

    /**
     * Retrieves a wrapper around both hand slots.
     */
    public ResourceHandler<ItemVariant> getHandSlots() {
        return new CombinedResourceHandler<>(getMainHandSlot(), getHandSlot(InteractionHand.OFF_HAND));
    }

    /**
     * Retrieves a wrapper around the main slots only.
     */
    public ResourceHandler<ItemVariant> getMainSlots() {
        return RangedResourceHandler.of(this, 0, Inventory.INVENTORY_SIZE);
    }

    /**
     * Retrieves a wrapper around a single armor slot.
     */
    public ResourceHandler<ItemVariant> getArmorSlot(EquipmentSlot slot) {
        if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) {
            throw new IllegalArgumentException("EquipmentSlot is not an armor slot: " + slot);
        }
        return getSlotHandler(slot.getIndex(Inventory.INVENTORY_SIZE));
    }

    /**
     * Retrieves a wrapper around all 4 armor slots.
     */
    public ResourceHandler<ItemVariant> getArmorSlots() {
        return RangedResourceHandler.of(this, Inventory.INVENTORY_SIZE, Inventory.SLOT_OFFHAND);
    }

    /**
     * Inserts items into this player inventory, trying to place items
     * following the logic of {@link Inventory#placeItemBackInInventory}.
     */
    @Override
    public int insert(ItemVariant resource, int amount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, amount);

        int inserted = 0;

        // Stack into the main stack first and the offhand stack second.
        for (InteractionHand hand : InteractionHand.values()) {
            var handSlot = getHandSlot(hand);

            if (handSlot.getResource(0).equals(resource)) {
                inserted += handSlot.insert(resource, amount - inserted, transaction);
                if (inserted == amount) {
                    return inserted;
                }
            }
        }

        // Otherwise insert into the main slots, stacking first.
        inserted += ResourceHandlerUtil.insertStacking(getMainSlots(), resource, amount - inserted, transaction);

        return inserted;
    }

    /**
     * Transactional version of {@link Inventory#placeItemBackInInventory}:
     * tries to insert as much as possible into the player inventory, and drops the remainder.
     */
    public void placeItemBackInInventory(ItemVariant resource, int amount, TransactionContext transactionContext) {
        int inserted = insert(resource, amount, transactionContext);
        if (inserted < amount) {
            // If we couldn't insert all of it, drop the remainder.
            drop(resource, amount - inserted, false, false, transactionContext);
        }
    }

    /**
     * Transactionally drops an item in the world.
     */
    public void drop(ItemVariant resource, int amount, boolean dropAround, boolean includeThrowerName, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, amount);
        if (amount == 0) return;

        // Drop in the world on the server side (will be synced by the game with the client).
        // Dropping items is server-side only because it involves randomness.
        if (!inventory.player.level().isClientSide()) {
            droppedItems.addDrop(resource, amount, dropAround, includeThrowerName, transaction);
        }
    }

    @Override
    public String toString() {
        return "PlayerInventoryWrapper{player=%s}".formatted(inventory.player);
    }

    private class DroppedItems extends SnapshotJournal<Integer> {
        final Deque<DropInfo> entries = new ArrayDeque<>();

        void addDrop(ItemVariant resource, int amount, boolean dropAround, boolean includeThrowerName, TransactionContext transaction) {
            updateSnapshots(transaction);
            entries.add(new DropInfo(resource, amount, dropAround, includeThrowerName));
        }

        @Override
        protected Integer createSnapshot() {
            return entries.size();
        }

        @Override
        protected void readSnapshot(Integer snapshot) {
            // effectively cancel dropping the stacks
            int previousSize = snapshot;

            while (entries.size() > previousSize) {
                entries.removeLast();
            }
        }

        @Override
        protected void onRootCommit(Integer originalState) {
            // actually drop the stacks
            // process elements of the queue one by one to avoid a CME if dropping the entity triggers more additions to the queue
            while (!entries.isEmpty()) {
                DropInfo dropInfo = entries.removeFirst();
                int remainder = dropInfo.amount;

                int maxStackSize = dropInfo.resource.toStack().getMaxStackSize();
                while (remainder > 0) {
                    int dropped = Math.min(maxStackSize, remainder);
                    // This takes care of firing ItemTossEvent + dropping the entity if the event is not canceled
                    // CommonHooks.onPlayerTossEvent(inventory.player, dropInfo.resource.toStack(dropped), dropInfo.dropAround, dropInfo.includeThrowerName);
                    remainder -= dropped;
                }
            }
        }

        private record DropInfo(ItemVariant resource, int amount, boolean dropAround, boolean includeThrowerName) {
        }
    }

    /**
     * Specialized slot wrapper for one of the armor slots.
     * Limits size to 1, disallows insertion of non-equippable items, and extraction of cursed items for non-creative players.
     */
    private class ArmorSlotWrapper extends SlotWrapper {
        private final EquipmentSlot slot;

        ArmorSlotWrapper(int index, EquipmentSlot slot) {
            super(index);
            this.slot = slot;
        }

        @Override
        protected boolean isValid(ItemVariant resource) {
            return inventory.player.isEquippableInSlot(resource.toStack(), slot) && super.isValid(resource);
        }

        @Override
        protected int getCapacity(ItemVariant resource) {
            return slot.countLimit;
        }

        @Override
        public int extract(int index, ItemVariant resource, int amount, TransactionContext transaction) {
            // Prevent extraction of items with the curse of binding for non-creative players.
            if (!inventory.player.isCreative() && EnchantmentHelper.has(resource.toStack(), EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) {
                return 0;
            }
            return super.extract(index, resource, amount, transaction);
        }
    }
}
