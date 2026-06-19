package com.github.tartaricacid.touhoulittlemaid.compat.jei.handler;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.AbstractContainerScreenAccessor;
import com.github.tartaricacid.touhoulittlemaid.client.gui.item.WirelessIOContainerGui;
import com.github.tartaricacid.touhoulittlemaid.network.message.WirelessIOFilterSlotPackage;
import com.google.common.collect.Lists;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.github.tartaricacid.touhoulittlemaid.inventory.handler.WirelessIOItemHandler.FILTER_LIST_SIZE;

public class WirelessIOGhostHandler implements IGhostIngredientHandler<WirelessIOContainerGui> {
    @Override
    public <I> List<Target<I>> getTargetsTyped(WirelessIOContainerGui gui, ITypedIngredient<I> ingredient, boolean doStart) {
        if (ingredient.getType() != VanillaTypes.ITEM_STACK) {
            return List.of();
        }

        List<Target<I>> targets = Lists.newArrayList();
        for (int i = 0; i < FILTER_LIST_SIZE; i++) {
            int x = ((AbstractContainerScreenAccessor) gui).tlm$getLeftPos() + 62 + i % 3 * 18;
            int y = ((AbstractContainerScreenAccessor) gui).tlm$getTopPos() + 17 + i / 3 * 18;
            targets.add(new FilterTarget<>(x, y, i));
        }
        return targets;
    }

    @Override
    public void onComplete() {
    }

    private record FilterTarget<I>(int x, int y, int index) implements Target<I> {
        @Override
        public Rect2i getArea() {
            return new Rect2i(x, y, 16, 16);
        }

        @Override
        public void accept(I ingredient) {
            if (ingredient instanceof ItemStack stack) {
                var msg = new WirelessIOFilterSlotPackage(index, stack.copyWithCount(1));
                ClientPlayNetworking.send(msg);
            }
        }
    }
}
