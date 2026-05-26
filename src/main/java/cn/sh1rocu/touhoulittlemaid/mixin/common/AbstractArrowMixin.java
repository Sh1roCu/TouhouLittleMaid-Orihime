package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.util.neoforge.EventHooks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Entity {
    public AbstractArrowMixin(EntityType<?> variant, Level world) {
        super(variant, world);
    }

    @Inject(method = "stepMoveAndHit", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"
    ), cancellable = true)
    private void tlm$onProjectileImpact(BlockHitResult blockHitResult, CallbackInfo ci) {
        if (EventHooks.onProjectileImpact((AbstractArrow) (Object) this, blockHitResult)) {
            ci.cancel();
        }
    }

    @Inject(method = "stepMoveAndHit", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;hitTargetsOrDeflectSelf(Ljava/util/Collection;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"
    ), cancellable = true)
    private void tlm$onProjectileImpact(CallbackInfo ci, @Local EntityHitResult firstEntityHit) {
        if (firstEntityHit.getType() != HitResult.Type.MISS) {
            if (EventHooks.onProjectileImpact((AbstractArrow) (Object) this, firstEntityHit)) {
                ci.cancel();
            }
        }
    }
}