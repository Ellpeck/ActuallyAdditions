/*
 * This file ("IMethodHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.internal;

import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;

import java.util.List;

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

    boolean invokeConversionLens(IBlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile);

    boolean addCrusherRecipes(List<ItemStack> inputs, List<ItemStack> outputOnes, int outputOneAmounts, List<ItemStack> outputTwos, int outputTwoAmounts, int outputTwoChance);
}
