/*
 * This file ("CompostRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class CompostRecipe{

    public final ItemStack input;
    public final ItemStack output;
    public final Block inputDisplay;
    public final Block outputDisplay;

    public CompostRecipe(ItemStack input, Block inputDisplay, ItemStack output, Block outputDisplay){
        this.input = input;
        this.output = output;
        this.inputDisplay = inputDisplay;
        this.outputDisplay = outputDisplay;
    }

}
