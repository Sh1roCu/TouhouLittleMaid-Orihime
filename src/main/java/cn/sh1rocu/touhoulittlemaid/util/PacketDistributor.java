package cn.sh1rocu.touhoulittlemaid.util;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PacketDistributor {
    private PacketDistributor() {
    }

    public static void sendToServer(CustomPacketPayload payload) {
        ClientPlayNetworking.send(payload);
    }

    public static void sendToPlayer(ServerPlayer serverPlayer, CustomPacketPayload payload) {
        ServerPlayNetworking.send(serverPlayer, payload);
    }

    public static void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload toSend) {
        for (ServerPlayer target : PlayerLookup.tracking(entity)) {
            sendToPlayer(target, toSend);
        }
    }

    public static void sendToPlayersTrackingEntityAndSelf(Entity entity, CustomPacketPayload payload) {
        if (entity instanceof ServerPlayer player) {
            sendToPlayer(player, payload);
        }

        sendToPlayersTrackingEntity(entity, payload);
    }

    public static void sendToAllPlayers(CustomPacketPayload payload, MinecraftServer server) {
        for (ServerPlayer player : PlayerLookup.all(server)) {
            sendToPlayer(player, payload);
        }

    }
}
