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

public class CrusherRecipe{

    public int outputTwoChance;
    public String input;
    public String outputOne;
    public int outputOneAmount;
    public String outputTwo;
    public int outputTwoAmount;
    public ItemStack inputStack;
    public ItemStack outputOneStack;
    public ItemStack outputTwoStack;

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

}
