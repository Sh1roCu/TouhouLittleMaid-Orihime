package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.datagen.EnchantmentKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class TagEnchantment extends FabricTagProvider.EnchantmentTagProvider {
    public TagEnchantment(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        getOrCreateTagBuilder(EnchantmentTags.NON_TREASURE).add(EnchantmentKeys.SPEEDY, EnchantmentKeys.IMPEDING);
        getOrCreateTagBuilder(EnchantmentTags.TREASURE).add(EnchantmentKeys.ENDERS_ENDER);
        getOrCreateTagBuilder(EnchantmentTags.TRADEABLE).add(EnchantmentKeys.ENDERS_ENDER);
    }
}