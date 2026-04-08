package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.ai.manager.entity.MaidAIChatSerializable;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.getResourceLocation;

public class SaveMaidAIDataMessage {
    public static final ResourceLocation ID = getResourceLocation("save_maid_ai_data");

    public static FriendlyByteBuf encode(int entityId, MaidAIChatSerializable data) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(entityId);
        data.encode(buf);
        return buf;
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int entityId = buf.readInt();
        MaidAIChatSerializable data = new MaidAIChatSerializable();
        data.decode(buf);
        server.execute(() -> handle(entityId, data, player));
    }

    private static void handle(int entityId, MaidAIChatSerializable data, ServerPlayer player) {
        Entity entity = player.level.getEntity(entityId);
        if (entity instanceof EntityMaid maid && maid.isOwnedBy(player)) {
            maid.getAiChatManager().copyFrom(data);
        }
    }
}
