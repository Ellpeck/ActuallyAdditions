/*
 * This file ("CrusherRecipeManualRegistry.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.recipe;

import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class CrusherRecipeManualRegistry{

    public static ArrayList<CrusherRecipe> recipes = new ArrayList<CrusherRecipe>();

    public static void registerRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int secondChance){
        if(!hasRecipe(input, outputOne, outputTwo)){
            recipes.add(new CrusherRecipe(input, outputOne, outputTwo, secondChance));
        }
    }

    public static void registerRecipe(String input, String outputOne, int outputOneAmount){
        registerRecipe(input, outputOne, "", 0, outputOneAmount, 0);
    }

    public static void registerRecipe(String input, String outputOne, String outputTwo, int secondChance, int outputOneAmount, int outputTwoAmount){
        ArrayList<ItemStack> inputStacks = (ArrayList<ItemStack>)OreDictionary.getOres(input, false);
        ArrayList<ItemStack> outputOneStacks = (ArrayList<ItemStack>)OreDictionary.getOres(outputOne, false);
        ArrayList<ItemStack> outputTwoStacks = (outputTwo == null || outputTwo.isEmpty()) ? null : (ArrayList<ItemStack>)OreDictionary.getOres(outputTwo, false);

        if(inputStacks != null && !inputStacks.isEmpty()){
            for(ItemStack anInput : inputStacks){
                ItemStack theInput = anInput.copy();
                if(outputOneStacks != null && !outputOneStacks.isEmpty()){
                    for(ItemStack anOutputOne : outputOneStacks){
                        ItemStack theOutputOne = anOutputOne.copy();
                        theOutputOne.stackSize = outputOneAmount;
                        if(outputTwoStacks != null && !outputTwoStacks.isEmpty()){
                            for(ItemStack anOutputTwo : outputTwoStacks){
                                ItemStack theOutputTwo = anOutputTwo.copy();
                                theOutputTwo.stackSize = outputTwoAmount;
                                registerRecipe(theInput, theOutputOne, theOutputTwo, secondChance);
                            }
                        }
                        else{
                            registerRecipe(theInput, theOutputOne, null, 0);
                        }
                    }
                }
                else{
                    if(ConfigBoolValues.DO_CRUSHER_SPAM.isEnabled()){
                        ModUtil.LOGGER.warn("Couldn't register Crusher Recipe! An Item with OreDictionary Registry '"+outputOne+"' doesn't exist! It should be the output of '"+input+"'!");
                    }
                }
            }
        }
        else{
            if(ConfigBoolValues.DO_CRUSHER_SPAM.isEnabled()){
                ModUtil.LOGGER.warn("Couldn't register Crusher Recipe! Didn't find Items registered as '"+input+"'!");
            }
        }
    }

    public static void registerRecipe(ItemStack input, ItemStack outputOne){
        registerRecipe(input, outputOne, null, 0);
    }

    public static ItemStack getOutput(ItemStack input, boolean wantSecond){
        for(CrusherRecipe recipe : recipes){
            if(recipe.input.isItemEqual(input) || (recipe.input.getItem() == input.getItem() && recipe.input.getItemDamage() == Util.WILDCARD)){
                return wantSecond ? recipe.secondOutput : recipe.firstOutput;
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo){
        for(CrusherRecipe recipe : recipes){
            if(outputTwo == null || (recipe.secondOutput != null && recipe.secondOutput.isItemEqual(outputTwo))){
                if(recipe.input.isItemEqual(input) && recipe.firstOutput.isItemEqual(outputOne) || (recipe.input.getItem() == input.getItem() && recipe.firstOutput.getItem() == outputOne.getItem() && recipe.input.getItemDamage() == Util.WILDCARD)){
                    return true;
                }
            }
        }
        return false;
    }

    public static int getSecondChance(ItemStack input){
        for(CrusherRecipe recipe : recipes){
            if(recipe.input.isItemEqual(input) || (recipe.input.getItem() == input.getItem() && recipe.input.getItemDamage() == Util.WILDCARD)){
                return recipe.secondChance;
            }
        }
        return 0;
    }

    public static class CrusherRecipe{

        public final ItemStack input;
        public final ItemStack firstOutput;
        public final ItemStack secondOutput;
        public final int secondChance;

        public CrusherRecipe(ItemStack input, ItemStack firstOutput, ItemStack secondOutput, int secondChance){
            this.input = input;
            this.firstOutput = firstOutput;
            this.secondOutput = secondOutput;
            this.secondChance = secondChance;
        }

    }
}