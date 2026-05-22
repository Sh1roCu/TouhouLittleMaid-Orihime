package com.github.tartaricacid.touhoulittlemaid.client.event;

import cn.sh1rocu.touhoulittlemaid.api.event.RenderHandEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

@Environment(EnvType.CLIENT)
public class CarryMaidHideArmEvent {
    public static void onRenderHandEvent(RenderHandEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (player.getFirstPassenger() instanceof EntityMaid) {
            event.setCanceled(true);
        }
    }
}
