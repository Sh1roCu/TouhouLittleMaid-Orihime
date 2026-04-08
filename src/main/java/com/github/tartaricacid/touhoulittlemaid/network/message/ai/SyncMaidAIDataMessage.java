package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.ai.manager.site.ClientAvailableSitesSync;
import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.AIChatScreen;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.AIConfig;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitDataAttachment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Objects;

public record SyncMaidAIDataMessage(int entityId, CompoundTag configData, int currentTokens, int maxTokens) {
    public static final ResourceLocation ID = new ResourceLocation(TouhouLittleMaid.MOD_ID, "sync_maid_ai_data");

    public SyncMaidAIDataMessage(EntityMaid maid, ServerPlayer player) {
        this(maid.getId(), maid.getAiChatManager().writeToTag(new CompoundTag()),
                player.getAttachedOrCreate(InitDataAttachment.CHAT_TOKENS).get(),
                AIConfig.MAX_TOKENS_PER_PLAYER.get()
        );
    }

    public static FriendlyByteBuf encode(SyncMaidAIDataMessage message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(message.entityId);
        buf.writeNbt(message.configData);
        ClientAvailableSitesSync.writeToNetwork(buf);
        buf.writeVarInt(message.currentTokens);
        buf.writeVarInt(message.maxTokens);
        return buf;
    }

    public static SyncMaidAIDataMessage decode(FriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        CompoundTag configData = Objects.requireNonNullElse(buf.readNbt(), new CompoundTag());
        ClientAvailableSitesSync.readFromNetwork(buf);
        int currentTokens = buf.readVarInt();
        int maxTokens = buf.readVarInt();
        return new SyncMaidAIDataMessage(entityId, configData, currentTokens, maxTokens);
    }

    @Environment(EnvType.CLIENT)
    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var message = decode(buf);
        client.execute(() -> handle(message));
    }

    @Environment(EnvType.CLIENT)
    private static void handle(SyncMaidAIDataMessage message) {
        ClientLevel level = Minecraft.getInstance().level;
        LocalPlayer player = Minecraft.getInstance().player;
        if (level == null || player == null) {
            Minecraft.getInstance().setScreen(null);
            return;
        }
        Entity entity = level.getEntity(message.entityId);
        if (entity instanceof EntityMaid maid) {
            maid.getAiChatManager().readFromTag(message.configData);

            AIChatScreen chatScreen = new AIChatScreen(maid);
            chatScreen.updateTokens(message.currentTokens, message.maxTokens);
            Minecraft.getInstance().setScreen(chatScreen);
        } else {
            Minecraft.getInstance().setScreen(null);
        }
    }
}