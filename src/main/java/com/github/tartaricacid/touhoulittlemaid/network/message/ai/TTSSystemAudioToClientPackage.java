package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.ai.manager.site.AvailableSites;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSConfig;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSSite;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSSystemServices;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public record TTSSystemAudioToClientPackage(String siteName, String chatText, TTSConfig config,
                                            TTSSystemServices services) implements CustomPacketPayload {
    public static final Type<TTSSystemAudioToClientPackage> TYPE = new Type<>(getResourceLocation("tts_system_audio_to_client"));
    public static final StreamCodec<ByteBuf, TTSSystemAudioToClientPackage> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public TTSSystemAudioToClientPackage decode(ByteBuf byteBuf) {
            FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);
            String siteName = buf.readUtf();
            TTSSite ttsSite = AvailableSites.getTTSSite(siteName);
            if (ttsSite.client() instanceof TTSSystemServices services) {
                Pair<String, TTSConfig> pair = services.readFromNetwork(buf);
                return new TTSSystemAudioToClientPackage(siteName, pair.getLeft(), pair.getRight(), services);
            }
            throw new IllegalArgumentException("Invalid TTS site: " + siteName);
        }

        @Override
        public void encode(ByteBuf byteBuf, TTSSystemAudioToClientPackage message) {
            FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);
            buf.writeUtf(message.siteName);
            message.services.writeToNetwork(message.chatText, message.config, buf);
        }
    };

    @Environment(EnvType.CLIENT)
    public static void handle(TTSSystemAudioToClientPackage message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> onHandle(message));
    }

    @Environment(EnvType.CLIENT)
    private static void onHandle(TTSSystemAudioToClientPackage message) {
        TTSSite ttsSite = AvailableSites.getTTSSite(message.siteName);
        if (ttsSite == null || !ttsSite.enabled()) {
            return;
        }
        ttsSite.client().play(message.chatText, message.config, null);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
