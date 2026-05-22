package com.github.tartaricacid.touhoulittlemaid.compat.aquaculture.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class AquacultureClientRegister {
    public static void onEntityRenderers() {
        //EntityRendererRegistry.register(AquacultureFishingHook.TYPE, AquacultureFishingHookRenderer::new);
    }
}
