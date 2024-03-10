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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerData {
    private static PlayerSave clientData = null;
    public static PlayerSave getDataFromPlayer(Player player) {
        if (!(player.getCommandSenderWorld() instanceof ServerLevel)) {
            if (clientData == null) {
                clientData = new PlayerSave(player.getUUID());
            }
            return clientData;
        }
        WorldData worldData = WorldData.get(player.getCommandSenderWorld());
        ConcurrentHashMap<UUID, PlayerSave> data = worldData.playerSaveData;
        UUID id = player.getUUID();

        if (data.containsKey(id)) {
            PlayerSave save = data.get(id);
            if (save != null && save.id != null && save.id.equals(id)) {
                return save;
            }
        }

        //Add Data if none is existant
        PlayerSave save = new PlayerSave(id);
        data.put(id, save);
        worldData.setDirty();
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

//        @OnlyIn(Dist.CLIENT)
//        public GuiBooklet lastOpenBooklet;

        public PlayerSave(UUID id) {
            this.id = id;
        }

        public void readFromNBT(CompoundTag compound, boolean savingToFile) {
            this.bookGottenAlready = compound.getBoolean("BookGotten");
            this.didBookTutorial = compound.getBoolean("DidTutorial");

            this.hasBatWings = compound.getBoolean("HasBatWings");
            this.batWingsFlyTime = compound.getInt("BatWingsFlyTime");

            ListTag bookmarks = compound.getList("Bookmarks", 8);
            this.loadBookmarks(bookmarks);

            ListTag trials = compound.getList("Trials", 8);
            this.loadTrials(trials);

            if (!savingToFile) {
                this.shouldDisableBatWings = compound.getBoolean("ShouldDisableWings");
            }
        }

        public void writeToNBT(CompoundTag compound, boolean savingToFile) {
            compound.putBoolean("BookGotten", this.bookGottenAlready);
            compound.putBoolean("DidTutorial", this.didBookTutorial);

            compound.putBoolean("HasBatWings", this.hasBatWings);
            compound.putInt("BatWingsFlyTime", this.batWingsFlyTime);

            compound.put("Bookmarks", this.saveBookmarks());
            compound.put("Trials", this.saveTrials());

            if (!savingToFile) {
                compound.putBoolean("ShouldDisableWings", this.shouldDisableBatWings);
            }
        }

        public ListTag saveBookmarks() {
            ListTag bookmarks = new ListTag();
            for (IBookletPage bookmark : this.bookmarks) {
                bookmarks.add(StringTag.valueOf(bookmark == null
                    ? ""
                    : bookmark.getIdentifier()));
            }
            return bookmarks;
        }

        public void loadBookmarks(ListTag bookmarks) {
            for (int i = 0; i < bookmarks.size(); i++) {
                String strg = bookmarks.getString(i);
                if (!strg.isEmpty()) {
//                    IBookletPage page = BookletUtils.getBookletPageById(strg);
                    this.bookmarks[i] = null; // page;
                } else {
                    this.bookmarks[i] = null;
                }
            }
        }

        public ListTag saveTrials() {
            ListTag trials = new ListTag();
            for (String trial : this.completedTrials) {
                trials.add(StringTag.valueOf(trial));
            }
            return trials;
        }

        public void loadTrials(ListTag trials) {
            this.completedTrials.clear();

            for (int i = 0; i < trials.size(); i++) {
                String strg = trials.getString(i);
                if (!strg.isEmpty()) {
                    this.completedTrials.add(strg);
                }
            }
        }
    }

}
