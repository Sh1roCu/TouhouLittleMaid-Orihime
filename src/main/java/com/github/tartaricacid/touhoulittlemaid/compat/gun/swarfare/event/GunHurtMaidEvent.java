package com.github.tartaricacid.touhoulittlemaid.compat.gun.swarfare.event;

import cn.sh1rocu.touhoulittlemaid.api.event.LivingAttackEvent;
import com.atsuishio.superbwarfare.api.event.ProjectileHitEvent;
import com.atsuishio.superbwarfare.entity.projectile.FastThrowableProjectile;
import com.atsuishio.superbwarfare.entity.projectile.ProjectileEntity;
import com.atsuishio.superbwarfare.init.ModTags;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidHurtEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import java.util.List;

public class GunHurtMaidEvent {
    /**
     * 不伤害自己
     */
    public void onMaidHurt(MaidHurtEvent event) {
        DamageSource source = event.getSource();
        EntityMaid maid = event.getMaid();
        if (maid.getOwnerUUID() == null) {
            return;
        }
        if (isBulletDamage(source)) {
            event.setCanceled(true);
        }
    }

    /**
     * 避免通过事件引入伤害的附属模组打死玩家
     */
    public void onGunHurt(ProjectileHitEvent.HitEntity event) {
        Entity hurtEntity = event.getTarget();
        Entity attacker = event.getOwner();

        // 不伤害自己 x2
        if (attacker instanceof EntityMaid maid) {
            // 主人和同 Team 玩家免伤
            if (hurtEntity instanceof Player player && maid.isAlliedTo(player)) {
                event.setCanceled(true);
            }
        }

        // 不伤害他人 x2
        if (attacker instanceof Player player) {
            // 主人和同 Team 玩家免伤
            if (hurtEntity instanceof EntityMaid maid && maid.isAlliedTo(player)) {
                event.setCanceled(true);
            }
        }
    }

    /**
     * 不伤害他人
     */
    public void onPlayerHurt(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        if (entity instanceof Player player && isBulletDamage(source)) {
            Entity causingEntity = source.getEntity();
            // 主人和同 Team 玩家免伤
            if (causingEntity instanceof EntityMaid maid && maid.isAlliedTo(player)) {
                event.setCanceled(true);
            }
        }
    }

    public void onExplosionDetonateEvent(Level world, Explosion explosion, List<Entity> entities, double diameter) {
        Entity entity = explosion.getDirectSourceEntity();
        if (entity instanceof ProjectileEntity || entity instanceof FastThrowableProjectile) {
            entities.removeIf(e -> e instanceof EntityMaid);
        }
    }

    private boolean isBulletDamage(DamageSource source) {
        if (source.is(ModTags.DamageTypes.GUN_DAMAGE)) {
            return true;
        }
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            Entity directEntity = source.getDirectEntity();
            return directEntity instanceof ProjectileEntity || directEntity instanceof FastThrowableProjectile;
        }
        return false;
    }
}