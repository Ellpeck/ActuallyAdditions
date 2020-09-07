package de.ellpeck.actuallyadditions.api.recipe;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;

public class CoffeeIngredient {

    protected final Ingredient input;
    protected final int maxAmplifier;
    protected EffectInstance[] effects;

    @Deprecated
    public CoffeeIngredient(ItemStack input, EffectInstance[] effects, int maxAmplifier) {
        this(Ingredient.fromStacks(input), maxAmplifier, effects);
    }

    public CoffeeIngredient(Ingredient input, int maxAmplifier, EffectInstance... effects) {
        this.input = input;
        this.effects = effects;
        this.maxAmplifier = maxAmplifier;
    }

    public boolean matches(ItemStack stack) {
        return this.input.test(stack);
    }

    public Ingredient getInput() {
        return this.input;
    }

    public EffectInstance[] getEffects() {
        return this.effects;
    }

    public boolean effect(ItemStack stack) {
        return ActuallyAdditionsAPI.methodHandler.addEffectToStack(stack, this);
    }

    public String getExtraText() {
        return "";
    }

    public int getMaxAmplifier() {
        return this.maxAmplifier;
    }
}