package com.github.tartaricacid.touhoulittlemaid.crafting;

import cn.sh1rocu.touhoulittlemaid.util.forge.AbstractIngredient;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FallbackIngredient extends AbstractIngredient {
    public static final ResourceLocation ID = new ResourceLocation(TouhouLittleMaid.MOD_ID, "fallback_ingredient");

    private final Ingredient fallbacks;

    protected FallbackIngredient(Ingredient fallbacks) {
        this.fallbacks = fallbacks;
    }

    public static FallbackIngredient of(Ingredient fallbacks) {
        return new FallbackIngredient(fallbacks);
    }


    @Override
    public boolean test(@Nullable ItemStack stack) {
        return this.fallbacks.test(stack);
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return List.of(getItems());
    }

    @Override
    public boolean requiresTesting() {
        return this.fallbacks.requiresTesting();
    }

    @Override
    public ItemStack[] getItems() {
        return this.fallbacks.getItems();
    }

    @Override
    public IntList getStackingIds() {
        return this.fallbacks.getStackingIds();
    }

    @Override
    public CustomIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public JsonElement toJson() {
        throw new UnsupportedOperationException("FallbackIngredient does not support datagen toJson");
    }

    public static class Serializer implements CustomIngredientSerializer<FallbackIngredient> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ResourceLocation getIdentifier() {
            return ID;
        }

        @Override
        public FallbackIngredient read(JsonObject json) {
            JsonArray jsonArray = json.getAsJsonArray("fallbacks");
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                String modId = jsonObject.get("modid").getAsString();
                if (FabricLoader.getInstance().isModLoaded(modId)) {
                    Ingredient ingredient = Ingredient.fromJson(jsonObject.get("value"), true);
                    return new FallbackIngredient(ingredient);
                }
            }
            return new FallbackIngredient(Ingredient.EMPTY);
        }

        @Override
        public void write(JsonObject json, FallbackIngredient fallback) {
            // no op?
        }

        @Override
        public FallbackIngredient read(FriendlyByteBuf buffer) {
            ResourceLocation id = buffer.readResourceLocation();
            var custom = CustomIngredientSerializer.get(id);
            if (custom != null) {
                return new FallbackIngredient(custom.read(buffer).toVanilla());
            } else {
                return new FallbackIngredient(Ingredient.fromNetwork(buffer));
            }
        }

        @Override
        @SuppressWarnings("all")
        public void write(FriendlyByteBuf buffer, FallbackIngredient fallback) {
            Ingredient ingredient = fallback.fallbacks;
            var custom = ingredient.getCustomIngredient();
            ResourceLocation id = custom == null ? new ResourceLocation("minecraft", "item") : custom.getSerializer().getIdentifier();
            buffer.writeResourceLocation(id);
            if (custom == null) {
                ingredient.toNetwork(buffer);
            } else {
                ((Serializer) custom.getSerializer()).write(buffer, fallback);
            }
        }
    }
}
