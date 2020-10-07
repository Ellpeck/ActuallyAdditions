package de.ellpeck.actuallyadditions.common.recipes;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A dummy Recipe implementation, because the minecraft recipe system forces us to use IRecipe with all of it's methods,
 * but we don't need any of them.
 * Additionally the "isDynamic()" methods now returns true, because this indicates that the recipe book doesn't list
 * this recipe at all.
 */
public interface IDummyRecipe<T extends IDummyRecipe<T>> extends IRecipe<IInventory>, IFinishedRecipe {
    
    @Nonnull
    @Override
    IRecipeSerializer<T> getSerializer();
    
    @Override
    default boolean matches(@Nonnull IInventory inv, @Nonnull World world){
        return false;
    }
    
    @Nonnull
    @Override
    default ItemStack getCraftingResult(@Nonnull IInventory inv){
        return ItemStack.EMPTY;
    }
    
    @Override
    default boolean canFit(int width, int height){
        return false;
    }
    
    @Nonnull
    @Override
    default ItemStack getRecipeOutput(){
        return ItemStack.EMPTY;
    }
    
    // used for ignoring the recipe book, since this prevents it from showing up.
    @Override
    default boolean isDynamic(){
        return true;
    }
    
    @Override
    default void serialize(@Nonnull JsonObject json){
        if(this.getSerializer() instanceof RecipeFactoryBase){
            (((RecipeFactoryBase<T>) this.getSerializer())).write(json, (T) this);
        }
    }
    
    @Nonnull
    @Override
    default ResourceLocation getID(){
        return this.getId();
    }
    
    @Nullable
    @Override
    default JsonObject getAdvancementJson(){
        return null;
    }
    
    @Nullable
    @Override
    default ResourceLocation getAdvancementID(){
        return null;
    }
}
