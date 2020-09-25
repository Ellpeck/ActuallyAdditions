package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

@Deprecated
public class CompostRecipe {

    protected final Ingredient input;
    protected final ItemStack output;
    protected final BlockState inputDisplay;
    protected final BlockState outputDisplay;

    @Deprecated
    public CompostRecipe(ItemStack input, Block inputDisplay, ItemStack output, Block outputDisplay) {
        this(Ingredient.fromStacks(input), inputDisplay.getDefaultState(), output, outputDisplay.getDefaultState());
    }

    public CompostRecipe(Ingredient input, BlockState inputDisplay, ItemStack output, BlockState outputDisplay) {
        this.input = input;
        this.output = output;
        this.inputDisplay = inputDisplay;
        this.outputDisplay = outputDisplay;
    }

    public boolean matches(ItemStack stack) {
        return this.input.test(stack);
    }

    public Ingredient getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public BlockState getInputDisplay() {
        return this.inputDisplay;
    }

    public BlockState getOutputDisplay() {
        return this.outputDisplay;
    }

}
