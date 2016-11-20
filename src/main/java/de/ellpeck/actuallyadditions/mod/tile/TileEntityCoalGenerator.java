/*
 * This file ("TileEntityCoalGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCoalGenerator extends TileEntityInventoryBase implements ISharingEnergyProvider{

    public static final int PRODUCE = 30;
    public final EnergyStorage storage = new EnergyStorage(60000);
    public int maxBurnTime;
    public int currentBurnTime;
    private int lastEnergy;
    private int lastBurnTime;
    private int lastCurrentBurnTime;

    public TileEntityCoalGenerator(){
        super(1, "coalGenerator");
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getBurningScaled(int i){
        return this.currentBurnTime*i/this.maxBurnTime;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("BurnTime", this.currentBurnTime);
            compound.setInteger("MaxBurnTime", this.maxBurnTime);
        }
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            this.currentBurnTime = compound.getInteger("BurnTime");
            this.maxBurnTime = compound.getInteger("MaxBurnTime");
        }
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            boolean flag = this.currentBurnTime > 0;

            if(this.currentBurnTime > 0){
                this.currentBurnTime--;
                this.storage.receiveEnergy(PRODUCE, false);
            }

            if(this.currentBurnTime <= 0 && StackUtil.isValid(this.slots.get(0)) && TileEntityFurnace.getItemBurnTime(this.slots.get(0)) > 0 && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()){
                int burnTime = TileEntityFurnace.getItemBurnTime(this.slots.get(0));
                this.maxBurnTime = burnTime;
                this.currentBurnTime = burnTime;

                this.slots.set(0, StackUtil.addStackSize(this.slots.get(0), -1));
            }

            if(flag != this.currentBurnTime > 0){
                this.markDirty();
            }

            if((this.storage.getEnergyStored() != this.lastEnergy || this.currentBurnTime != this.lastCurrentBurnTime || this.lastBurnTime != this.maxBurnTime) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastCurrentBurnTime = this.currentBurnTime;
                this.lastBurnTime = this.currentBurnTime;
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return TileEntityFurnace.getItemBurnTime(stack) > 0;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return TileEntityFurnace.getItemBurnTime(this.slots.get(0)) <= 0;
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.storage.extractEnergy(maxReceive, simulate);
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
