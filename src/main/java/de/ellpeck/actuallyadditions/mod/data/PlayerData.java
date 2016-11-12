/*
 * This file ("PlayerData.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.data;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

public final class PlayerData{

    public static PlayerSave getDataFromPlayer(UUID id){
        List<PlayerSave> data = WorldData.getWorldUnspecificData().playerSaveData;
        //Get Data from existing data
        for(PlayerSave save : data){
            if(save.id != null && save.id.equals(id)){
                return save;
            }
        }

        //Add Data if none is existant
        PlayerSave aSave = new PlayerSave(id);
        data.add(aSave);
        return aSave;
    }

    public static PlayerSave getDataFromPlayer(EntityPlayer player){
        return getDataFromPlayer(player.getUniqueID());
    }

    public static class PlayerSave{

        public UUID id;

        public boolean displayTesla;
        public boolean bookGottenAlready;
        public boolean didBookTutorial;

        public IBookletPage[] bookmarks = new IBookletPage[12];

        @SideOnly(Side.CLIENT)
        public GuiBooklet lastOpenBooklet;

        public PlayerSave(UUID id){
            this.id = id;
        }

        public void readFromNBT(NBTTagCompound compound){
            this.displayTesla = compound.getBoolean("DisplayTesla");
            this.bookGottenAlready = compound.getBoolean("BookGotten");
            this.didBookTutorial = compound.getBoolean("DidTutorial");

            NBTTagList bookmarks = compound.getTagList("Bookmarks", 8);
            for(int i = 0; i < bookmarks.tagCount(); i++){
                String strg = bookmarks.getStringTagAt(i);
                if(strg != null && !strg.isEmpty()){
                    IBookletPage page = BookletUtils.getBookletPageById(strg);
                    this.bookmarks[i] = page;
                }
            }
        }

        public void writeToNBT(NBTTagCompound compound){
            compound.setBoolean("DisplayTesla", this.displayTesla);
            compound.setBoolean("BookGotten", this.bookGottenAlready);
            compound.setBoolean("DidTutorial", this.didBookTutorial);

            NBTTagList bookmarks = new NBTTagList();
            for(IBookletPage bookmark : this.bookmarks){
                bookmarks.appendTag(new NBTTagString(bookmark == null ? "" : bookmark.getIdentifier()));
            }
            compound.setTag("Bookmarks", bookmarks);
        }

    }


}
