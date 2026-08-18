package com.github.tartaricacid.touhoulittlemaid.compat.patchouli;

import com.github.tartaricacid.touhoulittlemaid.api.event.client.OpenPatchouliBookEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class PatchouliCompat {
    public static void init() {
        MultiblockRegistry.init();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            OpenPatchouliBookEvent.CALLBACK.register(OpenDefaultBook::onPatchouliBookEvent);
        }
    }
}