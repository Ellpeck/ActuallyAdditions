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
import java.util.Collections;
import java.util.List;


public class CrusherRecipeRegistry{

    public static ArrayList<CrusherRecipe> recipes = new ArrayList<CrusherRecipe>();
    public static ArrayList<SearchCase> searchCases = new ArrayList<SearchCase>();

    public static void addRecipe(String input, String outputOne, int outputOneAmount, String outputTwo, int outputTwoAmount, int outputTwoChance){
        if(!OreDictionary.getOres(input, false).isEmpty() && !OreDictionary.getOres(outputOne, false).isEmpty() && (outputTwo == null || outputTwo.isEmpty() || !OreDictionary.getOres(outputTwo, false).isEmpty())){
            recipes.add(new CrusherRecipe(input, outputOne, outputOneAmount, outputTwo, outputTwoAmount, outputTwoChance));
        }
    }

    public static void registerFinally(){
        ArrayList<String> oresNoResult = new ArrayList<String>();
        int recipeStartedAt = recipes.size();

        for(String ore : OreDictionary.getOreNames()){
            if(!hasException(ore)){
                for(SearchCase theCase : searchCases){
                    if(ore.length() > theCase.theCase.length()){
                        if(ore.substring(0, theCase.theCase.length()).equals(theCase.theCase)){
                            String output = theCase.resultPreString+ore.substring(theCase.theCase.length());

                            if(!hasOreRecipe(ore)){
                                if(!OreDictionary.getOres(output, false).isEmpty() && !OreDictionary.getOres(ore, false).isEmpty()){
                                    addRecipe(ore, output, theCase.resultAmount);
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
        ModUtil.LOGGER.info("Added "+addedRecipes.size()+" Crusher Recipes automatically: "+addedRecipes.toString());
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

    public static boolean hasOreRecipe(String input){
        for(CrusherRecipe recipe : recipes){
            if(recipe.input != null && recipe.input.equals(input)){
                return true;
            }
        }
        return false;
    }

    public static void addRecipe(ItemStack input, ItemStack outputOne){
        addRecipe(input, outputOne, null, 0);
    }

    public static void addRecipe(ItemStack input, String outputOne, int outputOneAmount){
        if(!OreDictionary.getOres(outputOne, false).isEmpty()){
            recipes.add(new CrusherRecipe(input, outputOne, outputOneAmount));
        }
    }

    public static void addRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int outputTwoChance){
        recipes.add(new CrusherRecipe(input, outputOne, outputTwo, outputTwoChance));
    }

    public static void addRecipe(String input, String outputOne, int outputOneAmount){
        addRecipe(input, outputOne, outputOneAmount, "", 0, 0);
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

        private String input;

        private String outputOne;
        private int outputOneAmount;

        private String outputTwo;
        private int outputTwoAmount;
        public int outputTwoChance;

        private ItemStack inputStack;
        private ItemStack outputOneStack;
        private ItemStack outputTwoStack;

        public CrusherRecipe(ItemStack input, String outputOne, int outputOneAmount){
            this.inputStack = input;
            this.outputOne = outputOne;
            this.outputOneAmount = outputOneAmount;
        }

        public CrusherRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int outputTwoChance){
            this.inputStack = input;
            this.outputOneStack = outputOne;
            this.outputTwoStack = outputTwo;
            this.outputTwoChance = outputTwoChance;
        }

        public CrusherRecipe(String input, String outputOne, int outputOneAmount, String outputTwo, int outputTwoAmount, int outputTwoChance){
            this.input = input;
            this.outputOne = outputOne;
            this.outputOneAmount = outputOneAmount;
            this.outputTwo = outputTwo;
            this.outputTwoAmount = outputTwoAmount;
            this.outputTwoChance = outputTwoChance;
        }

        public List<ItemStack> getRecipeOutputOnes(){
            if(this.outputOneStack != null){
                return Collections.singletonList(this.outputOneStack.copy());
            }

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
            if(this.outputTwoStack != null){
                return Collections.singletonList(this.outputTwoStack.copy());
            }

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
            if(this.inputStack != null){
                return Collections.singletonList(this.inputStack.copy());
            }

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
