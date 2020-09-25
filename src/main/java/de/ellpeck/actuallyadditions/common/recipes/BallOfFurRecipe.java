package de.ellpeck.actuallyadditions.common.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

public class BallOfFurRecipe implements IDummyRecipe {
    
    public static final IRecipeType<BallOfFurRecipe> BALL_OF_FUR_RECIPE_TYPE = IRecipeType.register("actuallyadditions:ball_of_fur");
    
    private final ResourceLocation recipeId;
    
    private final ItemStack returnItem;
    private final int chance;
    
    public BallOfFurRecipe(ResourceLocation recipeId, ItemStack returnItem, int chance){
        this.recipeId = recipeId;
        this.returnItem = returnItem;
        this.chance = chance;
    }
    
    public ItemStack getReturnItem(){
        return this.returnItem;
    }
    
    public int getChance(){
        return this.chance;
    }
    
    @Override
    public ResourceLocation getId(){
        return this.recipeId;
    }
    
    @Override
    public IRecipeSerializer<?> getSerializer(){
        return BallOfFurRecipeFactory.INSTANCE;
    }
    
    @Override
    public IRecipeType<?> getType(){
        return BALL_OF_FUR_RECIPE_TYPE;
    }
}
