package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.client.event.MaidAreaRenderEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.SchedulePos;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public record SyncMaidAreaPackage(int id, SchedulePos schedulePos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncMaidAreaPackage> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("sync_maid_area"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMaidAreaPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            SyncMaidAreaPackage::id,
            SchedulePos.SCHEDULE_POS_STREAM_CODEC,
            SyncMaidAreaPackage::schedulePos,
            SyncMaidAreaPackage::new
    );

    public static void handle(SyncMaidAreaPackage message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> writePos(message));
    }

    @Environment(EnvType.CLIENT)
    private static void writePos(SyncMaidAreaPackage message) {
        MaidAreaRenderEvent.addSchedulePos(message.id, message.schedulePos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
