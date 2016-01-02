/*
 * This file ("Crusher.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.util.compat.minetweaker;

import de.ellpeck.actuallyadditions.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.util.ItemUtil;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.actuallyadditions.crusher")
public class Crusher{

    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack outputOne, @Optional IItemStack outputTwo, @Optional int outputTwoChance){
        CrusherRecipeRegistry.CrusherRecipe recipe = new CrusherRecipeRegistry.CrusherRecipe(MineTweakerMC.getItemStack(input), MineTweakerMC.getItemStack(outputOne), MineTweakerMC.getItemStack(outputTwo), outputTwoChance);
        MineTweakerAPI.apply(new Add(recipe));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack outputOne){
        MineTweakerAPI.apply(new Remove(MineTweakerMC.getItemStack(outputOne)));
    }

    private static class Add implements IUndoableAction{

        private CrusherRecipeRegistry.CrusherRecipe recipe;

        public Add(CrusherRecipeRegistry.CrusherRecipe recipe){
            this.recipe = recipe;
        }

        @Override
        public void apply(){
            CrusherRecipeRegistry.recipes.add(this.recipe);
        }

        @Override
        public boolean canUndo(){
            return true;
        }

        @Override
        public void undo(){
            CrusherRecipeRegistry.recipes.remove(this.recipe);
        }

        @Override
        public String describe(){
            return "Adding Crusher Recipe for "+this.recipe.getRecipeOutputOnes().get(0);
        }

        @Override
        public String describeUndo(){
            return "Removing added Crusher Recipe for "+this.recipe.getRecipeOutputOnes().get(0);
        }

        @Override
        public Object getOverrideKey(){
            return null;
        }
    }

    private static class Remove implements IUndoableAction{

        private final ItemStack output;
        private List<CrusherRecipeRegistry.CrusherRecipe> removedRecipes = new ArrayList<CrusherRecipeRegistry.CrusherRecipe>();

        public Remove(ItemStack output){
            this.output = output;
        }

        @Override
        public void apply(){
            this.removedRecipes.clear();

            for(CrusherRecipeRegistry.CrusherRecipe recipe : CrusherRecipeRegistry.recipes){
                if(ItemUtil.contains(recipe.getRecipeOutputOnes(), this.output, true)){
                    this.removedRecipes.add(recipe);
                }
            }
            CrusherRecipeRegistry.recipes.removeAll(this.removedRecipes);
        }

        @Override
        public boolean canUndo(){
            return true;
        }

        @Override
        public void undo(){
            CrusherRecipeRegistry.recipes.addAll(this.removedRecipes);
        }

        @Override
        public String describe(){
            return "Removing Crusher Recipe for "+this.output.getDisplayName();
        }

        @Override
        public String describeUndo(){
            return "Re-Adding Crusher Recipe for "+this.output.getDisplayName();
        }

        @Override
        public Object getOverrideKey(){
            return null;
        }
    }
}
