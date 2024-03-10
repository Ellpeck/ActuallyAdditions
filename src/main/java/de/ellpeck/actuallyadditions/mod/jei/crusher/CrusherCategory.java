package de.ellpeck.actuallyadditions.mod.jei.crusher;

import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import de.ellpeck.actuallyadditions.mod.jei.JEIActuallyAdditionsPlugin;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CrusherCategory implements IRecipeCategory<CrushingRecipe> {
	private final IDrawableStatic background;

	public CrusherCategory(IGuiHelper helper) {
		this.background = helper.drawableBuilder(AssetUtil.getGuiLocation("gui_grinder"), 60, 13, 56, 79).setTextureSize(256,256).build();
	}

	@Override
	public RecipeType<CrushingRecipe> getRecipeType() {
		return JEIActuallyAdditionsPlugin.CRUSHING;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("container.actuallyadditions.crusher");
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
	public void setRecipe(IRecipeLayoutBuilder builder, CrushingRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 20, 8).addIngredients(recipe.getInput());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 8, 56).addItemStack(recipe.getOutputOne());
		if (!recipe.getOutputTwo().isEmpty()) {
			builder.addSlot(RecipeIngredientRole.OUTPUT, 32, 56).addItemStack(recipe.getOutputTwo());
		}
	}

	@Override
	public void draw(CrushingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

		Minecraft mc = Minecraft.getInstance();
        if (!recipe.getOutputTwo().isEmpty()) {
	        guiGraphics.drawString(mc.font, (int)(recipe.getSecondChance() * 100) + "%", 60, 60, 4210752, false);
        }
	}
}
