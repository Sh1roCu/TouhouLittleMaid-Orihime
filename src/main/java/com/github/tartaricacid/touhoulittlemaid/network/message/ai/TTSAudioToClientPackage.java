package com.github.tartaricacid.touhoulittlemaid.network.message.ai;

import com.github.tartaricacid.touhoulittlemaid.client.sound.data.MaidAISoundInstance;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public record TTSAudioToClientPackage(int maidId, byte[] data) implements CustomPacketPayload {
    public static final Type<TTSAudioToClientPackage> TYPE = new Type<>(getResourceLocation("tts_audio_to_client"));
    public static final StreamCodec<ByteBuf, TTSAudioToClientPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            TTSAudioToClientPackage::maidId,
            ByteBufCodecs.BYTE_ARRAY,
            TTSAudioToClientPackage::data,
            TTSAudioToClientPackage::new
    );

    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Environment(EnvType.CLIENT)
    public static void handle(TTSAudioToClientPackage message, ClientPlayNetworking.Context context) {
        context.client().execute(() -> onHandle(message));
    }

    @Environment(EnvType.CLIENT)
    private static void onHandle(TTSAudioToClientPackage message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        Entity entity = mc.level.getEntity(message.maidId);
        if (!(entity instanceof EntityMaid maid)) {
            return;
        }
        if (maid.isAlive()) {
            MaidAISoundInstance instance = new MaidAISoundInstance(maid, message.data);
            Minecraft.getInstance().getSoundManager().play(instance);
        }
    }
}
