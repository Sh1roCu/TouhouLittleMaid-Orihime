package com.github.tartaricacid.touhoulittlemaid.entity.data;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record ProfileData(
        String modelId,
        String soundPackId
) {
    private static final String KEY = IdentifierUtil.modLoc("profile").toString();
    private static final String DEFAULT_MODEL_ID = "touhou_little_maid:hakurei_reimu";
    private static final String DEFAULT_SOUND_PACK_ID = "touhou_little_maid";
    private static final String PECO_SOUND_PACK_ID = "littlemaid_peco";
    private static final double PECO_CHANCE = 0.75;

    private static final Codec<ProfileData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.STRING.fieldOf("model_id").forGetter(ProfileData::modelId),
            Codec.STRING.optionalFieldOf("sound_pack_id", getInitSoundPackId()).forGetter(ProfileData::soundPackId)
    ).apply(ins, ProfileData::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, ProfileData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ProfileData::modelId,
            ByteBufCodecs.STRING_UTF8, ProfileData::soundPackId,
            ProfileData::new
    );

    public static final AttachmentType<ProfileData> TYPE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "profile"), builder -> builder
                    .initializer(() -> new ProfileData(DEFAULT_MODEL_ID, getInitSoundPackId()))
                    .persistent(CODEC)
                    .syncWith(STREAM_CODEC, AttachmentSyncPredicate.all())
                    .copyOnDeath());

    /**
     * 直接给 NBT 数据设置 model id
     */
    public static void directSetModelId(CompoundTag data, String modelId) {
        CompoundTag profile = new CompoundTag();
        profile.putString("model_id", modelId);

        CompoundTag attachment = new CompoundTag();
        attachment.put(KEY, profile);

        CompoundTag root = new CompoundTag();
        root.put(AttachmentTarget.NBT_ATTACHMENT_KEY, attachment);

        data.merge(root);
    }

    /**
     * 直接从 NBT 里读取 model id，如果没有返回默认
     */
    public static String directGetModelId(CompoundTag data) {
        var attachment = data.getCompound(AttachmentTarget.NBT_ATTACHMENT_KEY);
        return attachment.map(compoundTag -> compoundTag.getCompound(KEY)
                .map(tag -> tag.getStringOr("model_id", DEFAULT_MODEL_ID))
                .orElse(DEFAULT_MODEL_ID)).orElse(DEFAULT_MODEL_ID);
    }

    private static String getInitSoundPackId() {
        if (Math.random() < PECO_CHANCE) {
            return PECO_SOUND_PACK_ID;
        }
        return DEFAULT_SOUND_PACK_ID;
    }

    public ProfileData withModelId(String modelId) {
        return new ProfileData(modelId, this.soundPackId);
    }

    public ProfileData withSoundPackId(String soundPackId) {
        return new ProfileData(this.modelId, soundPackId);
    }
}
