package com.github.tartaricacid.touhoulittlemaid.event;

import com.github.tartaricacid.touhoulittlemaid.config.subconfig.MiscConfig;
import com.github.tartaricacid.touhoulittlemaid.data.MaidNumAttachment;
import com.github.tartaricacid.touhoulittlemaid.data.PowerAttachment;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;

import static com.github.tartaricacid.touhoulittlemaid.init.InitDataAttachment.MAID_NUM;
import static com.github.tartaricacid.touhoulittlemaid.init.InitDataAttachment.POWER_NUM;

public class EntityDeathEvent {
    public static void onEntityDeath() {
        ServerLivingEntityEvents.AFTER_DEATH.register((target, damageSource) -> {
            Entity causingEntity = damageSource.getEntity();
            if (causingEntity instanceof EntityMaid maid) {
                maid.getKillRecordManager().onTargetDeath(maid, target);
            }
        });
    }

    public static void onPlayerCloned() {
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            boolean wasDeath = !alive;
            Level level = newPlayer.level;
            if (!(level instanceof ServerLevel serverLevel)) {
                return;
            }

            boolean isKeep = serverLevel.getGameRules().get(GameRules.KEEP_INVENTORY);

            PowerAttachment power = oldPlayer.getAttachedOrCreate(POWER_NUM, () -> new PowerAttachment(0));
            MaidNumAttachment maidNum = oldPlayer.getAttachedOrCreate(MAID_NUM, () -> new MaidNumAttachment(0));
            if (wasDeath && !isKeep) {
                power.min(MiscConfig.PLAYER_DEATH_LOSS_POWER_POINT.get().floatValue());
            }
            newPlayer.setAttached(POWER_NUM, power);
            newPlayer.setAttached(MAID_NUM, maidNum);
        });
    }
}
