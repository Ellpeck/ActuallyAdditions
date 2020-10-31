package de.ellpeck.actuallyadditions.common.recipes;

import de.ellpeck.actuallyadditions.api.lens.Lens;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Recipe class for the AtomicReconstructor when hitting a items stack
 */
public class AtomicReconstructorItemRecipe implements IDummyRecipe<AtomicReconstructorItemRecipe> {
    
    public static final IRecipeType<AtomicReconstructorItemRecipe> ATOMIC_RECONSTRUCTOR_ITEM_RECIPE_TYPE = IRecipeType.register("actuallyadditions:atomic_reconstructor_item");
    
    @Nonnull private final ResourceLocation recipeId;
    
    @Nonnull private final Lens lens;
    @Nonnull private final Ingredient input;
    @Nonnull private final ItemStack output;
    private final int energyConsumption;
    
    public AtomicReconstructorItemRecipe(@Nonnull ResourceLocation recipeId, @Nonnull Lens lens, @Nonnull Ingredient input, @Nonnull ItemStack output, int energyConsumption){
        this.recipeId = recipeId;
        this.lens = lens;
        this.input = input;
        this.output = output;
        this.energyConsumption = energyConsumption;
    }
    
    @Nonnull
    public Lens getLens(){
        return lens;
    }
    
    @Nonnull
    public Ingredient getInput(){
        return input;
    }
    
    @Nonnull
    public ItemStack getOutput(){
        return output;
    }
    
    public int getEnergyConsumption(){
        return energyConsumption;
    }
    
    @Nonnull
    @Override
    public ResourceLocation getId(){
        return this.recipeId;
    }
    
    @Nonnull
    @Override
    public IRecipeSerializer<AtomicReconstructorItemRecipe> getSerializer(){
        return AtomicReconstructorItemRecipeFactory.INSTANCE;
    }
    
    @Nonnull
    @Override
    public IRecipeType<?> getType(){
        return ATOMIC_RECONSTRUCTOR_ITEM_RECIPE_TYPE;
    }
}
