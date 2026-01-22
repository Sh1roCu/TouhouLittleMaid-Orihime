package com.github.tartaricacid.touhoulittlemaid.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.network.message.SyncBaubleMessage;
import com.github.tartaricacid.touhoulittlemaid.network.message.SyncYsmMaidDataMessage;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class MaidTrackEvent {
    public static void onTrackingPlayer(Entity target, Player player) {
        if (target instanceof EntityMaid maid && player instanceof ServerPlayer serverPlayer) {
            // 如果是 ysm 模型，那么同步 ysm 模型信息
            if (maid.isYsmModel()) {
                ServerPlayNetworking.send(serverPlayer, SyncYsmMaidDataMessage.ID, SyncYsmMaidDataMessage.encode(maid.getId(), maid.rouletteAnim, maid.rouletteAnimPlaying, maid.roamingVars));
            }

            // 如果包含需要同步到客户端的饰品信息，那么同步
            var syncClientBauble = maid.getMaidBauble().getSyncClientBauble(maid);
            if (!syncClientBauble.isEmpty()) {
                ServerPlayNetworking.send(serverPlayer, SyncBaubleMessage.ID, SyncBaubleMessage.fullSync(maid.getId(), syncClientBauble));
            }
        }
    }
}
