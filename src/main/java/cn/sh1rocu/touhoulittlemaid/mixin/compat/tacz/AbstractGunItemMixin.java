package cn.sh1rocu.touhoulittlemaid.mixin.compat.tacz;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractGunItem.class)
public class AbstractGunItemMixin {
    @Inject(
            method = "canReload",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;tacz$getItemHandler(Lnet/minecraft/core/Direction;)Lcn/sh1rocu/tacz/util/forge/LazyOptional;"
            ),
            cancellable = true
    )
    private static void tlm$canReload(LivingEntity shooter, ItemStack gunItem, CallbackInfoReturnable<Boolean> cir) {
        if (shooter instanceof EntityMaid maid) {
            // 检查背包内的弹药数量
            var cap = maid.getAllInv();
            // 背包检查
            for (int i = 0; i < cap.getSlots(); i++) {
                ItemStack checkAmmoStack = cap.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(gunItem, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(gunItem, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }

    @Inject(
            method = "hasInventoryAmmo",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;tacz$getItemHandler(Lnet/minecraft/core/Direction;)Lcn/sh1rocu/tacz/util/forge/LazyOptional;"
            ),
            cancellable = true
    )
    private static void tlm$hasInventoryAmmo(LivingEntity shooter, ItemStack gun, boolean needCheckAmmo, CallbackInfoReturnable<Boolean> cir) {
        if (shooter instanceof EntityMaid maid) {
            // 检查背包内的弹药数量
            var cap = maid.getAllInv();
            // 背包检查
            for (int i = 0; i < cap.getSlots(); i++) {
                ItemStack checkAmmoStack = cap.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(gun, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(gun, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
}
