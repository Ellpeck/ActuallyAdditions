/*
 * This file ("LensNoneRecipe.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LensConversionRecipe{

    public int energyUse;
    private String input;
    private String output;
    private ItemStack inputStack;
    private ItemStack outputStack;

    public LensConversionRecipe(ItemStack input, ItemStack output, int energyUse){
        this.inputStack = input;
        this.outputStack = output;
        this.energyUse = energyUse;
    }

    public LensConversionRecipe(String input, String output, int energyUse){
        this.input = input;
        this.output = output;
        this.energyUse = energyUse;
    }

    public List<ItemStack> getOutputs(){
        if(this.outputStack != null){
            return Collections.singletonList(this.outputStack.copy());
        }

        if(this.output == null || this.output.isEmpty()){
            return null;
        }

        List<ItemStack> stacks = OreDictionary.getOres(this.output, false);
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

    public List<ItemStack> getInputs(){
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
