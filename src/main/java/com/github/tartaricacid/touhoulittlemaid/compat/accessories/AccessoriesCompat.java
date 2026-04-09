package com.github.tartaricacid.touhoulittlemaid.compat.accessories;

import cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidTombstoneEvent;
import com.github.tartaricacid.touhoulittlemaid.compat.accessories.client.CuriosContainerScreen;
import com.github.tartaricacid.touhoulittlemaid.compat.accessories.menu.CuriosContainer;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.MaidConfig;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.wispforest.accessories.api.events.ContainersChangeCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.MenuProvider;

public class AccessoriesCompat {
    private static boolean IS_LOADED = false;

    public static void init() {
        IS_LOADED = true;
        ContainersChangeCallback.EVENT.register(AccessoriesEvent::onSlotUpdate);
        MaidTombstoneEvent.CALLBACK.register(TouhouLittleMaidFabric.LOWEST, AccessoriesEvent::onMaidTombstone);
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
