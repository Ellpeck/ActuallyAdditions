/*
 * This file ("ReconstructorRecipeWrapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.reconstructor;

import java.util.Arrays;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
import de.ellpeck.actuallyadditions.mod.recipe.EnchBookConversion;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class ReconstructorRecipeWrapper extends RecipeWrapperWithButton {

    public static final IRecipeWrapperFactory<LensConversionRecipe> FACTORY = (recipe) -> {
        if (recipe instanceof EnchBookConversion) return new EnchBookWrapper((EnchBookConversion) recipe);
        return new ReconstructorRecipeWrapper(recipe);
    };

    public final LensConversionRecipe theRecipe;

    public ReconstructorRecipeWrapper(LensConversionRecipe recipe) {
        this.theRecipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, Arrays.asList(this.theRecipe.getInput().getMatchingStacks()));
        ingredients.setOutput(VanillaTypes.ITEM, this.theRecipe.getOutput());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(this.theRecipe.getEnergyUsed() + " CF", 55, 0, 0xFFFFFF, true);
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
    }

    @Override
    public int getButtonX() {
        return 3;
    }

    @Override
    public int getButtonY() {
        return 40;
    }

    @Override
    public IBookletPage getPage() {
        return BookletUtils.findFirstPageForStack(new ItemStack(InitBlocks.blockAtomicReconstructor));
    }

    public static class EnchBookWrapper extends ReconstructorRecipeWrapper {

        private static final ItemStack BOOK = new ItemStack(Items.ENCHANTED_BOOK);
        private static final ItemStack OUT = new ItemStack(Items.ENCHANTED_BOOK);

        static {
            OUT.setStackDisplayName("Split Book");
            NBTTagCompound t = OUT.getTagCompound().getCompoundTag("display");
            NBTTagList l = new NBTTagList();
            l.appendTag(new NBTTagString("Book will be split based on enchantments!"));
            t.setTag("Lore", l);
        }

        public EnchBookWrapper(EnchBookConversion recipe) {
            super(recipe);
        }

        @Override
        public void getIngredients(IIngredients ingredients) {
            ingredients.setInput(VanillaTypes.ITEM, BOOK);
            ingredients.setOutput(VanillaTypes.ITEM, OUT);
        }

    }
}
