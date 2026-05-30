package com.github.tartaricacid.touhoulittlemaid.compat.embeddium;

import com.github.tartaricacid.touhoulittlemaid.geckolib3.geo.animated.AnimatedGeoBone;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.loader.api.FabricLoader;

public class EmbeddiumCompat {
    public static final String EMBEDDIUM = "embeddium";
    public static boolean IS_EMBEDDIUM_INSTALLED = false;

    public static void init() {
        IS_EMBEDDIUM_INSTALLED = FabricLoader.getInstance().isModLoaded(EMBEDDIUM);
    }

    public static boolean isEmbeddiumInstalled() {
        return IS_EMBEDDIUM_INSTALLED;
    }

    public static boolean embeddiumRenderCubesOfBone(AnimatedGeoBone bone, PoseStack poseStack, VertexConsumer buffer, int cubePackedLight,
                                                     int packedOverlay, float red, float green, float blue, float alpha) {
        return false;
    }
}
