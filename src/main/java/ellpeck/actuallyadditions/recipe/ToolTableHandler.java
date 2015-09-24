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

import ellpeck.actuallyadditions.items.tools.table.IToolTableRepairItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ToolTableHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public static void addRecipe(ItemStack output, ItemStack... itemsNeeded){
        recipes.add(new Recipe(output, itemsNeeded));
    }

    public static ItemStack getResultFromSlots(ItemStack[] slots){
        Recipe recipe = getRecipeFromSlots(slots);
        return recipe == null ? null : recipe.output;
    }

    public static Recipe getRecipeFromSlots(ItemStack[] slots){
        //Normal Recipes
        for(Recipe recipe : recipes){
            if(ItemUtil.containsAll(slots, recipe.itemsNeeded)){
                return recipe;
            }
        }

        //Repair Recipes
        for(ItemStack slot : slots){
            if(slot != null && slot.getItem() instanceof IToolTableRepairItem){
                if(ItemUtil.contains(slots, ((IToolTableRepairItem)slot.getItem()).getRepairStack()) && slot.getItemDamage() > 0){
                    ItemStack returnStack = slot.copy();
                    returnStack.setItemDamage(Math.max(0, returnStack.getItemDamage()-((IToolTableRepairItem)returnStack.getItem()).repairPerStack()));
                    return new Recipe(returnStack, slot, ((IToolTableRepairItem)returnStack.getItem()).getRepairStack());
                }
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
