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

import de.ellpeck.actuallyadditions.mod.inventory.ContainerEnergizer;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileEntityEnergizer extends TileEntityInventoryBase implements INamedContainerProvider {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 1000, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int lastEnergy;

    public TileEntityEnergizer() {
        super(ActuallyTiles.ENERGIZER_TILE.get(), 2);
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            if (StackUtil.isValid(this.inv.getStackInSlot(0)) && !StackUtil.isValid(this.inv.getStackInSlot(1))) {
                if (this.storage.getEnergyStored() > 0) {
                    int received = this.inv.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, null).map(cap -> cap.receiveEnergy(this.storage.getEnergyStored(), false)).orElse(0);
                    boolean canTakeUp = this.inv.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, null).map(cap -> cap.getEnergyStored() >= cap.getMaxEnergyStored()).orElse(false);

                    if (received > 0) {
                        this.storage.extractEnergyInternal(received, false);
                    }

                    if (canTakeUp) {
                        this.inv.setStackInSlot(1, this.inv.getStackInSlot(0).copy());
                        this.inv.setStackInSlot(0, StackUtil.shrink(this.inv.getStackInSlot(0), 1));
                    }
                }
            }

            if (this.lastEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || slot == 0 && stack.getCapability(CapabilityEnergy.ENERGY, null).isPresent();
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !EnchantmentHelper.hasBindingCurse(this.inv.getStackInSlot(slot)) && !automation || slot == 1;
    }

    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerEnergizer(windowId, playerInventory, this);
    }
}
