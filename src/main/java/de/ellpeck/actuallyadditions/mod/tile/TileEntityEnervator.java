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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityEnervator extends TileEntityInventoryBase implements ISharingEnergyProvider{

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 0, 1000);
    private int lastEnergy;

    public TileEntityEnervator(){
        super(2, "enervator");
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
                if(this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()){
                    int extracted = 0;
                    boolean canTakeUp = false;

                    int maxExtract = this.storage.getMaxEnergyStored()-this.storage.getEnergyStored();
                    if(this.slots.getStackInSlot(0).hasCapability(CapabilityEnergy.ENERGY, null)){
                        IEnergyStorage cap = this.slots.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, null);
                        if(cap != null){
                            extracted = cap.extractEnergy(maxExtract, false);
                            canTakeUp = cap.getEnergyStored() <= 0;
                        }
                    }
                    else if(ActuallyAdditions.teslaLoaded){
                        if(this.slots.getStackInSlot(0).hasCapability(TeslaUtil.teslaProducer, null)){
                            ITeslaProducer cap = this.slots.getStackInSlot(0).getCapability(TeslaUtil.teslaProducer, null);
                            if(cap != null){
                                extracted = (int)cap.takePower(maxExtract, false);
                            }
                        }
                        if(this.slots.getStackInSlot(0).hasCapability(TeslaUtil.teslaHolder, null)){
                            ITeslaHolder cap = this.slots.getStackInSlot(0).getCapability(TeslaUtil.teslaHolder, null);
                            if(cap != null){
                                canTakeUp = cap.getStoredPower() <= 0;
                            }
                        }
                    }
                    if(extracted > 0){
                        this.storage.receiveEnergyInternal(extracted, false);
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
        return i == 0 && ((ActuallyAdditions.teslaLoaded && stack.hasCapability(TeslaUtil.teslaProducer, null)) || stack.hasCapability(CapabilityEnergy.ENERGY, null));
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return slot == 1;
    }

    @Override
    public int getEnergyToSplitShare(){
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy(){
        return true;
    }

    @Override
    public EnumFacing[] getEnergyShareSides(){
        return EnumFacing.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile){
        return true;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
