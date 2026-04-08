package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.config.subconfig.AIConfig;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.network.message.ai.OpenMaidAIChatMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class PressAIChatKeyEvent {
    public static void onOpenConfig(int key, int scanCode, int action, int mods) {
        if (isInGame() && AIConfig.LLM_ENABLED.get() && keyIsMatch(key, scanCode, action, mods)) {
            EntityMaid maid = maidCheck();
            if (maid == null) {
                return;
            }
            Minecraft.getInstance().options.keyChat.consumeClick();
            // 先通过服务端鉴权，然后发送同步信息后再打开客户端界面
            ClientPlayNetworking.send(OpenMaidAIChatMessage.ID, OpenMaidAIChatMessage.encode(maid.getId()));
        }
    }

    private static boolean keyIsMatch(int key, int scanCode, int action, int mods) {
        KeyMapping keyChat = Minecraft.getInstance().options.keyChat;
        return action == GLFW.GLFW_PRESS
                && keyChat.matches(key, scanCode)
                /*&&keyChat.getKeyModifier().equals(KeyModifier.getActiveModifier())*/;
    }

    @Nullable
    private static EntityMaid maidCheck() {
        // 玩家不为空或者观察者模式
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.isSpectator()) {
            return null;
        }
        // 当前鼠标指向了特定的女仆
        Minecraft mc = Minecraft.getInstance();
        HitResult hitResult = mc.hitResult;
        if (!(hitResult instanceof EntityHitResult entityHitResult)) {
            return null;
        }
        if (!(entityHitResult.getEntity() instanceof EntityMaid maid)) {
            return null;
        }
        if (!maid.isOwnedBy(player)) {
            return null;
        }
        return maid;
    }

    private static boolean isInGame() {
        Minecraft mc = Minecraft.getInstance();
        // 不能是加载界面
        if (mc.getOverlay() != null) {
            return false;
        }
        // 不能打开任何 GUI
        if (mc.screen != null) {
            return false;
        }
        // 当前窗口捕获鼠标操作
        if (!mc.mouseHandler.isMouseGrabbed()) {
            return false;
        }
        // 选择了当前窗口
        return mc.isWindowActive();
    }
}
