package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.network.client.BeaconAbsorbPackageProxy;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil.modLoc;

public record BeaconAbsorbPackage(float x, float y, float z) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<BeaconAbsorbPackage> TYPE = new CustomPacketPayload.Type<>(modLoc("beacon_absorb"));
    public static final StreamCodec<ByteBuf, BeaconAbsorbPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            BeaconAbsorbPackage::x,
            ByteBufCodecs.FLOAT,
            BeaconAbsorbPackage::y,
            ByteBufCodecs.FLOAT,
            BeaconAbsorbPackage::z,
            BeaconAbsorbPackage::new
    );

    public static void handle(BeaconAbsorbPackage message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> BeaconAbsorbPackageProxy.handle(message));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
