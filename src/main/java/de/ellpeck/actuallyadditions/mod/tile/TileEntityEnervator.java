/*
 * This file ("TileEntityEnervator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerEnervator;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.Optional;

public class TileEntityEnervator extends TileEntityInventoryBase implements ISharingEnergyProvider, MenuProvider {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 0, 1000);
    private int lastEnergy;

    public TileEntityEnervator(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.ENERVATOR.getTileEntityType(), pos, state, 2);
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
        if (t instanceof TileEntityEnervator tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityEnervator tile) {
            tile.serverTick();

            if (!tile.inv.getStackInSlot(0).isEmpty() && tile.inv.getStackInSlot(1).isEmpty()) {
                if (tile.storage.getEnergyStored() < tile.storage.getMaxEnergyStored()) {
                    Optional<IEnergyStorage> capability = Optional.ofNullable(tile.inv.getStackInSlot(0).getCapability(Capabilities.EnergyStorage.ITEM, null));

                    int maxExtract = tile.storage.getMaxEnergyStored() - tile.storage.getEnergyStored();
                    int extracted = capability.map(cap -> cap.extractEnergy(maxExtract, false)).orElse(0);
                    boolean canTakeUp = capability.map(cap -> cap.getEnergyStored() <= 0).orElse(false);

                    if (extracted > 0) {
                        tile.storage.receiveEnergyInternal(extracted, false);
                    }

                    if (canTakeUp) {
                        tile.inv.setStackInSlot(1, tile.inv.getStackInSlot(0).copy());
                        tile.inv.getStackInSlot(0).shrink(1);
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
        return (slot, automation) -> !automation || slot == 1;
    }

    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @Override
    public int getEnergyToSplitShare() {
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy() {
        return true;
    }

    @Override
    public Direction[] getEnergyShareSides() {
        return Direction.values();
    }

    @Override
    public boolean canShareTo(BlockEntity tile) {
        return true;
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.enervator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
        return new ContainerEnervator(windowId, playerInventory, this);
    }
}
