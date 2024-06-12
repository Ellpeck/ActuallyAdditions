package de.ellpeck.actuallyadditions.mod.jei.coffee;

import com.google.common.base.Strings;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.CoffeeIngredientRecipe;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class CoffeeMachineCategory implements IRecipeCategory<CoffeeIngredientRecipe> {
	private final IDrawableStatic background;

	public CoffeeMachineCategory(IGuiHelper helper) {
		this.background = helper.drawableBuilder(AssetUtil.getGuiLocation("gui_nei_coffee_machine"), 0, 0, 126, 92).setTextureSize(256,256).build();
	}

	@Override
	public RecipeType<CoffeeIngredientRecipe> getRecipeType() {
		return JEIActuallyAdditionsPlugin.COFFEE_MACHINE;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("container.actuallyadditions.coffeeMachine");
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
	public void setRecipe(IRecipeLayoutBuilder builder, CoffeeIngredientRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 2, 39).addIngredients(Ingredient.of(ActuallyTags.Items.COFFEE_BEANS));
		builder.addSlot(RecipeIngredientRole.INPUT, 90, 21).addIngredients(recipe.getIngredient());
		builder.addSlot(RecipeIngredientRole.INPUT, 45, 39).addItemStack(new ItemStack(ActuallyItems.EMPTY_CUP.get()));

		ItemStack output = new ItemStack(ActuallyItems.COFFEE_CUP.get());
		ActuallyAdditionsAPI.methodHandler.addRecipeEffectToStack(output, recipe);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 45, 70).addItemStack(output);
	}

	@Override
	public void draw(CoffeeIngredientRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

		Minecraft mc = Minecraft.getInstance();
		if (!Strings.isNullOrEmpty(recipe.getExtraText())) {
			guiGraphics.drawString(mc.font, Component.translatable("jei.actuallyadditions.coffee.special").append( ":"), 2, 4, 4210752, false);
			guiGraphics.drawString(mc.font, Component.translatable(recipe.getExtraText()), 2, 16, 4210752, false);
		}

		if (recipe.getMaxAmplifier() > 0) {
			guiGraphics.drawString(mc.font, Component.translatable("jei.actuallyadditions.coffee.maxAmount").append(": " + recipe.getMaxAmplifier()), 2, 28, 4210752, false);
		}
	}
}
