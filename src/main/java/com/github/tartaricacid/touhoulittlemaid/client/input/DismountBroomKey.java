package com.github.tartaricacid.touhoulittlemaid.client.input;

import com.github.tartaricacid.touhoulittlemaid.network.message.DismountPackage;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class DismountBroomKey {
    public static final KeyMapping DISMOUNT_KEY = new KeyMapping("key.touhou_little_maid.dismount.desc",
//            KeyConflictContext.IN_GAME,
//            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "key.category.touhou_little_maid");

    public static void onDismountPress(int key, int scanCode, int action, int mods) {
        if (keyIsMatch(key, scanCode, action, mods)) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null || player.isSpectator()) {
                return;
            }
            if (!isInGame()) {
                return;
            }
            DISMOUNT_KEY.consumeClick();
            if (action == GLFW.GLFW_RELEASE) {
                ClientPlayNetworking.send(new DismountPackage(DismountPackage.DISMOUNT_BROOM));
            }
        }
    }

    private static boolean keyIsMatch(int key, int scanCode, int action, int mods) {
        return DISMOUNT_KEY.matches(key, scanCode)
                /*&& DISMOUNT_KEY.getKeyModifier().equals(KeyModifier.getActiveModifier())*/;
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