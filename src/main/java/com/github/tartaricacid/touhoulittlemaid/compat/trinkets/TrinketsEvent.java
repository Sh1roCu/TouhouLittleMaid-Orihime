package com.github.tartaricacid.touhoulittlemaid.compat.trinkets;


import com.github.tartaricacid.touhoulittlemaid.compat.trinkets.event.SlotModifiersUpdatedEvent;
import com.github.tartaricacid.touhoulittlemaid.compat.trinkets.menu.CuriosContainer;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class TrinketsEvent {
    /**
     * 当添加可以修改槽位数量的饰品时，重置饰品容器
     */
    public static void onSlotUpdate(SlotModifiersUpdatedEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof EntityMaid maid && maid.getOwner() instanceof Player player) {
            if (player.containerMenu instanceof CuriosContainer container) {
                container.resetPage(player);

                // 客户端需要再次更新，否则可能会触发增减槽位不更新问题
                if (entity.level.isClientSide) {
                    TrinketsCompat.clientResetPage();
                }
            }
        }
    }
}