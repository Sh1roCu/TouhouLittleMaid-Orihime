package cn.sh1rocu.touhoulittlemaid.util.transfer;

import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.world.item.ItemStack;

public class ItemStackStorage extends SingleStackStorage {
    private ItemStack stack;

    public ItemStackStorage(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    protected ItemStack getStack() {
        return stack;
    }

    @Override
    protected void setStack(ItemStack stack) {
        this.stack = stack;
    }
}
