package com.github.tartaricacid.touhoulittlemaid.compat.sodium;

import com.github.tartaricacid.touhoulittlemaid.geckolib3.geo.animated.AnimatedGeoBone;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

    public static boolean sodiumRenderCubesOfBone(AnimatedGeoBone bone, PoseStack poseStack, VertexConsumer buffer, int cubePackedLight,
                                                  int packedOverlay, float red, float green, float blue, float alpha) {
        if (SodiumCompat.isSodiumInstalled()) {
            return SodiumGeoRenderer.renderCubesOfBone(bone, poseStack, buffer, cubePackedLight, packedOverlay, red, green, blue, alpha);
        }
        return false;
    }
}
