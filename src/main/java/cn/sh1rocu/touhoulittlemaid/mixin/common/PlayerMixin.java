package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.event.LivingAttackEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.LivingDamageEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.LivingHurtEvent;
import cn.sh1rocu.touhoulittlemaid.util.forge.EventHooks;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tlm$playerStartTickEvent(CallbackInfo ci) {
        EventHooks.firePlayerTickPre((Player) (Object) this);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tlm$playerEndTickEvent(CallbackInfo ci) {
        EventHooks.firePlayerTickPost((Player) (Object) this);
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void tlm$attackEvent(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingAttackEvent event = new LivingAttackEvent(this, source, amount);
        LivingAttackEvent.CALLBACK.invoker().onLivingAttack(event);
        if (event.isCanceled())
            cir.setReturnValue(false);
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "LOAD", ordinal = 0), index = 2)
    private float tlm$livingHurtEvent(float value, DamageSource pDamageSource, @Share("hurt") LocalRef<LivingHurtEvent> eventRef) {
        LivingHurtEvent event = new LivingHurtEvent(this, pDamageSource, value);
        eventRef.set(event);
        LivingHurtEvent.CALLBACK.invoker().onLivingHurt(event);
        if (event.isCanceled())
            return 0;
        return event.getAmount();
    }

    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"), cancellable = true)
    private void tlm$shouldCancelHurt(DamageSource damageSource, float f, CallbackInfo ci, @Share("hurt") LocalRef<LivingHurtEvent> eventRef) {
        if (eventRef.get().getAmount() <= 0)
            ci.cancel();
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "LOAD", ordinal = 5), index = 2)
    private float tlm$livingDamageEvent(float value, DamageSource pDamageSource) {
        LivingDamageEvent event = new LivingDamageEvent(this, pDamageSource, value);
        LivingDamageEvent.CALLBACK.invoker().onLivingDamage(event);
        if (event.isCanceled())
            return 0;
        return event.getAmount();
    }
}
