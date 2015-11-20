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

import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReconstructorRecipeHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    public static final int RECIPES_FOR_BOOKLET_PAGE = 12;

    public static void init(){
        addRecipe("blockRedstone", "blockCrystalRed", 400);
        addRecipe("blockLapis", "blockCrystalBlue", 400);
        addRecipe("blockDiamond", "blockCrystalLightBlue", 6000);
        addRecipe("blockEmerald", "blockCrystalGreen", 10000);
        addRecipe("blockCoal", "blockCrystalBlack", 600);
        addRecipe("blockIron", "blockCrystalWhite", 800);

        addRecipe("dustRedstone", "crystalRed", 40);
        addRecipe("gemLapis", "crystalBlue", 40);
        addRecipe("gemDiamond", "crystalLightBlue", 600);
        addRecipe("gemEmerald", "crystalGreen", 1000);
        addRecipe("coal", "crystalBlack", 60);
        addRecipe("ingotIron", "crystalWhite", 80);

        if(ConfigCrafting.RECONSTRUCTOR_MISC.isEnabled()){
            addRecipe("sand", "soulSand", 20000);
            addRecipe("blockQuartz", "blockWhiteBrick", 10);
        }
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
