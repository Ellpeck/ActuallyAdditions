package de.ellpeck.actuallyadditions.common.jei.coffee;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.common.items.InitItems;
import de.ellpeck.actuallyadditions.common.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.common.jei.RecipeWrapperWithButton;
import de.ellpeck.actuallyadditions.common.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class CoffeeMachineRecipeWrapper extends RecipeWrapperWithButton {

    public final CoffeeIngredient ingredient;
    public final ItemStack theOutput;
    public final ItemStack cup = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal());

    public CoffeeMachineRecipeWrapper(CoffeeIngredient ingredient) {
        this.ingredient = ingredient;

        this.theOutput = new ItemStack(InitItems.itemCoffee);
        ActuallyAdditionsAPI.methodHandler.addEffectToStack(this.theOutput, this.ingredient);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack s : this.ingredient.getInput().getMatchingStacks())
            list.add(s);
        list.add(this.cup);
        for (ItemStack s : TileEntityCoffeeMachine.COFFEE.getMatchingStacks())
            list.add(s);
        ingredients.setInputs(VanillaTypes.ITEM, list);

        ingredients.setOutput(VanillaTypes.ITEM, this.theOutput);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (!Strings.isNullOrEmpty(this.ingredient.getExtraText())) {
            minecraft.fontRenderer.drawString(StringUtil.localize("container.nei." + ActuallyAdditions.MODID + ".coffee.special") + ":", 2, 4, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
            minecraft.fontRenderer.drawString(this.ingredient.getExtraText(), 2, 16, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }

        if (this.ingredient.getMaxAmplifier() > 0) {
            minecraft.fontRenderer.drawString(StringUtil.localize("container.nei." + ActuallyAdditions.MODID + ".coffee.maxAmount") + ": " + this.ingredient.getMaxAmplifier(), 2, 28, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }

        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
    }

    @Override
    public int getButtonX() {
        return 0;
    }

    @Override
    public int getButtonY() {
        return 68;
    }

    @Override
    public IBookletPage getPage() {
        return BookletUtils.findFirstPageForStack(new ItemStack(InitBlocks.blockCoffeeMachine));
    }
}
