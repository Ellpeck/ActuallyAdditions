/*
 * This file ("CoffeeIngredient.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;

public class CoffeeIngredient {

    protected final Ingredient input;
    protected final int maxAmplifier;
    protected PotionEffect[] effects;

    @Deprecated
    public CoffeeIngredient(ItemStack input, PotionEffect[] effects, int maxAmplifier) {
        this(Ingredient.fromStacks(input), maxAmplifier, effects);
    }
    
    public CoffeeIngredient(Ingredient input, int maxAmplifier, PotionEffect... effects) {
        this.input = input;
        this.effects = effects;
        this.maxAmplifier = maxAmplifier;
    }
    
    public boolean matches(ItemStack stack) {
        return input.apply(stack);
    }
    
    public Ingredient getInput() {
        return input;
    }

    public PotionEffect[] getEffects() {
        return this.effects;
    }

    public boolean effect(ItemStack stack) {
        return ActuallyAdditionsAPI.methodHandler.addEffectToStack(stack, this);
    }

    public String getExtraText() {
        return "";
    }
    
    public int getMaxAmplifier() {
        return maxAmplifier;
    }
}