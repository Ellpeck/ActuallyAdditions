///*
// Copyright 2014-2017, the Biomes O' Plenty Team
//
// This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
//
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
//
// Original: https://github.com/Glitchfiend/BiomesOPlenty/blob/0f8be0526e01d918cf8f22d4904a3b74981dee6f/src/main/java/biomesoplenty/common/util/inventory/CraftingUtil.java
// (edited to work with multiple mods)
// */
//package de.ellpeck.actuallyadditions.mod.util.crafting;
//
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.IRecipe;
//
//public final class RecipeHandler {
//
//    public static IRecipe lastRecipe;
//
//    public static void addOreDictRecipe(ItemStack stack, Object... inputs) {
//        addShapedRecipe(stack, inputs);
//    }
//
//    public static void addShapelessOreDictRecipe(ItemStack stack, Object... inputs) {
//        addShapelessRecipe(stack, inputs);
//    }
//
//    public static void addShapelessRecipe(ItemStack stack, Object... inputs) {
//        RecipeHelper.addOldShapeless(stack, inputs);
//    }
//
//    public static void addShapedRecipe(ItemStack stack, Object... inputs) {
//        RecipeHelper.addOldShaped(stack, inputs);
//    }
//
//}
