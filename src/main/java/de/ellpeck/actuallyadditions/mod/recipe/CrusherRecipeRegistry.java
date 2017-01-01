/*
 * This file ("CrusherRecipeRegistry.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.recipe;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;


public final class CrusherRecipeRegistry{

    public static final ArrayList<SearchCase> SEARCH_CASES = new ArrayList<SearchCase>();

    public static void registerFinally(){
        ArrayList<String> oresNoResult = new ArrayList<String>();
        int recipeStartedAt = ActuallyAdditionsAPI.CRUSHER_RECIPES.size();

        for(String ore : OreDictionary.getOreNames()){
            if(!hasException(ore)){
                for(SearchCase theCase : SEARCH_CASES){
                    if(ore.length() > theCase.theCase.length()){
                        if(ore.substring(0, theCase.theCase.length()).equals(theCase.theCase)){
                            String output = theCase.resultPreString+ore.substring(theCase.theCase.length());

                            if(!ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres(ore, false), OreDictionary.getOres(output, false), theCase.resultAmount, null, 0, 0)){
                                if(!oresNoResult.contains(ore)){
                                    oresNoResult.add(ore);
                                }
                            }
                        }
                    }
                }
            }
        }

        ArrayList<String> addedRecipes = new ArrayList<String>();
        for(int i = recipeStartedAt; i < ActuallyAdditionsAPI.CRUSHER_RECIPES.size(); i++){
            CrusherRecipe recipe = ActuallyAdditionsAPI.CRUSHER_RECIPES.get(i);
            addedRecipes.add(recipe.inputStack+" -> "+recipe.outputOneStack);
        }
        ModUtil.LOGGER.info("Added "+addedRecipes.size()+" Crusher Recipes automatically: "+addedRecipes);
        ModUtil.LOGGER.warn("Couldn't add "+oresNoResult.size()+" Crusher Recipes automatically, either because the inputs were missing outputs, or because they exist already: "+oresNoResult);
    }

    public static boolean hasBlacklistedOutput(ItemStack output, String[] config){
        if(StackUtil.isValid(output)){
            Item item = output.getItem();
            if(item != null){
                String reg = item.getRegistryName().toString();

                for(String conf : config){
                    String confReg = conf;
                    int meta = 0;

                    if(conf.contains("@")){
                        try{
                            String[] split = conf.split("@");
                            confReg = split[0];
                            meta = Integer.parseInt(split[1]);
                        }
                        catch(Exception e){
                            ModUtil.LOGGER.warn("A config option appears to be incorrect: The entry "+conf+" can't be parsed!");
                        }
                    }

                    if(reg.equals(confReg) && output.getItemDamage() == meta){
                        return true;
                    }
                }

                return false;
            }
        }
        return true;
    }

    private static boolean hasException(String ore){
        for(String conf : ConfigStringListValues.CRUSHER_RECIPE_EXCEPTIONS.getValue()){
            if(conf.equals(ore)){
                return true;
            }
        }
        return false;
    }

    public static ItemStack getOutputOnes(ItemStack input){
        CrusherRecipe recipe = getRecipeFromInput(input);
        return recipe == null ? null : recipe.outputOneStack;
    }

    public static CrusherRecipe getRecipeFromInput(ItemStack input){
        for(CrusherRecipe recipe : ActuallyAdditionsAPI.CRUSHER_RECIPES){
            if(ItemUtil.areItemsEqual(recipe.inputStack, input, true)){
                return recipe;
            }
        }
        return null;
    }

    public static ItemStack getOutputTwos(ItemStack input){
        CrusherRecipe recipe = getRecipeFromInput(input);
        return recipe == null ? null : recipe.outputTwoStack;
    }

    public static int getOutputTwoChance(ItemStack input){
        CrusherRecipe recipe = getRecipeFromInput(input);
        return recipe == null ? -1 : recipe.outputTwoChance;
    }

    public static class SearchCase{

        final String theCase;
        final int resultAmount;
        final String resultPreString;

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
