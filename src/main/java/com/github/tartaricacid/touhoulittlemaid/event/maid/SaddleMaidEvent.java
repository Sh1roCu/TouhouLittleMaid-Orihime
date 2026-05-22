package com.github.tartaricacid.touhoulittlemaid.event.maid;

import com.github.tartaricacid.touhoulittlemaid.advancements.maid.TriggerType;
import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitTrigger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SaddleMaidEvent {
    public static void onInteract(InteractMaidEvent event) {
        Player player = event.getPlayer();
        EntityMaid maid = event.getMaid();
        ItemStack stack = event.getStack();
        if (stack.is(Items.SADDLE)) {
            if (player.getPassengers().isEmpty() && maid.getPassengers().isEmpty()) {
                // FIXME 抱起后概率导致女仆留在原地悬空
                boolean success = maid.startRiding(player);
                if (success && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                    SaddleMaidEvent.showTips();
                }
                if (maid.isHomeModeEnable()) {
                    maid.setHomeModeEnable(false);
                }
                if (player instanceof ServerPlayer serverPlayer) {
                    InitTrigger.MAID_EVENT.trigger(serverPlayer, TriggerType.PICKUP_MAID);
                }
                event.setCanceled(true);
                return;
            }
            if (!player.getPassengers().isEmpty()) {
                player.ejectPassengers();
                event.setCanceled(true);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static void showTips() {
        Minecraft minecraft = Minecraft.getInstance();
        Component component = Component.translatable("message.touhou_little_maid.saddle.how_to_eject");
        minecraft.gui.setOverlayMessage(component, false);
        minecraft.getNarrator().sayNow(component);
    }
}
