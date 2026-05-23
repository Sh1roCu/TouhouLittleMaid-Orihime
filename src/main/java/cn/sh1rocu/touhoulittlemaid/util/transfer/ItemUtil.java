/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.transfer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Helper functions to work with {@link ResourceHandler}s of {@link ItemVariant}s.
 */
public final class ItemUtil {
    private ItemUtil() {
    }

    /**
     * Returns a new item stack with the contents of the handler at the given index.
     *
     * <p>The result's stack size may be greater than the max stack size.
     */
    public static ItemStack getStack(ResourceHandler<ItemVariant> handler, int index) {
        var resource = handler.getResource(index);
        if (resource.isBlank()) {
            return ItemStack.EMPTY;
        }
        return resource.toStack(handler.getAmountAsInt(index));
    }

    // Fabric
    public static ItemStack getStack(SlottedStorage<ItemVariant> handler, int index) {
        if (handler instanceof ResourceHandler<ItemVariant> resourceHandler) {
            return getStack(resourceHandler, index);
        }

        var storage = handler.getSlot(index);
        var resource = storage.getResource();
        if (resource.isBlank()) {
            return ItemStack.EMPTY;
        }
        return resource.toStack((int) storage.getAmount());
    }

    /**
     * Attempts to insert an item stack into a handler, leaving distribution to the handler, and returning any leftover.
     *
     * @param handler     handler to insert into
     * @param stack       the stack to insert, will not be modified by this function
     * @param simulate    {@code true} to simulate the result of the insert but leave the handler unmodified, {@code false} to modify the handler
     * @param transaction The transaction that this operation is part of.
     *                    This method will always use a nested transaction that will be rolled back.
     *                    {@code null} can be passed to conveniently have this method open its own root transaction.
     * @return the leftover: the stack of items that could <strong>not</strong> be inserted
     */
    public static ItemStack insertItemReturnRemaining(
            ResourceHandler<ItemVariant> handler,
            ItemStack stack,
            boolean simulate,
            @Nullable TransactionContext transaction) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        try (var tx = Transaction.openNested(transaction)) {
            int inserted = handler.insert(ItemVariant.of(stack), stack.getCount(), tx);
            if (!simulate) {
                tx.commit();
            }
            int leftover = stack.getCount() - inserted;
            return leftover == 0 ? ItemStack.EMPTY : stack.copyWithCount(leftover);
        }
    }

    /**
     * Attempts to insert an item stack into the index of a handler, returning any leftover.
     *
     * @param handler     handler to insert into
     * @param index       index to insert into
     * @param stack       the stack to insert, will not be modified by this function
     * @param simulate    {@code true} to simulate the result of the insert but leave the handler unmodified, {@code false} to modify the handler
     * @param transaction The transaction that this operation is part of.
     *                    This method will always use a nested transaction that will be rolled back.
     *                    {@code null} can be passed to conveniently have this method open its own root transaction.
     * @return the leftover: the stack of items that could <strong>not</strong> be inserted
     */
    public static ItemStack insertItemReturnRemaining(
            ResourceHandler<ItemVariant> handler,
            int index,
            ItemStack stack,
            boolean simulate,
            @Nullable TransactionContext transaction) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        try (var tx = Transaction.openNested(transaction)) {
            int inserted = handler.insert(index, ItemVariant.of(stack), stack.getCount(), tx);
            if (!simulate) {
                tx.commit();
            }
            int leftover = stack.getCount() - inserted;
            return leftover == 0 ? ItemStack.EMPTY : stack.copyWithCount(leftover);
        }
    }

    // Fabric
    public static ItemStack insertItemReturnRemaining(
            List<SingleSlotStorage<ItemVariant>> handler,
            int index,
            ItemStack stack,
            boolean simulate,
            @Nullable TransactionContext transaction) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        try (Transaction tx = Transaction.openNested(transaction)) {
            int inserted = (int) handler.get(index).insert(ItemVariant.of(stack), stack.getCount(), tx);
            if (!simulate) {
                tx.commit();
            }
            int leftover = stack.getCount() - inserted;
            return leftover == 0 ? ItemStack.EMPTY : stack.copyWithCount(leftover);
        }
    }

    // Fabric
    public static ItemStack insertItemReturnRemaining(
            SlottedStorage<ItemVariant> handler,
            int index,
            ItemStack stack,
            boolean simulate,
            @Nullable TransactionContext transaction) {
        if (handler instanceof ResourceHandler<ItemVariant>) {
            return insertItemReturnRemaining(handler, index, stack, simulate, transaction);
        }

        return insertItemReturnRemaining(handler.getSlots(), index, stack, simulate, transaction);
    }
}
