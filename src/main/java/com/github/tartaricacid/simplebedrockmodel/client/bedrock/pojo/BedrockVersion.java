package com.github.tartaricacid.simplebedrockmodel.client.bedrock.pojo;


import com.github.tartaricacid.simplebedrockmodel.SimpleBedrockModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;

@Environment(EnvType.CLIENT)
public enum BedrockVersion {
    /**
     * 旧版本基岩版模型，仅限 1.10.0
     */
    LEGACY("1.10.0"),
    /**
     * 新版本基岩版模型，往后的 1.14.0，1.16.0 1.21.0 通通用此版本读取
     */
    NEW(">=1.12.0");

    private final VersionPredicate versionRange;

    BedrockVersion(String version) {
        this.versionRange = createFromVersionSpec(version);
    }

    public static boolean isNewVersion(BedrockModelPOJO bedrockModel) {
        Version inputVersion;
        try {
            inputVersion = Version.parse(bedrockModel.getFormatVersion());
        } catch (VersionParsingException e) {
            throw new RuntimeException(e);
        }
        return NEW.versionRange.test(inputVersion);
    }

    public static boolean isLegacyVersion(BedrockModelPOJO bedrockModel) {
        Version inputVersion;
        try {
            inputVersion = Version.parse(bedrockModel.getFormatVersion());
        } catch (VersionParsingException e) {
            throw new RuntimeException(e);
        }
        return LEGACY.versionRange.test(inputVersion);
    }

    private static VersionPredicate createFromVersionSpec(final String spec) {
        try {
            return VersionPredicate.parse(spec);
        } catch (VersionParsingException e) {
            SimpleBedrockModel.LOGGER.fatal("Failed to parse version spec {}", spec, e);
            throw new RuntimeException("Failed to parse spec", e);
        }
    }

    public static BedrockVersion getVersion(BedrockModelPOJO pojo) {
        if (isNewVersion(pojo)) {
            return NEW;
        } else if (isLegacyVersion(pojo)) {
            return LEGACY;
        }
        throw new RuntimeException("Invalid version for model: " + pojo);
    }
}
