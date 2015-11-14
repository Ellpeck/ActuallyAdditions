/*
 * This file ("AtomicReconstructorRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Objects;

public class AtomicReconstructorRecipeHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public static void init(){
        addRecipe("blockRedstone", "blockCrystalRed");
        addRecipe("blockLapis", "blockCrystalBlue");
        addRecipe("blockDiamond", "blockCrystalLightBlue");
        addRecipe("blockEmerald", "blockCrystalGreen");
        addRecipe("blockCoal", "blockCrystalBlack");

        addRecipe("dustRedstone", "crystalRed");
        addRecipe("gemLapis", "crystalBlue");
        addRecipe("gemDiamond", "crystalLightBlue");
        addRecipe("gemEmerald", "crystalGreen");
        addRecipe("coal", "crystalBlack");
    }

    public static void addRecipe(String input, String output){
        recipes.add(new Recipe(input, output));
    }

    public static Recipe getRecipe(ItemStack input){
        for(Recipe recipe : recipes){
            int[] ids = OreDictionary.getOreIDs(input);
            for(int id : ids){
                if(Objects.equals(OreDictionary.getOreName(id), recipe.input)){
                    return recipe;
                }
            }
        }
        return null;
    }

    public static class Recipe{

        public String input;
        public String output;

        public Recipe(String input, String output){
            this.input = input;
            this.output = output;
        }

        public ItemStack getFirstOutput(){
            ArrayList<ItemStack> stacks = OreDictionary.getOres(this.output);
            if(stacks != null && !stacks.isEmpty()){
                return stacks.get(0);
            }
            return null;
        }
    }

}
