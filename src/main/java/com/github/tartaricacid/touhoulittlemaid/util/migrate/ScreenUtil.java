package com.github.tartaricacid.touhoulittlemaid.util.migrate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

import javax.annotation.Nullable;

/**
 * 方便 26.1 -> 26.2 的迁移，用此类归一化
 */
public final class ScreenUtil {
    private ScreenUtil() {
    }

    public static void setScreen(@Nullable Screen screen) {
        Minecraft.getInstance().gui.setScreen(screen);
    }

    @Nullable
    public static Screen getScreen() {
        return Minecraft.getInstance().gui.screen();
    }

    public static boolean hasOverlay() {
        return Minecraft.getInstance().gui.overlay() != null;
    }

    public static void setTitle(Component title) {
        Minecraft.getInstance().gui.hud.setTitle(title);
    }

    public static void setSubtitle(Component subtitle) {
        Minecraft.getInstance().gui.hud.setSubtitle(subtitle);
    }

    public static void setOverlayMessage(Component message, boolean animate) {
        Minecraft.getInstance().gui.hud.setOverlayMessage(message, animate);
    }

    public static Identifier getMobEffectSprite(Holder<MobEffect> effectHolder) {
        return Hud.getMobEffectSprite(effectHolder);
    }

    public static boolean isHideGui() {
        return Minecraft.getInstance().gui.hud.isHidden();
    }
}
