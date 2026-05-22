package com.github.tartaricacid.touhoulittlemaid.mixin.client;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ItemProperties.class)
public class ItemPropertiesMixin {
    @SuppressWarnings("target")
    @Inject(method = "method_27883", at = @At("HEAD"), cancellable = true)
    private static void onItemRender(ItemStack stack, @Nullable ClientLevel pLevel, @Nullable LivingEntity entity, int pSeed, CallbackInfoReturnable<Float> ci) {
        if (entity instanceof EntityMaid maid && maid.getMainHandItem() == stack) {
            float result = maid.hasFishingHook() ? 1.0F : 0.0F;
            ci.setReturnValue(result);
        }
    }
}
