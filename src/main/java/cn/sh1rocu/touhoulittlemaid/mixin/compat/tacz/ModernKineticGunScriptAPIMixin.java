package cn.sh1rocu.touhoulittlemaid.mixin.compat.tacz;

import cn.sh1rocu.touhoulittlemaid.util.compat.tacz.ItemHandlerUtil;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModernKineticGunScriptAPI.class)
public class ModernKineticGunScriptAPIMixin {
    @Shadow
    private LivingEntity shooter;

    @Shadow
    private ItemStack itemStack;

    @Inject(method = "consumeAmmoFromPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;tacz$getItemHandler(Lnet/minecraft/core/Direction;)Lcn/sh1rocu/tacz/util/forge/LazyOptional;"), cancellable = true)
    private void tlm$consumeAmmoFromPlayer(int neededAmount, CallbackInfoReturnable<Integer> cir) {
        if (this.shooter instanceof EntityMaid maid) {
            var cap = maid.getAllInv();
            cir.setReturnValue(ItemHandlerUtil.findAndExtractInventoryAmmo(cap, this.itemStack, neededAmount));
        }
    }

    @Inject(method = "hasAmmoToConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;tacz$getItemHandler(Lnet/minecraft/core/Direction;)Lcn/sh1rocu/tacz/util/forge/LazyOptional;"), cancellable = true)
    private void tlm$hasAmmoToConsume(CallbackInfoReturnable<Boolean> cir) {
        if (this.shooter instanceof EntityMaid maid) {
            var cap = maid.getAllInv();
            // 背包检查
            for (int i = 0; i < cap.getSlots(); i++) {
                ItemStack checkAmmoStack = cap.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(itemStack, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(itemStack, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
}
