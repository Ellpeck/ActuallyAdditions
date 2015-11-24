/*
 * This file ("GuiBookStand.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.network.PacketBookletStandButton;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityBookletStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiBookletStand extends GuiBooklet{

    private GuiButton buttonSetPage;

    private TileEntityBookletStand theStand;

    public GuiBookletStand(TileEntityBase theStand){
        super(null, false, false);
        this.theStand = (TileEntityBookletStand)theStand;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        super.initGui();

        //Remove Bookmark Buttons
        for(GuiButton bookmarkButton : this.bookmarkButtons){
            bookmarkButton.visible = false;
        }

        this.buttonSetPage = new GuiButton(-100, this.guiLeft+this.xSize+10, this.guiTop+10, 100, 20, "Set Page"){
            @Override
            public void drawButton(Minecraft mc, int x, int y){
                boolean unicodeBefore = mc.fontRenderer.getUnicodeFlag();
                mc.fontRenderer.setUnicodeFlag(false);
                super.drawButton(mc, x, y);
                mc.fontRenderer.setUnicodeFlag(unicodeBefore);
            }
        };
        this.buttonList.add(this.buttonSetPage);

        this.buttonSetPage.visible = Minecraft.getMinecraft().thePlayer.getUniqueID().equals(this.theStand.assignedPlayerUUID);

        //Open the pages the book was assigned
        BookletUtils.openIndexEntry(this, this.theStand.assignedEntry, this.theStand.assignedPageInIndex, true);
        BookletUtils.openChapter(this, this.theStand.assignedChapter, this.theStand.assignedPage);
    }

    @Override
    public void actionPerformed(GuiButton button){
        if(button == this.buttonSetPage){
            PacketHandler.theNetwork.sendToServer(new PacketBookletStandButton(this.theStand.xCoord, this.theStand.yCoord, this.theStand.zCoord, this.theStand.getWorldObj(), Minecraft.getMinecraft().thePlayer, this.currentIndexEntry, this.currentChapter, this.currentPage, this.pageOpenInIndex));
        }
        super.actionPerformed(button);
    }
}