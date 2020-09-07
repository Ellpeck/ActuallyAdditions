package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class EmpowererRecipe {

    protected final Ingredient input;
    protected final ItemStack output;

    protected final Ingredient modifier1;
    protected final Ingredient modifier2;
    protected final Ingredient modifier3;
    protected final Ingredient modifier4;

    protected final int energyPerStand;
    protected final float[] particleColor;
    protected final int time;

    @Deprecated
    public EmpowererRecipe(ItemStack input, ItemStack output, ItemStack modifier1, ItemStack modifier2, ItemStack modifier3, ItemStack modifier4, int energyPerStand, int time, float[] particleColor) {
        this(Ingredient.fromStacks(input), output, Ingredient.fromStacks(modifier1), Ingredient.fromStacks(modifier2), Ingredient.fromStacks(modifier3), Ingredient.fromStacks(modifier4), energyPerStand, time, particleColor);
    }

    public EmpowererRecipe(Ingredient input, ItemStack output, Ingredient modifier1, Ingredient modifier2, Ingredient modifier3, Ingredient modifier4, int energyPerStand, int time, float[] particleColor) {
        this.input = input;
        this.output = output;
        this.modifier1 = modifier1;
        this.modifier2 = modifier2;
        this.modifier3 = modifier3;
        this.modifier4 = modifier4;
        this.energyPerStand = energyPerStand;
        this.particleColor = particleColor;
        this.time = time;
    }

    public boolean matches(ItemStack base, ItemStack stand1, ItemStack stand2, ItemStack stand3, ItemStack stand4) {
        if (!this.input.test(base)) return false;
        List<Ingredient> matches = new ArrayList<>();
        ItemStack[] stacks = { stand1, stand2, stand3, stand4 };
        boolean[] unused = { true, true, true, true };
        for (ItemStack s : stacks) {
            if (unused[0] && this.modifier1.test(s)) {
                matches.add(this.modifier1);
                unused[0] = false;
            } else if (unused[1] && this.modifier2.test(s)) {
                matches.add(this.modifier2);
                unused[1] = false;
            } else if (unused[2] && this.modifier3.test(s)) {
                matches.add(this.modifier3);
                unused[2] = false;
            } else if (unused[3] && this.modifier4.test(s)) {
                matches.add(this.modifier4);
                unused[3] = false;
            }
        }

        return matches.size() == 4;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public Ingredient getStandOne() {
        return this.modifier1;
    }

    public Ingredient getStandTwo() {
        return this.modifier2;
    }

    public Ingredient getStandThree() {
        return this.modifier3;
    }

    public Ingredient getStandFour() {
        return this.modifier4;
    }

    public int getTime() {
        return this.time;
    }

    public int getEnergyPerStand() {
        return this.energyPerStand;
    }

    public float[] getParticleColors() {
        return this.particleColor;
    }
}
