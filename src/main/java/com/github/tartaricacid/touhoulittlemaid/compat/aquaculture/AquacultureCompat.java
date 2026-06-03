package com.github.tartaricacid.touhoulittlemaid.compat.aquaculture;

import com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil;
import com.github.tartaricacid.touhoulittlemaid.compat.aquaculture.client.AquacultureClientRegister;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.fishing.FishingTypeManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

public class AquacultureCompat {
    private static final String MOD_ID = "aquaculture";
    private static boolean INSTALLED;

    public static void init() {
        INSTALLED = FabricLoader.getInstance().isModLoaded(MOD_ID);
        if (INSTALLED) {
            registerAll();
        }
    }

    public static void registerFishingType(FishingTypeManager manager) {
        if (INSTALLED) {
            //manager.addFishingType(new AquacultureFishingType());
        }
    }

    private static void registerAll() {
        register();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            AquacultureClientRegister.onEntityRenderers();
        }
    }

    private static void register() {
        Identifier id = IdentifierUtil.modLoc("aquaculture_fishing_hook");
        //Registry.register(BuiltInRegistries.ENTITY_TYPE, id, AquacultureFishingHook.TYPE);
    }
}
