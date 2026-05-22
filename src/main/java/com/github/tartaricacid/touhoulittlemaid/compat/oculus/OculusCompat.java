package com.github.tartaricacid.touhoulittlemaid.compat.oculus;

import net.fabricmc.loader.api.FabricLoader;

public final class OculusCompat {
    // Fabric: Oculus->Iris
    public static final String OCULUS = "iris";
    public static boolean IS_OCULUS_INSTALLED = false;

    public static void init() {
        IS_OCULUS_INSTALLED = FabricLoader.getInstance().isModLoaded(OCULUS);
    }

    public static boolean isOculusInstalled() {
        return IS_OCULUS_INSTALLED;
    }
}
