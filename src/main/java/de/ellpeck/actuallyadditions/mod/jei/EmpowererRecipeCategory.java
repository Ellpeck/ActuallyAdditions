///*
// * This file ("EmpowererRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.jei;
//
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.crafting.EmpowererRecipe;
//import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.gui.IRecipeLayout;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.gui.drawable.IDrawableStatic;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.ingredients.IIngredients;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import net.minecraft.client.resources.language.I18n;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class EmpowererRecipeCategory implements IRecipeCategory<EmpowererRecipe> {
//
//    public static final ResourceLocation ID = new ResourceLocation(ActuallyAdditions.MODID, "empowerer_jei");
//
//    private final IDrawableStatic background;
//
//    public EmpowererRecipeCategory(IGuiHelper helper) {
//        this.background = helper.drawableBuilder(AssetUtil.getGuiLocation("gui_nei_empowerer"), 0, 0, 135, 80).setTextureSize(256,256).build();
//    }
//
//    @Override
//    public ResourceLocation getUid() {
//        return ID;
//    }
//
//    @Override
//    public Class<? extends EmpowererRecipe> getRecipeClass() {
//        return EmpowererRecipe.class;
//    }
//
//    @Override
//    public String getTitle() {
//        return I18n.get("container.actuallyadditions.empowerer");
//    }
//
//    @Override
//    public IDrawable getBackground() {
//        return background;
//    }
//
//    @Override
//    public IDrawable getIcon() {
//        return null;
//    }
//
//    @Override
//    public void setIngredients(EmpowererRecipe empowererRecipe, IIngredients ingredients) {
//        ArrayList<ItemStack> input = new ArrayList<>();
//        input.addAll(Arrays.asList(empowererRecipe.getInput().getItems()));
//        input.addAll(Arrays.asList(empowererRecipe.getStandOne().getItems()));
//        input.addAll(Arrays.asList(empowererRecipe.getStandTwo().getItems()));
//        input.addAll(Arrays.asList(empowererRecipe.getStandThree().getItems()));
//        input.addAll(Arrays.asList(empowererRecipe.getStandFour().getItems()));
//        ingredients.setInputs(VanillaTypes.ITEM, input);
//        ingredients.setOutput(VanillaTypes.ITEM, empowererRecipe.getOutput());
//    }
//
//    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, EmpowererRecipe recipe, IIngredients ingredients) {
//        recipeLayout.getItemStacks().init(0, true, 31, 31);
//        recipeLayout.getItemStacks().set(0, Arrays.asList(recipe.getInput().getItems()));
//
//        recipeLayout.getItemStacks().init(1, true, 1, 31);
//        recipeLayout.getItemStacks().set(1, Arrays.asList(recipe.getStandOne().getItems()));
//
//        recipeLayout.getItemStacks().init(2, true, 31, 1);
//        recipeLayout.getItemStacks().set(2, Arrays.asList(recipe.getStandTwo().getItems()));
//
//        recipeLayout.getItemStacks().init(3, true, 61, 31);
//        recipeLayout.getItemStacks().set(3, Arrays.asList(recipe.getStandThree().getItems()));
//
//        recipeLayout.getItemStacks().init(4, true, 31, 61);
//        recipeLayout.getItemStacks().set(4, Arrays.asList(recipe.getStandFour().getItems()));
//
//        recipeLayout.getItemStacks().init(5, false, 112, 31);
//        recipeLayout.getItemStacks().set(5, recipe.getResultItem());
//    }
//}
