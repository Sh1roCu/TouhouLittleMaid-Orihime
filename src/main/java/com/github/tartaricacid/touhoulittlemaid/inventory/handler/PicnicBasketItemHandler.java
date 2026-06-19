package com.github.tartaricacid.touhoulittlemaid.inventory.handler;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemAccessItemHandler;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStackStorage;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PicnicBasketItemHandler extends ItemAccessItemHandler {
    private static final int PICNIC_BASKET_SIZE = 9;

    public PicnicBasketItemHandler(ContainerItemContext itemAccess) {
        super(itemAccess, DataComponents.CONTAINER, PICNIC_BASKET_SIZE);
    }

    public static PicnicBasketItemHandler fromStack(ItemStack stack) {
        ContainerItemContext access = ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack));
        return new PicnicBasketItemHandler(access);
    }

    public static PicnicBasketItemHandler fromPlayer(Player player) {
        ContainerItemContext access = ContainerItemContext.ofPlayerSlot(
                player, PlayerInventoryStorage.of(player).getSlot(player.getInventory().getSelectedSlot()));
        return new PicnicBasketItemHandler(access);
    }

    @Override
    public boolean isValid(int index, ItemVariant resource) {
        return super.isValid(index, resource) && resource.has(DataComponents.FOOD);
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
}
