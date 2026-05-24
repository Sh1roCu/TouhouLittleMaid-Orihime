package com.github.tartaricacid.touhoulittlemaid.compat.iris;

import net.fabricmc.loader.api.FabricLoader;

public final class IrisCompat {
    public static final String IRIS = "iris";
    public static boolean IS_IRIS_INSTALLED = false;

    public static void init() {
        IS_IRIS_INSTALLED = FabricLoader.getInstance().isModLoaded(IRIS);
    }

    public static boolean isOculusInstalled() {
        return IS_IRIS_INSTALLED;
    }
}
