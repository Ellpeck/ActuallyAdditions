package de.ellpeck.actuallyadditions.mod.patchouli;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
import de.ellpeck.actuallyadditions.mod.crafting.LaserRecipe;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.Level;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.page.abstr.PageSimpleProcessingRecipe;

public class PageReconstructor extends PageSimpleProcessingRecipe<LaserRecipe> {

	public PageReconstructor() {
		super(ActuallyRecipes.Types.LASER.get());
	}

	@Override
	protected void drawRecipe(GuiGraphics graphics, LaserRecipe recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
		Level level = Minecraft.getInstance().level;
		if (level == null) {
			return;
		}

		RenderSystem.enableBlend();
		graphics.blit(AssetUtil.getBookletGuiLocation("gui_booklet_gadgets"), recipeX + 10, recipeY + 2, 80, 146, 68, 48, 256, 256);
		parent.drawCenteredStringNoShadow(graphics, getTitle(second).getVisualOrderText(), GuiBook.PAGE_WIDTH / 2, recipeY - 10, book.headerColor);

		parent.renderIngredient(graphics, recipeX + 11, recipeY + 15, mouseX, mouseY, recipe.getInput());
		parent.renderItemStack(graphics, recipeX + 33, recipeY + 15, mouseX, mouseY, recipe.getToastSymbol());
		parent.renderItemStack(graphics, recipeX + 57, recipeY + 15, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
	}

	@Override
	public int getTextHeight() {
		return 60;
	}

	@Override
	public boolean shouldRenderText() {
		return true;
	}

	@Override
	protected int getRecipeHeight() {
		return 90;
	}
}
