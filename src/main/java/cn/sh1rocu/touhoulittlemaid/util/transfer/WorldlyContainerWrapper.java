/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.transfer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import org.jspecify.annotations.Nullable;

/**
 * An implementation of {@code ResourceHandler<ItemVariant>} for vanilla's {@link WorldlyContainer} .
 *
 * <p><b>Important note:</b> This wrapper assumes that the container owns its slots.
 * If the container does not own its slots, for example because it delegates to another container, this wrapper should not be used!
 *
 * @see VanillaContainerWrapper
 * @see PlayerInventoryWrapper
 */
public class WorldlyContainerWrapper implements ResourceHandler<ItemVariant> {
    private final WorldlyContainer container;
    private final VanillaContainerWrapper wrapper;
    @Nullable
    private final Direction side;

    public WorldlyContainerWrapper(WorldlyContainer container, @Nullable Direction side) {
        this.container = container;
        this.wrapper = VanillaContainerWrapper.internalOf(container);
        this.side = side;
    }

    private int convertSlot(int slot) {
        if (slot < 0) {
            throw new IndexOutOfBoundsException("Cannot access container with negative slot index: " + slot);
        }
        if (side == null) {
            return slot;
        }
        int[] slots = container.getSlotsForFace(side);
        if (slot >= slots.length) {
            throw new IndexOutOfBoundsException("Cannot access worldly container on side " + side + " : out of bounds slot index " + slot + " with size " + slots.length);
        }
        return slots[slot];
    }

    @Override
    public int size() {
        return side == null ? container.getContainerSize() : container.getSlotsForFace(side).length;
    }

    @Override
    public ItemVariant getResource(int index) {
        return wrapper.getResource(convertSlot(index));
    }

    @Override
    public long getAmountAsLong(int index) {
        return wrapper.getAmountAsLong(convertSlot(index));
    }

    @Override
    public long getCapacityAsLong(int index, ItemVariant resource) {
        return wrapper.getCapacityAsLong(convertSlot(index), resource);
    }

    @Override
    public boolean isValid(int index, ItemVariant resource) {
        return wrapper.isValid(convertSlot(index), resource);
    }

    @Override
    public int insert(int index, ItemVariant resource, int amount, TransactionContext transaction) {
        int convertedSlot = convertSlot(index);
        if (!container.canPlaceItemThroughFace(convertedSlot, resource.toStack(), side)) {
            return 0;
        }
        return wrapper.insert(convertedSlot, resource, amount, transaction);
    }

    @Override
    public int extract(int index, ItemVariant resource, int amount, TransactionContext transaction) {
        int convertedSlot = convertSlot(index);
        if (side != null && !container.canTakeItemThroughFace(convertedSlot, resource.toStack(), side)) {
            return 0;
        }
        return wrapper.extract(convertedSlot, resource, amount, transaction);
    }
}
