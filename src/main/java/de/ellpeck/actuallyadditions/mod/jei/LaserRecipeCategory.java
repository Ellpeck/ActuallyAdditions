/*
 * This file ("ReconstructorRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.LaserRecipe;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class LaserRecipeCategory implements IRecipeCategory<LaserRecipe> {

    public static final ResourceLocation ID = new ResourceLocation(ActuallyAdditions.MODID, "laser_jei");
    private final IDrawableStatic background;
    private final ItemStack RECONSTRUCTOR = new ItemStack(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.getItem());

    public LaserRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(AssetUtil.getGuiLocation("gui_nei_atomic_reconstructor"), 0, 0, 96, 60).setTextureSize(256,256).build();
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends LaserRecipe> getRecipeClass() {
        return LaserRecipe.class;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("container.actuallyadditions.reconstructor").getString();
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
    public void setIngredients(LaserRecipe laserRecipe, IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, Arrays.asList(laserRecipe.getInput().getItems()));
        ingredients.setOutput(VanillaTypes.ITEM, laserRecipe.getResultItem());
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull LaserRecipe laserRecipe, @Nonnull IIngredients iIngredients) {
        layout.getItemStacks().init(0, true, 4, 18);
        layout.getItemStacks().set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));

        layout.getItemStacks().init(1, false, 34, 19);
        layout.getItemStacks().set(1, RECONSTRUCTOR);

        layout.getItemStacks().init(2, false, 66, 18);
        layout.getItemStacks().set(2, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
