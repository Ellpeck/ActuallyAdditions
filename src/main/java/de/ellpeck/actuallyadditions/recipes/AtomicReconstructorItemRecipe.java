package de.ellpeck.actuallyadditions.recipes;

import de.ellpeck.actuallyadditions.api.lens.Lens;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

/**
 * Recipe class for the AtomicReconstructor when hitting a items stack
 */
public class AtomicReconstructorItemRecipe implements IDummyRecipe {
    
    public static final IRecipeType<AtomicReconstructorItemRecipe> ATOMIC_RECONSTRUCTOR_ITEM_RECIPE_TYPE = IRecipeType.register("actuallyadditions:atomic_reconstructor_item");
    
    private final ResourceLocation recipeId;
    
    private final Lens lens;
    private final Ingredient input;
    private final ItemStack output;
    private final int energyConsumption;
    
    public AtomicReconstructorItemRecipe(ResourceLocation recipeId, Lens lens, Ingredient input, ItemStack output, int energyConsumption){
        this.recipeId = recipeId;
        this.lens = lens;
        this.input = input;
        this.output = output;
        this.energyConsumption = energyConsumption;
    }
    
    public Lens getLens(){
        return lens;
    }
    
    public Ingredient getInput(){
        return input;
    }
    
    public ItemStack getOutput(){
        return output;
    }
    
    public int getEnergyConsumption(){
        return energyConsumption;
    }
    
    @Override
    public ResourceLocation getId(){
        return this.recipeId;
    }
    
    @Override
    public IRecipeSerializer<?> getSerializer(){
        return AtomicReconstructorItemRecipeFactory.INSTANCE;
    }
    
    @Override
    public IRecipeType<?> getType(){
        return ATOMIC_RECONSTRUCTOR_ITEM_RECIPE_TYPE;
    }
}
