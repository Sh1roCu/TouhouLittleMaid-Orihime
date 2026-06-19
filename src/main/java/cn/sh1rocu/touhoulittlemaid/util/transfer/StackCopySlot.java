/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.transfer;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public abstract class StackCopySlot extends Slot {
    private static final Container emptyInventory = new SimpleContainer(0);

    @Nullable
    private ItemStack cachedReturnedStack = null;

    /**
     * @param slot The slot in the underlying container, whatever it may be; zero if not applicable.
     */
    public StackCopySlot(int slot, int x, int y) {
        super(emptyInventory, slot, x, y);
    }

    /**
     * Gets the itemstack from the storage.
     *
     * @return the stack in this slot
     */
    protected abstract ItemStack getStackCopy();

    /**
     * Sets the itemstack from the storage.
     *
     * @param stack the stack to put into this slot
     */
    protected abstract void setStackCopy(ItemStack stack);

    @Override
    public ItemStack getItem() {
        return cachedReturnedStack = getStackCopy();
    }

    @Override
    public final void set(ItemStack stack) {
        setStackCopy(stack);
        cachedReturnedStack = stack;
    }

    @Override
    public final void setChanged() {
        // Verify that the stack has actually changed before setting it.
        // Vanilla menu logic (like AbstractContainerMenu#moveItemStackTo) often already sets the stack through Slot#setByPlayer.
        // This is done to prevent slot change logic from running multiple times when not necessary.
        if (cachedReturnedStack != null && !ItemStack.matches(cachedReturnedStack, getStackCopy())) {
            set(cachedReturnedStack);
        }
    }

    @Override
    public ItemStack remove(int amount) {
        ItemStack stack = getStackCopy().copy();
        ItemStack ret = stack.split(amount);
        set(stack);
        cachedReturnedStack = null;
        return ret;
    }
}
