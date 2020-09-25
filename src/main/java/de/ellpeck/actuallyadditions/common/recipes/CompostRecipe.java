package de.ellpeck.actuallyadditions.common.recipes;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class CompostRecipe implements IDummyRecipe {
    
    public static final IRecipeType<CompostRecipe> COMPOST_RECIPE_TYPE = IRecipeType.register("actuallyadditions:compost");
    
    private final ResourceLocation recipeId;
    
    private final Ingredient input;
    private final ItemStack output;
    private final BlockState inputDisplay;
    private final BlockState outputDisplay;
    
    public CompostRecipe(ResourceLocation recipeId, Ingredient input, ItemStack output, BlockState inputDisplay, BlockState outputDisplay){
        this.recipeId = recipeId;
        this.input = input;
        this.output = output;
        this.inputDisplay = inputDisplay;
        this.outputDisplay = outputDisplay;
    }
    
    public Ingredient getInput(){
        return this.input;
    }
    
    public ItemStack getOutput(){
        return this.output;
    }
    
    public BlockState getInputDisplay(){
        return this.inputDisplay;
    }
    
    public BlockState getOutputDisplay(){
        return this.outputDisplay;
    }
    
    @Override
    public ResourceLocation getId(){
        return this.recipeId;
    }
    
    @Override
    public IRecipeSerializer<?> getSerializer(){
        return CompostRecipeFactory.INSTANCE;
    }
    
    @Override
    public IRecipeType<?> getType(){
        return COMPOST_RECIPE_TYPE;
    }
}
