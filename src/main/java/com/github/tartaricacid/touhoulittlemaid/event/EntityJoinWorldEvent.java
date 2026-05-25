package com.github.tartaricacid.touhoulittlemaid.event;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.MobAccessor;
import com.github.tartaricacid.touhoulittlemaid.data.MaidNumAttachment;
import com.github.tartaricacid.touhoulittlemaid.data.PowerAttachment;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.goal.MaidTemptGoal;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitDataAttachment;
import com.github.tartaricacid.touhoulittlemaid.network.message.SyncDataPackage;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Creeper;

import java.util.List;

import static com.github.tartaricacid.touhoulittlemaid.init.InitDataAttachment.POWER_NUM;

public class EntityJoinWorldEvent {
    public static void onCreeperJoinWorld(Entity entity, ServerLevel level) {
        if (entity instanceof Creeper creeper) {
            ((MobAccessor) creeper).tlm$goalSelector().addGoal(1, new AvoidEntityGoal<>(creeper, EntityMaid.class, 6, 1, 1.2));
        }
    }

    public static void onAnimalJoinWorld(Entity entity, ServerLevel level) {
        if (entity instanceof Animal animal) {
            GoalSelector goalSelector = ((MobAccessor) animal).tlm$goalSelector();
            // 先复制一遍进行遍历，避免出现 ConcurrentModificationException
            var goals = List.copyOf(goalSelector.getAvailableGoals());
            goals.stream().filter(goal -> goal.getGoal() instanceof TemptGoal).findFirst().ifPresent(g -> {
                if (g.getGoal() instanceof TemptGoal temptGoal && temptGoal.mob instanceof PathfinderMob pathfinderMob) {
                    MaidTemptGoal maidTemptGoal = new MaidTemptGoal(pathfinderMob, temptGoal.speedModifier, temptGoal.items, temptGoal.canScare);
                    goalSelector.addGoal(g.getPriority(), maidTemptGoal);
                }
            });
        }
    }

    public static void onPlayerJoinWorld(Entity entity, ServerLevel level) {
        if (entity instanceof ServerPlayer player) {
            PowerAttachment power = player.getAttachedOrCreate(POWER_NUM, () -> new PowerAttachment(0));
            MaidNumAttachment maidNum = player.getAttachedOrCreate(InitDataAttachment.MAID_NUM, () -> new MaidNumAttachment(0));
            ServerPlayNetworking.send(player, new SyncDataPackage(power.get(), maidNum.get()));
        }
    }
}