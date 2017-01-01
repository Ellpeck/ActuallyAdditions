/*
 * This file ("TileEntityDisplayStand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.misc.IDisplayStandItem;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityDisplayStand extends TileEntityInventoryBase implements IEnergyDisplay{

    public final CustomEnergyStorage storage = new CustomEnergyStorage(80000, 1000, 0);
    private int oldEnergy;

    public TileEntityDisplayStand(){
        super(1, "displayStand");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.world.isRemote){
            if(StackUtil.isValid(this.slots.getStackInSlot(0)) && !this.isRedstonePowered){
                IDisplayStandItem item = this.convertToDisplayStandItem(this.slots.getStackInSlot(0).getItem());
                if(item != null){
                    int energy = item.getUsePerTick(this.slots.getStackInSlot(0), this, this.ticksElapsed);
                    if(this.storage.getEnergyStored() >= energy){
                        if(item.update(this.slots.getStackInSlot(0), this, this.ticksElapsed)){
                            this.storage.extractEnergyInternal(energy, false);
                        }
                    }
                }
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return true;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    private IDisplayStandItem convertToDisplayStandItem(Item item){
        if(item instanceof IDisplayStandItem){
            return (IDisplayStandItem)item;
        }
        else if(item instanceof ItemBlock){
            Block block = Block.getBlockFromItem(item);
            if(block instanceof IDisplayStandItem){
                return (IDisplayStandItem)block;
            }
        }
        return null;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack){
        return true;
    }

    @Override
    public CustomEnergyStorage getEnergyStorage(){
        return this.storage;
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }

    @Override
    public int getMaxStackSizePerSlot(int slot){
        return 1;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
