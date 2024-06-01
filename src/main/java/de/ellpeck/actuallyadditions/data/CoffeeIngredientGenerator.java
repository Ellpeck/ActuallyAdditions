package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.CoffeeIngredientRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;

import javax.annotation.Nonnull;

public class CoffeeIngredientGenerator extends RecipeProvider {
    public CoffeeIngredientGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Coffee Ingredient " + super.getName();
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput recipeOutput) {
        buildIngredient(recipeOutput, Items.MILK_BUCKET, 0, "jei.actuallyadditions.coffee.extra.milk");

        //Pam's puts milk in a tag, so we'll use that
        TagKey<Item> milkTag = ItemTags.create(new ResourceLocation("forge", "milk"));
        RecipeOutput tagOutput = recipeOutput.withConditions(new NotCondition(new TagEmptyCondition(milkTag.location())));
        buildIngredient(tagOutput, new ResourceLocation(ActuallyAdditions.MODID, "coffee_ingredient/milk_tagged"),
                Ingredient.of(milkTag), 0, "jei.actuallyadditions.coffee.extra.milk");

        buildIngredient(recipeOutput, Items.SUGAR, 4, new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30, 0));
        buildIngredient(recipeOutput, Items.MAGMA_CREAM, 2, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 0));
        buildIngredient(recipeOutput, Items.PUFFERFISH, 2, new MobEffectInstance(MobEffects.WATER_BREATHING, 10, 0));
        buildIngredient(recipeOutput, Items.GOLDEN_CARROT, 2, new MobEffectInstance(MobEffects.NIGHT_VISION, 30, 0));
        buildIngredient(recipeOutput, Items.GHAST_TEAR, 3, new MobEffectInstance(MobEffects.REGENERATION, 5, 0));
        buildIngredient(recipeOutput, Items.BLAZE_POWDER, 4, new MobEffectInstance(MobEffects.DAMAGE_BOOST, 15, 0));
        buildIngredient(recipeOutput, Items.FERMENTED_SPIDER_EYE, 2, new MobEffectInstance(MobEffects.INVISIBILITY, 25, 0));
    }

    private void buildIngredient(RecipeOutput recipeOutput, ItemLike ingredient, int maxAmplifier, MobEffectInstance... effects) {
        buildIngredient(recipeOutput, ingredient, maxAmplifier, "", effects);
    }

    private void buildIngredient(RecipeOutput recipeOutput, ItemLike ingredient, int maxAmplifier, String extraText, MobEffectInstance... effects) {
        ResourceLocation id = new ResourceLocation(ActuallyAdditions.MODID, "coffee_ingredient/" + getItemName(ingredient));
        NonNullList<CoffeeIngredientRecipe.EffectInstance> instances = NonNullList.create();
        for (MobEffectInstance effect : effects) {
            instances.add(new CoffeeIngredientRecipe.EffectInstance(effect));
        }
        CoffeeIngredientRecipe recipe = new CoffeeIngredientRecipe(Ingredient.of(ingredient), instances, maxAmplifier, extraText);
        recipeOutput.accept(id, recipe, null);
    }

    private void buildIngredient(RecipeOutput recipeOutput, ResourceLocation id, Ingredient ingredient, int maxAmplifier, MobEffectInstance... effects) {
        buildIngredient(recipeOutput, id, ingredient, maxAmplifier, "", effects);
    }

    private void buildIngredient(RecipeOutput recipeOutput, ResourceLocation id, Ingredient ingredient, int maxAmplifier, String extraText, MobEffectInstance... effects) {
        NonNullList<CoffeeIngredientRecipe.EffectInstance> instances = NonNullList.create();
        for (MobEffectInstance effect : effects) {
            instances.add(new CoffeeIngredientRecipe.EffectInstance(effect));
        }
        CoffeeIngredientRecipe recipe = new CoffeeIngredientRecipe(ingredient, instances, maxAmplifier, extraText);
        recipeOutput.accept(id, recipe, null);
    }
}
