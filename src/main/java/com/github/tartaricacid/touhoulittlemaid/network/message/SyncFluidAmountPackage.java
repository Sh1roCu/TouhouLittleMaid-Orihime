package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack.TankBackpackContainer;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public record SyncFluidAmountPackage(int amount) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncFluidAmountPackage> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("client_sync_fluid_amount"));
    public static final StreamCodec<ByteBuf, SyncFluidAmountPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            SyncFluidAmountPackage::amount,
            SyncFluidAmountPackage::new
    );

    public static void handle(SyncFluidAmountPackage message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            if (context.player().containerMenu instanceof TankBackpackContainer tankBackpackContainer) {
                tankBackpackContainer.setClientFluidCount(message.amount);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
