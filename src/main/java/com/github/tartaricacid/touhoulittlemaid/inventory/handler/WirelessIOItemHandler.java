package com.github.tartaricacid.touhoulittlemaid.inventory.handler;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemAccessItemHandler;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStackStorage;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.MaidItemManager;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class WirelessIOItemHandler extends ItemAccessItemHandler {
    public static final int FILTER_LIST_SIZE = 9;

    public WirelessIOItemHandler(ContainerItemContext itemAccess) {
        super(itemAccess, DataComponents.CONTAINER, FILTER_LIST_SIZE);
    }

    public static WirelessIOItemHandler fromStack(ItemStack stack) {
        ContainerItemContext access = ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack));
        return new WirelessIOItemHandler(access);
    }

    public static WirelessIOItemHandler fromPlayer(Player player) {
        ContainerItemContext access = ContainerItemContext.ofPlayerSlot(
                player, PlayerInventoryStorage.of(player).getSlot(player.getInventory().getSelectedSlot()));
        return new WirelessIOItemHandler(access);
    }

    @Override
    protected int getCapacity(int index, ItemVariant resource) {
        return 1;
    }

    @Override
    public boolean isValid(int index, ItemVariant resource) {
        return super.isValid(index, resource) && MaidItemManager.canInsertItem(resource.toStack());
    }

    public void set(int index, ItemVariant resource, int amount) {
        try (Transaction tx = Transaction.openOuter()) {
            ItemVariant currentResource = this.getResource(index);
            int currentAmount = this.getAmountAsInt(index);
            if (currentAmount > 0) {
                this.extract(index, currentResource, currentAmount, tx);
            }
            if (!resource.isBlank() && amount > 0) {
                this.insert(index, resource, amount, tx);
            }
            tx.commit();
        }
    }

    public void setFilter(int index, ItemStack stack) {
        ItemVariant resource = stack.isEmpty() ? ItemVariant.blank() : ItemVariant.of(stack.copyWithCount(1));
        this.set(index, resource, resource.isBlank() ? 0 : 1);
    }
}
