package com.github.tartaricacid.touhoulittlemaid.mixin.client;

import com.github.tartaricacid.touhoulittlemaid.client.entity.GeckoMaidEntity;
import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityChair;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.geo.RenderContextManager;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {
    @WrapOperation(
            at = @At(remap = false, value = "INVOKE", target = "Lorg/joml/Quaternionf;rotateZ(F)Lorg/joml/Quaternionf;", ordinal = 0),
            method = "extractEntityInInventoryFollowsMouse")
    private static Quaternionf beforeRenderEntityInInventoryFollowsAngle(Quaternionf instance, float angle, Operation<Quaternionf> original) {
        RenderContextManager.setRenderingInInventory(true);
        return original.call(instance, angle);
    }

    @Inject(at = @At("RETURN"), method = "extractEntityInInventoryFollowsMouse")
    private static void afterRenderEntityInInventoryFollowsAngle(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, int size, float offsetY, float xAngle, float yAngle, LivingEntity entity, CallbackInfo ci) {
        // 以后如果移除了 EntityCache 机制，可以删掉这段
        if (entity instanceof EntityMaid maid) {
            var animatable = maid.getAttached(GeckoMaidEntity.TYPE);
            if (animatable != null && animatable.getLastUpdateTask() != null) {
                animatable.getLastUpdateTask().start();
            }
        } else if (entity instanceof EntityChair chair) {
            if (chair.getAnimatableEntity().getLastUpdateTask() != null) {
                chair.getAnimatableEntity().getLastUpdateTask().start();
            }
        }

        RenderContextManager.setRenderingInInventory(false);
    }
}
