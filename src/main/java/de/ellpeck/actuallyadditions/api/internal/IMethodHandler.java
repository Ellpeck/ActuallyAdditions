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

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

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

    BookletPage generateTextPage(int id);

    BookletPage generatePicturePage(int id, ResourceLocation resLoc, int textStartY);

    BookletPage generateCraftingPage(int id, IRecipe... recipes);

    BookletPage generateFurnacePage(int id, ItemStack input, ItemStack result);

    IBookletChapter generateBookletChapter(String unlocalizedName, IBookletEntry entry, ItemStack displayStack, BookletPage... pages);
}
