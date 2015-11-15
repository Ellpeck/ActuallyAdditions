/*
 * This file ("CrusherRecipeRegistry.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.recipe;

import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;


public class CrusherRecipeRegistry{

    public static ArrayList<CrusherRecipe> recipes = new ArrayList<CrusherRecipe>();
    public static ArrayList<SearchCase> searchCases = new ArrayList<SearchCase>();

    public static void addRecipe(String input, String outputOne, int outputOneAmount, String outputTwo, int outputTwoAmount, int outputTwoChance){
        recipes.add(new CrusherRecipe(input, outputOne, outputOneAmount, outputTwo, outputTwoAmount, outputTwoChance));
    }

    public static void registerFinally(){
        ArrayList<String> oresNoResult = new ArrayList<String>();
        int recipesAdded = 0;
        int recipeStartedAt = recipes.size();

        for(String ore : OreDictionary.getOreNames()){
            if(!hasException(ore)){
                for(SearchCase theCase : searchCases){
                    if(ore.length() > theCase.theCase.length()){
                        if(ore.substring(0, theCase.theCase.length()).equals(theCase.theCase)){
                            String output = theCase.resultPreString+ore.substring(theCase.theCase.length());

                            if(!hasRecipe(ore)){
                                if(!OreDictionary.getOres(output, false).isEmpty() && !OreDictionary.getOres(ore, false).isEmpty()){
                                    addRecipe(ore, output, theCase.resultAmount);
                                    recipesAdded++;
                                }
                                else{
                                    oresNoResult.add(ore);
                                }
                            }

                            break;
                        }
                    }
                }
            }
        }

        ArrayList<String> addedRecipes = new ArrayList<String>();
        for(int i = recipeStartedAt; i < recipes.size(); i++){
            CrusherRecipe recipe = recipes.get(i);
            addedRecipes.add(recipe.input+" -> "+recipe.outputOneAmount+"x "+recipe.outputOne);
        }
        ModUtil.LOGGER.info("Added "+recipesAdded+" Crusher Recipes automatically: "+addedRecipes.toString());
        ModUtil.LOGGER.warn("Couldn't add "+oresNoResult.size()+" Crusher Recipes automatically because the inputs were missing outputs: "+oresNoResult.toString());
    }

    private static boolean hasException(String ore){
        for(String conf : ConfigValues.crusherRecipeExceptions){
            if(conf.equals(ore)){
                return true;
            }
        }
        return false;
    }

    public static boolean hasRecipe(String input){
        for(CrusherRecipe recipe : recipes){
            if(recipe.input.equals(input)){
                return true;
            }
        }
        return false;
    }

    public static void addRecipe(String input, String outputOne, int outputOneAmount){
        recipes.add(new CrusherRecipe(input, outputOne, outputOneAmount, "", 0, 0));
    }

    public static List<ItemStack> getOutputOnes(ItemStack input){
        CrusherRecipe recipe = getRecipeFromInput(input);
        return recipe == null ? null : recipe.getRecipeOutputOnes();
    }

    public static CrusherRecipe getRecipeFromInput(ItemStack input){
        for(CrusherRecipe recipe : recipes){
            if(ItemUtil.contains(recipe.getRecipeInputs(), input, true)){
                return recipe;
            }
        }
        return null;
    }

    public static List<ItemStack> getOutputTwos(ItemStack input){
        CrusherRecipe recipe = getRecipeFromInput(input);
        return recipe == null ? null : recipe.getRecipeOutputTwos();
    }

    public static int getOutputTwoChance(ItemStack input){
        CrusherRecipe recipe = getRecipeFromInput(input);
        return recipe == null ? -1 : recipe.outputTwoChance;
    }

    public static class CrusherRecipe{

        public String input;

        public String outputOne;
        public int outputOneAmount;

        public String outputTwo;
        public int outputTwoAmount;
        public int outputTwoChance;

        public CrusherRecipe(String input, String outputOne, int outputOneAmount, String outputTwo, int outputTwoAmount, int outputTwoChance){
            this.input = input;
            this.outputOne = outputOne;
            this.outputOneAmount = outputOneAmount;
            this.outputTwo = outputTwo;
            this.outputTwoAmount = outputTwoAmount;
            this.outputTwoChance = outputTwoChance;
        }

        public List<ItemStack> getRecipeOutputOnes(){
            if(this.outputOne == null || this.outputOne.isEmpty()){
                return null;
            }

            List<ItemStack> stacks = OreDictionary.getOres(this.outputOne, false);
            for(ItemStack stack : stacks){
                stack.stackSize = this.outputOneAmount;
            }
            return stacks;
        }

        public List<ItemStack> getRecipeOutputTwos(){
            if(this.outputTwo == null || this.outputTwo.isEmpty()){
                return null;
            }

            List<ItemStack> stacks = OreDictionary.getOres(this.outputTwo, false);
            for(ItemStack stack : stacks){
                stack.stackSize = this.outputTwoAmount;
            }
            return stacks;
        }

        public List<ItemStack> getRecipeInputs(){
            if(this.input == null || this.input.isEmpty()){
                return null;
            }

            List<ItemStack> stacks = OreDictionary.getOres(this.input, false);
            for(ItemStack stack : stacks){
                stack.stackSize = 1;
            }
            return stacks;
        }
    }

    public static class SearchCase{

        String theCase;
        int resultAmount;
        String resultPreString;

        public SearchCase(String theCase, int resultAmount){
            this(theCase, resultAmount, "dust");
        }

        public SearchCase(String theCase, int resultAmount, String resultPreString){
            this.theCase = theCase;
            this.resultAmount = resultAmount;
            this.resultPreString = resultPreString;
        }
    }

}
