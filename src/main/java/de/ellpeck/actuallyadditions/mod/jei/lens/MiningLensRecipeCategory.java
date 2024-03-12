/*
 * This file ("ReconstructorRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.lens;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.MiningLensRecipe;
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
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class MiningLensRecipeCategory implements IRecipeCategory<MiningLensRecipe> {
    private final IDrawableStatic background;
    private final ItemStack RECONSTRUCTOR = new ItemStack(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.getItem());

    public MiningLensRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(AssetUtil.getGuiLocation("gui_nei_atomic_reconstructor"), 0, 0, 96, 60).setTextureSize(256,256).build();
    }

	@Override
	public RecipeType<MiningLensRecipe> getRecipeType() {
		return JEIActuallyAdditionsPlugin.MINING_LENS;
	}

    @Override
    public Component getTitle() {
        return Component.translatable("jei.actuallyadditions.mining_lens");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MiningLensRecipe recipe, IFocusGroup focuses) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            throw new NullPointerException("level must not be null.");
        }
        RegistryAccess registryAccess = level.registryAccess();

        builder.addSlot(RecipeIngredientRole.INPUT, 5, 19).addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 20).addItemStack(RECONSTRUCTOR);
        builder.addSlot(RecipeIngredientRole.INPUT, 43, 20).addItemStack(new ItemStack(ActuallyItems.LENS_OF_THE_MINER.get()));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 66, 19).addItemStack(recipe.getResultItem(registryAccess));
    }

    @Override
    public void draw(MiningLensRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft mc = Minecraft.getInstance();

        Component component = Component.translatable("jei.actuallyadditions.mining_lens.weight");
        guiGraphics.drawString(mc.font, component, 2, 42, 0, false);

        String weight = String.valueOf(recipe.getWeight());
        guiGraphics.drawString(mc.font, weight, 16 - mc.font.width(weight) / 2, 52, 0, false);
    }
}
