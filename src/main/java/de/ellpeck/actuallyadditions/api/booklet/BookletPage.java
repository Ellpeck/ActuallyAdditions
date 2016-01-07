/*
 * This file ("IBookletPage.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.booklet;

import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BookletPage{

    public boolean arePageStacksWildcard;
    protected IBookletChapter chapter;

    /**
     * The ID of the page, for the page number etc.
     * Don't make two pages in the same chapter with the same ID.
     */
    public abstract int getID();

    /**
     * Gets the localized text to be displayed
     */
    public abstract String getText();

    /**
     * This render method ica called before super.drawScreen() is called in the GUI
     */
    @SideOnly(Side.CLIENT)
    public abstract void renderPre(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed);

    /**
     * This render method ica called after super.drawScreen() is called in the GUI
     */
    @SideOnly(Side.CLIENT)
    public abstract void render(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed);

    /**
     * Equivalent to updateScreen() in GuiScreen
     */
    @SideOnly(Side.CLIENT)
    public abstract void updateScreen(int ticksElapsed);

    /**
     * Gets the ItemStacks that are part of or displayed on this page (for NEI Handler, right-click function etc.)
     */
    public abstract ItemStack[] getItemStacksForPage();

    /**
     * Gets the text that is displayed when an Item is hovered over that can be clicked on to go to its page
     */
    public abstract String getClickToSeeRecipeString();

    public IBookletChapter getChapter(){
        return this.chapter;
    }

    public void setChapter(IBookletChapter chapter){
        this.chapter = chapter;
    }

    /**
     * Sets the stacks on the page to be wildcard, meaning the metadata doesn't matter
     * This applies for all stacks at once
     */
    public BookletPage setPageStacksWildcard(){
        this.arePageStacksWildcard = true;
        return this;
    }
}
