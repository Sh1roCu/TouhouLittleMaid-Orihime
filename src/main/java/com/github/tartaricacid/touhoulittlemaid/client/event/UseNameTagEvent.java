package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.client.gui.item.NameTagGui;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class UseNameTagEvent {
    public static void onInteractClient(InteractMaidEvent event) {
        ItemStack stack = event.getStack();
        Player player = event.getPlayer();
        EntityMaid maid = event.getMaid();

        if (stack.get(DataComponents.CUSTOM_NAME) == null
                && player.getMainHandItem().getItem() == Items.NAME_TAG
                && maid.getOwner() != null
                && player.equals(maid.getOwner())
        ) {
            if (player.level.isClientSide()) {
                Minecraft.getInstance().setScreen(new NameTagGui(maid));
            }
            event.setCanceled(true);
        }
    }
}
