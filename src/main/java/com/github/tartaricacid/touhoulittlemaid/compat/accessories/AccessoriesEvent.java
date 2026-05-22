package com.github.tartaricacid.touhoulittlemaid.compat.accessories;


import com.github.tartaricacid.touhoulittlemaid.api.event.MaidTombstoneEvent;
import com.github.tartaricacid.touhoulittlemaid.compat.accessories.menu.CuriosContainer;
import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.MaidContainerCache;
import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityTombstone;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class AccessoriesEvent {
    /**
     * 当添加可以修改槽位数量的饰品时，重置饰品容器
     */
    public static void onSlotUpdate(LivingEntity entity, AccessoriesCapability capability, Map<AccessoriesContainer, Boolean> changedContainers) {
        if (entity instanceof EntityMaid maid && maid.getOwner() instanceof Player player) {
            MaidContainerCache.invalidate(maid);
            if (player.containerMenu instanceof CuriosContainer container) {
                container.resetPage(player);

                // 客户端需要再次更新，否则可能会触发增减槽位不更新问题
                if (entity.level.isClientSide) {
                    AccessoriesCompat.clientResetPage();
                }
            }
        }
    }

    /**
     * 当女仆墓碑生成时，将 Curios 饰品从女仆身上转移到墓碑中
     * Curios 后续的掉落事件仍然会触发，但此时女仆身上已经没有饰品了
     */
    public static void onMaidTombstone(MaidTombstoneEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (!AccessoriesCompat.isLoadedOrEnable()) {
            return;
        }

        EntityTombstone tombstone = event.getTombstone();
        EntityMaid maid = event.getMaid();

        AccessoriesCapability.getOptionally(maid).ifPresent(handler -> {
            var values = handler.getContainers().values();
            for (AccessoriesContainer stacksHandler : values) {
                var stacks = stacksHandler.getAccessories();
                for (int i = 0; i < stacks.getContainerSize(); i++) {
                    ItemStack stack = stacks.removeItem(i, stacks.getItem(i).getCount());
                    if (!stack.isEmpty()) {
                        tombstone.insertItem(stack);
                    }
                }
            }
        });
    }
}