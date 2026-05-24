package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.client.gui.block.ModelSwitcherGui;
import com.github.tartaricacid.touhoulittlemaid.tileentity.TileEntityModelSwitcher;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public record OpenSwitcherGuiPackage(BlockPos pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenSwitcherGuiPackage> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("open_switcher_gui"));
    public static final StreamCodec<ByteBuf, OpenSwitcherGuiPackage> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            OpenSwitcherGuiPackage::pos,
            OpenSwitcherGuiPackage::new
    );

    public static void handle(OpenSwitcherGuiPackage message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> handleOpenGui(message));
    }

    @Environment(EnvType.CLIENT)
    private static void handleOpenGui(OpenSwitcherGuiPackage message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        BlockEntity te = mc.level.getBlockEntity(message.pos);
        if (mc.player != null && mc.player.isAlive() && te instanceof TileEntityModelSwitcher) {
            mc.setScreen(new ModelSwitcherGui((TileEntityModelSwitcher) te));
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
