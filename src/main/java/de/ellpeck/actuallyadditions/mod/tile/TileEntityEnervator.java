package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnervator extends TileEntityInventoryBase implements ISharingEnergyProvider {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 0, 1000);
    private int lastEnergy;

    public TileEntityEnervator() {
        super(2, "enervator");
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
                if (this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
                    int extracted = 0;
                    boolean canTakeUp = false;

                    int maxExtract = this.storage.getMaxEnergyStored() - this.storage.getEnergyStored();
                    if (this.inv.getStackInSlot(0).hasCapability(CapabilityEnergy.ENERGY, null)) {
                        IEnergyStorage cap = this.inv.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, null);
                        if (cap != null) {
                            extracted = cap.extractEnergy(maxExtract, false);
                            canTakeUp = cap.getEnergyStored() <= 0;
                        }
                    }
                    if (extracted > 0) {
                        this.storage.receiveEnergyInternal(extracted, false);
                    }

                    if (canTakeUp) {
                        this.inv.setStackInSlot(1, this.inv.getStackInSlot(0).copy());
                        this.inv.getStackInSlot(0).shrink(1);
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
    public EnumFacing[] getEnergyShareSides() {
        return EnumFacing.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile) {
        return true;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
    }
}
