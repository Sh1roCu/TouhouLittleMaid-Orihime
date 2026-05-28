/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.transfer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStacksResourceHandler extends StacksResourceHandler<ItemStack, ItemVariant> {
    public ItemStacksResourceHandler(int size) {
        super(size, ItemStack.EMPTY, ItemStack.OPTIONAL_CODEC);
    }

    public ItemStacksResourceHandler(NonNullList<ItemStack> stacks) {
        super(stacks, ItemStack.EMPTY, ItemStack.OPTIONAL_CODEC);
    }

    @Override
    public ItemVariant getResourceFrom(ItemStack stack) {
        return ItemVariant.of(stack);
    }

    @Override
    public int getAmountFrom(ItemStack stack) {
        return stack.getCount();
    }

    @Override
    protected ItemStack getStackFrom(ItemVariant resource, int amount) {
        return resource.toStack(amount);
    }

    @Override
    protected int getCapacity(int index, ItemVariant resource) {
        return resource.isBlank() ? Item.ABSOLUTE_MAX_STACK_SIZE : Math.min(resource.toStack().getMaxStackSize(), Item.ABSOLUTE_MAX_STACK_SIZE);
    }

    @Override
    protected ItemStack copyOf(ItemStack stack) {
        return stack.copy();
    }

    @Override
    public boolean matches(ItemStack stack, ItemVariant resource) {
        return resource.matches(stack);
    }
}
