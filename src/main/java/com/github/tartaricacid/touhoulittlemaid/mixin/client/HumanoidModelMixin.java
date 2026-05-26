package com.github.tartaricacid.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.util.Dummy;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

// FIXME
//@Mixin(HumanoidModel.class)
@Mixin(Dummy.class)
public class HumanoidModelMixin<T extends LivingEntity> {
//    @Shadow
//    @Final
//    public ModelPart leftArm;
//    @Shadow
//    @Final
//    public ModelPart rightArm;
//
//    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "TAIL"))
//    private void setRotationAnglesHead(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
//        if (entityIn instanceof Player player && player.getFirstPassenger() instanceof EntityMaid) {
//            leftArm.xRot = (float) Math.toRadians(-65);
//            leftArm.yRot = (float) Math.toRadians(10);
//            rightArm.xRot = (float) Math.toRadians(-65);
//            rightArm.yRot = (float) Math.toRadians(-10);
//        }
//    }
}
