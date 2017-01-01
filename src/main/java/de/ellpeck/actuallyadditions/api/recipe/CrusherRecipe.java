/*
 * This file ("CrusherRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;

public class CrusherRecipe{

    public ItemStack inputStack;
    public ItemStack outputOneStack;
    public ItemStack outputTwoStack;
    public int outputTwoChance;

    public CrusherRecipe(ItemStack inputStack, ItemStack outputOneStack, ItemStack outputTwoStack, int outputTwoChance){
        this.inputStack = inputStack;
        this.outputOneStack = outputOneStack;
        this.outputTwoStack = outputTwoStack;
        this.outputTwoChance = outputTwoChance;
    }


}
