package com.github.tartaricacid.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.util.Dummy;
import org.spongepowered.asm.mixin.Mixin;

// FIXME
//@Mixin(ItemProperties.class)
@Mixin(Dummy.class)
public class ItemPropertiesMixin {
//    @SuppressWarnings("target")
//    @Inject(method = "lambda$static$18(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/entity/LivingEntity;I)F", at = @At("HEAD"), cancellable = true)
//    private static void onItemRender(ItemStack stack, @Nullable ClientLevel pLevel, @Nullable LivingEntity entity, int pSeed, CallbackInfoReturnable<Float> ci) {
//        if (entity instanceof EntityMaid maid && maid.getMainHandItem() == stack) {
//            float result = maid.hasFishingHook() ? 1.0F : 0.0F;
//            ci.setReturnValue(result);
//        }
//    }
}
