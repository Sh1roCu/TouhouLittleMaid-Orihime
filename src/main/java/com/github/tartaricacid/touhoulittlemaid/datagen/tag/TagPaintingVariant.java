package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TagPaintingVariant extends FabricTagsProvider<PaintingVariant> {
    public static final ResourceKey<PaintingVariant> WINE_FOX = ResourceKey.create(Registries.PAINTING_VARIANT, ResourceLocationUtil.getResourceLocation("wine_fox"));

    public TagPaintingVariant(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.PAINTING_VARIANT, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.builder(PaintingVariantTags.PLACEABLE).add(WINE_FOX);
    }
}
