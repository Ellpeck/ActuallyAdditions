/*
 * This file ("GuiBookletStand.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet;

import de.ellpeck.actuallyadditions.mod.network.PacketBookletStandButton;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBookletStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class GuiBookletStand extends GuiBooklet{

    private GuiButton buttonSetPage;

    private TileEntityBookletStand theStand;

    public GuiBookletStand(TileEntityBase theStand){
        super(null, false, false);
        this.theStand = (TileEntityBookletStand)theStand;
    }

    @Override
    public void actionPerformed(GuiButton button){
        if(button == this.buttonSetPage){
            PacketHandler.theNetwork.sendToServer(new PacketBookletStandButton(this.theStand.getPos().getX(), this.theStand.getPos().getY(), this.theStand.getPos().getZ(), this.theStand.getWorld(), Minecraft.getMinecraft().thePlayer, this.currentEntrySet));
        }
        super.actionPerformed(button);
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
                boolean unicodeBefore = mc.fontRendererObj.getUnicodeFlag();
                mc.fontRendererObj.setUnicodeFlag(false);
                super.drawButton(mc, x, y);
                mc.fontRendererObj.setUnicodeFlag(unicodeBefore);
            }
        };
        this.buttonList.add(this.buttonSetPage);

        this.buttonSetPage.visible = Objects.equals(Minecraft.getMinecraft().thePlayer.getName(), this.theStand.assignedPlayer);

        //Open the pages the book was assigned
        BookletUtils.openIndexEntry(this, this.theStand.assignedEntry.entry, this.theStand.assignedEntry.pageInIndex, true);
        BookletUtils.openChapter(this, this.theStand.assignedEntry.chapter, this.theStand.assignedEntry.page);
    }
}