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

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnergizer extends TileEntityInventoryBase {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 1000, 0);
    private int lastEnergy;

    public TileEntityEnergizer() {
        super(2, "energizer");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            if (StackUtil.isValid(this.inv.getStackInSlot(0)) && !StackUtil.isValid(this.inv.getStackInSlot(1))) {
                if (this.storage.getEnergyStored() > 0) {
                    int received = 0;
                    boolean canTakeUp = false;

                    if (this.inv.getStackInSlot(0).hasCapability(CapabilityEnergy.ENERGY, null)) {
                        IEnergyStorage cap = this.inv.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, null);
                        if (cap != null) {
                            received = cap.receiveEnergy(this.storage.getEnergyStored(), false);
                            canTakeUp = cap.getEnergyStored() >= cap.getMaxEnergyStored();
                        }
                    }
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
        return (slot, stack, automation) -> !automation || slot == 0 && stack.hasCapability(CapabilityEnergy.ENERGY, null);
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !EnchantmentHelper.hasBindingCurse(this.inv.getStackInSlot(slot)) && !automation || slot == 1;
    }

    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
    }
}
