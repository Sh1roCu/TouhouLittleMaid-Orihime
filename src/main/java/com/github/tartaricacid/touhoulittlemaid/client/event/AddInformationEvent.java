package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.entity.backpack.BackpackManager;
import com.github.tartaricacid.touhoulittlemaid.item.bauble.BaubleManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

@Environment(EnvType.CLIENT)
public final class AddInformationEvent {
    public static void onRenderTooltips(ItemStack stack, Item.TooltipContext context, TooltipFlag type, List<Component> lines) {
        if (stack.isEmpty()) {
            return;
        }
        if (BaubleManager.getBauble(stack) != null) {
            lines.add(Component.literal(" "));
            lines.add(Component.translatable("tooltips.touhou_little_maid.bauble.desc"));
            lines.add(Component.translatable("tooltips.touhou_little_maid.bauble.usage").withStyle(ChatFormatting.GRAY));
        }
        if (BackpackManager.findBackpack(stack).isPresent()) {
            lines.add(Component.literal(" "));
            lines.add(Component.translatable("tooltips.touhou_little_maid.backpack.desc"));
            lines.add(Component.translatable("tooltips.touhou_little_maid.backpack.usage").withStyle(ChatFormatting.GRAY));
        }
    }
}
