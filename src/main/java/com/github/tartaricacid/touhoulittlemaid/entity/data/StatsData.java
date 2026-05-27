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

public record StatsData(
        int hunger,
        int favorability,
        int experience,
        boolean struckByLightning
) {
    private static final int DEFAULT_HUNGER = 0;
    private static final int DEFAULT_FAVORABILITY = 0;
    private static final int DEFAULT_EXPERIENCE = 0;
    private static final boolean DEFAULT_STRUCK_BY_LIGHTNING = false;

    private static final Codec<StatsData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.INT.fieldOf("hunger").forGetter(StatsData::hunger),
            Codec.INT.fieldOf("favorability").forGetter(StatsData::favorability),
            Codec.INT.fieldOf("experience").forGetter(StatsData::experience),
            Codec.BOOL.fieldOf("struck_by_lightning").forGetter(StatsData::struckByLightning)
    ).apply(ins, StatsData::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, StatsData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, StatsData::hunger,
            ByteBufCodecs.INT, StatsData::favorability,
            ByteBufCodecs.INT, StatsData::experience,
            ByteBufCodecs.BOOL, StatsData::struckByLightning,
            StatsData::new
    );

    public static final AttachmentType<StatsData> TYPE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "stats"), builder -> builder
                    .initializer(StatsData::defaultStats)
                    .persistent(CODEC)
                    .syncWith(STREAM_CODEC, AttachmentSyncPredicate.all()));

    private static StatsData defaultStats() {
        return new StatsData(DEFAULT_HUNGER, DEFAULT_FAVORABILITY, DEFAULT_EXPERIENCE, DEFAULT_STRUCK_BY_LIGHTNING);
    }

    public StatsData withHunger(int hunger) {
        return new StatsData(hunger, this.favorability, this.experience, this.struckByLightning);
    }

    public StatsData withFavorability(int favorability) {
        return new StatsData(this.hunger, favorability, this.experience, this.struckByLightning);
    }

    public StatsData withExperience(int experience) {
        return new StatsData(this.hunger, this.favorability, experience, this.struckByLightning);
    }

    public StatsData withStruckByLightning(boolean struckByLightning) {
        return new StatsData(this.hunger, this.favorability, this.experience, struckByLightning);
    }
}
