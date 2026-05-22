package com.github.tartaricacid.simplebedrockmodel.client.compat.sodium;


import net.fabricmc.loader.api.FabricLoader;

public class SodiumCompat {
    public static final String SODIUM = "sodium";
    public static boolean IS_SODIUM_INSTALLED = false;

    public static void init() {
        IS_SODIUM_INSTALLED = FabricLoader.getInstance().isModLoaded(SODIUM);
    }

    public static boolean isSodiumInstalled() {
        return IS_SODIUM_INSTALLED;
    }
}
