package de.ellpeck.actuallyadditions.mod.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.FermentingRecipe;
import de.ellpeck.actuallyadditions.mod.inventory.gui.FluidDisplay;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

import javax.annotation.Nonnull;

public class FermentingCategory implements IRecipeCategory<FermentingRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(ActuallyAdditions.MODID, "fermenting_jei");

    private final IDrawableStatic background;

    public FermentingCategory(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(new ResourceLocation(ActuallyAdditions.MODID, "textures/gui/gui_fermenting_barrel.png"), 41, 4, 94, 86).setTextureSize(256,256).build();
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends FermentingRecipe> getRecipeClass() {
        return FermentingRecipe.class;
    }

    @Override
    public String getTitle() {
        return "Fermenting Recipe";
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
    public void setIngredients(@Nonnull FermentingRecipe fermentingRecipe, @Nonnull IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, fermentingRecipe.getInput());
        ingredients.setOutput(VanillaTypes.FLUID, fermentingRecipe.getOutput());
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull FermentingRecipe fermentingRecipe, @Nonnull IIngredients ingredients) {
        int maxFluid = Math.max(FluidAttributes.BUCKET_VOLUME, Math.max(fermentingRecipe.getInput().getAmount(), fermentingRecipe.getOutput().getAmount()));

        fermentingRecipe.setInputDisplay(new FluidDisplay(19, 1, fermentingRecipe.getInput(), maxFluid, false));
        fermentingRecipe.setOutputDisplay(new FluidDisplay(19+38, 1, fermentingRecipe.getOutput(), maxFluid, false));
    }

    @Override
    public void draw(FermentingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

        recipe.getInputDisplay().ifPresent(display -> {
            display.draw(matrixStack);
            display.render(matrixStack, (int) mouseX, (int) mouseY);
        });

        recipe.getOutputDisplay().ifPresent(display -> {
            display.draw(matrixStack);
            display.render(matrixStack, (int) mouseX, (int) mouseY);
        });


        //TODO draw the progress indicator, scaled to the recipe duration.
    }
}
