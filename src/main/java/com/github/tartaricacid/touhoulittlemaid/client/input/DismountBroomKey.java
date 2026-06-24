package com.github.tartaricacid.touhoulittlemaid.client.input;

import com.github.tartaricacid.touhoulittlemaid.network.message.DismountPackage;
import com.github.tartaricacid.touhoulittlemaid.util.migrate.ScreenUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.player.LocalPlayer;
import org.lwjgl.glfw.GLFW;

import static com.github.tartaricacid.touhoulittlemaid.client.init.KeyMappingRegister.MAID_CATEGORY;

public class DismountBroomKey {
    public static final KeyMapping DISMOUNT_KEY = new KeyMapping("key.touhou_little_maid.dismount.desc",
//            KeyConflictContext.IN_GAME,
//            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            MAID_CATEGORY
    );

    public static void onDismountPress(int action, KeyEvent event) {
        if (keyIsMatch(event)) {
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

    @SuppressWarnings("removal")
    private static boolean keyIsMatch(KeyEvent event) {
        return DISMOUNT_KEY.matches(event);
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
