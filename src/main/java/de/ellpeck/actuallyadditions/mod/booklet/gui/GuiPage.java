/*
 * This file ("GuiPage.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.gui;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.booklet.internal.IPageGui;
import net.minecraft.client.gui.GuiScreen;

public class GuiPage extends GuiBooklet implements IPageGui{

    private final IBookletPage page;

    public GuiPage(GuiScreen parent, IBookletPage page){
        super(parent);
        this.page = page;
    }

    @Override
    public IBookletPage getPage(){
        return this.page;
    }
}
