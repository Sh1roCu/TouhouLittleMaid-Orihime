package com.github.tartaricacid.touhoulittlemaid.item;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStacksResourceHandler;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemUtil;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.other.PicnicBasketContainer;
import com.github.tartaricacid.touhoulittlemaid.inventory.tooltip.ItemContainerTooltip;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ItemPicnicBasket extends BlockItem implements ExtendedMenuProvider<ItemStack> {
    private static final int PICNIC_BASKET_SIZE = 9;

    public ItemPicnicBasket(Identifier id, Block block) {
        super(block, (new Properties())
                .setId(ResourceKey.create(Registries.ITEM, id))
                .stacksTo(1)
                .overrideDescription("item.touhou_little_maid.picnic_basket"));
    }

    public static ItemStacksResourceHandler getContainer(ItemStack stack) {
        var handler = new ItemStacksResourceHandler(PICNIC_BASKET_SIZE);
        if (stack.getItem() == InitItems.PICNIC_BASKET) {
            ItemContainerContents container = stack.get(DataComponents.CONTAINER);
            if (container != null) {
                List<ItemStack> itemStacks = container.allItemsCopyStream().toList();
                for (int i = 0; i < itemStacks.size(); i++) {
                    ItemStack itemStack = itemStacks.get(i);
                    handler.set(i, ItemVariant.of(itemStack), itemStack.getCount());
                }
            }
        }
        return handler;
    }

    public static void setContainer(ItemStack stack, ItemStacksResourceHandler itemStackHandler) {
        if (stack.getItem() == InitItems.PICNIC_BASKET) {
            NonNullList<ItemStack> items = NonNullList.withSize(PICNIC_BASKET_SIZE, ItemStack.EMPTY);
            for (int i = 0; i < itemStackHandler.size(); i++) {
                items.set(i, ItemUtil.getStack(itemStackHandler, i));
            }
            ItemContainerContents container = ItemContainerContents.fromItems(items);
            stack.set(DataComponents.CONTAINER, container);
        }
    }

    @Override
    public InteractionResult use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (handIn == InteractionHand.MAIN_HAND && playerIn instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(this);
            return InteractionResult.SUCCESS;
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        var container = getContainer(stack);
        return Optional.of(new ItemContainerTooltip(container));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new PicnicBasketContainer(containerId, playerInventory, player.getMainHandItem());
    }

    @Override
    public ItemStack getScreenOpeningData(ServerPlayer player) {
        return player.getMainHandItem();
    }
}
