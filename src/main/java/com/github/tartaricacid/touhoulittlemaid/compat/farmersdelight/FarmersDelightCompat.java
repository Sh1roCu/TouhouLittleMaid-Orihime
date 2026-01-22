package com.github.tartaricacid.touhoulittlemaid.compat.farmersdelight;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.edible.MaidEdibleBlockManager;
import net.fabricmc.loader.api.FabricLoader;

public class FarmersDelightCompat {
    public static final String ID = "farmersdelight";

    public static void addFarmersDelightEdible(MaidEdibleBlockManager manager) {
        if (FabricLoader.getInstance().isModLoaded(ID)) {
            manager.add(new FarmersDelightEdible());
        }
    }
}
