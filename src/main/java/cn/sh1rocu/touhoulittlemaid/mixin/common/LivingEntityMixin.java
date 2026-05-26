package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.event.LivingAttackEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.LivingDamageEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.LivingHurtEvent;
import cn.sh1rocu.touhoulittlemaid.util.forge.EventHooks;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract ItemStack getUseItem();

    @Shadow
    public abstract int getUseItemRemainingTicks();

    @Shadow
    protected int lastHurtByPlayerTime;

    @Shadow
    @Nullable
    protected Player lastHurtByPlayer;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "LOAD", ordinal = 0), index = 2)
    private float tlm$livingHurtEvent(float value, DamageSource pDamageSource, @Share("hurt") LocalRef<LivingHurtEvent> eventRef) {
        LivingHurtEvent event = new LivingHurtEvent((LivingEntity) (Object) this, pDamageSource, value);
        eventRef.set(event);
        LivingHurtEvent.CALLBACK.invoker().onLivingHurt(event);
        if (event.isCanceled())
            return 0;
        return event.getAmount();
    }

    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"), cancellable = true)
    private void tlm$shouldCancelHurt(DamageSource damageSource, float f, CallbackInfo ci, @Share("hurt") LocalRef<LivingHurtEvent> eventRef) {
        if (eventRef.get().getAmount() <= 0)
            ci.cancel();
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "LOAD", ordinal = 6), index = 2)
    private float tlm$livingDamageEvent(float value, DamageSource pDamageSource) {
        LivingDamageEvent event = new LivingDamageEvent((LivingEntity) (Object) this, pDamageSource, value);
        LivingDamageEvent.CALLBACK.invoker().onLivingDamage(event);
        if (event.isCanceled())
            return 0;
        return event.getAmount();
    }

    @WrapOperation(method = "completeUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack tlm$onItemUseFinish(ItemStack instance, Level level, LivingEntity livingEntity, Operation<ItemStack> original) {
        return EventHooks.onItemUseFinish((LivingEntity) (Object) this, this.getUseItem().copy(), this.getUseItemRemainingTicks(), original.call(instance, level, livingEntity));
    }

    // 女仆攻击完成后，给受伤实体设置lastHurtByPlayerTime，便于经验掉落等计算
    @Inject(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/damagesource/DamageSource;getEntity()Lnet/minecraft/world/entity/Entity;")
    )
    private void tlm$hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity attacker = source.getEntity();
        if (attacker instanceof EntityMaid maid && maid.isTame()) {
            this.lastHurtByPlayerTime = 100;
            if (maid.getOwner() instanceof Player player) {
                this.lastHurtByPlayer = player;
            } else {
                this.lastHurtByPlayer = null;
            }
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void tlm$attackEvent(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof Player)) {
            LivingAttackEvent event = new LivingAttackEvent(self, source, amount);
            LivingAttackEvent.CALLBACK.invoker().onLivingAttack(event);
            if (event.isCanceled())
                cir.setReturnValue(false);
        }
    }
}
