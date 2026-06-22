package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityChair;
import com.github.tartaricacid.touhoulittlemaid.network.client.OpenChairGuiPackageProxy;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil.modLoc;

public record OpenChairGuiPackage(int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenChairGuiPackage> TYPE = new CustomPacketPayload.Type<>(modLoc("open_chair_gui"));
    public static final StreamCodec<ByteBuf, OpenChairGuiPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            OpenChairGuiPackage::id,
            OpenChairGuiPackage::new
    );

    public OpenChairGuiPackage(EntityChair chair) {
        this(chair.getId());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenChairGuiPackage message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> OpenChairGuiPackageProxy.handle(message));
    }
}
