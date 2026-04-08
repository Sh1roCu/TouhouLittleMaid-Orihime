package com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.accessories.ref;

import com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.SBackpackCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.accessories.SBackpackAccessoriesCompat;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.common.BackpackWrapperLookup;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

import java.util.Optional;
import java.util.Set;

public class BackpackSlotRef implements ContainerRef {
    public final String slotType;
    public final int slotIndex;
    public final int priority;
    private final EntityMaid maid;

    public BackpackSlotRef(EntityMaid maid, String slotType, int slotIndex) {
        this.maid = maid;
        this.slotType = slotType;
        this.slotIndex = slotIndex;
        this.priority = SBackpackAccessoriesCompat.getSlotPriority(slotType);
    }

    public ItemStack getBackpackStack() {
        var inventory = AccessoriesCapability.getOptionally(maid);
        return inventory.map(handler -> Optional.ofNullable(handler.getContainers().get(slotType))
                .map(stacksHandler -> {
                    var stacks = stacksHandler.getAccessories();
                    if (slotIndex >= stacks.getContainerSize()) {
                        return ItemStack.EMPTY;
                    }

                    ItemStack stack = stacks.getItem(slotIndex);
                    if (SBackpackCompat.isBackpack(stack)) {
                        return stack;
                    }

                    return ItemStack.EMPTY;
                }).orElse(ItemStack.EMPTY)
        ).orElse(ItemStack.EMPTY);
    }

    @Override
    public boolean containing(ItemStack itemToCheck) {
        ItemStack backpackStack = getBackpackStack();
        if (backpackStack.isEmpty()) {
            return false;
        }
        var capability = BackpackWrapperLookup.get(backpackStack);
        return capability.map(wrapper -> {
            ITrackedContentsItemHandler inv = wrapper.getInventoryForUpgradeProcessing();
            Set<ItemStackKey> trackedStacks = inv.getTrackedStacks();
            return trackedStacks.stream()
                    .anyMatch(key -> ItemStack.isSameItemSameTags(key.getStack(), itemToCheck));
        }).orElse(false);
    }

    @Override
    public ItemStack insert(ItemStack itemstack, boolean simulate) {
        ItemStack backpackStack = getBackpackStack();
        if (backpackStack.isEmpty()) {
            return itemstack;
        }
        var capability = BackpackWrapperLookup.get(backpackStack);
        return capability.map(wrapper -> {
            ITrackedContentsItemHandler inv = wrapper.getInventoryForUpgradeProcessing();
            if (simulate) {
                return InventoryHelper.simulateInsertIntoInventory(inv, ItemVariant.of(itemstack), itemstack.getCount(), null);
            } else {
                return InventoryHelper.insertIntoInventory(inv, ItemVariant.of(itemstack), itemstack.getCount(), null);
            }
        }).orElse(itemstack);
    }

    public int compareTo(BackpackSlotRef other) {
        int priorityCompare = Integer.compare(this.priority, other.priority);
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        return Integer.compare(this.slotIndex, other.slotIndex);
    }
}