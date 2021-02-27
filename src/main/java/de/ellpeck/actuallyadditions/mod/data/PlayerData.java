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
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.relauncher.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerData {

    public static PlayerSave getDataFromPlayer(PlayerEntity player) {
        WorldData worldData = WorldData.get(player.getEntityWorld());
        ConcurrentHashMap<UUID, PlayerSave> data = worldData.playerSaveData;
        UUID id = player.getUniqueID();

        if (data.containsKey(id)) {
            PlayerSave save = data.get(id);
            if (save != null && save.id != null && save.id.equals(id)) {
                return save;
            }
        }

        //Add Data if none is existant
        PlayerSave save = new PlayerSave(id);
        data.put(id, save);
        worldData.markDirty();
        return save;
    }

    public static class PlayerSave {

        public UUID id;

        public boolean bookGottenAlready;
        public boolean didBookTutorial;
        public boolean hasBatWings;
        public boolean shouldDisableBatWings;
        public int batWingsFlyTime;

        public IBookletPage[] bookmarks = new IBookletPage[12];
        public List<String> completedTrials = new ArrayList<>();

        @OnlyIn(Dist.CLIENT)
        public GuiBooklet lastOpenBooklet;

        public PlayerSave(UUID id) {
            this.id = id;
        }

        public void readFromNBT(CompoundNBT compound, boolean savingToFile) {
            this.bookGottenAlready = compound.getBoolean("BookGotten");
            this.didBookTutorial = compound.getBoolean("DidTutorial");

            this.hasBatWings = compound.getBoolean("HasBatWings");
            this.batWingsFlyTime = compound.getInt("BatWingsFlyTime");

            ListNBT bookmarks = compound.getList("Bookmarks", 8);
            this.loadBookmarks(bookmarks);

            ListNBT trials = compound.getList("Trials", 8);
            this.loadTrials(trials);

            if (!savingToFile) {
                this.shouldDisableBatWings = compound.getBoolean("ShouldDisableWings");
            }
        }

        public void writeToNBT(CompoundNBT compound, boolean savingToFile) {
            compound.putBoolean("BookGotten", this.bookGottenAlready);
            compound.putBoolean("DidTutorial", this.didBookTutorial);

            compound.putBoolean("HasBatWings", this.hasBatWings);
            compound.putInt("BatWingsFlyTime", this.batWingsFlyTime);

            compound.setTag("Bookmarks", this.saveBookmarks());
            compound.setTag("Trials", this.saveTrials());

            if (!savingToFile) {
                compound.putBoolean("ShouldDisableWings", this.shouldDisableBatWings);
            }
        }

        public ListNBT saveBookmarks() {
            ListNBT bookmarks = new ListNBT();
            for (IBookletPage bookmark : this.bookmarks) {
                bookmarks.appendTag(new NBTTagString(bookmark == null
                    ? ""
                    : bookmark.getIdentifier()));
            }
            return bookmarks;
        }

        public void loadBookmarks(ListNBT bookmarks) {
            for (int i = 0; i < bookmarks.size(); i++) {
                String strg = bookmarks.getStringTagAt(i);
                if (strg != null && !strg.isEmpty()) {
                    IBookletPage page = BookletUtils.getBookletPageById(strg);
                    this.bookmarks[i] = page;
                } else {
                    this.bookmarks[i] = null;
                }
            }
        }

        public ListNBT saveTrials() {
            ListNBT trials = new ListNBT();
            for (String trial : this.completedTrials) {
                trials.appendTag(new NBTTagString(trial));
            }
            return trials;
        }

        public void loadTrials(ListNBT trials) {
            this.completedTrials.clear();

            for (int i = 0; i < trials.size(); i++) {
                String strg = trials.getStringTagAt(i);
                if (strg != null && !strg.isEmpty()) {
                    this.completedTrials.add(strg);
                }
            }
        }
    }

}
