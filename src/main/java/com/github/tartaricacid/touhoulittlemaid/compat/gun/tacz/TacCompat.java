package com.github.tartaricacid.touhoulittlemaid.compat.gun.tacz;

import cn.sh1rocu.touhoulittlemaid.api.event.ExplosionEvents;
import cn.sh1rocu.touhoulittlemaid.api.event.LivingAttackEvent;
import com.github.tartaricacid.touhoulittlemaid.api.entity.IMaid;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidEquipEvent;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidHurtEvent;
import com.github.tartaricacid.touhoulittlemaid.client.animation.script.ModelRendererWrapper;
import com.github.tartaricacid.touhoulittlemaid.client.entity.GeckoMaidEntity;
import com.github.tartaricacid.touhoulittlemaid.compat.gun.tacz.client.GunBaseAnimation;
import com.github.tartaricacid.touhoulittlemaid.compat.gun.tacz.client.GunGeckoAnimation;
import com.github.tartaricacid.touhoulittlemaid.compat.gun.tacz.client.GunMaidRender;
import com.github.tartaricacid.touhoulittlemaid.compat.gun.tacz.event.GunHurtMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.compat.gun.tacz.event.MaidGunEquipEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.PlayState;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.builder.ILoopType;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.event.predicate.AnimationEvent;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.geo.animated.ILocationModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class TacCompat {
    public static final Identifier MINIGUN_ID = Identifier.fromNamespaceAndPath("tacz", "minigun");
    private static final String TACZ_ID = "tacz";
    private static boolean INSTALLED = false;

    public static boolean init() {
        if (FabricLoader.getInstance().isModLoaded(TACZ_ID)) {
            GunHurtMaidEvent gunHurtMaidEvent = new GunHurtMaidEvent();
            MaidHurtEvent.CALLBACK.register(gunHurtMaidEvent::onMaidHurt);
            EntityHurtByGunEvent.PRE.register(gunHurtMaidEvent::onGunHurt);
            LivingAttackEvent.CALLBACK.register(gunHurtMaidEvent::onPlayerHurt);
            ExplosionEvents.DETONATE.register(gunHurtMaidEvent::onExplosionDetonateEvent);

            MaidGunEquipEvent maidGunEquipEvent = new MaidGunEquipEvent();
            MaidEquipEvent.CALLBACK.register(maidGunEquipEvent::onMaidEquip);

            INSTALLED = true;
        }
        return INSTALLED;
    }

    public static boolean isInstalled() {
        return INSTALLED;
    }

    public static boolean isGun(ItemStack stack) {
        if (INSTALLED) {
            return TacInnerCompat.isGun(stack);
        }
        return false;
    }

    public static boolean isGrenade(ItemStack itemStack) {
        // TODO 手雷还没有
        return false;
    }

    @Nullable
    public static Identifier getGunId(ItemStack stack) {
        if (INSTALLED) {
            return TacInnerCompat.getGunId(stack);
        }
        return null;
    }

    public static boolean canSee(EntityMaid maid, LivingEntity target) {
        if (INSTALLED) {
            return TacInnerCompat.canSee(maid, target);
        }
        return false;
    }

    public static int performGunAttack(EntityMaid shooter, LivingEntity target, ItemStack gunItem) throws Exception {
        if (INSTALLED) {
            return TacInnerCompat.performGunAttack(shooter, target, gunItem);
        }
        return 100;
    }

    public static void stopAim(EntityMaid maid) {
        if (INSTALLED) {
            TacInnerCompat.stopAim(maid);
        }
    }

    @Environment(EnvType.CLIENT)
    public static boolean onHoldGun(IMaid maid, @Nullable ModelRendererWrapper armLeft, @Nullable ModelRendererWrapper armRight) {
        if (INSTALLED) {
            return GunBaseAnimation.onHoldGun(maid, armLeft, armRight);
        }
        return false;
    }

    @Environment(EnvType.CLIENT)
    public static void addItemTranslate(PoseStack matrixStack, ItemStack itemStack, boolean isLeft) {
        if (INSTALLED) {
            GunMaidRender.addItemTranslate(matrixStack, itemStack, isLeft);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void renderBackGun(PoseStack matrixStack, MultiBufferSource bufferIn, int packedLightIn, ItemStack stack, IMaid maid) {
        if (INSTALLED) {
            GunMaidRender.renderBackGun(matrixStack, bufferIn, packedLightIn, stack, maid);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void renderBackGun(ItemStack offhandItem, ILocationModel geoModel, IMaid maid, PoseStack poseStack, MultiBufferSource bufferIn, int packedLight) {
        if (INSTALLED && isGun(offhandItem)) {
            poseStack.pushPose();
            GunMaidRender.renderBackGun(offhandItem, geoModel, maid, poseStack, bufferIn, packedLight);
            poseStack.popPose();
        }
    }

    @Environment(EnvType.CLIENT)
    @Nullable
    public static PlayState playGunMainAnimation(IMaid maid, AnimationEvent<GeckoMaidEntity<?>> event, String animationName, ILoopType loopType) {
        if (INSTALLED && isGun(maid.asEntity().getMainHandItem())) {
            return GunGeckoAnimation.playGunMainAnimation(event, animationName, loopType);
        }
        return null;
    }

    @Environment(EnvType.CLIENT)
    @Nullable
    public static PlayState playGunHoldAnimation(ItemStack mainHandItem, AnimationEvent<GeckoMaidEntity<?>> event) {
        if (INSTALLED && isGun(mainHandItem)) {
            return GunGeckoAnimation.playGunHoldAnimation(event, mainHandItem);
        }
        return null;
    }
}