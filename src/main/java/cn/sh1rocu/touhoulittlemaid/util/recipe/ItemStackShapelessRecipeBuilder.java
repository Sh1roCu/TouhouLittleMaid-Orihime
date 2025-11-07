package cn.sh1rocu.touhoulittlemaid.util.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ItemStackShapelessRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStack resultStack;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public ItemStackShapelessRecipeBuilder(RecipeCategory category, ItemStack resultStack) {
        this.category = category;
        this.resultStack = resultStack;
    }

    public static ItemStackShapelessRecipeBuilder shapeless(RecipeCategory category, ItemStack resultStack) {
        return new ItemStackShapelessRecipeBuilder(category, resultStack);
    }

    public ItemStackShapelessRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public ItemStackShapelessRecipeBuilder requires(ItemLike item) {
        return this.requires(item, 1);
    }

    public ItemStackShapelessRecipeBuilder requires(ItemLike item, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            this.requires(Ingredient.of(item));
        }

        return this;
    }

    public ItemStackShapelessRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public ItemStackShapelessRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public @NotNull ItemStackShapelessRecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public @NotNull ItemStackShapelessRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return resultStack.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        ShapelessRecipe shapelessrecipe = new ShapelessRecipe(
                Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category),
                this.resultStack,
                this.ingredients
        );
        recipeOutput.accept(id, shapelessrecipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
