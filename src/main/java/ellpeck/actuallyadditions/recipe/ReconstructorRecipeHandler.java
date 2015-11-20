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
import java.util.List;
import java.util.Objects;

public class ReconstructorRecipeHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public static void init(){
        addRecipe("blockRedstone", "blockCrystalRed", 200);
        addRecipe("blockLapis", "blockCrystalBlue", 200);
        addRecipe("blockDiamond", "blockCrystalLightBlue", 600);
        addRecipe("blockEmerald", "blockCrystalGreen", 1000);
        addRecipe("blockCoal", "blockCrystalBlack", 400);
        addRecipe("blockIron", "blockCrystalWhite", 300);

        addRecipe("dustRedstone", "crystalRed", 20);
        addRecipe("gemLapis", "crystalBlue", 20);
        addRecipe("gemDiamond", "crystalLightBlue", 60);
        addRecipe("gemEmerald", "crystalGreen", 100);
        addRecipe("coal", "crystalBlack", 40);
        addRecipe("ingotIron", "crystalWhite", 30);
    }

    public static void addRecipe(String input, String output, int energyUse){
        recipes.add(new Recipe(input, output, energyUse));
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
        public int energyUse;

        public Recipe(String input, String output, int energyUse){
            this.input = input;
            this.output = output;
            this.energyUse = energyUse;
        }

        public ItemStack getFirstOutput(){
            List<ItemStack> stacks = OreDictionary.getOres(this.output, false);
            if(stacks != null && !stacks.isEmpty()){
                return stacks.get(0);
            }
            return null;
        }
    }

}
