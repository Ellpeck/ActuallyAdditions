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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityEnergizer extends TileEntityInventoryBase{

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 1000, 0);
    private int lastEnergy;

    public TileEntityEnergizer(){
        super(2, "energizer");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            if(StackUtil.isValid(this.slots.getStackInSlot(0)) && !StackUtil.isValid(this.slots.getStackInSlot(1))){
                if(this.storage.getEnergyStored() > 0){
                    int received = 0;
                    boolean canTakeUp = false;

                    if(this.slots.getStackInSlot(0).hasCapability(CapabilityEnergy.ENERGY, null)){
                        IEnergyStorage cap = this.slots.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, null);
                        if(cap != null){
                            received = cap.receiveEnergy(this.storage.getEnergyStored(), false);
                            canTakeUp = cap.getEnergyStored() >= cap.getMaxEnergyStored();
                        }
                    }
                    else if(ActuallyAdditions.teslaLoaded){
                        if(this.slots.getStackInSlot(0).hasCapability(TeslaUtil.teslaConsumer, null)){
                            ITeslaConsumer cap = this.slots.getStackInSlot(0).getCapability(TeslaUtil.teslaConsumer, null);
                            if(cap != null){
                                received = (int)cap.givePower(this.storage.getEnergyStored(), false);
                            }
                        }
                        if(this.slots.getStackInSlot(0).hasCapability(TeslaUtil.teslaHolder, null)){
                            ITeslaHolder cap = this.slots.getStackInSlot(0).getCapability(TeslaUtil.teslaHolder, null);
                            if(cap != null){
                                canTakeUp = cap.getStoredPower() >= cap.getCapacity();
                            }
                        }
                    }
                    if(received > 0){
                        this.storage.extractEnergyInternal(received, false);
                    }

                    if(canTakeUp){
                        this.slots.setStackInSlot(1, this.slots.getStackInSlot(0).copy());
                        this.slots.setStackInSlot(0, StackUtil.addStackSize(this.slots.getStackInSlot(0), -1));
                    }
                }
            }

            if(this.lastEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return i == 0 && ((ActuallyAdditions.teslaLoaded && stack.hasCapability(TeslaUtil.teslaConsumer, null)) || stack.hasCapability(CapabilityEnergy.ENERGY, null));
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return slot == 1;
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
