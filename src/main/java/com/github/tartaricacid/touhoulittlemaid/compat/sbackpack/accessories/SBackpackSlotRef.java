package com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.accessories;

import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.accessories.AccessoriesSlotRef;
import com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.SBackpackCompat;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.common.BackpackWrapperLookup;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

import java.util.Set;
import java.util.function.Predicate;

public class SBackpackSlotRef extends AccessoriesSlotRef {
    public SBackpackSlotRef(String slotType, int slotIndex) {
        super(slotType, slotIndex);
    }

    public ItemStack getBackpackStack(EntityMaid maid) {
        ItemStack stack = getCuriosStack(maid);
        if (SBackpackCompat.isBackpack(stack)) {
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean containing(EntityMaid maid, ItemStack itemToCheck) {
        ItemStack backpackStack = getBackpackStack(maid);
        if (backpackStack.isEmpty()) {
            return false;
        }
        var capability = BackpackWrapperLookup.get(backpackStack);
        return capability.map(wrapper -> {
            ITrackedContentsItemHandler inv = wrapper.getInventoryForUpgradeProcessing();
            Set<ItemStackKey> trackedStacks = inv.getTrackedStacks();
            return trackedStacks.stream().anyMatch(key ->
                    ItemStack.isSameItemSameTags(key.getStack(), itemToCheck));
        }).orElse(false);
    }

    @Override
    public ItemStack insert(EntityMaid maid, ItemStack itemStack, boolean simulate) {
        ItemStack backpackStack = getBackpackStack(maid);
        if (backpackStack.isEmpty()) {
            return itemStack;
        }
        var capability = BackpackWrapperLookup.get(backpackStack);
        return capability.map(wrapper -> {
            ITrackedContentsItemHandler inv = wrapper.getInventoryForUpgradeProcessing();
            if (simulate) {
                return InventoryHelper.simulateInsertIntoInventory(inv, ItemVariant.of(itemStack), itemStack.getCount(), null);
            } else {
                return InventoryHelper.insertIntoInventory(inv, ItemVariant.of(itemStack), itemStack.getCount(), null);
            }
        }).orElse(itemStack);
    }

    @Override
    public ItemStack extract(EntityMaid maid, Predicate<ItemStack> filter, int maxCount) {
        ItemStack backpackStack = getBackpackStack(maid);
        if (backpackStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        var capability = BackpackWrapperLookup.get(backpackStack);

        return capability.map(wrapper -> {
            ITrackedContentsItemHandler inv = wrapper.getInventoryForUpgradeProcessing();
            for (int slot = 0; slot < inv.getSlotCount(); slot++) {
                ItemStack stackInSlot = inv.getStackInSlot(slot);
                if (stackInSlot.isEmpty() || !filter.test(stackInSlot)) {
                    continue;
                }

                int itemMaxStack = stackInSlot.getMaxStackSize();
                int effectiveMaxCount = (maxCount == -1)
                        ? itemMaxStack
                        : Math.min(maxCount, itemMaxStack);
                int extractCount = Math.min(effectiveMaxCount, stackInSlot.getCount());
                return InventoryHelper.extractFromInventory(ItemVariant.of(stackInSlot), extractCount, inv, null);
            }
            return ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }
}