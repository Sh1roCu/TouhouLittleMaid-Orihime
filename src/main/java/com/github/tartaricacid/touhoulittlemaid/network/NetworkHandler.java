package com.github.tartaricacid.touhoulittlemaid.network;

import cn.sh1rocu.touhoulittlemaid.api.extension.IEntityAdditionalSpawnData;
import com.github.tartaricacid.touhoulittlemaid.network.message.*;
import com.github.tartaricacid.touhoulittlemaid.network.message.ai.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class NetworkHandler {
    private static void registerC2SPacket(ResourceLocation id, ServerPlayNetworking.PlayChannelHandler handler) {
        ServerPlayNetworking.registerGlobalReceiver(id, handler);
    }

    @Environment(EnvType.CLIENT)
    private static void registerS2CPacket(ResourceLocation id, ClientPlayNetworking.PlayChannelHandler handler) {
        ClientPlayNetworking.registerGlobalReceiver(id, handler);
    }

    @Environment(EnvType.CLIENT)
    public static void registerS2CPackets() {
        registerS2CPacket(IEntityAdditionalSpawnData.EXTRA_DATA_PACKET, (client, handler, buf, responseSender) -> {
            int entityId = buf.readVarInt();
            buf.retain();
            client.execute(() -> {
                Entity entity = Objects.requireNonNull(client.level).getEntity(entityId);
                if (entity instanceof IEntityAdditionalSpawnData extra) {
                    extra.readSpawnData(buf);
                }
                buf.release();
            });
        });

        registerS2CPacket(OpenChairGuiMessage.ID, OpenChairGuiMessage::handle);
        registerS2CPacket(ItemBreakMessage.ID, ItemBreakMessage::handle);
        registerS2CPacket(SpawnParticleMessage.ID, SpawnParticleMessage::handle);
        registerS2CPacket(SyncDataMessage.ID, SyncDataMessage::handle);
        registerS2CPacket(OpenBeaconGuiMessage.ID, OpenBeaconGuiMessage::handle);
        registerS2CPacket(BeaconAbsorbMessage.ID, BeaconAbsorbMessage::handle);
        registerS2CPacket(OpenSwitcherGuiMessage.ID, OpenSwitcherGuiMessage::handle);
        registerS2CPacket(SendEffectMessage.ID, SendEffectMessage::handle);
        registerS2CPacket(PlayMaidSoundMessage.ID, PlayMaidSoundMessage::handle);
        registerS2CPacket(GomokuToClientMessage.ID, GomokuToClientMessage::handle);
        registerS2CPacket(FoxScrollMessage.ID, FoxScrollMessage::handle);
        registerS2CPacket(CheckScheduleMessage.ID, CheckScheduleMessage::handle);
        registerS2CPacket(SyncMaidAreaMessage.ID, SyncMaidAreaMessage::handle);
        registerS2CPacket(CChessToClientMessage.ID, CChessToClientMessage::handle);
        registerS2CPacket(WChessToClientMessage.ID, WChessToClientMessage::handle);
        registerS2CPacket(TTSAudioToClientMessage.ID, TTSAudioToClientMessage::handle);
        // 仅安装 YSM 后才会发送此包
        registerS2CPacket(SyncYsmMaidDataMessage.ID, SyncYsmMaidDataMessage::handle);
        registerS2CPacket(TTSSystemAudioToClientMessage.ID, TTSSystemAudioToClientMessage::handle);

        registerS2CPacket(SyncFluidAmountMessage.ID, SyncFluidAmountMessage::handle);
        registerS2CPacket(OpenPlayerInventoryMessage.ID, OpenPlayerInventoryMessage::handle);
        registerS2CPacket(MaidAnimationMessage.ID, MaidAnimationMessage::handle);

        registerS2CPacket(SyncBaubleMessage.ID, SyncBaubleMessage::handle);
        registerS2CPacket(CuriosS2CUpdateMessage.ID, CuriosS2CUpdateMessage::handle);

        registerS2CPacket(SyncMaidAIDataMessage.ID, SyncMaidAIDataMessage::handle);
        registerS2CPacket(SyncAISitesMessage.ID, SyncAISitesMessage::handle);

    }

    public static void registerC2SPackets() {
        registerC2SPacket(MaidModelMessage.ID, MaidModelMessage::handle);
        registerC2SPacket(ChairModelMessage.ID, ChairModelMessage::handle);
        registerC2SPacket(MaidConfigMessage.ID, MaidConfigMessage::handle);
        registerC2SPacket(MaidTaskMessage.ID, MaidTaskMessage::handle);
        registerC2SPacket(SendNameTagMessage.ID, SendNameTagMessage::handle);
        registerC2SPacket(WirelessIOGuiMessage.ID, WirelessIOGuiMessage::handle);
        registerC2SPacket(WirelessIOSlotConfigMessage.ID, WirelessIOSlotConfigMessage::handle);
        registerC2SPacket(SetBeaconPotionMessage.ID, SetBeaconPotionMessage::handle);
        registerC2SPacket(StorageAndTakePowerMessage.ID, StorageAndTakePowerMessage::handle);
        registerC2SPacket(SetBeaconOverflowMessage.ID, SetBeaconOverflowMessage::handle);
        registerC2SPacket(SaveSwitcherDataMessage.ID, SaveSwitcherDataMessage::handle);
        registerC2SPacket(ToggleTabMessage.ID, ToggleTabMessage::handle);
        registerC2SPacket(RequestEffectMessage.ID, RequestEffectMessage::handle);
        registerC2SPacket(SetMaidSoundIdMessage.ID, SetMaidSoundIdMessage::handle);
        registerC2SPacket(GomokuToServerMessage.ID, GomokuToServerMessage::handle);
        registerC2SPacket(SetScrollData.ID, SetScrollData::handle);
        registerC2SPacket(ServantBellSetMessage.ID, ServantBellSetMessage::handle);
        registerC2SPacket(SetAttackListMessage.ID, SetAttackListMessage::handle);
        registerC2SPacket(RefreshMaidBrainMessage.ID, RefreshMaidBrainMessage::handle);
        registerC2SPacket(MaidSubConfigMessage.ID, MaidSubConfigMessage::handle);
        registerC2SPacket(CChessToServerMessage.ID, CChessToServerMessage::handle);
        registerC2SPacket(WChessToServerMessage.ID, WChessToServerMessage::handle);
        registerC2SPacket(SendUserChatMessage.ID, SendUserChatMessage::handle);
        // 仅安装 YSM 后才会发送此包
        registerC2SPacket(YsmMaidModelMessage.ID, YsmMaidModelMessage::handle);
        registerC2SPacket(SaveMaidAIDataMessage.ID, SaveMaidAIDataMessage::handle);
        registerC2SPacket(ClearMaidAIDataMessage.ID, ClearMaidAIDataMessage::handle);
        registerC2SPacket(OpenMaidGuiMessage.ID, OpenMaidGuiMessage::handle);
        registerC2SPacket(DismountMessage.ID, DismountMessage::handle);

        registerC2SPacket(OpenMaidAIChatMessage.ID, OpenMaidAIChatMessage::handle);
        registerC2SPacket(OpenAIConfigMessage.ID, OpenAIConfigMessage::handle);
        registerC2SPacket(SaveLLMSiteMessage.ID, SaveLLMSiteMessage::handle);
        registerC2SPacket(SaveTTSSiteMessage.ID, SaveTTSSiteMessage::handle);
    }

    public static void sendToTrackingEntity(Entity entity, ResourceLocation channelName, FriendlyByteBuf buf) {
        for (ServerPlayer target : PlayerLookup.tracking(entity)) {
            ServerPlayNetworking.send(target, channelName, buf);
        }
    }

    public static void sendToNearby(Level world, BlockPos pos, ResourceLocation channelName, FriendlyByteBuf buf) {
        if (world instanceof ServerLevel ws) {
            for (ServerPlayer target : PlayerLookup.around(ws, new Vec3i(pos.getX(), pos.getY(), pos.getZ()), 192)) {
                ServerPlayNetworking.send(target, channelName, buf);
            }
        }
    }

    public static void sendToNearby(Entity entity, ResourceLocation channelName, FriendlyByteBuf buf) {
        if (entity.level instanceof ServerLevel) {
            ServerLevel ws = (ServerLevel) entity.level();
            BlockPos pos = entity.blockPosition();
            for (ServerPlayer target : PlayerLookup.around(ws, new Vec3i(pos.getX(), pos.getY(), pos.getZ()), 192)) {
                ServerPlayNetworking.send(target, channelName, buf);
            }
        }
    }

    public static void sendToNearby(Entity entity, ResourceLocation channelName, FriendlyByteBuf buf, int distance) {
        if (entity.level instanceof ServerLevel serverLevel) {
            BlockPos pos = entity.blockPosition();
            for (ServerPlayer target : PlayerLookup.around(serverLevel, new Vec3i(pos.getX(), pos.getY(), pos.getZ()), distance)) {
                ServerPlayNetworking.send(target, channelName, buf);
            }
        }
    }
}
