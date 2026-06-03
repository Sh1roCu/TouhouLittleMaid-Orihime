package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.item.ItemWirelessIO;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil.modLoc;

public record WirelessIOSlotConfigPackage(List<Boolean> configData) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<WirelessIOSlotConfigPackage> TYPE = new CustomPacketPayload.Type<>(modLoc("wireless_slot_config"));
    public static final StreamCodec<ByteBuf, WirelessIOSlotConfigPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL.apply(ByteBufCodecs.list()),
            WirelessIOSlotConfigPackage::configData,
            WirelessIOSlotConfigPackage::new
    );
    private static final List<Boolean> EMPTY = new ArrayList<>();

    public WirelessIOSlotConfigPackage() {
        this(EMPTY);
    }

    public static void handle(WirelessIOSlotConfigPackage message, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayer sender = context.player();
            ItemStack handItem = sender.getMainHandItem();
            if (handItem.getItem() == InitItems.WIRELESS_IO) {
                if (!message.configData.isEmpty()) {
                    ItemWirelessIO.setSlotConfig(handItem, message.configData);
                }
                sender.openMenu((ItemWirelessIO) handItem.getItem());
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
