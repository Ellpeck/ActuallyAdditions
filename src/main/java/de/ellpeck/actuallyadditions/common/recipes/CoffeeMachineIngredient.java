package de.ellpeck.actuallyadditions.common.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class CoffeeMachineIngredient implements IDummyRecipe<CoffeeMachineIngredient> {
    
    public static final IRecipeType<CoffeeMachineIngredient> COFFEE_MACHINE_RECIPE_TYPE = IRecipeType.register("actuallyadditions:coffee_machine");
    
    @Nonnull private final ResourceLocation recipeId;
    @Nonnull private final Ingredient input;
    private final int maxAmplifier;
    @Nonnull private final EffectInstance[] effects;
    
    public CoffeeMachineIngredient(@Nonnull ResourceLocation recipeId, @Nonnull Ingredient input, int maxAmplifier, @Nonnull EffectInstance[] effects){
        this.recipeId = recipeId;
        this.input = input;
        this.maxAmplifier = maxAmplifier;
        this.effects = effects;
    }
    
    @Nonnull
    public Ingredient getInput(){
        return input;
    }
    
    public int getMaxAmplifier(){
        return maxAmplifier;
    }
    
    @Nonnull
    public EffectInstance[] getEffects(){
        return effects;
    }
    
    @Nonnull
    @Override
    public ResourceLocation getId(){
        return this.recipeId;
    }
    
    @Nonnull
    @Override
    public IRecipeSerializer<CoffeeMachineIngredient> getSerializer(){
        return CoffeeMachineIngredientFactory.INSTANCE;
    }
    
    @Nonnull
    @Override
    public IRecipeType<?> getType(){
        return COFFEE_MACHINE_RECIPE_TYPE;
    }
}
