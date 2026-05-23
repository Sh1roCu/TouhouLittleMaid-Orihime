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

public record ToggleTabPackage(int entityId, int tabId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ToggleTabPackage> TYPE = new CustomPacketPayload.Type<>(getIdentifier("toggle_tab"));
    public static final StreamCodec<ByteBuf, ToggleTabPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ToggleTabPackage::entityId,
            ByteBufCodecs.VAR_INT,
            ToggleTabPackage::tabId,
            ToggleTabPackage::new
    );

    public static void handle(ToggleTabPackage message, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayer sender = context.player();
            Entity entity = sender.level.getEntity(message.entityId);
            if (entity instanceof EntityMaid maid && maid.isOwnedBy(sender)) {
                maid.openMaidGui(sender, message.tabId);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
