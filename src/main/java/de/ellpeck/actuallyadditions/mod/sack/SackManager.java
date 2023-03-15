package de.ellpeck.actuallyadditions.mod.sack;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class SackManager extends WorldSavedData {
    private static final String NAME = ActuallyAdditions.MODID + "_sack_data";

    private static final SackManager blankClient = new SackManager();

    private static final HashMap<UUID, SackData> data = new HashMap<>();

    public SackManager() {
        super(NAME);
    }

    public static SackManager get() {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            return ServerLifecycleHooks.getCurrentServer().getLevel(World.OVERWORLD).getDataStorage().computeIfAbsent(SackManager::new, NAME);
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

    @Override
    public void load(CompoundNBT nbt) {
        if (nbt.contains("Sacks")) {
            ListNBT list = nbt.getList("Sacks", Constants.NBT.TAG_COMPOUND);
            list.forEach((sackNBT) -> SackData.fromNBT((CompoundNBT) sackNBT).ifPresent((sack) -> data.put(sack.getUuid(), sack)));
        }
    }

    @Override
    @Nonnull
    public CompoundNBT save(CompoundNBT compound) {
        ListNBT sacks = new ListNBT();
        data.forEach(((uuid, sackData) -> sacks.add(sackData.toNBT())));
        compound.put("Sacks", sacks);
        return compound;
    }
}
