/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.transfer;

import cn.sh1rocu.touhoulittlemaid.util.neoforge.ItemContainerContentsUtil;
import cn.sh1rocu.touhoulittlemaid.util.neoforge.TransferVariantUtil;
import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.impl.transfer.item.ItemContainerContentsStorage;
import net.fabricmc.fabric.impl.transfer.item.ItemVariantImpl;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

/**
 * Base implementation of an item {@link ResourceHandler} backed by an {@link ItemVariant}.
 * The stacks are stored in a {@link ItemContainerContents} data component.
 * <p>
 */
public class ItemAccessItemHandler extends ItemAccessResourceHandler<ItemVariant> {
    protected final Item validItem;
    protected final DataComponentType<ItemContainerContents> component;

    public ItemAccessItemHandler(ContainerItemContext itemAccess, DataComponentType<ItemContainerContents> component, int size) {
        super(itemAccess, size);
        // Store the current item, such that if the item changes later we don't return any stored content from it.
        this.validItem = itemAccess.getItemVariant().getItem();
        this.component = component;
        Preconditions.checkArgument(size <= /* ItemContainerContents.MAX_SIZE */ 256,
                "The max size of ItemContainerContents is 256 slots.");
    }

    /**
     * Retrieves the {@link ItemContainerContents} from the current resource of the item access.
     */
    protected ItemContainerContents getContents(ItemVariant accessResource) {
        return accessResource.getOrDefault(component, ItemContainerContents.EMPTY);
    }

    /**
     * Retrieves a copy of a single stack from the underlying data component,
     * returning {@link ItemStack#EMPTY} if the component does not have a slot present.
     *
     * @param contents the existing contents
     * @param slot     the target slot
     * @return a copy of the stack in the target slot
     */
    protected ItemStack getStackFromContents(ItemContainerContents contents, int slot) {
        return slot < ItemContainerContentsUtil.getSlots(contents) ?
                ItemContainerContentsUtil.getStackInSlot(contents, slot) : ItemStack.EMPTY;
    }

    @Override
    protected ItemVariant getResourceFrom(ItemVariant accessResource, int index) {
        if (accessResource.is(validItem)) {
            return ItemVariant.of(getStackFromContents(getContents(accessResource), index));
        } else {
            return ItemVariant.blank();
        }
    }

    @Override
    protected int getAmountFrom(ItemVariant accessResource, int index) {
        if (accessResource.is(validItem)) {
            return getStackFromContents(getContents(accessResource), index).getCount();
        } else {
            return 0;
        }
    }

    @Override
    protected ItemVariant update(ItemVariant accessResource, int index, ItemVariant newResource, int newAmount) {
        var contents = getContents(accessResource);
        // Ensure we don't truncate any data by taking the max of the number of slots we need to fit, and our desired size
        NonNullList<ItemStack> list = NonNullList.withSize(Math.max(ItemContainerContentsUtil.getSlots(contents), size), ItemStack.EMPTY);
        contents.copyInto(list);
        list.set(index, newResource.toStack(newAmount));
        return TransferVariantUtil.with(accessResource, this.component, ItemContainerContents.fromItems(list));
    }

    @Override
    public boolean isValid(int index, ItemVariant resource) {
        // Any resource is valid, but we have to check that the item of the item access has not changed.
        return itemAccess.getItemVariant().is(validItem);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    protected int getCapacity(int index, ItemVariant resource) {
        return resource.isBlank() ? Item.ABSOLUTE_MAX_STACK_SIZE : Math.min(ItemVariantImpl.getMaxStackSize(resource), Item.ABSOLUTE_MAX_STACK_SIZE);
    }
}
