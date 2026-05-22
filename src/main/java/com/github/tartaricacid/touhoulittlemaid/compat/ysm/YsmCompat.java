package com.github.tartaricacid.touhoulittlemaid.compat.ysm;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.tooltip.YsmMaidInfo;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.minecraft.nbt.CompoundTag;

public class YsmCompat {
    private static final String MOD_ID = "yes_steve_model";
    private static final VersionPredicate VERSION_RANGE;
    private static boolean INSTALLED = false;

    static {
        try {
            VERSION_RANGE = VersionPredicate.parse(">=2.3.3");
        } catch (VersionParsingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
            Version version = modContainer.getMetadata().getVersion();
            if (VERSION_RANGE.test(version)) {
                INSTALLED = true;
            } else {
                // 开发环境下，version 是空的，所以需要额外判断
                INSTALLED = FabricLoader.getInstance().isDevelopmentEnvironment();
            }
        });
    }

    public static boolean isInstalled() {
        return INSTALLED;
    }

    public static YsmMaidInfo getYsmMaidInfo(CompoundTag maidData) {
        if (isInstalled()) {
            boolean isYsmModel = maidData.getBoolean(EntityMaid.IS_YSM_MODEL_TAG);
            String ysmModelId = maidData.getString(EntityMaid.YSM_MODEL_ID_TAG);
            String ysmTextureId = maidData.getString(EntityMaid.YSM_MODEL_TEXTURE_TAG);
            String ysmName = maidData.getString(EntityMaid.YSM_MODEL_NAME_TAG);
            return new YsmMaidInfo(isYsmModel, ysmModelId, ysmTextureId, ysmName);
        }
        return YsmMaidInfo.EMPTY;
    }
}
