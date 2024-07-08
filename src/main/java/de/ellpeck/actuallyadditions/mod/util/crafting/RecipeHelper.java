//package de.ellpeck.actuallyadditions.mod.util.crafting;
//
//import java.util.List;
//
//import de.ellpeck.actuallyadditions.api.misc.IDisableableItem;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.RegistryHandler;
//import net.minecraft.block.Block;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.item.crafting.Ingredient;
//import net.minecraft.item.crafting.ShapedRecipes;
//import net.minecraft.item.crafting.ShapelessRecipes;
//import net.minecraft.util.NonNullList;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.crafting.CraftingHelper;
//import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
//import net.minecraftforge.oredict.OreIngredient;
//
////This class created by Shadows_of_Fire
////MIT License
//public final class RecipeHelper {
//
//    private static int j = 0;
//    private static final String MODID = ActuallyAdditions.MODID;
//    private static final String MODNAME = ActuallyAdditions.NAME;
//    public static final List<IRecipe> RECIPE_LIST = RegistryHandler.RECIPES_TO_REGISTER;
//
//    /*
//     * This adds the recipe to the list of crafting recipes.  Since who cares about names, it adds it as recipesX, where X is the current recipe you are adding.
//     */
//    public static void addRecipe(int j, IRecipe rec) {
//        addRecipe("recipes" + j, rec);
//    }
//
//    /*
//     * This adds the recipe to the list of crafting recipes.  Cares about names.
//     */
//    public static void addRecipe(String name, IRecipe rec) {
//        Item i = rec.getRecipeOutput().getItem();
//        if (i instanceof IDisableableItem && ((IDisableableItem) i).isDisabled()) rec = new BlankRecipe();
//        if (rec.getRegistryName() == null) {
//            RECIPE_LIST.add(rec.setRegistryName(ResourceLocation.tryParse(MODID, name)));
//        } else {
//            RECIPE_LIST.add(rec);
//        }
//        RecipeHandler.lastRecipe = rec;
//    }
//
//    /*
//     * This adds a shaped recipe to the list of crafting recipes, using the forge format.
//     */
//    public static void addOldShaped(ItemStack stack, Object... input) {
//        ShapedPrimer primer = CraftingHelper.parseShaped(input);
//        addRecipe(j++, new ShapedRecipes(ResourceLocation.tryParse(MODID, "recipes" + j).toString(), primer.width, primer.height, primer.input, stack));
//    }
//
//    /*
//     * This adds a shaped recipe to the list of crafting recipes, using the forge format, with a custom group.
//     */
//    public static void addOldShaped(String group, ItemStack stack, Object... input) {
//        ShapedPrimer primer = CraftingHelper.parseShaped(input);
//        addRecipe(j++, new ShapedRecipes(ResourceLocation.tryParse(MODID, group).toString(), primer.width, primer.height, primer.input, stack));
//    }
//
//    /*
//     * This adds a shaped recipe to the list of crafting recipes, using the forge format, with a custom group.
//     */
//    public static void addOldShaped(String name, String group, ItemStack stack, Object... input) {
//        ShapedPrimer primer = CraftingHelper.parseShaped(input);
//        addRecipe(j++, new ShapedRecipes(ResourceLocation.tryParse(MODID, group).toString(), primer.width, primer.height, primer.input, stack).setRegistryName(MODID, name));
//    }
//
//    /*
//     * This adds a shapeless recipe to the list of crafting recipes, using the forge format.
//     */
//    public static void addOldShapeless(ItemStack stack, Object... input) {
//        addRecipe(j++, new ShapelessRecipes(ResourceLocation.tryParse(MODID, "recipes" + j).toString(), stack, createInput(input)));
//    }
//
//    /*
//     * This adds a shapeless recipe to the list of crafting recipes, using the forge format, with a custom group.
//     */
//    public static void addOldShapeless(String group, ItemStack stack, Object... input) {
//        addRecipe(j++, new ShapelessRecipes(ResourceLocation.tryParse(MODID, group).toString(), stack, createInput(input)));
//    }
//
//    public static void addOldShapeless(String name, String group, ItemStack stack, Object... input) {
//        addRecipe(j++, new ShapelessRecipes(ResourceLocation.tryParse(MODID, group).toString(), stack, createInput(input)).setRegistryName(MODID, name));
//    }
//
//    /*
//     * Adds a shapeless recipe with X stack using an array of inputs. Use Strings for OreDictionary support. This array is not ordered.
//     */
//    public static void addShapeless(ItemStack stack, Object... inputs) {
//        addRecipe(j++, new ShapelessRecipes(MODID + ":" + j, stack, createInput(inputs)));
//    }
//
//    public static void addShapeless(Item stack, Object... inputs) {
//        addShapeless(new ItemStack(stack), inputs);
//    }
//
//    public static void addShapeless(Block stack, Object... inputs) {
//        addShapeless(new ItemStack(stack), inputs);
//    }
//
//    /*
//     * Adds a shapeless recipe with X stack using an array of inputs. Use Strings for OreDictionary support. This array is not ordered.  This has a custom group.
//     */
//    public static void addShapeless(String group, ItemStack stack, Object... inputs) {
//        addRecipe(j++, new ShapelessRecipes(MODID + ":" + group, stack, createInput(inputs)));
//    }
//
//    public static void addShapeless(String group, Item stack, Object... inputs) {
//        addShapeless(group, new ItemStack(stack), inputs);
//    }
//
//    public static void addShapeless(String group, Block stack, Object... inputs) {
//        addShapeless(group, new ItemStack(stack), inputs);
//    }
//
//    /*
//     * Adds a shapeless recipe with X stack on a crafting grid that is W x H, using an array of inputs.  Use null for nothing, use Strings for OreDictionary support, this array must have a length of width * height.
//     * This array is ordered, and items must follow from left to right, top to bottom of the crafting grid.
//     */
//    public static void addShaped(ItemStack stack, int width, int height, Object... input) {
//        addRecipe(j++, genShaped(stack, width, height, input));
//    }
//
//    public static void addShaped(Item stack, int width, int height, Object... input) {
//        addShaped(new ItemStack(stack), width, height, input);
//    }
//
//    public static void addShaped(Block stack, int width, int height, Object... input) {
//        addShaped(new ItemStack(stack), width, height, input);
//    }
//
//    /*
//     * Adds a shapeless recipe with X stack on a crafting grid that is W x H, using an array of inputs.  Use null for nothing, use Strings for OreDictionary support, this array must have a length of width * height.
//     * This array is ordered, and items must follow from left to right, top to bottom of the crafting grid. This has a custom group.
//     */
//    public static void addShaped(String group, ItemStack stack, int width, int height, Object... input) {
//        addRecipe(j++, genShaped(MODID + ":" + group, stack, width, height, input));
//    }
//
//    public static void addShaped(String group, Item stack, int width, int height, Object... input) {
//        addShaped(group, new ItemStack(stack), width, height, input);
//    }
//
//    public static void addShaped(String group, Block stack, int width, int height, Object... input) {
//        addShaped(group, new ItemStack(stack), width, height, input);
//    }
//
//    public static ShapedRecipes genShaped(ItemStack stack, int l, int w, Object[] input) {
//        if (input[0] instanceof Object[]) {
//            input = (Object[]) input[0];
//        }
//        if (l * w != input.length) { throw new UnsupportedOperationException("Attempted to add invalid shaped recipe.  Complain to the author of " + MODNAME); }
//        NonNullList<Ingredient> inputL = NonNullList.create();
//        for (int i = 0; i < input.length; i++) {
//            Object k = input[i];
//            if (k instanceof String) {
//                inputL.add(i, new OreIngredient((String) k));
//            } else if (k instanceof ItemStack && !((ItemStack) k).isEmpty()) {
//                inputL.add(i, Ingredient.fromStacks((ItemStack) k));
//            } else if (k instanceof Item) {
//                inputL.add(i, Ingredient.fromStacks(new ItemStack((Item) k)));
//            } else if (k instanceof Block) {
//                inputL.add(i, Ingredient.fromStacks(new ItemStack((Block) k)));
//            } else {
//                inputL.add(i, Ingredient.EMPTY);
//            }
//        }
//
//        return new ShapedRecipes(MODID + ":" + j, l, w, inputL, stack);
//    }
//
//    public static ShapedRecipes genShaped(String group, ItemStack stack, int l, int w, Object[] input) {
//        if (input[0] instanceof List) {
//            input = ((List<?>) input[0]).toArray();
//        } else if (input[0] instanceof Object[]) {
//            input = (Object[]) input[0];
//        }
//        if (l * w != input.length) { throw new UnsupportedOperationException("Attempted to add invalid shaped recipe.  Complain to the author of " + MODNAME); }
//        NonNullList<Ingredient> inputL = NonNullList.create();
//        for (int i = 0; i < input.length; i++) {
//            Object k = input[i];
//            if (k instanceof String) {
//                inputL.add(i, new OreIngredient((String) k));
//            } else if (k instanceof ItemStack && !((ItemStack) k).isEmpty()) {
//                inputL.add(i, Ingredient.fromStacks((ItemStack) k));
//            } else if (k instanceof Item) {
//                inputL.add(i, Ingredient.fromStacks(new ItemStack((Item) k)));
//            } else if (k instanceof Block) {
//                inputL.add(i, Ingredient.fromStacks(new ItemStack((Block) k)));
//            } else if (k instanceof Ingredient) {
//                inputL.add(i, (Ingredient) k);
//            } else {
//                inputL.add(i, Ingredient.EMPTY);
//            }
//        }
//
//        return new ShapedRecipes(group, l, w, inputL, stack);
//    }
//
//    public static NonNullList<Ingredient> createInput(Object[] input) {
//        if (input[0] instanceof List) {
//            input = ((List<?>) input[0]).toArray();
//        } else if (input[0] instanceof Object[]) {
//            input = (Object[]) input[0];
//        }
//        NonNullList<Ingredient> inputL = NonNullList.create();
//        for (int i = 0; i < input.length; i++) {
//            Object k = input[i];
//            if (k instanceof String) {
//                inputL.add(i, new OreIngredient((String) k));
//            } else if (k instanceof ItemStack) {
//                inputL.add(i, Ingredient.fromStacks((ItemStack) k));
//            } else if (k instanceof Item) {
//                inputL.add(i, Ingredient.fromStacks(new ItemStack((Item) k)));
//            } else if (k instanceof Block) {
//                inputL.add(i, Ingredient.fromStacks(new ItemStack((Block) k)));
//            } else if (k instanceof Ingredient) {
//                inputL.add(i, (Ingredient) k);
//            } else {
//                throw new UnsupportedOperationException("Attempted to add invalid shapeless recipe.  Complain to the author of " + MODNAME);
//            }
//        }
//        return inputL;
//    }
//}
