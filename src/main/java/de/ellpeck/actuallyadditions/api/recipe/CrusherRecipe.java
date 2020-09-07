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

    protected Ingredient input;
    protected ItemStack outputOne;
    protected ItemStack outputTwo;
    protected int outputChance;

    @Deprecated
    public CrusherRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int outputChance) {
        this(Ingredient.fromStacks(input), outputOne, outputTwo, outputChance);
    }

    public CrusherRecipe(Ingredient input, ItemStack outputOne, ItemStack outputTwo, int outputChance) {
        this.input = input;
        this.outputOne = outputOne;
        this.outputTwo = outputTwo;
        this.outputChance = outputChance;
    }

    public boolean matches(ItemStack stack) {
        return this.input.test(stack);
    }

    public ItemStack getOutputOne() {
        return this.outputOne;
    }

    public ItemStack getOutputTwo() {
        return this.outputTwo;
    }

    public int getSecondChance() {
        return this.outputChance;
    }

    public Ingredient getInput() {
        return this.input;
    }

}
