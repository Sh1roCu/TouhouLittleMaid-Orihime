package com.github.tartaricacid.touhoulittlemaid.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CancelSaddleMaidEvent {
    public static InteractionResult onItemRightClick(Player player, Level world, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player.getFirstPassenger() instanceof EntityMaid && itemStack.is(Items.SADDLE)) {
            player.ejectPassengers();
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }
}
