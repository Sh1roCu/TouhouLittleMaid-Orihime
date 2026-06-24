package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.config.subconfig.AIConfig;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.network.message.ai.OpenMaidAIChatPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import com.github.tartaricacid.touhoulittlemaid.util.ScreenUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class PressAIChatKeyEvent {
    public static void onOpenConfig(int action, KeyEvent event) {
        if (isInGame() && AIConfig.LLM_ENABLED.get() && keyIsMatch(action, event)) {
            EntityMaid maid = maidCheck();
            if (maid == null) {
                return;
            }
            Minecraft.getInstance().options.keyChat.consumeClick();
            // 先通过服务端鉴权，然后发送同步信息后再打开客户端界面
            ClientPlayNetworking.send(new OpenMaidAIChatPacket(maid));
        }
    }

    @SuppressWarnings("removal")
    private static boolean keyIsMatch(int action, KeyEvent event) {
        KeyMapping keyChat = Minecraft.getInstance().options.keyChat;
        return action == GLFW.GLFW_PRESS
                && keyChat.matches(event);
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
        if (ScreenUtil.hasOverlay()) {
            return false;
        }
        // 不能打开任何 GUI
        if (ScreenUtil.getScreen() != null) {
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
