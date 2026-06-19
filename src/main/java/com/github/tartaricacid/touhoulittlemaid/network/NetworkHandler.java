package com.github.tartaricacid.touhoulittlemaid.network;

import cn.sh1rocu.touhoulittlemaid.util.neoforge.network.AdvancedAddEntityPayload;
import com.github.tartaricacid.touhoulittlemaid.network.message.*;
import com.github.tartaricacid.touhoulittlemaid.network.message.ai.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class NetworkHandler {
    public static void registerPackets() {
        registerC2SPackets();
        registerS2CPackets();
    }

    private static <T extends CustomPacketPayload> void registerC2SPacket(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, ServerPlayNetworking.PlayPayloadHandler<T> handler) {
        PayloadTypeRegistry.serverboundPlay().register(type, streamCodec);
        ServerPlayNetworking.registerGlobalReceiver(type, handler);
    }

    private static <T extends CustomPacketPayload> void registerS2CPacket(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        PayloadTypeRegistry.clientboundPlay().register(type, streamCodec);
    }

    public static <T extends CustomPacketPayload> void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(OpenChairGuiPackage.TYPE, OpenChairGuiPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(ItemBreakPackage.TYPE, ItemBreakPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(SpawnParticlePackage.TYPE, SpawnParticlePackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(SyncDataPackage.TYPE, SyncDataPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(OpenBeaconGuiPackage.TYPE, OpenBeaconGuiPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(BeaconAbsorbPackage.TYPE, BeaconAbsorbPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(OpenSwitcherGuiPackage.TYPE, OpenSwitcherGuiPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(SendEffectPackage.TYPE, SendEffectPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(PlayMaidSoundPackage.TYPE, PlayMaidSoundPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(GomokuClientPackage.TYPE, GomokuClientPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(FoxScrollPackage.TYPE, FoxScrollPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(CheckSchedulePosPacket.TYPE, CheckSchedulePosPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(SyncMaidAreaPackage.TYPE, SyncMaidAreaPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(CChessToClientPackage.TYPE, CChessToClientPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(WChessToClientPackage.TYPE, WChessToClientPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(TTSAudioToClientPackage.TYPE, TTSAudioToClientPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(TTSSystemAudioToClientPackage.TYPE, TTSSystemAudioToClientPackage::handle);

        ClientPlayNetworking.registerGlobalReceiver(AdvancedAddEntityPayload.TYPE, AdvancedAddEntityPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(OpenPlayerInventoryPackage.TYPE, OpenPlayerInventoryPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(MaidAnimationPackage.TYPE, MaidAnimationPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(PlayMaidSoundAtPosPackage.TYPE, PlayMaidSoundAtPosPackage::handle);
        ClientPlayNetworking.registerGlobalReceiver(CuriosS2CUpdatePacket.TYPE, CuriosS2CUpdatePacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(SyncBaublePackage.TYPE, SyncBaublePackage::handle);

        ClientPlayNetworking.registerGlobalReceiver(SyncAISitesPacket.TYPE, SyncAISitesPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(SyncMaidAIDataPacket.TYPE, SyncMaidAIDataPacket::handle);
    }

    public static void registerS2CPackets() {
        registerS2CPacket(OpenChairGuiPackage.TYPE, OpenChairGuiPackage.STREAM_CODEC);
        registerS2CPacket(ItemBreakPackage.TYPE, ItemBreakPackage.STREAM_CODEC);
        registerS2CPacket(SpawnParticlePackage.TYPE, SpawnParticlePackage.STREAM_CODEC);
        registerS2CPacket(SyncDataPackage.TYPE, SyncDataPackage.STREAM_CODEC);
        registerS2CPacket(OpenBeaconGuiPackage.TYPE, OpenBeaconGuiPackage.STREAM_CODEC);
        registerS2CPacket(BeaconAbsorbPackage.TYPE, BeaconAbsorbPackage.STREAM_CODEC);
        registerS2CPacket(OpenSwitcherGuiPackage.TYPE, OpenSwitcherGuiPackage.STREAM_CODEC);
        registerS2CPacket(SendEffectPackage.TYPE, SendEffectPackage.STREAM_CODEC);
        registerS2CPacket(PlayMaidSoundPackage.TYPE, PlayMaidSoundPackage.STREAM_CODEC);
        registerS2CPacket(GomokuClientPackage.TYPE, GomokuClientPackage.STREAM_CODEC);
        registerS2CPacket(FoxScrollPackage.TYPE, FoxScrollPackage.STREAM_CODEC);
        registerS2CPacket(CheckSchedulePosPacket.TYPE, CheckSchedulePosPacket.STREAM_CODEC);
        registerS2CPacket(SyncMaidAreaPackage.TYPE, SyncMaidAreaPackage.STREAM_CODEC);
        registerS2CPacket(CChessToClientPackage.TYPE, CChessToClientPackage.STREAM_CODEC);
        registerS2CPacket(WChessToClientPackage.TYPE, WChessToClientPackage.STREAM_CODEC);
        registerS2CPacket(TTSAudioToClientPackage.TYPE, TTSAudioToClientPackage.STREAM_CODEC);
        registerS2CPacket(TTSSystemAudioToClientPackage.TYPE, TTSSystemAudioToClientPackage.STREAM_CODEC);

        registerS2CPacket(AdvancedAddEntityPayload.TYPE, AdvancedAddEntityPayload.STREAM_CODEC);
        registerS2CPacket(OpenPlayerInventoryPackage.TYPE, OpenPlayerInventoryPackage.STREAM_CODEC);
        registerS2CPacket(MaidAnimationPackage.TYPE, MaidAnimationPackage.STREAM_CODEC);
        registerS2CPacket(PlayMaidSoundAtPosPackage.TYPE, PlayMaidSoundAtPosPackage.STREAM_CODEC);
        registerS2CPacket(CuriosS2CUpdatePacket.TYPE, CuriosS2CUpdatePacket.STREAM_CODEC);
        registerS2CPacket(SyncBaublePackage.TYPE, SyncBaublePackage.STREAM_CODEC);

        registerS2CPacket(SyncAISitesPacket.TYPE, SyncAISitesPacket.STREAM_CODEC);
        registerS2CPacket(SyncMaidAIDataPacket.TYPE, SyncMaidAIDataPacket.STREAM_CODEC);
    }

    public static void registerC2SPackets() {
        registerC2SPacket(MaidModelPackage.TYPE, MaidModelPackage.STREAM_CODEC, MaidModelPackage::handle);
        registerC2SPacket(ChairModelPackage.TYPE, ChairModelPackage.STREAM_CODEC, ChairModelPackage::handle);
        registerC2SPacket(MaidConfigPackage.TYPE, MaidConfigPackage.STREAM_CODEC, MaidConfigPackage::handle);
        registerC2SPacket(MaidTaskPackage.TYPE, MaidTaskPackage.STREAM_CODEC, MaidTaskPackage::handle);
        registerC2SPacket(SendNameTagPackage.TYPE, SendNameTagPackage.STREAM_CODEC, SendNameTagPackage::handle);
        registerC2SPacket(WirelessIOGuiPackage.TYPE, WirelessIOGuiPackage.STREAM_CODEC, WirelessIOGuiPackage::handle);
        registerC2SPacket(WirelessIOFilterSlotPackage.TYPE, WirelessIOFilterSlotPackage.STREAM_CODEC, WirelessIOFilterSlotPackage::handle);
        registerC2SPacket(WirelessIOSlotConfigPackage.TYPE, WirelessIOSlotConfigPackage.STREAM_CODEC, WirelessIOSlotConfigPackage::handle);
        registerC2SPacket(SetBeaconPotionPackage.TYPE, SetBeaconPotionPackage.STREAM_CODEC, SetBeaconPotionPackage::handle);
        registerC2SPacket(StorageAndTakePowerPackage.TYPE, StorageAndTakePowerPackage.STREAM_CODEC, StorageAndTakePowerPackage::handle);
        registerC2SPacket(SetBeaconOverflowPackage.TYPE, SetBeaconOverflowPackage.STREAM_CODEC, SetBeaconOverflowPackage::handle);
        registerC2SPacket(SaveSwitcherDataPackage.TYPE, SaveSwitcherDataPackage.STREAM_CODEC, SaveSwitcherDataPackage::handle);
        registerC2SPacket(ToggleTabPackage.TYPE, ToggleTabPackage.STREAM_CODEC, ToggleTabPackage::handle);
        registerC2SPacket(RequestEffectPackage.TYPE, RequestEffectPackage.STREAM_CODEC, RequestEffectPackage::handle);
        registerC2SPacket(SetMaidSoundIdPackage.TYPE, SetMaidSoundIdPackage.STREAM_CODEC, SetMaidSoundIdPackage::handle);
        registerC2SPacket(GomokuServerPackage.TYPE, GomokuServerPackage.STREAM_CODEC, GomokuServerPackage::handle);
        registerC2SPacket(SetScrollPackage.TYPE, SetScrollPackage.STREAM_CODEC, SetScrollPackage::handle);
        registerC2SPacket(ServantBellSetPackage.TYPE, ServantBellSetPackage.STREAM_CODEC, ServantBellSetPackage::handle);
        registerC2SPacket(SetAttackListPackage.TYPE, SetAttackListPackage.STREAM_CODEC, SetAttackListPackage::handle);
        registerC2SPacket(RefreshMaidBrainPackage.TYPE, RefreshMaidBrainPackage.STREAM_CODEC, RefreshMaidBrainPackage::handle);
        registerC2SPacket(MaidSubConfigPackage.TYPE, MaidSubConfigPackage.STREAM_CODEC, MaidSubConfigPackage::handle);
        registerC2SPacket(CChessToServerPackage.TYPE, CChessToServerPackage.STREAM_CODEC, CChessToServerPackage::handle);
        registerC2SPacket(WChessToServerPackage.TYPE, WChessToServerPackage.STREAM_CODEC, WChessToServerPackage::handle);
        registerC2SPacket(SendUserChatPackage.TYPE, SendUserChatPackage.STREAM_CODEC, SendUserChatPackage::handle);
        registerC2SPacket(SaveMaidAIDataPackage.TYPE, SaveMaidAIDataPackage.STREAM_CODEC, SaveMaidAIDataPackage::handle);
        registerC2SPacket(ClearMaidAIDataPacket.TYPE, ClearMaidAIDataPacket.STREAM_CODEC, ClearMaidAIDataPacket::handle);
        registerC2SPacket(OpenMaidGuiPackage.TYPE, OpenMaidGuiPackage.STREAM_CODEC, OpenMaidGuiPackage::handle);
        registerC2SPacket(DismountPackage.TYPE, DismountPackage.STREAM_CODEC, DismountPackage::handle);

        registerC2SPacket(OpenAIConfigPacket.TYPE, OpenAIConfigPacket.STREAM_CODEC, OpenAIConfigPacket::handle);
        registerC2SPacket(OpenMaidAIChatPacket.TYPE, OpenMaidAIChatPacket.STREAM_CODEC, OpenMaidAIChatPacket::handle);
        registerC2SPacket(SaveLLMSitePacket.TYPE, SaveLLMSitePacket.STREAM_CODEC, SaveLLMSitePacket::handle);
        registerC2SPacket(SaveTTSSitePacket.TYPE, SaveTTSSitePacket.STREAM_CODEC, SaveTTSSitePacket::handle);
    }

    public static void sendToNearby(Entity entity, CustomPacketPayload toSend) {
        if (entity.level instanceof ServerLevel serverLevel) {
            for (ServerPlayer target : PlayerLookup.tracking(serverLevel, entity.blockPosition())) {
                ServerPlayNetworking.send(target, toSend);
            }
        }
    }

    public static void sendToNearby(Entity entity, CustomPacketPayload toSend, int distance) {
        if (entity.level instanceof ServerLevel serverLevel) {
            BlockPos pos = entity.blockPosition();
            for (ServerPlayer target : PlayerLookup.around(serverLevel, new Vec3i(pos.getX(), pos.getY(), pos.getZ()), distance)) {
                ServerPlayNetworking.send(target, toSend);
            }
        }
    }

    public static void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload toSend) {
        if (entity.level instanceof ServerLevel) {
            for (ServerPlayer target : PlayerLookup.tracking(entity)) {
                ServerPlayNetworking.send(target, toSend);
            }
        }
    }
}
