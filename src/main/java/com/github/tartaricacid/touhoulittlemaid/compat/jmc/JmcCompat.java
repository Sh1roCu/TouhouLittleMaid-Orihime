package com.github.tartaricacid.touhoulittlemaid.compat.jmc;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.edible.MaidEdibleBlockManager;
import net.fabricmc.loader.api.FabricLoader;

public class JmcCompat {
    public static final String ID = "jmc";

    public static void addJmcEdible(MaidEdibleBlockManager manager) {
        if (FabricLoader.getInstance().isModLoaded(ID)) {
            manager.add(new JmcEdible());
        }
    }
}