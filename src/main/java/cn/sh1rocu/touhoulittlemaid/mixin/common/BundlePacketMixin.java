package cn.sh1rocu.touhoulittlemaid.mixin.common;

import net.minecraft.network.protocol.BundlePacket;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * this is needed for {@link cn.sh1rocu.touhoulittlemaid.util.forge.network.IEntityWithComplexSpawn} to work properly on dedicated servers, since it ends up nesting bundle packets.
 */

// PortingLib
@Mixin(BundlePacket.class)
public class BundlePacketMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Iterable<Packet<?>> tlm$flattenPackets(Iterable<Packet<?>> packets) {
        List<Packet<?>> list = new ArrayList<>();
        tlm$recursivelyCollectBundledPackets(packets, list);
        return list;
    }

    @Unique
    private static void tlm$recursivelyCollectBundledPackets(Iterable<Packet<?>> packets, List<Packet<?>> list) {
        for (Packet<?> packet : packets) {
            if (packet instanceof BundlePacket<?> bundle) {
                //noinspection unchecked,rawtypes
                tlm$recursivelyCollectBundledPackets((Iterable) bundle.subPackets(), list);
            } else {
                list.add(packet);
            }
        }
    }
}