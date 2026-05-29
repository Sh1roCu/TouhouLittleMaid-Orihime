package com.github.tartaricacid.touhoulittlemaid.inventory.handler;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStacksResourceHandler;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemUtil;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.MaidItemManager;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MaidBackpackHandler extends ItemStacksResourceHandler {
    public static final int BACKPACK_ITEM_SLOT = 5;
    private final EntityMaid maid;

    public MaidBackpackHandler(int size, EntityMaid maid) {
        super(size);
        this.maid = maid;
    }

    @Override
    public boolean isValid(int slot, @Nonnull ItemVariant variant) {
        return MaidItemManager.canInsertItem(variant.toStack());
    }

    @Override
    protected void onContentsChanged(int slot, @Nonnull ItemStack previousStack) {
        if (slot == BACKPACK_ITEM_SLOT) {
            maid.setBackpackShowItem(ItemUtil.getStack(this, slot));
        }
    }
}
