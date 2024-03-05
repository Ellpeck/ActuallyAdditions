/*
 * This file ("ReconstructorRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.laser;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.LaserRecipe;
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
import net.minecraft.world.item.ItemStack;

public class LaserRecipeCategory implements IRecipeCategory<LaserRecipe> {
    private final IDrawableStatic background;
    private final ItemStack RECONSTRUCTOR = new ItemStack(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.getItem());

    public LaserRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(AssetUtil.getGuiLocation("gui_nei_atomic_reconstructor"), 0, 0, 96, 60).setTextureSize(256,256).build();
    }

	@Override
	public RecipeType<LaserRecipe> getRecipeType() {
		return JEIActuallyAdditionsPlugin.LASER;
	}

    @Override
    public Component getTitle() {
        return Component.translatable("container.actuallyadditions.reconstructor");
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
    public void setRecipe(IRecipeLayoutBuilder builder, LaserRecipe recipe, IFocusGroup focuses) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            throw new NullPointerException("level must not be null.");
        }
        RegistryAccess registryAccess = level.registryAccess();

        builder.addSlot(RecipeIngredientRole.INPUT, 5, 19).addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 20).addItemStack(RECONSTRUCTOR);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 66, 19).addItemStack(recipe.getResultItem(registryAccess));
    }
}
