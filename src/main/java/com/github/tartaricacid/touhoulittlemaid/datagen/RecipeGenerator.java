package com.github.tartaricacid.touhoulittlemaid.datagen;

import com.github.tartaricacid.touhoulittlemaid.datagen.builder.AltarRecipeBuilder;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricPackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new Provider(registries, output);
    }

    @Override
    public String getName() {
        return "Touhou Little Maid Recipes";
    }

    @SuppressWarnings("all")
    private class Provider extends RecipeProvider {
        protected Provider(HolderLookup.Provider registries, RecipeOutput output) {
            super(registries, output);
        }

        @Override
        public void buildRecipes() {
            RecipeOutput recipeOutput = this.output;
            var items = registries.lookupOrThrow(Registries.ITEM);
            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.BOOKSHELF)
                    .power(0.1F)
                    .requires(4, ItemTags.PLANKS)
                    .requires(Items.BOOK)
                    .requires(ConventionalItemTags.DIAMOND_GEMS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.BROOM)
                    .power(0.2F)
                    .requires(3, Items.HAY_BLOCK)
                    .requires(2, ConventionalItemTags.WOODEN_RODS)
                    .requires(Items.ENDER_EYE)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.CAMERA)
                    .power(0.2F)
                    .requires(4, Items.QUARTZ_BLOCK)
                    .requires(2, ConventionalItemTags.OBSIDIANS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.CHISEL)
                    .power(0.2F)
                    .requires(2, ConventionalItemTags.WOODEN_RODS)
                    .requires(2, ConventionalItemTags.IRON_INGOTS)
                    .requires(ConventionalItemTags.YELLOW_DYES)
                    .requires(ConventionalItemTags.RED_DYES)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.COMPUTER)
                    .power(0.1F)
                    .requires(3, ItemTags.PLANKS)
                    .requires(Items.NOTE_BLOCK)
                    .requires(Items.LEVER)
                    .requires(ConventionalItemTags.DIAMOND_GEMS)
                    .save(recipeOutput);

        AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.DROWN_PROTECT_BAUBLE)
                .power(0.2F)
                .requires(ConventionalItemTags.NETHER_WART_CROPS)
                .requires(ConventionalItemTags.LIME_DYES)
                .requires(4, ItemTags.FISHES)
                .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.EXPLOSION_PROTECT_BAUBLE)
                    .power(0.2F)
                    .requires(ConventionalItemTags.NETHER_WART_CROPS)
                    .requires(ConventionalItemTags.ORANGE_DYES)
                    .requires(4, ConventionalItemTags.OBSIDIANS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.EXTINGUISHER)
                    .power(0.2F)
                    .requires(4, Items.CLAY_BALL)
                    .requires(ConventionalItemTags.ORANGE_DYES)
                    .requires(ConventionalItemTags.RED_DYES)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.FALL_PROTECT_BAUBLE)
                    .power(0.2F)
                    .requires(ConventionalItemTags.NETHER_WART_CROPS)
                    .requires(ConventionalItemTags.YELLOW_DYES)
                    .requires(4, ConventionalItemTags.FEATHERS)
                    .save(recipeOutput);

        AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.FIRE_PROTECT_BAUBLE)
                .power(0.2F)
                .requires(ConventionalItemTags.NETHER_WART_CROPS)
                .requires(ConventionalItemTags.RED_DYES)
                .requires(4, Items.BLAZE_POWDER)
                .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.GOMOKU)
                    .power(0.1F)
                    .requires(3, ItemTags.PLANKS)
                    .requires(ConventionalItemTags.BLACK_DYES)
                    .requires(ConventionalItemTags.WHITE_DYES)
                    .requires(ConventionalItemTags.DIAMOND_GEMS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.CCHESS)
                    .power(0.1F)
                    .requires(3, ItemTags.PLANKS)
                    .requires(ConventionalItemTags.BLACK_DYES)
                    .requires(ConventionalItemTags.RED_DYES)
                    .requires(ConventionalItemTags.DIAMOND_GEMS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.WCHESS)
                    .power(0.1F)
                    .requires(3, ItemTags.PLANKS)
                    .requires(ConventionalItemTags.BLACK_DYES)
                    .requires(ConventionalItemTags.WHITE_DYES)
                    .requires(ConventionalItemTags.EMERALD_GEMS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.HAKUREI_GOHEI)
                    .power(0.15F)
                    .requires(3, ConventionalItemTags.WOODEN_RODS)
                    .requires(3, Items.PAPER)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.ITEM_MAGNET_BAUBLE)
                    .power(0.2F)
                    .requires(3, ConventionalItemTags.REDSTONE_DUSTS)
                    .requires(3, ConventionalItemTags.IRON_INGOTS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.KAPPA_COMPASS)
                    .power(0.1F)
                    .requires(3, ConventionalItemTags.OBSIDIANS)
                    .requires(ConventionalItemTags.CYAN_DYES)
                    .requires(2, ConventionalItemTags.REDSTONE_DUSTS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.KEYBOARD)
                    .power(0.1F)
                    .requires(4, ItemTags.PLANKS)
                    .requires(Items.NOTE_BLOCK)
                    .requires(ConventionalItemTags.DIAMOND_GEMS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.MAGIC_PROTECT_BAUBLE)
                    .power(0.2F)
                    .requires(ConventionalItemTags.NETHER_WART_CROPS)
                    .requires(ConventionalItemTags.CYAN_DYES)
                    .requires(4, Items.SUGAR)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.MAID_BACKPACK_BIG)
                    .power(0.3F)
                    .requires(4, Items.GRAY_WOOL)
                    .requires(ConventionalItemTags.DIAMOND_GEMS)
                    .requires(Items.GRAY_WOOL)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.MAID_BACKPACK_MIDDLE)
                    .power(0.2F)
                    .requires(4, Items.PINK_WOOL)
                    .requires(ConventionalItemTags.GOLD_INGOTS)
                    .requires(Items.PINK_WOOL)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.MAID_BACKPACK_SMALL)
                    .power(0.1F)
                    .requires(4, Items.RED_WOOL)
                    .requires(ConventionalItemTags.IRON_INGOTS)
                    .requires(Items.RED_WOOL)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.MAID_BEACON)
                    .power(0.2F)
                    .requires(ItemTags.PLANKS)
                    .requires(ConventionalItemTags.RED_DYES)
                    .requires(ItemTags.PLANKS)
                    .requires(ConventionalItemTags.OBSIDIANS)
                    .requires(ConventionalItemTags.DIAMOND_GEMS)
                    .requires(ConventionalItemTags.OBSIDIANS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.MAID_BED)
                    .power(0.2F)
                    .requires(Items.PINK_WOOL)
                    .requires(ItemTags.PLANKS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.MUTE_BAUBLE)
                    .power(0.2F)
                    .requires(ItemTags.WOOL)
                    .requires(Items.CLAY_BALL)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.NIMBLE_FABRIC)
                    .power(0.2F)
                    .requires(ConventionalItemTags.ENDER_PEARLS)
                    .requires(ItemTags.WOOL)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.PICNIC_BASKET)
                    .power(0.2F)
                    .requires(ConventionalItemTags.WOODEN_CHESTS)
                    .requires(4, Items.BAMBOO)
                    .requires(ItemTags.WOOL_CARPETS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.PROJECTILE_PROTECT_BAUBLE)
                    .power(0.2F)
                    .requires(ConventionalItemTags.NETHER_WART_CROPS)
                    .requires(ConventionalItemTags.BLUE_DYES)
                    .requires(4, Items.SHIELD)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.RED_FOX_SCROLL)
                    .power(0.1F)
                    .requires(4, Items.PAPER)
                    .requires(ConventionalItemTags.RED_DYES)
                    .requires(ConventionalItemTags.DIAMOND_GEMS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.SANAE_GOHEI)
                    .power(0.15F)
                    .requires(4, ConventionalItemTags.WOODEN_RODS)
                    .requires(2, Items.PAPER)
                    .save(recipeOutput);

        AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.SCARECROW)
                .power(0.2F)
                .requires(2, Items.HAY_BLOCK)
                .requires(2, Items.GRANITE)
                .requires(2, ConventionalItemTags.REDSTONE_DUSTS)
                .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.TRUMPET)
                    .power(0.2F)
                    .requires(2, ConventionalItemTags.GOLD_INGOTS)
                    .requires(3, ConventionalItemTags.IRON_INGOTS)
                    .requires(Items.NOTE_BLOCK)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.ULTRAMARINE_ORB_ELIXIR)
                    .power(0.3F)
                    .requires(ConventionalItemTags.EMERALD_GEMS)
                    .requires(ConventionalItemTags.ENDER_PEARLS)
                    .requires(4, ConventionalItemTags.CYAN_DYES)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.WHITE_FOX_SCROLL)
                    .power(0.1F)
                    .requires(4, Items.PAPER)
                    .requires(ConventionalItemTags.WHITE_DYES)
                    .requires(ConventionalItemTags.DIAMOND_GEMS)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.WIRELESS_IO)
                    .power(0.2F)
                    .requires(ConventionalItemTags.ENDER_PEARLS)
                    .requires(ConventionalItemTags.WOODEN_CHESTS)
                    .requires(Items.HOPPER)
                    .save(recipeOutput);

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.SERVANT_BELL)
                    .power(0.2F)
                    .requires(2, ConventionalItemTags.GOLD_INGOTS)
                    .requires(2, ConventionalItemTags.GOLD_NUGGETS)
                    .requires(2, ConventionalItemTags.WOODEN_RODS)
                    .save(recipeOutput);

//            ItemStack entityPlaceholder = new ItemStack(InitItems.ENTITY_PLACEHOLDER);
//            entityPlaceholder.set(InitDataComponent.RECIPES_ID_TAG, "reborn_maid");
//            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, entityPlaceholder)
//                    .power(0.5F)
//                    .requires(InitItems.FILM)
//                    .requires(ConventionalItemTags.LAPIS_GEMS)
//                    .requires(ConventionalItemTags.GOLD_INGOTS)
//                    .requires(ConventionalItemTags.REDSTONE_DUSTS)
//                    .requires(ConventionalItemTags.IRON_INGOTS)
//                    .requires(Items.COAL)
//                    .entity(EntityType.getKey(InitEntities.MAID))
//                    .langKey("jei.touhou_little_maid.altar_craft.reborn_maid.result")
//                    .save(recipeOutput, "reborn_maid");
//
//            entityPlaceholder.set(InitDataComponent.RECIPES_ID_TAG, "spawn_box");
//            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, entityPlaceholder)
//                    .power(0.5F)
//                    .requires(ConventionalItemTags.DIAMOND_GEMS)
//                    .requires(ConventionalItemTags.LAPIS_GEMS)
//                    .requires(ConventionalItemTags.GOLD_INGOTS)
//                    .requires(ConventionalItemTags.REDSTONE_DUSTS)
//                    .requires(ConventionalItemTags.IRON_INGOTS)
//                    .requires(Items.COAL)
//                    .entity(EntityType.getKey(InitEntities.BOX))
//                    .langKey("jei.touhou_little_maid.altar_craft.spawn_box.result")
//                    .save(recipeOutput, "spawn_box");
//
//            entityPlaceholder.set(InitDataComponent.RECIPES_ID_TAG, "spawn_lightning_bolt");
//            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, entityPlaceholder)
//                    .power(0.2F)
//                    .requires(3, ConventionalItemTags.GUNPOWDERS)
//                    .requires(3, Items.BLAZE_POWDER)
//                    .entity(EntityType.getKey(EntityType.LIGHTNING_BOLT))
//                    .langKey("jei.touhou_little_maid.altar_craft.spawn_lightning_bolt.result")
//                    .save(recipeOutput, "spawn_lightning_bolt");

            AltarRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.SNACK_CABINET)
                    .power(0.1F)
                    .requires(3, ItemTags.PLANKS)
                    .requires(2, ConventionalItemTags.GLASS_PANES)
                    .requires(Items.BARREL)
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, InitItems.HAKUREI_GOHEI)
                    .pattern("  D")
                    .pattern(" SP")
                    .pattern("S P")
                    .define('S', ConventionalItemTags.WOODEN_RODS)
                    .define('D', ConventionalItemTags.DIAMOND_GEMS)
                    .define('P', Items.PAPER)
                    .unlockedBy(getHasName(Items.DIAMOND), has(ConventionalItemTags.DIAMOND_GEMS))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, InitItems.SANAE_GOHEI)
                    .pattern(" PD")
                    .pattern(" SP")
                    .pattern("S  ")
                    .define('S', ConventionalItemTags.WOODEN_RODS)
                    .define('D', ConventionalItemTags.DIAMOND_GEMS)
                    .define('P', Items.PAPER)
                    .unlockedBy(getHasName(Items.DIAMOND), has(ConventionalItemTags.DIAMOND_GEMS))
                    .save(recipeOutput);

//        ModLoadedCondition modLoadedCondition = new ModLoadedCondition(CompatRegistry.PATCHOULI);
//        ItemStack patchouliBook = new ItemStack(PatchouliItems.BOOK);
//        patchouliBook.set(PatchouliDataComponents.BOOK, InitItems.MEMORIZABLE_GENSOKYO_LOCATION);
//        this.shapeless(RecipeCategory.MISC, patchouliBook)
//                .requires(Tags.Items.DYES_WHITE)
//                .requires(Tags.Items.DYES_RED)
//                .requires(Items.BOOK)
//                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
//                .save(recipeOutput.withConditions(modLoadedCondition), InitItems.MEMORIZABLE_GENSOKYO_LOCATION);

            ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, InitItems.CHAIR)
                    .pattern("   ")
                    .pattern("WWW")
                    .pattern("IPI")
                    .define('W', ItemTags.WOOL)
                    .define('I', ConventionalItemTags.IRON_INGOTS)
                    .define('P', ItemTags.PLANKS)
                    .unlockedBy("has_wool", has(ItemTags.WOOL))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, InitItems.CHAIR_SHOW)
                    .pattern(" R ")
                    .pattern("WWW")
                    .pattern("IPI")
                    .define('W', ItemTags.WOOL)
                    .define('I', ConventionalItemTags.IRON_INGOTS)
                    .define('P', ItemTags.PLANKS)
                    .define('R', ConventionalItemTags.REDSTONE_DUSTS)
                    .unlockedBy(getHasName(Items.REDSTONE), has(ConventionalItemTags.REDSTONE_DUSTS))
                    .save(recipeOutput);

            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, InitItems.ENTITY_ID_COPY)
                    .requires(ConventionalItemTags.LEATHERS)
                    .requires(Items.PAPER)
                    .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                    .save(recipeOutput);
        }
    }
}
