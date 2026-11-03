package cn.sh1rocu.touhoulittlemaid.util.neoforge.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;

public interface IEntityExtension {
    default void tlm$sendPairingData(ServerPlayer serverPlayer, Consumer<CustomPacketPayload> bundleBuilder) {
        if (this instanceof IEntityWithComplexSpawn) {
            bundleBuilder.accept(new AdvancedAddEntityPayload((Entity) this));
        }
    }
}
