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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

@Deprecated
public class CoffeeIngredient {

    protected final Ingredient input;
    protected final int maxAmplifier;
    protected MobEffectInstance[] effects;

    @Deprecated
    public CoffeeIngredient(ItemStack input, MobEffectInstance[] effects, int maxAmplifier) {
        this(Ingredient.of(input), maxAmplifier, effects);
    }

    public CoffeeIngredient(Ingredient input, int maxAmplifier, MobEffectInstance... effects) {
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

    public MobEffectInstance[] getEffects() {
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
