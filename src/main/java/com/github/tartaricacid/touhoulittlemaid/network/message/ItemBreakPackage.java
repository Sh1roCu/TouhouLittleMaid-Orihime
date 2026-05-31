package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.network.client.ItemBreakPackageProxy;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public record ItemBreakPackage(int id, ItemStack item) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ItemBreakPackage> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("item_break"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemBreakPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ItemBreakPackage::id,
            ItemStack.STREAM_CODEC,
            ItemBreakPackage::item,
            ItemBreakPackage::new
    );

    public static void handle(ItemBreakPackage message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> ItemBreakPackageProxy.handle(message));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
