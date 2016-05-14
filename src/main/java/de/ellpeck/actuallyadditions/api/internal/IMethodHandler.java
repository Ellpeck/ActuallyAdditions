package de.ellpeck.actuallyadditions.api.internal;

import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

/**
 * This is the internal method handler.
 * Use ActuallyAdditionsAPI.methodHandler for calling
 * This is not supposed to be implemented.
 */
public interface IMethodHandler{

    boolean addEffectToStack(ItemStack stack, CoffeeIngredient ingredient);

    PotionEffect getSameEffectFromStack(ItemStack stack, PotionEffect effect);

    void addEffectProperties(ItemStack stack, PotionEffect effect, boolean addDur, boolean addAmp);

    void addEffectToStack(ItemStack stack, PotionEffect effect);

    PotionEffect[] getEffectsFromStack(ItemStack stack);
}
