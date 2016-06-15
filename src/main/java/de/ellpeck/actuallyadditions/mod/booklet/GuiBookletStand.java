/*
 * This file ("GuiBookletStand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet;

import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBookletStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("X", this.theStand.getPos().getX());
            compound.setInteger("Y", this.theStand.getPos().getY());
            compound.setInteger("Z", this.theStand.getPos().getZ());
            compound.setInteger("PlayerID", Minecraft.getMinecraft().thePlayer.getEntityId());
            compound.setInteger("WorldID", this.theStand.getWorld().provider.getDimension());
            compound.setTag("EntrySet", this.currentEntrySet.writeToNBT());
            PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.BOOKLET_STAND_BUTTON_HANDLER));
        }
        super.actionPerformed(button);
    }

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

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(player != null && player.getName() != null){
            this.buttonSetPage.visible = player.getName().equalsIgnoreCase(this.theStand.assignedPlayer);
        }

        //Open the pages the book was assigned
        BookletUtils.openIndexEntry(this, this.theStand.assignedEntry.entry, this.theStand.assignedEntry.pageInIndex, true);
        BookletUtils.openChapter(this, this.theStand.assignedEntry.chapter, this.theStand.assignedEntry.page);
    }
}
