/*
 * This file ("TileEntityCoalGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCoalGenerator extends TileEntityInventoryBase implements ISharingEnergyProvider{

    public static final int PRODUCE = 30;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(60000, 0, 80);
    public int maxBurnTime;
    public int currentBurnTime;
    private int lastEnergy;
    private int lastBurnTime;
    private int lastCurrentBurnTime;
    private int lastCompare;

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
        if(!this.world.isRemote){
            boolean flag = this.currentBurnTime > 0;

            if(this.currentBurnTime > 0){
                this.currentBurnTime--;
                this.storage.receiveEnergyInternal(PRODUCE, false);
            }

            if(!this.isRedstonePowered && this.currentBurnTime <= 0 && StackUtil.isValid(this.slots.getStackInSlot(0)) && TileEntityFurnace.getItemBurnTime(this.slots.getStackInSlot(0)) > 0 && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()){
                int burnTime = TileEntityFurnace.getItemBurnTime(this.slots.getStackInSlot(0));
                this.maxBurnTime = burnTime;
                this.currentBurnTime = burnTime;

                this.slots.setStackInSlot(0, StackUtil.addStackSize(this.slots.getStackInSlot(0), -1, true));
            }

            if(flag != this.currentBurnTime > 0 || this.lastCompare != this.getComparatorStrength()){
                this.lastCompare = this.getComparatorStrength();

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
    public int getComparatorStrength(){
        float calc = ((float)this.storage.getEnergyStored()/(float)this.storage.getMaxEnergyStored())*15F;
        return (int)calc;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return TileEntityFurnace.getItemBurnTime(stack) > 0;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return TileEntityFurnace.getItemBurnTime(this.slots.getStackInSlot(0)) <= 0;
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
