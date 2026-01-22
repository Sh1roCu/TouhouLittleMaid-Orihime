package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.compat.trinkets.TrinketsCompat;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.getResourceLocation;

public class CuriosS2CUpdateMessage {
    public static final ResourceLocation ID = getResourceLocation("curios_s2c_update");

    public static FriendlyByteBuf encode(int page) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(page);
        return buf;
    }

    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var page = buf.readVarInt();
        client.execute(() -> TrinketsCompat.clientUpdatePage(page));
    }
}