package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.ai.manager.site.AvailableSites;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSConfig;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSSite;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSSystemServices;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.getResourceLocation;

public class TTSSystemAudioToClientMessage {
    public static final ResourceLocation ID = getResourceLocation("tts_system_audio_to_client");

    public static FriendlyByteBuf encode(String siteName, String chatText, TTSConfig config, TTSSystemServices services) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUtf(siteName);
        services.writeToNetwork(chatText, config, buf);
        return buf;
    }

    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        String siteName = buf.readUtf();
        TTSSite ttsSite = AvailableSites.getTTSSite(siteName);
        if (ttsSite.client() instanceof TTSSystemServices services) {
            Pair<String, TTSConfig> pair = services.readFromNetwork(buf);
            client.execute(() -> onHandle(siteName, pair.getLeft(), pair.getRight(), services));
        }
    }

    @Environment(EnvType.CLIENT)
    private static void onHandle(String siteName, String chatText, TTSConfig config, TTSSystemServices services) {
        TTSSite ttsSite = AvailableSites.getTTSSite(siteName);
        if (ttsSite == null || !ttsSite.enabled()) {
            return;
        }
        ttsSite.client().play(chatText, config, null);
    }
}
