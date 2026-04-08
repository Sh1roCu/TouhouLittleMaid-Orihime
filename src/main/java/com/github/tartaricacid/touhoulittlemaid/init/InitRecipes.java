package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.crafting.AltarRecipe;
import com.github.tartaricacid.touhoulittlemaid.crafting.AltarRecipeSerializer;
import com.github.tartaricacid.touhoulittlemaid.crafting.FallbackIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class InitRecipes {
    public static void init() {
        CustomIngredientSerializer.register(FallbackIngredient.Serializer.INSTANCE);
    }

    public static final RecipeSerializer<AltarRecipe> ALTAR_RECIPE_SERIALIZER = registerSerializer("altar_crafting", new AltarRecipeSerializer());
    public static final RecipeType<AltarRecipe> ALTAR_CRAFTING = registerType("altar_crafting", simple(new ResourceLocation(TouhouLittleMaid.MOD_ID, "altar_crafting")));

    private static <T extends RecipeSerializer<?>> T registerSerializer(String id, T serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), serializer);
    }

    private static <T extends RecipeType<?>> T registerType(String id, T type) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), type);
    }

    private static <T extends Recipe<?>> RecipeType<T> simple(ResourceLocation name) {
        final String toString = name.toString();
        return new RecipeType<>() {
            public String toString() {
                return toString;
            }
        };
    }
}
