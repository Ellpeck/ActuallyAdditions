/*
 * This file ("LensRecipeHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

// TODO: Need to figure out the enchanted stuff, and color changing still, the rest are datagen now.
public final class LensRecipeHandler {
    public static void init() {

        //
        //        ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES.add(recipeEnchBook = new EnchBookConversion());
        //
        //        IColorLensChanger changer = new ColorLensChangerByDyeMeta();
        //        if (ConfigBoolValues.COLOR_LENS_USES_OREDICT.isEnabled()) {
        //            initOredictDyeRotator();
        //        } else {
        //            ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Items.DYE, changer);
        //        }
        //        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.WOOL), changer);
        //        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.STAINED_GLASS), changer);
        //        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE), changer);
        //        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.STAINED_HARDENED_CLAY), changer);
        //        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.CARPET), changer);
        //        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(InitBlocks.blockColoredLamp), changer);
        //        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(InitBlocks.blockColoredLampOn), changer);
    }
    //
    //    @Deprecated //Use lens-checking method below.
    //    public static List<LensConversionRecipe> getRecipesFor(ItemStack input) {
    //        List<LensConversionRecipe> possibleRecipes = new ArrayList<>();
    //        for (LensConversionRecipe recipe : ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES)
    //            if (recipe.getInput().apply(input)) possibleRecipes.add(recipe);
    //        return possibleRecipes;
    //    }
    //
    //    @Nullable
    //    public static LensConversionRecipe findMatchingRecipe(ItemStack input, Lens lens) {
    //        for (LensConversionRecipe recipe : ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES)
    //            if (recipe.matches(input, lens)) return recipe;
    //        return null;
    //    }
    //
    //    private static Ingredient fromBlock(Block b) {
    //        return Ingredient.fromItems(Item.getItemFromBlock(b));
    //    }
    //
    //    private static void initOredictDyeRotator() {
    //        List<ItemStack> stacks = NonNullList.withSize(16, ItemStack.EMPTY);
    //        List<ItemStack> dyeItems = new ArrayList<>();
    //        String[] dyes = { "White", "Orange", "Magenta", "LightBlue", "Yellow", "Lime", "Pink", "Gray", "LightGray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
    //        for (int i = 0; i < dyes.length; i++) {
    //            List<ItemStack> ores = OreDictionary.getOres("dye" + dyes[i]);
    //            dyeItems.addAll(ores);
    //            stacks.set(i, ores.get(ores.size() - 1));
    //        }
    //        ColorLensRotator rotator = new ColorLensRotator(stacks);
    //        for (ItemStack s : dyeItems)
    //            ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(s.getItem(), rotator);
    //    }
}
