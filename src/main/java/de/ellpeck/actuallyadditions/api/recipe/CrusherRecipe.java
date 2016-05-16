/*
 * This file ("CrusherRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrusherRecipe{

    public int outputTwoChance;
    public String input;
    public String outputOne;
    public int outputOneAmount;
    public String outputTwo;
    public int outputTwoAmount;
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
        if(stacks != null && !stacks.isEmpty()){
            List<ItemStack> stacksCopy = new ArrayList<ItemStack>();
            for(ItemStack stack : stacks){
                if(stack != null){
                    ItemStack stackCopy = stack.copy();
                    stackCopy.stackSize = this.outputOneAmount;
                    stacksCopy.add(stackCopy);
                }
            }
            return stacksCopy;
        }
        return null;
    }

    public List<ItemStack> getRecipeOutputTwos(){
        if(this.outputTwoStack != null){
            return Collections.singletonList(this.outputTwoStack.copy());
        }

        if(this.outputTwo == null || this.outputTwo.isEmpty()){
            return null;
        }

        List<ItemStack> stacks = OreDictionary.getOres(this.outputTwo, false);
        if(stacks != null && !stacks.isEmpty()){
            List<ItemStack> stacksCopy = new ArrayList<ItemStack>();
            for(ItemStack stack : stacks){
                if(stack != null){
                    ItemStack stackCopy = stack.copy();
                    stackCopy.stackSize = this.outputTwoAmount;
                    stacksCopy.add(stackCopy);
                }
            }
            return stacksCopy;
        }
        return null;
    }

    public List<ItemStack> getRecipeInputs(){
        if(this.inputStack != null){
            return Collections.singletonList(this.inputStack.copy());
        }

        if(this.input == null || this.input.isEmpty()){
            return null;
        }

        List<ItemStack> stacks = OreDictionary.getOres(this.input, false);
        if(stacks != null && !stacks.isEmpty()){
            List<ItemStack> stacksCopy = new ArrayList<ItemStack>();
            for(ItemStack stack : stacks){
                if(stack != null){
                    ItemStack stackCopy = stack.copy();
                    stackCopy.stackSize = 1;
                    stacksCopy.add(stackCopy);
                }
            }
            return stacksCopy;
        }
        return null;
    }
}
