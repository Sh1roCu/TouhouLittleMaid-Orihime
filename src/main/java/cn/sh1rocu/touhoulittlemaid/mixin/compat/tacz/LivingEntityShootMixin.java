package cn.sh1rocu.touhoulittlemaid.mixin.compat.tacz;

import cn.sh1rocu.touhoulittlemaid.util.compat.tacz.ItemHandlerUtil;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.tacz.guns.entity.shooter.LivingEntityShoot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityShoot.class)
public class LivingEntityShootMixin {
    @Shadow
    @Final
    private LivingEntity shooter;

    @Inject(method = "consumeAmmoFromPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;tacz$getItemHandler(Lnet/minecraft/core/Direction;)Lcn/sh1rocu/tacz/util/forge/LazyOptional;"), cancellable = true)
    private void tlm$consumeAmmoFromPlayer(int neededAmount, ItemStack itemStack, boolean needCheckAmmo, CallbackInfo ci) {
        if (this.shooter instanceof EntityMaid maid) {
            var cap = maid.getAllInv();
            ItemHandlerUtil.findAndExtractInventoryAmmo(cap, itemStack, neededAmount);
            ci.cancel();
        }
    }
}
