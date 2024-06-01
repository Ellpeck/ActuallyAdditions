///*
// * This file ("PageCrafting.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.booklet.page;
//
//import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
//import de.ellpeck.actuallyadditions.mod.util.RefHelp;
//import de.ellpeck.actuallyadditions.mod.util.StackUtil;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import de.ellpeck.actuallyadditions.mod.util.Util;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.item.crafting.Ingredient;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.fml.client.gui.GuiUtils;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class PageCrafting extends BookletPage {
//
//    private final List<IRecipe> recipes;
//    private int recipeAt;
//    private String recipeTypeLocKey;
//    private boolean isWildcard;
//
//    public PageCrafting(int localizationKey, int priority, List<IRecipe> recipes) {
//        super(localizationKey, priority);
//        this.recipes = recipes;
//    }
//
//    public PageCrafting(int localizationKey, List<IRecipe> recipes) {
//        this(localizationKey, 0, recipes);
//    }
//
//    public PageCrafting(int localizationKey, IRecipe... recipes) {
//        this(localizationKey, 0, recipes);
//    }
//
//    public PageCrafting(int localizationKey, int priority, IRecipe... recipes) {
//        this(localizationKey, priority, Arrays.asList(recipes));
//    }
//
//    public BookletPage setWildcard() {
//        this.isWildcard = true;
//        return this;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
//        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
//
//        gui.getMinecraft().getTextureManager().bind(GuiBooklet.RES_LOC_GADGETS);
//        GuiUtils.drawTexturedModalRect(startX + 5, startY + 6, 20, 0, 116, 54, 0);
//
//        if (this.recipeTypeLocKey != null) {
//            gui.renderScaledAsciiString("(" + StringUtil.localize(this.recipeTypeLocKey) + ")", startX + 6, startY + 65, 0, false, gui.getMediumFontSize());
//        }
//
//        PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 80);
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void updateScreen(GuiBookletBase gui, int startX, int startY, int pageTimer) {
//        super.updateScreen(gui, startX, startY, pageTimer);
//
//        if (pageTimer % 20 == 0) {
//            this.findRecipe(gui, startX, startY);
//        }
//    }
//
//    private void findRecipe(GuiBookletBase gui, int startX, int startY) {
//        if (!this.recipes.isEmpty()) {
//            IRecipe recipe = this.recipes.get(this.recipeAt);
//            if (recipe != null) {
//                this.setupRecipe(gui, recipe, startX, startY);
//            }
//
//            this.recipeAt++;
//            if (this.recipeAt >= this.recipes.size()) {
//                this.recipeAt = 0;
//            }
//        }
//    }
//
//    @Override
//    public void init(GuiBookletBase gui, int startX, int startY) {
//        super.init(gui, startX, startY);
//        this.findRecipe(gui, startX, startY);
//    }
//
//    @Override
//    public void getItemStacksForPage(List<ItemStack> list) {
//        super.getItemStacksForPage(list);
//
//        if (!this.recipes.isEmpty()) {
//            for (IRecipe recipe : this.recipes) {
//                if (recipe != null) {
//                    ItemStack stack = recipe.getResultItem();
//                    if (StackUtil.isValid(stack)) {
//                        ItemStack copy = stack.copy();
//                        /*
//                        if (this.isWildcard) {
//                            copy.setItemDamage(Util.WILDCARD);
//                        }
//
//                         */
//                        list.add(copy);
//                    }
//                }
//            }
//        }
//    }
//
//    private void setupRecipe(GuiBookletBase gui, IRecipe recipe, int startX, int startY) {
//        Ingredient[] ings = new Ingredient[9];
//        int width = 3;
//        int height = 3;
///*
//        if (recipe instanceof BlankRecipe) {
//            this.recipeTypeLocKey = "tooltip.actuallyadditions.disabled";
//            gui.addOrModifyItemRenderer(recipe.getResultItem(), startX + 100, startY + 25, 1F, false);
//            return;
//        } else if (recipe instanceof ShapedRecipes) {
//            ShapedRecipes shaped = (ShapedRecipes) recipe;
//            width = shaped.recipeWidth;
//            height = shaped.recipeHeight;
//            ings = shaped.recipeItems.toArray(new Ingredient[shaped.recipeItems.size()]);
//            this.recipeTypeLocKey = "booklet.actuallyadditions.shapedRecipe";
//        } else if (recipe instanceof ShapelessRecipes) {
//            ShapelessRecipes shapeless = (ShapelessRecipes) recipe;
//            for (int i = 0; i < shapeless.recipeItems.size(); i++) {
//                ings[i] = shapeless.recipeItems.get(i);
//            }
//            this.recipeTypeLocKey = "booklet.actuallyadditions.shapelessRecipe";
//        } else if (recipe instanceof ShapedOreRecipe) {
//            ShapedOreRecipe shaped = (ShapedOreRecipe) recipe;
//            try {
//                width = RefHelp.getPrivateValue(ShapedOreRecipe.class, shaped, 4);
//                height = RefHelp.getPrivateValue(ShapedOreRecipe.class, shaped, 5);
//            } catch (Exception e) {
//                ActuallyAdditions.LOGGER.error("Something went wrong trying to get the Crafting Recipe in the booklet to display!", e);
//            }
//            for (int i = 0; i < shaped.getIngredients().size(); i++) {
//                ings[i] = shaped.getIngredients().get(i);
//            }
//            this.recipeTypeLocKey = "booklet.actuallyadditions.shapedOreRecipe";
//        } else if (recipe instanceof ShapelessOreRecipe) {
//            ShapelessOreRecipe shapeless = (ShapelessOreRecipe) recipe;
//            for (int i = 0; i < shapeless.getIngredients().size(); i++) {
//                ings[i] = shapeless.getIngredients().get(i);
//            }
//            this.recipeTypeLocKey = "booklet.actuallyadditions.shapelessOreRecipe";
//        }
//
//
// */
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                Ingredient ing = ings[y * width + x];
//                if (ing != null) {
//                    ItemStack[] stacks = ing.getItems();
//                    if (stacks != null && stacks.length > 0) {
//                        ItemStack stack = stacks[0];
//                        if (StackUtil.isValid(stack)) {
//                            ItemStack copy = stack.copy();
//                            copy.setCount(1);
//                            /*
//                            if (copy.getItemDamage() == Util.WILDCARD) {
//                                copy.setItemDamage(0);
//                            }
//                            */
//                            gui.addOrModifyItemRenderer(copy, startX + 6 + x * 18, startY + 7 + y * 18, 1F, true);
//                        }
//                    }
//                }
//            }
//        }
//
//        gui.addOrModifyItemRenderer(recipe.getResultItem(), startX + 100, startY + 25, 1F, false);
//    }
//}
