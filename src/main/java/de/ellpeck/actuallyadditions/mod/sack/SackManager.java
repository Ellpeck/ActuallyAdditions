package de.ellpeck.actuallyadditions.mod.sack;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

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
            return ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(new Factory<>(SackManager::new, SackManager::load), NAME);
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
//            backpack.getOptional().invalidate();
            data.remove(uuid);
            setDirty();
        });
    }

    public Optional<IItemHandler> getCapability(UUID uuid) {
        if (data.containsKey(uuid))
            return data.get(uuid).getOptional();

        return Optional.empty();
    }

    public Optional<IItemHandler> getCapability(ItemStack stack) {
        if (stack.has(ActuallyComponents.UUID)) {
            UUID uuid = stack.get(ActuallyComponents.UUID);
            if (data.containsKey(uuid))
                return data.get(uuid).getOptional();
        }

        return Optional.empty();
    }

    public Optional<ItemStackHandlerAA> getHandler(ItemStack stack) {
        if (stack.has(ActuallyComponents.UUID)) {
            UUID uuid = stack.get(ActuallyComponents.UUID);
            if (data.containsKey(uuid))
                return Optional.of(data.get(uuid).getSpecialHandler());
        }

        return Optional.empty();
    }

    public static SackManager load(CompoundTag nbt, HolderLookup.Provider provider) {
        if (nbt.contains("Sacks")) {
            ListTag list = nbt.getList("Sacks", CompoundTag.TAG_COMPOUND);
            list.forEach((sackNBT) -> SackData.fromNBT((CompoundTag) sackNBT, provider).ifPresent((sack) -> data.put(sack.getUuid(), sack)));
        }
        return new SackManager();
    }

    @Override
    @Nonnull
    public CompoundTag save(CompoundTag compound, HolderLookup.Provider provider) {
        ListTag sacks = new ListTag();
        data.forEach(((uuid, sackData) -> sacks.add(sackData.toNBT(provider))));
        compound.put("Sacks", sacks);
        return compound;
    }
}
