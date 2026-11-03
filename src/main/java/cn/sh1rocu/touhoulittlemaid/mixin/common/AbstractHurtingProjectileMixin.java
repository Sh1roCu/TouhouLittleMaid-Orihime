package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.util.neoforge.EventHooks;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({AbstractHurtingProjectile.class})
public class AbstractHurtingProjectileMixin {
    public AbstractHurtingProjectileMixin() {
    }

    @WrapWithCondition(
            method = {"tick"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractHurtingProjectile;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"
            )}
    )
    private boolean tlm$onProjectileImpact(AbstractHurtingProjectile projectile, HitResult result) {
        return !EventHooks.onProjectileImpact(projectile, result);
    }
}
