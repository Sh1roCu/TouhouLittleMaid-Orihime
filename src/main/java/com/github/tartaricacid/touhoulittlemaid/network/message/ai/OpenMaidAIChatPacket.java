package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

import static com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil.getIdentifier;

public record OpenMaidAIChatPacket(int entityId) implements CustomPacketPayload {
    public static final Type<OpenMaidAIChatPacket> TYPE = new Type<>(getIdentifier("open_maid_ai_chat"));
    public static final StreamCodec<ByteBuf, OpenMaidAIChatPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, OpenMaidAIChatPacket::entityId,
            OpenMaidAIChatPacket::new
    );

    public OpenMaidAIChatPacket(EntityMaid maid) {
        this(maid.getId());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenMaidAIChatPacket message, ServerPlayNetworking.Context context) {
        context.server().execute(() -> handle(message, context.player()));
    }

    private static void handle(OpenMaidAIChatPacket message, @Nullable ServerPlayer player) {
        if (player == null) {
            return;
        }
        Entity entity = player.level.getEntity(message.entityId);
        if (entity instanceof EntityMaid maid && maid.isAlive() && maid.isOwnedBy(player)) {
            // 发送同步信息（包含 Token 用量）
            ServerPlayNetworking.send(player, new SyncMaidAIDataPacket(maid, player));
        }
    }
}
