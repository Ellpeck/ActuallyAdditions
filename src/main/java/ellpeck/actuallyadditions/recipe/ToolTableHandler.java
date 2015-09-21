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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ToolTableHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public static void init(){
        addRecipe(new ItemStack(Items.diamond_pickaxe), new ItemStack(InitItems.itemPhantomConnector), new ItemStack(Blocks.stone_brick_stairs), new ItemStack(Blocks.planks));
    }

    private static void addRecipe(ItemStack tool, ItemStack output, ItemStack... itemsNeeded){
        recipes.add(new Recipe(tool, output, itemsNeeded));
    }

    public static class Recipe{

        public ItemStack tool;
        public ItemStack[] itemsNeeded;
        public ItemStack output;

        public Recipe(ItemStack tool, ItemStack output, ItemStack... itemsNeeded){
            this.tool = tool;
            this.output = output;
            this.itemsNeeded = itemsNeeded;
        }

    }

}
