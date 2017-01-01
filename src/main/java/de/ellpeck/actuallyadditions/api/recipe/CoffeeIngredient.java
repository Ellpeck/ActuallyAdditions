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
import net.minecraft.potion.PotionEffect;

public class CoffeeIngredient{

    public final ItemStack ingredient;
    public final int maxAmplifier;
    protected PotionEffect[] effects;

    public CoffeeIngredient(ItemStack ingredient, PotionEffect[] effects, int maxAmplifier){
        this.ingredient = ingredient.copy();
        this.effects = effects;
        this.maxAmplifier = maxAmplifier;
    }

    public PotionEffect[] getEffects(){
        return this.effects;
    }

    public boolean effect(ItemStack stack){
        return ActuallyAdditionsAPI.methodHandler.addEffectToStack(stack, this);
    }

    public String getExtraText(){
        return null;
    }
}