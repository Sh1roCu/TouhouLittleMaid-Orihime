package com.github.tartaricacid.touhoulittlemaid.entity.data;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record ProfileData(
        String modelId,
        String soundPackId
) {
    private static final String DEFAULT_MODEL_ID = "touhou_little_maid:hakurei_reimu";
    private static final String DEFAULT_SOUND_PACK_ID = "touhou_little_maid";
    private static final String PECO_SOUND_PACK_ID = "littlemaid_peco";
    private static final double PECO_CHANCE = 0.75;

    private static final Codec<ProfileData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.STRING.fieldOf("model_id").forGetter(ProfileData::modelId),
            Codec.STRING.fieldOf("sound_pack_id").forGetter(ProfileData::soundPackId)
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
