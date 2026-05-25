package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.compat.curios.CuriosCompat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public record CuriosS2CUpdatePacket(int page) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CuriosS2CUpdatePacket> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("curios_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CuriosS2CUpdatePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CuriosS2CUpdatePacket::page,
            CuriosS2CUpdatePacket::new
    );

    @Environment(EnvType.CLIENT)
    public static void handle(CuriosS2CUpdatePacket message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> CuriosCompat.clientUpdatePage(message.page()));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}