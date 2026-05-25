package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.datagen.EnchantmentKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TagEnchantment extends FabricTagsProvider<Enchantment> {
    public TagEnchantment(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, Registries.ENCHANTMENT, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        builder(EnchantmentTags.NON_TREASURE).add(EnchantmentKeys.SPEEDY, EnchantmentKeys.IMPEDING);
        builder(EnchantmentTags.TREASURE).add(EnchantmentKeys.ENDERS_ENDER);
        builder(EnchantmentTags.TRADEABLE).add(EnchantmentKeys.ENDERS_ENDER);
    }
}
