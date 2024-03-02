package de.ellpeck.actuallyadditions.mod.sack;

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import java.util.Optional;
import java.util.UUID;

//Mainly copied my backpacks into here for the sacks --Flanks255
public class SackData {
    public static final int SIZE = 28;
    private final UUID uuid;

    private final ItemStackHandlerAA inventory;
    private final LazyOptional<IItemHandler> optional;

    public final Metadata meta = new Metadata();

    public LazyOptional<IItemHandler> getOptional() {
        return optional;
    }

    public IItemHandler getHandler() {
        return inventory;
    }
    public ItemStackHandlerAA getSpecialHandler() {
        return inventory;
    }

    public void updateAccessRecords(String player, long time) {
        if (meta.firstAccessedTime == 0) {
            meta.firstAccessedTime = time;
            meta.firstAccessedPlayer = player;
        }

        meta.setLastAccessedTime(time);
        meta.setLastAccessedPlayer(player);
    }

    public SackData(UUID uuid) {
        this.uuid = uuid;

        inventory = new ItemStackHandlerAA(SIZE) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);

                SackManager.get().setDirty();
            }
        };
        optional = LazyOptional.of(() -> inventory);
    }

    public SackData(UUID uuid, CompoundTag incoming) {
        this.uuid = uuid;

        inventory = new ItemStackHandlerAA(SIZE){
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);

                SackManager.get().setDirty();
            }
        };

        inventory.deserializeNBT(incoming.getCompound("Inventory"));

        optional = LazyOptional.of(() -> inventory);

        if (incoming.contains("Metadata"))
            meta.deserializeNBT(incoming.getCompound("Metadata"));
    }

    public UUID getUuid() {
        return uuid;
    }

    public static Optional<SackData> fromNBT(CompoundTag nbt) {
        if (nbt.contains("UUID")) {
            UUID uuid = nbt.getUUID("UUID");
            return Optional.of(new SackData(uuid, nbt));
        }
        return Optional.empty();
    }

    public CompoundTag toNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putUUID("UUID", uuid);

        nbt.put("Inventory", inventory.serializeNBT());

        nbt.put("Metadata", meta.serializeNBT());

        return nbt;
    }

    public static class Metadata implements INBTSerializable<CompoundTag> {
        private String firstAccessedPlayer = "";

        private long firstAccessedTime = 0;
        private String lastAccessedPlayer = "";
        private long lastAccessedTime = 0;
        public long getLastAccessedTime() {
            return lastAccessedTime;
        }

        public void setLastAccessedTime(long lastAccessedTime) {
            this.lastAccessedTime = lastAccessedTime;
        }

        public String getLastAccessedPlayer() {
            return lastAccessedPlayer;
        }

        public void setLastAccessedPlayer(String lastAccessedPlayer) {
            this.lastAccessedPlayer = lastAccessedPlayer;
        }

        public long getFirstAccessedTime() {
            return firstAccessedTime;
        }

        public String getFirstAccessedPlayer() {
            return firstAccessedPlayer;
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();

            nbt.putString("firstPlayer", firstAccessedPlayer);
            nbt.putLong("firstTime", firstAccessedTime);
            nbt.putString("lastPlayer", lastAccessedPlayer);
            nbt.putLong("lastTime", lastAccessedTime);

            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            firstAccessedPlayer = nbt.getString("firstPlayer");
            firstAccessedTime = nbt.getLong("firstTime");
            lastAccessedPlayer = nbt.getString("lastPlayer");
            lastAccessedTime = nbt.getLong("lastTime");
        }
    }
}
