package com.github.tartaricacid.touhoulittlemaid.event;

import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityChair;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.item.ItemChair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public final class InstallChairEvent {
    public static InteractionResult onPlayerEntityInteract(Player player, Level world, InteractionHand hand, Entity target, @Nullable HitResult hitResult) {
        ItemStack mainHandItem = player.getMainHandItem();
        if (target instanceof Boat boat && boat.getPassengers().isEmpty() && mainHandItem.is(InitItems.CHAIR)) {
            if (player.level instanceof ServerLevel serverLevel) {
                EntityChair spawnChair = ItemChair.getSpawnChair(serverLevel, player, mainHandItem, target.blockPosition(), target.getXRot());
                if (spawnChair != null) {
                    serverLevel.addFreshEntity(spawnChair);
                    spawnChair.startRiding(target);
                    mainHandItem.shrink(1);
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
