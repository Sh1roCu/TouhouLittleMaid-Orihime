package cn.sh1rocu.touhoulittlemaid.mixin.compat.tacz;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.client.animation.statemachine.GunAnimationStateContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GunAnimationStateContext.class)
public class GunAnimationStateContextMixin {
    @Shadow
    private ItemStack currentGunItem;

    @Inject(
            remap = false,
            method = "lambda$hasAmmoToConsume$8",
            at = @At("HEAD"),
            cancellable = true
    )
    private void tlm$hasAmmoToConsume(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof EntityMaid maid) {
            var cap = maid.getAllInv();
            // 背包检查
            for (int i = 0; i < cap.getSlots(); i++) {
                ItemStack checkAmmoStack = cap.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(this.currentGunItem, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(this.currentGunItem, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
}
