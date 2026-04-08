package com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.accessories.ref;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.ItemHandlerHelper;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.item.ItemStack;

public class MaidInventoryRef implements ContainerRef {
    private final EntityMaid maid;

    public MaidInventoryRef(EntityMaid maid) {
        this.maid = maid;
    }

    @Override
    public boolean containing(ItemStack itemToCheck) {
        // 女仆物品栏不存在 O(1) 复杂度的物品包含方法，只能遍历
        var inv = maid.getAvailableInv(false);
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack stackInSlot = inv.getStackInSlot(i);
            if (stackInSlot.isEmpty()) {
                continue;
            }
            if (ItemStack.isSameItemSameTags(stackInSlot, itemToCheck)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack insert(ItemStack itemstack, boolean simulate) {
        var inv = maid.getAvailableInv(false);
        return ItemHandlerHelper.insertItemStacked(inv, itemstack, simulate);
    }
}