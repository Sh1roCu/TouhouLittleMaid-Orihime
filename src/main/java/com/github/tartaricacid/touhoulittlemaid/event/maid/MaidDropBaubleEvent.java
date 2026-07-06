package com.github.tartaricacid.touhoulittlemaid.event.maid;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidFavorabilityLevelChangeEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.BaubleItemHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

public class MaidDropBaubleEvent {
    /**
     * 当女仆好感度降级时，需要移除超出上限的饰品
     */
    public static void onFavorabilityLevelChange(MaidFavorabilityLevelChangeEvent event) {
        int newLevel = event.getNewLevel();
        EntityMaid maid = event.getMaid();
        if (!(maid.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        // 3 级：不需要掉落
        if (newLevel >= 3) {
            return;
        }
        // 2 级：20 个格子
        // 0 和 1 级：10 个格子
        int startIndex = newLevel <= 1 ? 10 : 20;
        BaubleItemHandler maidBauble = maid.getMaidBauble();
        try (Transaction tx = Transaction.openOuter()) {
            for (int i = startIndex; i < maidBauble.size(); i++) {
                ItemVariant resource = maidBauble.getResource(i);
                if (resource.isBlank()){
                    continue;
                }
                int extract = maidBauble.extract(i, resource, 1, tx);
                if (extract == 0) {
                    continue;
                }
                ItemStack drop = resource.toStack(extract);
                maid.spawnAtLocation(serverLevel, drop);
            }
            tx.commit();
        }
    }
}
