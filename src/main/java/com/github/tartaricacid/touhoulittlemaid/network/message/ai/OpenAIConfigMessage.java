package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.ai.manager.site.AvailableSites;
import com.github.tartaricacid.touhoulittlemaid.util.GameModeUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class OpenAIConfigMessage {
    public static final ResourceLocation ID = new ResourceLocation(TouhouLittleMaid.MOD_ID, "open_ai_config");
    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    @Environment(EnvType.CLIENT)
    public static void sendToServer() {
        ClientPlayNetworking.send(OpenAIConfigMessage.ID, DUMMY);
    }

    public static void handle(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> onHandle(sender));
    }

    private static void onHandle(@Nullable ServerPlayer player) {
        if (player == null) {
            return;
        }

        // 是否发送站点数据
        if (GameModeUtil.canEditSite(player)) {
            SyncAISitesMessage msg = new SyncAISitesMessage(AvailableSites.LLM_SITES, AvailableSites.TTS_SITES, false);
            ServerPlayNetworking.send(player, SyncAISitesMessage.ID, SyncAISitesMessage.encode(msg));
        } else {
            // 否则发送一个空的站点数据
            SyncAISitesMessage msg = new SyncAISitesMessage(Collections.emptyMap(), Collections.emptyMap(), true);
            ServerPlayNetworking.send(player, SyncAISitesMessage.ID, SyncAISitesMessage.encode(msg));
        }
    }
}