package com.github.tartaricacid.touhoulittlemaid.entity.data;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.backpack.BackpackManager;
import com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record BackpackData(String type) {
    public static final String KEY = IdentifierUtil.modLoc("backpack").toString();

    private static final Codec<BackpackData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.STRING.fieldOf("type").forGetter(BackpackData::type)
    ).apply(ins, BackpackData::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, BackpackData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, BackpackData::type,
            BackpackData::new
    );

    public static final AttachmentType<BackpackData> TYPE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "backpack"), builder -> builder
                    .initializer(BackpackData::defaultBackpack)
                    .persistent(CODEC)
                    .syncWith(STREAM_CODEC, AttachmentSyncPredicate.all()));

    private static BackpackData defaultBackpack() {
        return new BackpackData(BackpackManager.getEmptyBackpack().getId().toString());
    }
}
