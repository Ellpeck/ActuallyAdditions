package de.ellpeck.actuallyadditions.mod.sack;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class SackManager extends SavedData {
    private static final String NAME = ActuallyAdditions.MODID + "_sack_data";

    private static final SackManager blankClient = new SackManager();

    private static final HashMap<UUID, SackData> data = new HashMap<>();

    public SackManager() {
        super();
    }

    public static SackManager get() {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            return ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(SackManager::load, SackManager::new, NAME);
        else
            return blankClient;
    }

    public Optional<SackData> getSack(UUID uuid) {
        if (data.containsKey(uuid))
            return Optional.of(data.get(uuid));
        return Optional.empty();
    }

    public SackData getOrCreateSack(UUID uuid) {
        return data.computeIfAbsent(uuid, id -> {
            setDirty();
            return new SackData(id);
        });
    }
    public void removeSack(UUID uuid) {
        getSack(uuid).ifPresent(backpack -> {
            backpack.getOptional().invalidate();
            data.remove(uuid);
            setDirty();
        });
    }

    public LazyOptional<IItemHandler> getCapability(UUID uuid) {
        if (data.containsKey(uuid))
            return data.get(uuid).getOptional();

        return LazyOptional.empty();
    }

    public LazyOptional<IItemHandler> getCapability(ItemStack stack) {
        if (stack.getOrCreateTag().contains("UUID")) {
            UUID uuid = stack.getTag().getUUID("UUID");
            if (data.containsKey(uuid))
                return data.get(uuid).getOptional();
        }

        return LazyOptional.empty();
    }

    public static SackManager load(CompoundTag nbt) {
        if (nbt.contains("Sacks")) {
            ListTag list = nbt.getList("Sacks", CompoundTag.TAG_COMPOUND);
            list.forEach((sackNBT) -> SackData.fromNBT((CompoundTag) sackNBT).ifPresent((sack) -> data.put(sack.getUuid(), sack)));
        }
        return new SackManager();
    }

    @Override
    @Nonnull
    public CompoundTag save(CompoundTag compound) {
        ListTag sacks = new ListTag();
        data.forEach(((uuid, sackData) -> sacks.add(sackData.toNBT())));
        compound.put("Sacks", sacks);
        return compound;
    }
}
