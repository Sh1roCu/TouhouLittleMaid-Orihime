package com.github.tartaricacid.touhoulittlemaid.compat.embeddium;

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

    // 暂无Fabric1.21的emb
/*    public static boolean embeddiumRenderCubesOfBone(AnimatedGeoBone bone, PoseStack poseStack, VertexConsumer buffer, int cubePackedLight,
                                                     int packedOverlay, float red, float green, float blue, float alpha) {
        if (EmbeddiumCompat.isEmbeddiumInstalled()) {
            return EmbeddiumGeoRenderer.renderCubesOfBone(bone, poseStack, buffer, cubePackedLight, packedOverlay, red, green, blue, alpha);
        }
        return false;
    }*/
}
