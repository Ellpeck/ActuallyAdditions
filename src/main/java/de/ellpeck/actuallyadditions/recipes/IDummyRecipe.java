package de.ellpeck.actuallyadditions.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * A dummy Recipe implementation, because the minecraft recipe system forces us to use IRecipe with all of it's methods,
 * but we don't need any of them.
 * Additionally the "isDynamic()" methods now returns true, because this indicates that the recipe book doesn't list
 * this recipe at all.
 */
public interface IDummyRecipe extends IRecipe<IInventory> {
    
    @Override
    default boolean matches(IInventory inv, World worldIn){
        return false;
    }
    
    @Override
    default ItemStack getCraftingResult(IInventory inv){
        return ItemStack.EMPTY;
    }
    
    @Override
    default boolean canFit(int width, int height){
        return false;
    }
    
    @Override
    default ItemStack getRecipeOutput(){
        return ItemStack.EMPTY;
    }
    
    // used for ignoring the recipe book, since this prevents it from showing up.
    @Override
    default boolean isDynamic(){
        return true;
    }
    
}
