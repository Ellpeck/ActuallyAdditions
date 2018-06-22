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
import net.minecraft.item.crafting.Ingredient;

public class CrusherRecipe {

    @Deprecated //ModTweaker compat, will be removed soon.
    public ItemStack outputOneStack;
    
    private Ingredient input;
    private ItemStack outputOne;
    private ItemStack outputTwo;
    private int outputChance;

    @Deprecated //ModTweaker compat, will be removed soon.
    public CrusherRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int outputChance) {
        this(Ingredient.fromStacks(input), outputOne, outputTwo, outputChance);
    }
    
    public CrusherRecipe(Ingredient input, ItemStack outputOne, ItemStack outputTwo, int outputChance) {
        this.input = input;
        this.outputOne = outputOne;
        this.outputTwo = outputTwo;
        this.outputChance = outputChance;
        outputOneStack = outputOne;
    }

    public boolean matches(ItemStack stack) {
        return input.apply(stack);
    }

    public ItemStack getOutputOne() {
        return outputOne;
    }

    public ItemStack getOutputTwo() {
        return outputTwo;
    }

    public int getSecondChance() {
        return outputChance;
    }

    public Ingredient getInput() {
        return input;
    }

}
