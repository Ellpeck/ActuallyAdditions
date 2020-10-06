package de.ellpeck.actuallyadditions.common.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class CrusherRecipe implements IDummyRecipe {
    
    public static final IRecipeType<CrusherRecipe> CRUSHER_RECIPE_TYPE = IRecipeType.register("actuallyadditions:crusher");
    
    @Nonnull private final ResourceLocation recipeId;
    
    @Nonnull private final Ingredient input;
    @Nonnull private final ItemStack output;
    @Nonnull private final ItemStack secondaryOutput;
    private final int outputChance;
    
    public CrusherRecipe(@Nonnull ResourceLocation recipeId, @Nonnull Ingredient input, @Nonnull ItemStack output, @Nonnull ItemStack secondaryOutput, int outputChance){
        this.recipeId = recipeId;
        this.input = input;
        this.output = output;
        this.secondaryOutput = secondaryOutput;
        this.outputChance = outputChance;
    }
    
    @Nonnull
    public Ingredient getInput(){
        return input;
    }
    
    @Nonnull
    public ItemStack getOutput(){
        return output;
    }
    
    @Nonnull
    public ItemStack getSecondaryOutput(){
        return secondaryOutput;
    }
    
    public int getOutputChance(){
        return outputChance;
    }
    
    @Nonnull
    @Override
    public ResourceLocation getId(){
        return this.recipeId;
    }
    
    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer(){
        return CrusherRecipeFactory.INSTANCE;
    }
    
    @Nonnull
    @Override
    public IRecipeType<?> getType(){
        return CRUSHER_RECIPE_TYPE;
    }
}
