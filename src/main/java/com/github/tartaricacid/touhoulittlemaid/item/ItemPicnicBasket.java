package com.github.tartaricacid.touhoulittlemaid.item;

import cn.sh1rocu.touhoulittlemaid.api.extension.IItemRenderer;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.ItemStackHandler;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity.PicnicBasketRender;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.other.PicnicBasketContainer;
import com.github.tartaricacid.touhoulittlemaid.inventory.tooltip.ItemContainerTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ItemPicnicBasket extends BlockItem implements ExtendedScreenHandlerFactory, IItemRenderer {
    @Environment(EnvType.CLIENT)
    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return PicnicBasketRender.INSTANCE.get();
    }

    private static final int PICNIC_BASKET_SIZE = 9;
    private static final String PICNIC_BASKET_TAG = "PicnicBasketContainer";

    public ItemPicnicBasket(Block block) {
        super(block, (new Properties()).stacksTo(1));
    }

    public static ItemStackHandler getContainer(ItemStack stack) {
        ItemStackHandler handler = new ItemStackHandler(PICNIC_BASKET_SIZE);
        if (stack.getItem() == InitItems.PICNIC_BASKET) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains(PICNIC_BASKET_TAG, Tag.TAG_COMPOUND)) {
                handler.deserializeNBT(tag.getCompound(PICNIC_BASKET_TAG));
            }
        }
        return handler;
    }

    public static void setContainer(ItemStack stack, ItemStackHandler itemStackHandler) {
        if (stack.getItem() == InitItems.PICNIC_BASKET) {
            stack.getOrCreateTag().put(PICNIC_BASKET_TAG, itemStackHandler.serializeNBT());
        }
    }

    // FIXME
    // (概率出现)未知原因导致创造模式时无法通过右键空气打开GUI，use压根没调用；但是右键不能交互的实体能正常打开，神了。
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (handIn == InteractionHand.MAIN_HAND && playerIn instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(this/*, data -> ItemStack.STREAM_CODEC.encode(data, serverPlayer.getMainHandItem())*/);
            return InteractionResultHolder.success(playerIn.getMainHandItem());
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        ItemStackHandler container = getContainer(stack);
        return Optional.of(new ItemContainerTooltip(container));
    }

    @Override
    public String getDescriptionId() {
        return "item.touhou_little_maid.picnic_basket";
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
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeItem(player.getMainHandItem());
    }
}
