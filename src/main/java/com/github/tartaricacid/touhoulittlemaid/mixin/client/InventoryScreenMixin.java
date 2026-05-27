package com.github.tartaricacid.touhoulittlemaid.mixin.client;

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
    private static void afterRenderEntityInInventoryFollowsAngle(GuiGraphicsExtractor p_282802_, int p_275688_, int p_275245_, int p_275535_, int p_294406_, int p_294663_, float p_275604_, float angleXComponent, float angleYComponent, LivingEntity p_275689_, CallbackInfo ci) {
        RenderContextManager.setRenderingInInventory(false);
    }
}
