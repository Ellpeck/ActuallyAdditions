package de.ellpeck.actuallyadditions.api.recipe;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;

public class LensConversionRecipe {

    protected final Ingredient input;
    protected final ItemStack output;
    protected final int energy;
    protected final Lens type;

    @Deprecated
    public LensConversionRecipe(ItemStack input, ItemStack output, int energy, Lens type) {
        this(Ingredient.fromStacks(input), output, energy, type);
    }

    public LensConversionRecipe(Ingredient input, ItemStack output, int energy, Lens type) {
        this.input = input;
        this.output = output;
        this.energy = energy;
        this.type = type;
    }

    public boolean matches(ItemStack input, Lens lens) {
        return this.input.test(input) && this.type == lens;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public int getEnergyUsed() {
        return this.energy;
    }

    public Lens getType() {
        return this.type;
    }

    public void transformHook(ItemStack stack, BlockState state, BlockPos pos, IAtomicReconstructor tile) {
    }
}
