package com.github.tartaricacid.touhoulittlemaid.entity.data;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.Map;

public record GameData(
        Object2IntArrayMap<String> winCounts,
        byte gameStatue
) {
    private static final byte DEFAULT_GAME_STATUE = 0;

    private static final Codec<Object2IntArrayMap<String>> WIN_COUNTS_CODEC = Codec.unboundedMap(Codec.STRING, Codec.INT)
            .xmap(Object2IntArrayMap::new, map -> map);

    private static final StreamCodec<RegistryFriendlyByteBuf, Object2IntArrayMap<String>> WIN_COUNTS_STREAM_CODEC =
            ByteBufCodecs.map(Object2IntArrayMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.INT);

    private static final Codec<GameData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            WIN_COUNTS_CODEC.fieldOf("win_counts").forGetter(GameData::winCounts),
            Codec.BYTE.fieldOf("game_statue").forGetter(GameData::gameStatue)
    ).apply(ins, GameData::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, GameData> STREAM_CODEC = StreamCodec.composite(
            WIN_COUNTS_STREAM_CODEC, GameData::winCounts,
            ByteBufCodecs.BYTE, GameData::gameStatue,
            GameData::new
    );

    public static final AttachmentType<GameData> TYPE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "game"), builder -> builder
                    .initializer(GameData::defaultGame)
                    .persistent(CODEC)
                    .syncWith(STREAM_CODEC, AttachmentSyncPredicate.all()));

    public GameData {
        winCounts = winCounts == null ? new Object2IntArrayMap<>() : new Object2IntArrayMap<>(winCounts);
    }

    private static GameData defaultGame() {
        return new GameData(new Object2IntArrayMap<>(), DEFAULT_GAME_STATUE);
    }

    public GameData withWinCounts(Map<String, Integer> winCounts) {
        return new GameData(new Object2IntArrayMap<>(winCounts), this.gameStatue);
    }

    public GameData withGameStatue(byte gameStatue) {
        return new GameData(this.winCounts, gameStatue);
    }

    public int getWinCount(String gameType) {
        return this.winCounts.getOrDefault(gameType, 0);
    }

    public GameData withIncreasedWinCount(String gameType) {
        Object2IntArrayMap<String> newWinCounts = new Object2IntArrayMap<>(this.winCounts);
        newWinCounts.put(gameType, newWinCounts.getOrDefault(gameType, 0) + 1);
        return new GameData(newWinCounts, this.gameStatue);
    }
}
