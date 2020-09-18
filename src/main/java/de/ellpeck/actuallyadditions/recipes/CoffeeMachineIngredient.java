package de.ellpeck.actuallyadditions.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;

public class CoffeeMachineIngredient implements IDummyRecipe{
    
    public static final IRecipeType<CoffeeMachineIngredient> COFFEE_MACHINE_RECIPE_TYPE = IRecipeType.register("actuallyadditions:coffee_machine");
    
    protected final ResourceLocation recipeId;
    protected final Ingredient input;
    protected final int maxAmplifier;
    protected final EffectInstance[] effects;
    
    public CoffeeMachineIngredient(ResourceLocation recipeId, Ingredient input, int maxAmplifier, EffectInstance[] effects){
        this.recipeId = recipeId;
        this.input = input;
        this.maxAmplifier = maxAmplifier;
        this.effects = effects;
    }
    
    public Ingredient getInput(){
        return input;
    }
    
    public int getMaxAmplifier(){
        return maxAmplifier;
    }
    
    public EffectInstance[] getEffects(){
        return effects;
    }
    
    @Override
    public ResourceLocation getId(){
        return this.recipeId;
    }
    
    @Override
    public IRecipeSerializer<?> getSerializer(){
        return CoffeeMachineIngredientFactory.INSTANCE;
    }
    
    @Override
    public IRecipeType<?> getType(){
        return COFFEE_MACHINE_RECIPE_TYPE;
    }
}
