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
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CompostRecipe {

    protected final Ingredient input;
    protected final ItemStack output;
    protected final IBlockState inputDisplay;
    protected final IBlockState outputDisplay;

    @Deprecated
    public CompostRecipe(ItemStack input, Block inputDisplay, ItemStack output, Block outputDisplay) {
        this(Ingredient.fromStacks(input), inputDisplay.getDefaultState(), output, outputDisplay.getDefaultState());
    }

    public CompostRecipe(Ingredient input, IBlockState inputDisplay, ItemStack output, IBlockState outputDisplay) {
        this.input = input;
        this.output = output;
        this.inputDisplay = inputDisplay;
        this.outputDisplay = outputDisplay;
    }

    public boolean matches(ItemStack stack) {
        return this.input.apply(stack);
    }

    public Ingredient getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public IBlockState getInputDisplay() {
        return this.inputDisplay;
    }

    public IBlockState getOutputDisplay() {
        return this.outputDisplay;
    }

}
