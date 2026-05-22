package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import static com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil.getIdentifier;

public record RequestEffectPackage(int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RequestEffectPackage> TYPE = new CustomPacketPayload.Type<>(getIdentifier("request_effect"));
    public static final StreamCodec<ByteBuf, RequestEffectPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            RequestEffectPackage::id,
            RequestEffectPackage::new
    );

    public static void handle(RequestEffectPackage message, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayer sender = context.player();
            Entity entity = sender.level.getEntity(message.id);
            if (entity instanceof EntityMaid maid && maid.isOwnedBy(sender)) {
                SendEffectPackage sendEffectMessage = new SendEffectPackage(message.id, maid.getActiveEffects());
                ServerPlayNetworking.send(sender, sendEffectMessage);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
