package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class OpenMaidAIChatMessage {
    public static final ResourceLocation ID = new ResourceLocation(TouhouLittleMaid.MOD_ID, "open_maid_ai_chat");

    public static FriendlyByteBuf encode(int entityId) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(entityId);
        return buf;
    }

    public static void handle(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var entityId = buf.readVarInt();
        server.execute(() -> handle(entityId, sender));
    }

    private static void handle(int entityId, @Nullable ServerPlayer player) {
        if (player == null) {
            return;
        }
        Entity entity = player.level.getEntity(entityId);
        if (entity instanceof EntityMaid maid) {
            // 发送同步信息（包含 Token 用量）
            ServerPlayNetworking.send(player, SyncMaidAIDataMessage.ID, SyncMaidAIDataMessage.encode(new SyncMaidAIDataMessage(maid, player)));
        }
    }
}