/*
 * This file ("GuiMainPage.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.gui;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.mod.booklet.button.EntryButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiMainPage extends GuiBooklet{

    public GuiMainPage(GuiScreen parent){
        super(parent);
    }

    @Override
    public void initGui(){
        super.initGui();

        for(int x = 0; x < 2; x++){
            for(int y = 0; y < BUTTONS_PER_PAGE; y++){
                int id = y+x*BUTTONS_PER_PAGE;
                if(ActuallyAdditionsAPI.BOOKLET_ENTRIES.size() > id){
                    IBookletEntry entry = ActuallyAdditionsAPI.BOOKLET_ENTRIES.get(id);
                    this.buttonList.add(new EntryButton(id, this.guiLeft+12, this.guiTop+12+y*16, 115, 10, entry.getLocalizedNameWithFormatting(), null));
                }
                else{
                    return;
                }
            }
        }
    }
}
