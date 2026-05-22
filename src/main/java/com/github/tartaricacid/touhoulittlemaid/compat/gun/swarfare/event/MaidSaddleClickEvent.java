package com.github.tartaricacid.touhoulittlemaid.compat.gun.swarfare.event;

import com.atsuishio.superbwarfare.entity.vehicle.base.VehicleEntity;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class MaidSaddleClickEvent {
    public InteractionResult onEntityRightClick(Player player, Level world, InteractionHand hand, Entity target, @Nullable EntityHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player.getFirstPassenger() instanceof EntityMaid maid
                && itemStack.is(Items.SADDLE)
                && target instanceof VehicleEntity
                && maid.startRiding(target)) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}