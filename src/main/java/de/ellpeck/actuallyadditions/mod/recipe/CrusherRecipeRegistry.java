/*
 * This file ("CrusherRecipeRegistry.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.recipe;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;


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

                            if(!hasOreRecipe(ore)){
                                List<ItemStack> outputs = OreDictionary.getOres(output, false);
                                if(!outputs.isEmpty() && !OreDictionary.getOres(ore, false).isEmpty()){
                                    for(ItemStack stack : outputs){
                                        if(!hasBlacklistedOutput(stack)){
                                            ItemStack copy = stack.copy();
                                            copy.stackSize = theCase.resultAmount;
                                            ActuallyAdditionsAPI.addCrusherRecipe(ore, copy);
                                        }
                                        else if(!oresNoResult.contains(ore)){
                                            oresNoResult.add(ore);
                                        }
                                    }
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
        for(int i = recipeStartedAt; i < ActuallyAdditionsAPI.CRUSHER_RECIPES.size(); i++){
            CrusherRecipe recipe = ActuallyAdditionsAPI.CRUSHER_RECIPES.get(i);
            addedRecipes.add(recipe.input+" -> "+recipe.outputOneStack);
        }
        ModUtil.LOGGER.info("Added "+addedRecipes.size()+" Crusher Recipes automatically: "+addedRecipes.toString());
        ModUtil.LOGGER.warn("Couldn't add "+oresNoResult.size()+" Crusher Recipes automatically because the inputs were missing outputs: "+oresNoResult.toString());
    }

    private static boolean hasBlacklistedOutput(ItemStack output){
        if(output != null){
            Item item = output.getItem();
            if(item != null){
                String reg = item.getRegistryName().toString();

                for(String conf : ConfigStringListValues.CRUSHER_OUTPUT_BLACKLIST.getValue()){
                    String confReg = conf;
                    int meta = 0;

                    if(conf.contains("@")){
                        try{
                            String[] split = conf.split("@");
                            confReg = split[0];
                            meta = Integer.parseInt(split[1]);
                        }
                        catch(Exception e){
                            ModUtil.LOGGER.warn("A config option appears to be incorrect: The Crusher Output Blacklist entry "+conf+" can't be parsed!");
                        }
                    }

                    if(reg.equals(confReg) && output.getItemDamage() == meta){
                        System.out.println("Blacklisting "+output);
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

    public static boolean hasOreRecipe(String input){
        for(CrusherRecipe recipe : ActuallyAdditionsAPI.CRUSHER_RECIPES){
            if(recipe.input != null && recipe.input.equals(input)){
                return true;
            }
        }
        return false;
    }

    public static List<ItemStack> getOutputOnes(ItemStack input){
        CrusherRecipe recipe = getRecipeFromInput(input);
        return recipe == null ? null : RecipeUtil.getCrusherRecipeOutputOnes(recipe);
    }

    public static CrusherRecipe getRecipeFromInput(ItemStack input){
        for(CrusherRecipe recipe : ActuallyAdditionsAPI.CRUSHER_RECIPES){
            if(ItemUtil.contains(RecipeUtil.getCrusherRecipeInputs(recipe), input, true)){
                return recipe;
            }
        }
        return null;
    }

    public static List<ItemStack> getOutputTwos(ItemStack input){
        CrusherRecipe recipe = getRecipeFromInput(input);
        return recipe == null ? null : RecipeUtil.getCrusherRecipeOutputTwos(recipe);
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
