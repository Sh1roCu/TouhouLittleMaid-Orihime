package com.github.tartaricacid.touhoulittlemaid.compat.trinkets;

import com.github.tartaricacid.touhoulittlemaid.compat.trinkets.client.CuriosContainerScreen;
import com.github.tartaricacid.touhoulittlemaid.compat.trinkets.event.SlotModifiersUpdatedEvent;
import com.github.tartaricacid.touhoulittlemaid.compat.trinkets.menu.CuriosContainer;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.MaidConfig;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.MenuProvider;

public class TrinketsCompat {
    private static boolean IS_LOADED = false;

    public static void init() {
        IS_LOADED = true;
        SlotModifiersUpdatedEvent.EVENT.register(TrinketsEvent::onSlotUpdate);
    }

    public static boolean isLoaded() {
        return IS_LOADED;
    }

    public static boolean isLoadedOrEnable() {
        return isLoaded() && MaidConfig.ENABLE_MAID_CURIOS.get();
    }

    public static MenuProvider create(EntityMaid maid) {
        if (isLoadedOrEnable()) {
            return CuriosContainer.create(maid);
        } else {
            return maid.getMaidBackpackType().getGuiProvider(maid.getId());
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerScreen() {
        MenuScreens.register(CuriosContainer.TYPE, CuriosContainerScreen::new);
    }

    @Environment(EnvType.CLIENT)
    public static void clientUpdatePage(int page) {
        if (isLoadedOrEnable()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.screen instanceof CuriosContainerScreen screen) {
                screen.updatePage(page);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static void clientResetPage() {
        if (isLoadedOrEnable()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.screen instanceof CuriosContainerScreen screen) {
                screen.updatePage(screen.getPage());
            }
        }
    }
}
