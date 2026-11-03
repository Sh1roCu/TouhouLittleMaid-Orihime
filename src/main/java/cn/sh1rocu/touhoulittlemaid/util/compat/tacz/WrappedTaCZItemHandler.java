package cn.sh1rocu.touhoulittlemaid.util.compat.tacz;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import net.minecraft.world.item.ItemStack;

public class WrappedTaCZItemHandler implements cn.sh1rocu.tacz.util.itemhandler.IItemHandler {
    private final IItemHandler handler;

    public WrappedTaCZItemHandler(IItemHandler itemHandler) {
        this.handler = itemHandler;
    }

    @Override
    public int getSlots() {
        return handler.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return handler.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack itemStack, boolean simulate) {
        return handler.insertItem(slot, itemStack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int count, boolean simulate) {
        return handler.extractItem(slot, count, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return handler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return handler.isItemValid(slot, itemStack);
    }
}
