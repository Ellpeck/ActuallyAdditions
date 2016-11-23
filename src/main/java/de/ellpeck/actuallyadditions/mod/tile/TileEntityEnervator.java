/*
 * This file ("TileEntityEnervator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityEnervator extends TileEntityInventoryBase implements ISharingEnergyProvider{

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 1000);
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
        if(!this.worldObj.isRemote){
            if(StackUtil.isValid(this.slots.get(0)) && !StackUtil.isValid(this.slots.get(1))){
                if(this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()){
                    int extracted = 0;
                    boolean canTakeUp = false;

                    int maxExtract = this.storage.getMaxEnergyStored()-this.storage.getEnergyStored();
                    if(this.slots.get(0).getItem() instanceof IEnergyContainerItem){
                        IEnergyContainerItem item = (IEnergyContainerItem)this.slots.get(0).getItem();
                        extracted = item.extractEnergy(this.slots.get(0), maxExtract, false);
                        canTakeUp = item.getEnergyStored(this.slots.get(0)) <= 0;
                    }
                    else if(ActuallyAdditions.teslaLoaded){
                        if(this.slots.get(0).hasCapability(TeslaUtil.teslaProducer, null)){
                            ITeslaProducer cap = this.slots.get(0).getCapability(TeslaUtil.teslaProducer, null);
                            if(cap != null){
                                extracted = (int)cap.takePower(maxExtract, false);
                            }
                        }
                        if(this.slots.get(0).hasCapability(TeslaUtil.teslaHolder, null)){
                            ITeslaHolder cap = this.slots.get(0).getCapability(TeslaUtil.teslaHolder, null);
                            if(cap != null){
                                canTakeUp = cap.getStoredPower() <= 0;
                            }
                        }
                    }
                    if(extracted > 0){
                        this.storage.receiveEnergyInternal(extracted, false);
                    }

                    if(canTakeUp){
                        this.slots.set(1, this.slots.get(0).copy());
                        this.slots.set(0, StackUtil.addStackSize(this.slots.get(0), -1));
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
        return i == 0 && (stack.getItem() instanceof IEnergyContainerItem || (ActuallyAdditions.teslaLoaded && stack.hasCapability(TeslaUtil.teslaProducer, null)));
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate){
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
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
}
