package de.ellpeck.actuallyadditions.mod.jei.pressing;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.PressingRecipe;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

public class PressingCategory implements IRecipeCategory<PressingRecipe> {

    private final IDrawableStatic background;
    private final IDrawableStatic fluidBackground;

    public PressingCategory(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(new ResourceLocation(ActuallyAdditions.MODID, "textures/gui/gui_canola_press.png"), 41, 4, 93, 85).setTextureSize(256,256).build();
        fluidBackground = guiHelper.drawableBuilder(AssetUtil.GUI_INVENTORY_LOCATION, 0, 171, 18, 85).build();
    }
    @Override
    public RecipeType<PressingRecipe> getRecipeType() {
        return JEIActuallyAdditionsPlugin.PRESSING;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Pressing Recipe");
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
    public void setRecipe(IRecipeLayoutBuilder builder, PressingRecipe recipe, IFocusGroup iFocusGroup) {
        Ingredient input = recipe.getInput();
        FluidStack output = recipe.getOutput();

        builder.addSlot(RecipeIngredientRole.INPUT, 40, 6)
                .addIngredients(input);

        int height = (int)(83D / 1000 * output.getAmount());
        int offset = 83 - height;

        builder.addSlot(RecipeIngredientRole.OUTPUT, 75, 1 + offset)
                .addFluidStack(output.getFluid(), output.getAmount())
                .setFluidRenderer(output.getAmount(), false, 16, height)
                .setBackground(fluidBackground, -1, -1 - offset);
    }
}
