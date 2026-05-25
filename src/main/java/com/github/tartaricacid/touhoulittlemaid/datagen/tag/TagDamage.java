package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.init.InitDamage;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TagDamage extends FabricTagsProvider<DamageType> {
    public TagDamage(FabricPackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider) {
        super(pOutput, Registries.DAMAGE_TYPE, pLookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider pProvider) {
        this.builder(DamageTypeTags.IS_PROJECTILE).add(InitDamage.DANMAKU);
        this.builder(DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS)
                .add(InitDamage.DANMAKU_ENDER_KILLER);
    }
}
