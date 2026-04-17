package com.github.tartaricacid.touhoulittlemaid.init.registry;

import com.github.tartaricacid.touhoulittlemaid.compat.accessories.AccessoriesCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.carryon.BlackList;
import com.github.tartaricacid.touhoulittlemaid.compat.immersivemelodies.server.ImmersiveMelodiesServerCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.patchouli.PatchouliCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.SBackpackCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.tbackpack.TBackpackCompat;
import net.fabricmc.loader.api.FabricLoader;

public final class CompatRegistry {
    public static final String TOP = "theoneprobe";
    public static final String PATCHOULI = "patchouli";
    // public static final String CLOTH_CONFIG = "cloth_config";
    // 为什么Fabric端的id要改（
    public static final String CLOTH_CONFIG = "cloth-config";
    public static final String CARRY_ON = "carryon";
    public static final String SBACKPACK = "sophisticatedbackpacks";
    public static final String TBACKPACK = "travelersbackpack";
    public static final String ACCESSORIES = "accessories";
    public static final String IMMERSIVE_MELODIES = "immersive_melodies";

    public static void onEnqueue() {
        checkModLoad(PATCHOULI, PatchouliCompat::init);
        checkModLoad(CARRY_ON, BlackList::addBlackList);
        checkModLoad(SBACKPACK, SBackpackCompat::init);
        checkModLoad(TBACKPACK, TBackpackCompat::init);
        checkModLoad(ACCESSORIES, AccessoriesCompat::init);
        checkModLoad(IMMERSIVE_MELODIES, ImmersiveMelodiesServerCompat::init);
    }

    private static void checkModLoad(String modId, Runnable runnable) {
        if (FabricLoader.getInstance().isModLoaded(modId)) {
            runnable.run();
        }
    }
}
