package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.getResourceLocation;

public class ClearMaidAIDataMessage {
    private static final int ALL_MSG_INDEX = -1;
    public static final ResourceLocation ID = getResourceLocation("clear_maid_ai_data");


    public static FriendlyByteBuf encode(int entityId) {
        return encode(entityId, ALL_MSG_INDEX);
    }

    public static FriendlyByteBuf encode(int entityId, int msgIndex) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(entityId);
        buf.writeVarInt(msgIndex);
        return buf;
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int entityId = buf.readVarInt();
        int msgIndex = buf.readVarInt();
        server.execute(() -> handle(entityId, msgIndex, player));
    }

    private static void handle(int entityId, int msgIndex, @Nullable ServerPlayer player) {
        if (player == null) {
            return;
        }
        Entity entity = player.level.getEntity(entityId);
        if (entity instanceof EntityMaid maid && maid.isOwnedBy(player)) {
            if (msgIndex == ALL_MSG_INDEX) {
                maid.getAiChatManager().clearAllChatMemory();
            }
        }
    }
}