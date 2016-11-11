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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiMainPage extends GuiBooklet{

    public GuiMainPage(GuiScreen previousScreen){
        super(previousScreen, null);
    }

    @Override
    public void initGui(){
        super.initGui();

        for(int x = 0; x < 2; x++){
            for(int y = 0; y < BUTTONS_PER_PAGE; y++){
                int id = y+x*BUTTONS_PER_PAGE;
                if(ActuallyAdditionsAPI.BOOKLET_ENTRIES.size() > id){
                    IBookletEntry entry = ActuallyAdditionsAPI.BOOKLET_ENTRIES.get(id);
                    this.buttonList.add(new EntryButton(id, this.guiLeft+14+x*142, this.guiTop+11+y*16, 115, 10, entry.getLocalizedNameWithFormatting(), null));
                }
                else{
                    return;
                }
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        if(button instanceof EntryButton){
            if(ActuallyAdditionsAPI.BOOKLET_ENTRIES.size() > button.id){
                IBookletEntry entry = ActuallyAdditionsAPI.BOOKLET_ENTRIES.get(button.id);
                if(entry != null){
                    this.mc.displayGuiScreen(new GuiEntry(this.previousScreen, this, entry, 0));
                }
            }
        }
        else{
            super.actionPerformed(button);
        }
    }

    @Override
    public void addItemRenderer(ItemStack renderedStack, int x, int y, float scale, boolean shouldTryTransfer){

    }
}
