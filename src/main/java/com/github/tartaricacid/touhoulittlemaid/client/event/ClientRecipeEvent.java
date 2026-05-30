package com.github.tartaricacid.touhoulittlemaid.client.event;


import com.github.tartaricacid.touhoulittlemaid.crafting.AltarRecipe;
import com.github.tartaricacid.touhoulittlemaid.init.InitRecipes;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.recipe.v1.sync.SynchronizedRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;
import java.util.List;

public class ClientRecipeEvent {
    public static List<RecipeHolder<AltarRecipe>> ALTAR_RECIPES = Collections.emptyList();

    public static void onRecipeReceived(Minecraft client, SynchronizedRecipes recipes) {
        ALTAR_RECIPES = Lists.newArrayList(recipes.getAllOfType(InitRecipes.ALTAR_RECIPE));
    }
}