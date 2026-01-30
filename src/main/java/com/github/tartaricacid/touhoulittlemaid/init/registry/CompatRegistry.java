package com.github.tartaricacid.touhoulittlemaid.init.registry;

import com.github.tartaricacid.touhoulittlemaid.compat.patchouli.PatchouliCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.SBackpackCompat;
import net.fabricmc.loader.api.FabricLoader;

public final class CompatRegistry {
    public static final String TOP = "theoneprobe";
    public static final String PATCHOULI = "patchouli";
    //public static final String CLOTH_CONFIG = "cloth_config";
    // 为什么Fabric端的id要改（
    public static final String CLOTH_CONFIG = "cloth-config";
    public static final String CARRY_ON = "carryon";
    public static final String SBACKPACK = "sophisticatedbackpacks";
    public static final String TRINKETS = "trinkets";

    public static void onEnqueue() {
        checkModLoad(PATCHOULI, PatchouliCompat::init);
        checkModLoad(SBACKPACK, SBackpackCompat::init);
        // TODO: FIXME
        // checkModLoad(TRINKETS, TrinketsCompat::init);
    }

    private static void checkModLoad(String modId, Runnable runnable) {
        if (FabricLoader.getInstance().isModLoaded(modId)) {
            runnable.run();
        }
    }
}
