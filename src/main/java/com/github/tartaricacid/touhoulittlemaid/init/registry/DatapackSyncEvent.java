package com.github.tartaricacid.touhoulittlemaid.init.registry;

import com.github.tartaricacid.touhoulittlemaid.init.InitRecipes;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;

public class DatapackSyncEvent {
    public static void onDatapackSyncEvent() {
        RecipeSynchronization.synchronizeRecipeSerializer(InitRecipes.ALTAR_RECIPE_SERIALIZER);
    }
}
