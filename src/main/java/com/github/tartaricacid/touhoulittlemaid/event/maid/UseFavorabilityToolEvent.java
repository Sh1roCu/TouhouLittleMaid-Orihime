package com.github.tartaricacid.touhoulittlemaid.event.maid;

import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.favorability.FavorabilityManager;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class UseFavorabilityToolEvent {
    public static void onInteract(InteractMaidEvent event) {
        EntityMaid maid = event.getMaid();
        FavorabilityManager manager = maid.getFavorabilityManager();
        ItemStack stack = event.getStack();
        Player player = event.getPlayer();
        int point = player.isShiftKeyDown() ? 1 : 64;
        boolean success = false;
        if (stack.getItem() == InitItems.FAVORABILITY_TOOL_ADD) {
            manager.add(point);
            success = true;
        }
        if (stack.getItem() == InitItems.FAVORABILITY_TOOL_REDUCE) {
            manager.reduceWithoutLevel(point);
            success = true;
        }
        if (stack.getItem() == InitItems.FAVORABILITY_TOOL_FULL) {
            manager.max();
            success = true;
        }
        if (success) {
            maid.playSound(SoundEvents.PLAYER_LEVELUP, 0.5F, maid.getRandom().nextFloat() * 0.1F + 0.9F);
            event.setCanceled(true);
        }
    }
}
