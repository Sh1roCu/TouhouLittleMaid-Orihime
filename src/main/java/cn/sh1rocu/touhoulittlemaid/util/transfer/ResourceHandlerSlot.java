/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.transfer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Base implementation for a {@link Slot} backed by a {@code ResourceHandler<ItemVariant>}.
 * Requires the handler to expose a {@linkplain IndexModifier direct mutation function},
 * such as {@link StacksResourceHandler#set}.
 */
public class ResourceHandlerSlot extends StackCopySlot {
    private final ResourceHandler<ItemVariant> handler;
    private final IndexModifier<ItemVariant> slotModifier;

    public ResourceHandlerSlot(ResourceHandler<ItemVariant> handler, IndexModifier<ItemVariant> slotModifier, int handlerSlot, int xPosition, int yPosition) {
        super(handlerSlot, xPosition, yPosition);
        this.handler = handler;
        this.slotModifier = slotModifier;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.isEmpty())
            return false;
        // Use isValid as a reasonable estimate.
        // We can't try to insert as we don't want to check the current contents to allow swapping.
        // This method is left for mods to override if this is not sufficient.
        return handler.isValid(this.getContainerSlot(), ItemVariant.of(stack));
    }

    @Override
    protected ItemStack getStackCopy() {
        return handler.getResource(this.getContainerSlot()).toStack(handler.getAmountAsInt(this.getContainerSlot()));
    }

    @Override
    protected void setStackCopy(ItemStack stack) {
        slotModifier.set(this.getContainerSlot(), ItemVariant.of(stack), stack.getCount());
    }

    @Override
    public void onQuickCraft(ItemStack oldStackIn, ItemStack newStackIn) {
    }

    @Override
    public int getMaxStackSize() {
        return handler.getCapacityAsInt(this.getContainerSlot(), ItemVariant.blank());
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return handler.getCapacityAsInt(this.getContainerSlot(), ItemVariant.of(stack));
    }

    @Override
    public boolean mayPickup(Player player) {
        var resource = handler.getResource(this.getContainerSlot());
        if (resource.isBlank()) {
            return false;
        }
        try (var tx = Transaction.openOuter()) {
            // Simulated extraction
            return handler.extract(this.getContainerSlot(), resource, 1, tx) == 1;
        }
    }

    public ResourceHandler<ItemVariant> getResourceHandler() {
        return handler;
    }

//    @Override
//    public boolean isSameInventory(Slot other) {
//        return other instanceof ResourceHandlerSlot rhs && rhs.handler == this.handler;
//    }
}
