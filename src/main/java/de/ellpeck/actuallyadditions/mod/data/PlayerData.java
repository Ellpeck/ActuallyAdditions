/*
 * This file ("PlayerData.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.data;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PlayerData{

    public static PlayerSave getDataFromPlayer(EntityPlayer player){
        WorldData data = WorldData.get(player.getEntityWorld());
        UUID id = player.getUniqueID();

        if(data.playerSaveData.containsKey(id)){
            PlayerSave save = data.playerSaveData.get(id);
            if(save != null && save.id != null && save.id.equals(id)){
                return save;
            }
        }

        //Add Data if none is existant
        PlayerSave save = new PlayerSave(id);
        data.playerSaveData.put(id, save);
        data.markDirty();
        return save;
    }

    public static class PlayerSave{

        public UUID id;

        public boolean bookGottenAlready;
        public boolean didBookTutorial;
        public boolean hasBatWings;
        public boolean shouldDisableBatWings;
        public int batWingsFlyTime;

        public int receivedCaveMessages;

        public IBookletPage[] bookmarks = new IBookletPage[12];
        public List<String> completedTrials = new ArrayList<String>();

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
            this.loadBookmarks(bookmarks);

            NBTTagList trials = compound.getTagList("Trials", 8);
            this.loadTrials(trials);

            if(!savingToFile){
                this.shouldDisableBatWings = compound.getBoolean("ShouldDisableWings");
            }

            if(ActuallyAdditions.isCaveMode){
                this.receivedCaveMessages = compound.getInteger("ReceivedCaveMessages");
            }
        }

        public void writeToNBT(NBTTagCompound compound, boolean savingToFile){
            compound.setBoolean("BookGotten", this.bookGottenAlready);
            compound.setBoolean("DidTutorial", this.didBookTutorial);

            compound.setBoolean("HasBatWings", this.hasBatWings);
            compound.setInteger("BatWingsFlyTime", this.batWingsFlyTime);

            compound.setTag("Bookmarks", this.saveBookmarks());
            compound.setTag("Trials", this.saveTrials());

            if(!savingToFile){
                compound.setBoolean("ShouldDisableWings", this.shouldDisableBatWings);
            }

            if(ActuallyAdditions.isCaveMode){
                compound.setInteger("ReceivedCaveMessages", this.receivedCaveMessages);
            }
        }

        public NBTTagList saveBookmarks(){
            NBTTagList bookmarks = new NBTTagList();
            for(IBookletPage bookmark : this.bookmarks){
                bookmarks.appendTag(new NBTTagString(bookmark == null ? "" : bookmark.getIdentifier()));
            }
            return bookmarks;
        }

        public void loadBookmarks(NBTTagList bookmarks){
            for(int i = 0; i < bookmarks.tagCount(); i++){
                String strg = bookmarks.getStringTagAt(i);
                if(strg != null && !strg.isEmpty()){
                    IBookletPage page = BookletUtils.getBookletPageById(strg);
                    this.bookmarks[i] = page;
                }
                else{
                    this.bookmarks[i] = null;
                }
            }
        }

        public NBTTagList saveTrials(){
            NBTTagList trials = new NBTTagList();
            for(String trial : this.completedTrials){
                trials.appendTag(new NBTTagString(trial));
            }
            return trials;
        }

        public void loadTrials(NBTTagList trials){
            this.completedTrials.clear();

            for(int i = 0; i < trials.tagCount(); i++){
                String strg = trials.getStringTagAt(i);
                if(strg != null && !strg.isEmpty()){
                    this.completedTrials.add(strg);
                }
            }
        }
    }


}
