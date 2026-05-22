package com.github.tartaricacid.touhoulittlemaid.compat.ponder;


import net.fabricmc.loader.api.FabricLoader;

public class PonderCompat {
    public static final String MOD_ID = "ponder";

    public static void register() {
        if (FabricLoader.getInstance().isModLoaded(MOD_ID)) {
            MaidPonderPlugin.register();
        }
    }
}