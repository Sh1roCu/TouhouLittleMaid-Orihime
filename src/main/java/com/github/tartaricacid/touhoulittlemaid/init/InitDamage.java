package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityDanmaku;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.Entity;

public final class InitDamage {
    public static final ResourceKey<DamageType> DANMAKU = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(TouhouLittleMaid.MOD_ID, "danmaku"));
    public static final ResourceKey<DamageType> DANMAKU_ENDER_KILLER = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(TouhouLittleMaid.MOD_ID, "danmaku_ender_killer"));

    public static DamageSource danmakuDamage(Entity thrower, EntityDanmaku danmaku) {
        Registry<DamageType> damageTypes = thrower.level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        if (danmaku.isHurtEnderman()) {
            return new DamageSource(damageTypes.getHolderOrThrow(DANMAKU_ENDER_KILLER), danmaku, thrower);
        } else {
            return new DamageSource(damageTypes.getHolderOrThrow(DANMAKU), danmaku, thrower);
        }
    }

    public static void bootstrap(BootstapContext<DamageType> ctx) {
        ctx.register(DANMAKU, new DamageType("touhou_little_maid.danmaku", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f, DamageEffects.HURT, DeathMessageType.DEFAULT));
        ctx.register(DANMAKU_ENDER_KILLER, new DamageType("touhou_little_maid.danmaku", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f, DamageEffects.HURT, DeathMessageType.DEFAULT));
    }
}
