package com.github.tartaricacid.touhoulittlemaid.compat.curios;

import cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidTombstoneEvent;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.MaidConfig;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import eu.pb4.trinkets.api.event.TrinketEquipmentChangedCallback;
import net.minecraft.world.MenuProvider;

public class CuriosCompat {
    private static boolean IS_LOADED = false;

    public static void init() {
        IS_LOADED = true;
        TrinketEquipmentChangedCallback.EVENT.register(CuriosEvent::onSlotUpdate);
        MaidTombstoneEvent.CALLBACK.register(TouhouLittleMaidFabric.LOWEST, CuriosEvent::onMaidTombstone);
    }

    public static boolean isLoaded() {
        return IS_LOADED;
    }

    public static boolean isLoadedOrEnable() {
        return isLoaded() && MaidConfig.ENABLE_MAID_CURIOS.get();
    }

    public static MenuProvider create(EntityMaid maid) {
//        if (isLoadedOrEnable()) {
//            return CuriosContainer.create(maid);
//        } else {
        return maid.getMaidBackpackType().getGuiProvider(maid.getId());
//        }
    }

    public static void registerScreen() {
        //MenuScreens.register(CuriosContainer.TYPE, CuriosContainerScreen::new);
    }

    public static void clientUpdatePage(int page) {
//        if (isLoadedOrEnable()) {
//            Minecraft mc = Minecraft.getInstance();
//            if (mc.screen instanceof CuriosContainerScreen screen) {
//                screen.updatePage(page);
//            }
//        }
    }

    public static void clientResetPage() {
//        if (isLoadedOrEnable()) {
//            Minecraft mc = Minecraft.getInstance();
//            if (mc.screen instanceof CuriosContainerScreen screen) {
//                screen.updatePage(screen.getPage());
//            }
//        }
    }
}
