/*
 * This file ("TileEntityEnergizer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerEnergizer;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.Optional;

public class TileEntityEnergizer extends TileEntityInventoryBase implements MenuProvider {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 1000, 0);
    private int lastEnergy;

    public TileEntityEnergizer(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.ENERGIZER.getTileEntityType(), pos, state, 2);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, lookupProvider, type);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, lookupProvider, type);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityEnergizer tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityEnergizer tile) {
            tile.serverTick();

            if (!tile.inv.getStackInSlot(0).isEmpty() && tile.inv.getStackInSlot(1).isEmpty()) {
                if (tile.storage.getEnergyStored() > 0) {
                    Optional<IEnergyStorage> capability = Optional.ofNullable(tile.inv.getStackInSlot(0).getCapability(Capabilities.EnergyStorage.ITEM, null));

                    int received = capability.map(cap -> cap.receiveEnergy(tile.storage.getEnergyStored(), false)).orElse(0);
                    boolean canTakeUp = capability.map(cap -> cap.getEnergyStored() >= cap.getMaxEnergyStored()).orElse(false);

                    if (received > 0) {
                        tile.storage.extractEnergy(received, false);
                    }

                    if (canTakeUp) {
                        tile.inv.setStackInSlot(1, tile.inv.getStackInSlot(0).copy());
                        tile.inv.setStackInSlot(0, StackUtil.shrink(tile.inv.getStackInSlot(0), 1));
                    }
                }
            }

            if (tile.lastEnergy != tile.storage.getEnergyStored() && tile.sendUpdateWithInterval()) {
                tile.lastEnergy = tile.storage.getEnergyStored();
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || slot == 0 && stack.getCapability(Capabilities.EnergyStorage.ITEM, null) != null;
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !(EnchantmentHelper.has(this.inv.getStackInSlot(slot), EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) && !automation || slot == 1;
    }

    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.energizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
        return new ContainerEnergizer(windowId, playerInventory, this);
    }
}
