package de.ellpeck.actuallyadditions.common.recipes;

import de.ellpeck.actuallyadditions.api.lens.Lens;
import net.minecraft.block.BlockState;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

/**
 * Recipe class for the AtomicReconstructor when hitting a items stack
 */
public class AtomicReconstructorBlockRecipe implements IDummyRecipe {
    
    public static final IRecipeType<AtomicReconstructorBlockRecipe> ATOMIC_RECONSTRUCTOR_BLOCK_RECIPE_TYPE = IRecipeType.register("actuallyadditions:atomic_reconstructor_block");
    
    private final ResourceLocation recipeId;
    
    private final Lens lens;
    private final BlockState input;
    private final BlockState output;
    private final int energyConsumption;
    
    public AtomicReconstructorBlockRecipe(ResourceLocation recipeId, Lens lens, BlockState input, BlockState output, int energyConsumption){
        this.recipeId = recipeId;
        this.lens = lens;
        this.input = input;
        this.output = output;
        this.energyConsumption = energyConsumption;
    }
    
    public Lens getLens(){
        return lens;
    }
    
    public BlockState getInput(){
        return input;
    }
    
    public BlockState getOutput(){
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
        return AtomicReconstructorBlockRecipeFactory.INSTANCE;
    }
    
    @Override
    public IRecipeType<?> getType(){
        return ATOMIC_RECONSTRUCTOR_BLOCK_RECIPE_TYPE;
    }
}
