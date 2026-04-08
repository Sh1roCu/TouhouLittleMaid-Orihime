package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.client.sound.data.MaidAISoundInstance;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.getResourceLocation;

public class TTSAudioToClientMessage {
    public static final ResourceLocation ID = getResourceLocation("tts_audio_to_client");

    public static FriendlyByteBuf encode(int maidId, byte[] data) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(maidId);
        buf.writeByteArray(data);
        return buf;
    }

    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int maidId = buf.readVarInt();
        byte[] data = buf.readByteArray();
        client.execute(() -> onHandle(maidId, data));
    }

    @Environment(EnvType.CLIENT)
    private static void onHandle(int maidId, byte[] data) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        Entity entity = mc.level.getEntity(maidId);
        if (!(entity instanceof EntityMaid maid)) {
            return;
        }
        if (maid.isAlive()) {
            MaidAISoundInstance instance = new MaidAISoundInstance(maid, data);
            Minecraft.getInstance().getSoundManager().play(instance);
        }
    }
}
