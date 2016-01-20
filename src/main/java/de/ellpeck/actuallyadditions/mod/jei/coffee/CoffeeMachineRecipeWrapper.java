/*
 * This file ("CoffeeMachineRecipeWrapper.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.coffee;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.recipe.coffee.CoffeeBrewing;
import de.ellpeck.actuallyadditions.api.recipe.coffee.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoffeeMachineRecipeWrapper extends RecipeWrapperWithButton implements IRecipeWrapper{

    public CoffeeIngredient theIngredient;
    public ItemStack theOutput;
    public ItemStack cup = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal());
    public ItemStack coffeeBeans = new ItemStack(InitItems.itemCoffeeBean);

    public CoffeeMachineRecipeWrapper(CoffeeIngredient ingredient){
        this.theIngredient = ingredient;

        this.theOutput = new ItemStack(InitItems.itemCoffee);
        CoffeeBrewing.addEffectToStack(this.theOutput, this.theIngredient);
    }

    @Override
    public List getInputs(){
        List list = new ArrayList();
        list.add(this.theIngredient.ingredient);
        list.add(this.cup);
        list.add(this.coffeeBeans);
        return list;
    }

    @Override
    public List getOutputs(){
        return Collections.singletonList(this.theOutput);
    }

    @Override
    public List<FluidStack> getFluidInputs(){
        return new ArrayList<FluidStack>();
    }

    @Override
    public List<FluidStack> getFluidOutputs(){
        return new ArrayList<FluidStack>();
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight){

    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY){
        this.updateButton(minecraft, mouseX, mouseY);

        if(this.theIngredient.getExtraText() != null){
            minecraft.fontRendererObj.drawString(StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".coffee.special")+":", 2, 4, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
            minecraft.fontRendererObj.drawString(this.theIngredient.getExtraText(), 2, 16, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }

        if(this.theIngredient.maxAmplifier > 0){
            minecraft.fontRendererObj.drawString(StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".coffee.maxAmount")+": "+this.theIngredient.maxAmplifier, 2, 28, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight){

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY){
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton){
        return this.handleClick(minecraft, mouseX, mouseY);
    }

    @Override
    public BookletPage getPage(){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockCoffeeMachine));
    }

    @Override
    public int getButtonX(){
        return 0;
    }

    @Override
    public int getButtonY(){
        return 70;
    }
}
