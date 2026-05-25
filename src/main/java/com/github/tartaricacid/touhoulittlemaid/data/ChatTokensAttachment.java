package com.github.tartaricacid.touhoulittlemaid.data;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.AIConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class ChatTokensAttachment {
    public static final Codec<ChatTokensAttachment> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.INT.fieldOf("num").forGetter(o -> o.num)
    ).apply(ins, ChatTokensAttachment::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChatTokensAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ChatTokensAttachment::get,
            ChatTokensAttachment::new
    );

    public static final AttachmentType<ChatTokensAttachment> TYPE = AttachmentRegistry.create(Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "chat_tokens"),
            builder -> builder
                    .initializer(() -> new ChatTokensAttachment(0))
                    .copyOnDeath()
                    .persistent(CODEC)
                    .syncWith(STREAM_CODEC, AttachmentSyncPredicate.all()));

    private int num;

    public ChatTokensAttachment(int num) {
        this.num = num;
    }

    public boolean canAdd() {
        return this.num + 1 <= getMaxNum();
    }

    public void add(int num) {
        if (num + this.num <= getMaxNum()) {
            this.num += num;
        } else {
            this.num = getMaxNum();
        }
    }

    public void min(int num) {
        if (num <= this.num) {
            this.num -= num;
        } else {
            this.num = 0;
        }
    }

    public void set(int num) {
        this.num = Mth.clamp(num, 0, getMaxNum());
    }

    public int getMaxNum() {
        return AIConfig.MAX_TOKENS_PER_PLAYER.get();
    }

    public int get() {
        return this.num;
    }
}