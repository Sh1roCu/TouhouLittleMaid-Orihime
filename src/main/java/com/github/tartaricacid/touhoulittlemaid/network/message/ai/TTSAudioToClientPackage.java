package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.network.client.ai.TTSAudioToClientPackageProxy;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil.modLoc;

public record TTSAudioToClientPackage(int maidId, byte[] data) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TTSAudioToClientPackage> TYPE = new CustomPacketPayload.Type<>(modLoc("tts_audio_to_client"));
    public static final StreamCodec<ByteBuf, TTSAudioToClientPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            TTSAudioToClientPackage::maidId,
            ByteBufCodecs.BYTE_ARRAY,
            TTSAudioToClientPackage::data,
            TTSAudioToClientPackage::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TTSAudioToClientPackage message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> TTSAudioToClientPackageProxy.handle(message));
    }
}
