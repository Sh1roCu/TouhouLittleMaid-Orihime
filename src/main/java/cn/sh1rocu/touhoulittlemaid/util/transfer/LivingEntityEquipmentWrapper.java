/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.transfer;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.LivingEntityAccessor;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.MaidItemManager;
import com.google.common.collect.MapMaker;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedSlottedStorage;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LivingEntityEquipmentWrapper {
    private static final Map<LivingEntity, LivingEntityEquipmentWrapper> wrappers = new MapMaker().weakKeys().weakValues().makeMap();

    /**
     * Gets a wrapper for all equipment slots of a {@linkplain EquipmentSlot.Type given type}.
     *
     * @param entity        the entity whose equipment slots should be wrapped
     * @param equipmentType the type of equipment slots to wrap
     * @throws IllegalArgumentException if the entity is a player and the equipment type is neither
     *                                  {@link EquipmentSlot.Type#HAND} nor {@link EquipmentSlot.Type#HUMANOID_ARMOR}
     */
    public static SlottedStorage<ItemVariant> of(LivingEntity entity, EquipmentSlot.Type equipmentType) {
        if (entity instanceof Player player) {
            var storage = PlayerInventoryStorage.of(player);
            return switch (equipmentType) {
                case HAND ->
                        new CombinedSlottedStorage<>(List.of(storage.getHandSlot(InteractionHand.MAIN_HAND), storage.getHandSlot(InteractionHand.OFF_HAND)));
                case HUMANOID_ARMOR ->
                        new CombinedSlottedStorage<>(storage.getSlots().subList(Inventory.INVENTORY_SIZE, Inventory.SLOT_OFFHAND));
                default ->
                        throw new IllegalArgumentException("Wrapping the equipment type " + equipmentType + " of a player is not supported.");
            };
        }
        // Only expose a ResourceHandler in this method.
        return internalOf(entity, equipmentType);
    }

    /**
     * Gets a wrapper for a single {@linkplain EquipmentSlot equipment slot}.
     *
     * @param entity        the entity whose equipment slots should be wrapped
     * @param equipmentSlot the equipment slot to wrap
     * @throws IllegalArgumentException if the entity is a player and the equipment slot's type is neither
     *                                  {@link EquipmentSlot.Type#HAND} nor {@link EquipmentSlot.Type#HUMANOID_ARMOR}
     */
    public static SlottedStorage<ItemVariant> of(LivingEntity entity, EquipmentSlot equipmentSlot) {
        if (entity instanceof Player player) {
            var storage = PlayerInventoryStorage.of(player);
            if (equipmentSlot == EquipmentSlot.MAINHAND) {
                return storage.getHandSlot(InteractionHand.MAIN_HAND);
            } else if (equipmentSlot == EquipmentSlot.OFFHAND) {
                return storage.getHandSlot(InteractionHand.OFF_HAND);
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                return storage.getSlot(equipmentSlot.getIndex(Inventory.INVENTORY_SIZE));
            }
            throw new IllegalArgumentException("Wrapping the equipment slot " + equipmentSlot + " of a player is not supported.");
        }
        return internalOf(entity, equipmentSlot.getType()).getSlotWrapper(equipmentSlot.getIndex());
    }

    private static EquipmentTypeWrapper internalOf(LivingEntity entity, EquipmentSlot.Type equipmentType) {
        var wrapper = wrappers.computeIfAbsent(entity, LivingEntityEquipmentWrapper::new);
        return wrapper.byType.get(equipmentType);
    }

    private final LivingEntity entity;
    private final Map<EquipmentSlot.Type, EquipmentTypeWrapper> byType;

    private LivingEntityEquipmentWrapper(LivingEntity entity) {
        this.entity = entity;
        this.byType = new EnumMap<>(EquipmentSlot.Type.class);
        for (var equipmentType : EquipmentSlot.Type.values()) {
            var slotWrappers = new ArrayList<SlotWrapper>();
            for (var equipmentSlot : EquipmentSlot.VALUES) {
                if (equipmentSlot.getType() == equipmentType) {
                    slotWrappers.add(new SlotWrapper(equipmentSlot));
                }
            }
            this.byType.put(equipmentType, new EquipmentTypeWrapper(slotWrappers.toArray(SlotWrapper[]::new)));
        }
    }

    private class EquipmentTypeWrapper extends CombinedResourceHandler<ItemVariant> {
        EquipmentTypeWrapper(SlotWrapper... handlers) {
            super(handlers);
        }

        SlotWrapper getSlotWrapper(int index) {
            return (SlotWrapper) getHandlerFromIndex(index);
        }
    }

    /**
     * The wrapper for a single {@link EquipmentSlot}, used as a building block.
     */
    private class SlotWrapper extends ItemStackResourceHandler {
        private final EquipmentSlot slot;

        private SlotWrapper(EquipmentSlot slot) {
            this.slot = slot;
        }

        @Override
        protected ItemStack getStack() {
            return entity.getItemBySlot(slot);
        }

        @Override
        protected void setStack(ItemStack stack) {
            // We pass insideTransaction = true to disable all non-transactional actions.
            setItemSlot(entity, slot, stack, true);
        }

        @Override
        protected boolean isValid(ItemVariant resource) {
            // 适配女仆的黑名单检查
            if (entity instanceof EntityMaid) {
                return MaidItemManager.canInsertItem(resource.toStack());
            }
            return entity.isEquippableInSlot(resource.toStack(), slot);
        }

        public void setItemSlot(LivingEntity entity, EquipmentSlot slot, ItemStack itemStack, boolean insideTransaction) {
            var oldStack = ((LivingEntityAccessor) entity).tlm$equipment().set(slot, itemStack);
            if (!insideTransaction) entity.onEquipItem(slot, oldStack, itemStack);
        }

        @Override
        protected int getCapacity(ItemVariant resource) {
            int slotLimit = slot.countLimit == 0 ? Item.ABSOLUTE_MAX_STACK_SIZE : slot.countLimit;
            return resource.isBlank() ? slotLimit : Math.min(slotLimit, resource.toStack().getMaxStackSize());
        }

        @Override
        protected void onRootCommit(ItemStack originalState) {
            // Perform the delayed non-transactional actions
            // Note that this will not capture the details of all intermediate item changes that happened inside the transaction.
            entity.onEquipItem(slot, originalState, getStack());
        }

        @Override
        public String toString() {
            return "entity equipment wrapper[entity=" + entity + ",slot=" + slot + "]";
        }
    }
}
