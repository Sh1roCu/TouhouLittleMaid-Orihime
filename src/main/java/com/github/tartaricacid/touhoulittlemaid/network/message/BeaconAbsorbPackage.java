package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityPowerPoint;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public record BeaconAbsorbPackage(float x, float y, float z) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<BeaconAbsorbPackage> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("beacon_absorb"));
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
        context.client().execute(() -> spawnParticle(message));
    }

    private static void spawnParticle(BeaconAbsorbPackage message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            EntityPowerPoint.spawnExplosionParticle(mc.level, message.x, message.y, message.z, mc.level.getRandom());
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
