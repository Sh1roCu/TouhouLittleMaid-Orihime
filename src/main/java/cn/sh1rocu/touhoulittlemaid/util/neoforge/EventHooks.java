package cn.sh1rocu.touhoulittlemaid.util.neoforge;

import cn.sh1rocu.touhoulittlemaid.api.event.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.phys.HitResult;

public class EventHooks {
    private static final WeightedList<MobSpawnSettings.SpawnerData> NO_SPAWNS = WeightedList.of();

    public static WeightedList<MobSpawnSettings.SpawnerData> getPotentialSpawns(LevelAccessor level, MobCategory category, BlockPos pos, WeightedList<MobSpawnSettings.SpawnerData> oldList) {
        PotentialSpawnsEvent event = new PotentialSpawnsEvent(level, category, pos, oldList);
        PotentialSpawnsEvent.CALLBACK.invoker().post(event);
        if (event.isCanceled())
            return NO_SPAWNS;
        else if (event.getSpawnerDataList() == oldList.unwrap())
            return oldList;
        return WeightedList.of(event.getSpawnerDataList());
    }

    public static boolean canMountEntity(Entity entityMounting, Entity entityBeingMounted, boolean isMounting) {
        EntityMountEvent event = new EntityMountEvent(entityMounting, entityBeingMounted, entityMounting.level(), isMounting);
        EntityMountEvent.CALLBACK.invoker().post(event);
        if (event.isCanceled()) {
            entityMounting.absMoveTo(entityMounting.getX(), entityMounting.getY(), entityMounting.getZ(), entityMounting.yRotO, entityMounting.xRotO);
            return false;
        } else
            return true;
    }

    public static ItemStack onItemUseFinish(LivingEntity entity, ItemStack item, int duration, ItemStack result) {
        LivingEntityUseItemFinishEvent event = new LivingEntityUseItemFinishEvent(entity, item, duration, result);
        LivingEntityUseItemFinishEvent.CALLBACK.invoker().post(event);
        return event.getResultStack();
    }

    public static void firePlayerTickPre(Player player) {
        PlayerTickEvent.START.invoker().onStart(new PlayerTickEvent.Pre(player));
    }

    public static void firePlayerTickPost(Player player) {
        PlayerTickEvent.END.invoker().onEnd(new PlayerTickEvent.Post(player));
    }

    public static boolean onProjectileImpact(Projectile projectile, HitResult ray) {
        ProjectileImpactEvent event = new ProjectileImpactEvent(projectile, ray);
        ProjectileImpactEvent.CALLBACK.invoker().post(event);
        return event.isCanceled();
    }
}
