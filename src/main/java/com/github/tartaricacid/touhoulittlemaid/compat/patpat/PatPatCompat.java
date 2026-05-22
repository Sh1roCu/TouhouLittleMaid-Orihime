package com.github.tartaricacid.touhoulittlemaid.compat.patpat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.LivingEntity;

public class PatPatCompat {
    private static final String PATPAT_ID = "patpat";
    private static boolean isLoaded = false;

    public static void init() {
        isLoaded = FabricLoader.getInstance().isModLoaded(PATPAT_ID);
    }

    public static void renderPat(LivingEntity livingEntity, PoseStack matrixStack, float tickDelta) {
        if (isLoaded) {
            PatPatRenderer.scaleEntityIfPatted(livingEntity, matrixStack, tickDelta);
        }
    }
}