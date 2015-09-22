/*
 * This file ("ToolTableHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.recipe;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.ItemUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ToolTableHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public static void init(){
        //TODO Actual real recipes
        addRecipe(new ItemStack(InitItems.itemPhantomConnector), new ItemStack(Items.diamond_pickaxe), new ItemStack(Blocks.stone_brick_stairs), new ItemStack(Blocks.planks));
    }

    private static void addRecipe(ItemStack output, ItemStack... itemsNeeded){
        recipes.add(new Recipe(output, itemsNeeded));
    }

    public static ItemStack getResultFromSlots(ItemStack[] slots){
        Recipe recipe = getRecipeFromSlots(slots);
        return recipe == null ? null : recipe.output;
    }

    public static Recipe getRecipeFromSlots(ItemStack[] slots){
        for(Recipe recipe : recipes){
            if(ItemUtil.containsAll(slots, recipe.itemsNeeded)){
                return recipe;
            }
        }
        return null;
    }

    public static boolean isIngredient(ItemStack stack){
        for(Recipe recipe : recipes){
            if(stack != null){
                for(ItemStack aStack : recipe.itemsNeeded){
                    if(aStack != null && stack.isItemEqual(aStack)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static class Recipe{

        public ItemStack[] itemsNeeded;
        public ItemStack output;

        public Recipe(ItemStack output, ItemStack... itemsNeeded){
            this.output = output;
            this.itemsNeeded = itemsNeeded;
        }

    }

}
