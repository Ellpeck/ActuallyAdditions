package de.ellpeck.actuallyadditions.common.jei.reconstructor;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ReconstructorRecipeCategory implements IRecipeCategory<ReconstructorRecipeWrapper> {

    public static final String NAME = "actuallyadditions.reconstructor";

    private static final ItemStack RECONSTRUCTOR = new ItemStack(InitBlocks.blockAtomicReconstructor);
    private final IDrawable background;

    public ReconstructorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("gui_nei_atomic_reconstructor"), 0, 0, 96, 60);
    }

    @Override
    public String getUid() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return StringUtil.localize("container.nei." + NAME + ".name");
    }

    @Override
    public String getModName() {
        return ActuallyAdditions.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        AssetUtil.renderStackToGui(RECONSTRUCTOR, 34, 19, 1.0F);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ReconstructorRecipeWrapper wrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 4, 18);
        recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0).get(0));
        recipeLayout.getItemStacks().init(1, false, 66, 18);
        recipeLayout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0).get(0));
    }
}
