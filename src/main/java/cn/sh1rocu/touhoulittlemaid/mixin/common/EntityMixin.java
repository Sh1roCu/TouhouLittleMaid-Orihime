package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.extension.IEntity;
import cn.sh1rocu.touhoulittlemaid.util.neoforge.EventHooks;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    @Nullable
    private Entity vehicle;

    @Inject(method = "setPosRaw", at = @At("TAIL"))
    private void tlm$setPosRaw(double x, double y, double z, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;
        if (self instanceof IEntity entity && entity.isAddedToLevel() && !self.level.isClientSide() && !self.isRemoved())
            //强加载区块
            self.level.getChunk((int) Math.floor(x) >> 4, (int) Math.floor(z) >> 4);
    }

    @Inject(
            method = "startRiding(Lnet/minecraft/world/entity/Entity;ZZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;canRide(Lnet/minecraft/world/entity/Entity;)Z",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void tlm$startRiding(Entity entityToRide, boolean force, boolean sendEventAndTriggers, CallbackInfoReturnable<Boolean> cir) {
        if (!EventHooks.canMountEntity((Entity) (Object) this, entityToRide, true))
            cir.setReturnValue(false);
    }

    @Inject(method = "removeVehicle", at = @At(value = "CONSTANT", args = "nullValue=true"), cancellable = true)
    public void tlm$removeRidingEntity(CallbackInfo ci) {
        if (!EventHooks.canMountEntity((Entity) (Object) this, this.vehicle, false))
            ci.cancel();
    }
}
