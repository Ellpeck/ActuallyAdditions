/*
 * This file ("EmpowererRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.empowerer;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.jei.JEIActuallyAdditionsPlugin;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class EmpowererRecipeCategory implements IRecipeCategory<EmpowererRecipe> {

    public static final ResourceLocation ID = new ResourceLocation(ActuallyAdditions.MODID, "empowerer_jei");

    private final IDrawableStatic background;

    public EmpowererRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(AssetUtil.getGuiLocation("gui_nei_empowerer"), 0, 0, 135, 80).setTextureSize(256,256).build();
    }

	@Override
	public RecipeType<EmpowererRecipe> getRecipeType() {
		return JEIActuallyAdditionsPlugin.EMPOWERER;
	}

    @Override
    public Component getTitle() {
        return Component.translatable("container.actuallyadditions.empowerer");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, EmpowererRecipe recipe, IFocusGroup focuses) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		if (level == null) {
			throw new NullPointerException("level must not be null.");
		}
		RegistryAccess registryAccess = level.registryAccess();

		builder.addSlot(RecipeIngredientRole.INPUT, 32, 32).addIngredients(recipe.getInput());
		builder.addSlot(RecipeIngredientRole.INPUT, 2, 32).addIngredients(recipe.getStandOne());
		builder.addSlot(RecipeIngredientRole.INPUT, 32, 2).addIngredients(recipe.getStandTwo());
		builder.addSlot(RecipeIngredientRole.INPUT, 62, 32).addIngredients(recipe.getStandThree());
		builder.addSlot(RecipeIngredientRole.INPUT, 32, 62).addIngredients(recipe.getStandFour());

		builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 32).addItemStack(recipe.getResultItem(registryAccess));
	}
}
