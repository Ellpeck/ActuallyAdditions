/*
 * This file ("CrusherRecipeRegistry.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.recipe;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import net.minecraft.world.item.ItemStack;

public final class CrusherRecipeRegistry {
    //
    //    public static final ArrayList<SearchCase> SEARCH_CASES = new ArrayList<>();
    //
    //    public static void registerFinally() {
    //        ArrayList<String> oresNoResult = new ArrayList<>();
    //        int recipeStartedAt = ActuallyAdditionsAPI.CRUSHER_RECIPES.size();
    //
    //        for (String ore : OreDictionary.getOreNames()) {
    //            if (!hasException(ore)) {
    //                for (SearchCase theCase : SEARCH_CASES) {
    //                    if (ore.length() > theCase.theCase.length()) {
    //                        if (ore.substring(0, theCase.theCase.length()).equals(theCase.theCase)) {
    //                            String outputOre = theCase.resultPreString + ore.substring(theCase.theCase.length());
    //                            List<ItemStack> outputs = OreDictionary.getOres(outputOre, false);
    //                            ItemStack output = outputs.isEmpty() ? ItemStack.EMPTY : outputs.get(0).copy();
    //                            output.setCount(theCase.resultAmount);
    //                            if (output.isEmpty()) {
    //                                if (!oresNoResult.contains(ore)) {
    //                                    oresNoResult.add(ore);
    //                                }
    //                            } else ActuallyAdditionsAPI.addCrusherRecipe(new OreIngredient(ore), output, StackUtil.getEmpty(), 0);
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //
    //        ArrayList<String> addedRecipes = new ArrayList<>();
    //        for (int i = recipeStartedAt; i < ActuallyAdditionsAPI.CRUSHER_RECIPES.size(); i++) {
    //            CrusherRecipe recipe = ActuallyAdditionsAPI.CRUSHER_RECIPES.get(i);
    //            addedRecipes.add(recipe.getInput().getMatchingStacks() + " -> " + recipe.getOutputOne());
    //        }
    //        ActuallyAdditions.LOGGER.debug("Added " + addedRecipes.size() + " Crusher Recipes automatically: " + addedRecipes);
    //        ActuallyAdditions.LOGGER.debug("Couldn't add " + oresNoResult.size() + " Crusher Recipes automatically, either because the inputs were missing outputs, or because they exist already: " + oresNoResult);
    //        removeDuplicateRecipes();
    //    }
    //
    //    public static void removeDuplicateRecipes() {
    //        ArrayList<CrusherRecipe> usable = new ArrayList<>();
    //        ArrayList<CrusherRecipe> removed = new ArrayList<>();
    //        for (CrusherRecipe r : ActuallyAdditionsAPI.CRUSHER_RECIPES) {
    //            boolean canUse = true;
    //            if (r.getInput().getMatchingStacks().length == 0) canUse = false;
    //            else for (CrusherRecipe re : usable) {
    //                if (re.getInput().apply(r.getInput().getMatchingStacks()[0])) canUse = false;
    //            }
    //
    //            if (canUse) usable.add(r);
    //            else removed.add(r);
    //        }
    //
    //        ActuallyAdditionsAPI.CRUSHER_RECIPES.clear();
    //        ActuallyAdditionsAPI.CRUSHER_RECIPES.addAll(usable);
    //        ActuallyAdditions.LOGGER.debug(String.format("Removed %s crusher recipes that had dupliate inputs, %s remain.", removed.size(), usable.size()));
    //    }
    //
    //    public static boolean hasBlacklistedOutput(ItemStack output, String[] config) {
    //        if (StackUtil.isValid(output)) {
    //            Item item = output.getItem();
    //            if (item != null) {
    //                String reg = item.getRegistryName().toString();
    //
    //                for (String conf : config) {
    //                    String confReg = conf;
    //                    int meta = 0;
    //
    //                    if (conf.contains("@")) {
    //                        try {
    //                            String[] split = conf.split("@");
    //                            confReg = split[0];
    //                            meta = Integer.parseInt(split[1]);
    //                        } catch (Exception e) {
    //                            ActuallyAdditions.LOGGER.warn("A config option appears to be incorrect: The entry " + conf + " can't be parsed!");
    //                        }
    //                    }
    //
    //                    if (reg.equals(confReg) && output.getItemDamage() == meta) { return true; }
    //                }
    //
    //                return false;
    //            }
    //        }
    //        return true;
    //    }
    //
    //    public static boolean hasException(String ore) {
    //        for (String conf : ConfigStringListValues.CRUSHER_RECIPE_EXCEPTIONS.getValue()) {
    //            if (conf.equals(ore)) { return true; }
    //        }
    //        return false;
    //    }
    //
    public static CrushingRecipe getRecipeFromInput(ItemStack input) {
        for (CrushingRecipe recipe : ActuallyAdditionsAPI.CRUSHER_RECIPES)
            if (recipe.matches(input)) return recipe;
        return null;
    }
    //
    //    public static class SearchCase {
    //
    //        final String theCase;
    //        final int resultAmount;
    //        final String resultPreString;
    //
    //        public SearchCase(String theCase, int resultAmount) {
    //            this(theCase, resultAmount, "dust");
    //        }
    //
    //        public SearchCase(String theCase, int resultAmount, String resultPreString) {
    //            this.theCase = theCase;
    //            this.resultAmount = resultAmount;
    //            this.resultPreString = resultPreString;
    //        }
    //    }

}
