package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.crafting.AltarRecipe;
import com.github.tartaricacid.touhoulittlemaid.crafting.AltarRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public interface InitRecipes {
    static void init() {
    }

    RecipeBookCategory ALTAR_RECIPE_CATEGORY = registerCategory("altar", new RecipeBookCategory());

    RecipeSerializer<AltarRecipe> ALTAR_RECIPE_SERIALIZER = registerSerializer("altar_recipe_serializers", AltarRecipeSerializer.SERIALIZER);

    RecipeType<AltarRecipe> ALTAR_RECIPE = registerType("altar_recipe", simple(Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "altar_crafting")));

    private static <T extends RecipeBookCategory> T registerCategory(String id, T category) {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), category);
    }

    private static <T extends RecipeSerializer<?>> T registerSerializer(String id, T serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), serializer);
    }

    private static <T extends RecipeType<?>> T registerType(String id, T type) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), type);
    }

    private static <T extends Recipe<?>> RecipeType<T> simple(Identifier name) {
        final String toString = name.toString();
        return new RecipeType<>() {
            public String toString() {
                return toString;
            }
        };
    }
}
