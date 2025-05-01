package de.ellpeck.actuallyadditions.mod.jei.fermenting;

import de.ellpeck.actuallyadditions.mod.crafting.FermentingRecipe;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.fluids.FluidStack;

public class FermentingCategory implements IRecipeCategory<FermentingRecipe> {
    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_fermenting_barrel");
    private final IDrawableStatic background;
    private final IDrawableStatic fluidBackground;

    public FermentingCategory(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(RES_LOC, 41, 4, 94, 86).build();
	    fluidBackground = guiHelper.drawableBuilder(AssetUtil.GUI_INVENTORY_LOCATION, 0, 171, 18, 85).build();
    }

	@Override
	public RecipeType<FermentingRecipe> getRecipeType() {
		return JEIActuallyAdditionsPlugin.FERMENTING;
	}

    @Override
    public Component getTitle() {
        return Component.literal("Fermenting Recipe");
    }

	@Override
	public int getWidth() {
		return 94;
	}

	@Override
	public int getHeight() {
		return 86;
	}

    @Override
    public IDrawable getIcon() {
        return null;
    }

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, FermentingRecipe recipe, IFocusGroup focuses) {
		FluidStack input = recipe.getInput();
		int height = (int)(83D / 1000 * input.getAmount());
		int offset = 83 - height;
		builder.addSlot(RecipeIngredientRole.INPUT, 20, 1 + offset)
				.addFluidStack(input.getFluid(), input.getAmount())
				.setFluidRenderer(input.getAmount(), false, 16, height)
				.setBackground(fluidBackground, -1, -1 - offset);

		FluidStack output = recipe.getOutput();
		height = (int)(83D / 1000 * input.getAmount());
		offset = 83 - height;
		builder.addSlot(RecipeIngredientRole.OUTPUT, 20+38, 1 + offset)
				.addFluidStack(output.getFluid(), output.getAmount())
				.setFluidRenderer(output.getAmount(), false, 16, height)
				.setBackground(fluidBackground, -1, -1 - offset);
	}

	@Override
	public void draw(FermentingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		this.background.draw(guiGraphics);
		IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		int recipeTime = recipe.getTime();
		if (recipeTime > 0) {
			int currentProcessTime = (int) (System.currentTimeMillis() / 50 % recipeTime);
			if (currentProcessTime > 0) {
				int i = Mth.ceil((float) (currentProcessTime * 29) / recipeTime);
				guiGraphics.blit(RES_LOC, 41, 30, 176, 0, 12, i);
			}
		}
	}
}
