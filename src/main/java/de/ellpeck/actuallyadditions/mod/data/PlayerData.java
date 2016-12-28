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

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerData{

    public static PlayerSave getDataFromPlayer(EntityPlayer player){
        WorldData worldData = WorldData.get(player.getEntityWorld());
        ConcurrentHashMap<UUID, PlayerSave> data = worldData.playerSaveData;
        UUID id = player.getUniqueID();

        if(data.containsKey(id)){
            PlayerSave save = data.get(id);
            if(save != null && save.id != null && save.id.equals(id)){
                return save;
            }
        }

        //Add Data if none is existant
        PlayerSave save = new PlayerSave(id);
        data.put(id, save);
        worldData.markDirty();
        return save;
    }

    public static class PlayerSave{

        public UUID id;

        public boolean bookGottenAlready;
        public boolean didBookTutorial;
        public boolean hasBatWings;
        public boolean shouldDisableBatWings;
        public int batWingsFlyTime;

        public IBookletPage[] bookmarks = new IBookletPage[12];

        @SideOnly(Side.CLIENT)
        public GuiBooklet lastOpenBooklet;

        public PlayerSave(UUID id){
            this.id = id;
        }

        public void readFromNBT(NBTTagCompound compound, boolean savingToFile){
            this.bookGottenAlready = compound.getBoolean("BookGotten");
            this.didBookTutorial = compound.getBoolean("DidTutorial");

            this.hasBatWings = compound.getBoolean("HasBatWings");
            this.batWingsFlyTime = compound.getInteger("BatWingsFlyTime");

            NBTTagList bookmarks = compound.getTagList("Bookmarks", 8);
            for(int i = 0; i < bookmarks.tagCount(); i++){
                String strg = bookmarks.getStringTagAt(i);
                if(strg != null && !strg.isEmpty()){
                    IBookletPage page = BookletUtils.getBookletPageById(strg);
                    this.bookmarks[i] = page;
                }
            }

            if(!savingToFile){
                this.shouldDisableBatWings = compound.getBoolean("ShouldDisableWings");
            }
        }

        public void writeToNBT(NBTTagCompound compound, boolean savingToFile){
            compound.setBoolean("BookGotten", this.bookGottenAlready);
            compound.setBoolean("DidTutorial", this.didBookTutorial);

            compound.setBoolean("HasBatWings", this.hasBatWings);
            compound.setInteger("BatWingsFlyTime", this.batWingsFlyTime);

            NBTTagList bookmarks = new NBTTagList();
            for(IBookletPage bookmark : this.bookmarks){
                bookmarks.appendTag(new NBTTagString(bookmark == null ? "" : bookmark.getIdentifier()));
            }
            compound.setTag("Bookmarks", bookmarks);

            if(!savingToFile){
                compound.setBoolean("ShouldDisableWings", this.shouldDisableBatWings);
            }
        }

    }


}
