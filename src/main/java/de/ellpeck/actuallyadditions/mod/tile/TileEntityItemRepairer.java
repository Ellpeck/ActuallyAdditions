/*
 * This file ("TileEntityItemRepairer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityItemRepairer extends TileEntityInventoryBase implements IEnergyReceiver{

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int ENERGY_USE = 1500;
    public final EnergyStorage storage = new EnergyStorage(300000);
    public int nextRepairTick;
    private int lastEnergy;

    public TileEntityItemRepairer(){
        super(2, "repairer");
    }

    public static boolean canBeRepaired(ItemStack stack){
        if(stack != null){
            Item item = stack.getItem();
            if(item != null){
                if(item.isRepairable()){
                    return true;
                }
                else{
                    String reg = item.getRegistryName().toString();
                    if(reg != null){
                        for(String strg : ConfigStringListValues.REPAIRER_EXTRA_WHITELIST.getValue()){
                            if(reg.equals(strg)){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("NextRepairTick", this.nextRepairTick);
        }
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            this.nextRepairTick = compound.getInteger("NextRepairTick");
        }
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(this.slots[SLOT_OUTPUT] == null && canBeRepaired(this.slots[SLOT_INPUT])){
                if(this.slots[SLOT_INPUT].getItemDamage() <= 0){
                    this.slots[SLOT_OUTPUT] = this.slots[SLOT_INPUT].copy();
                    this.slots[SLOT_INPUT] = null;
                    this.nextRepairTick = 0;
                }
                else{
                    if(this.storage.getEnergyStored() >= ENERGY_USE){
                        this.nextRepairTick++;
                        this.storage.extractEnergy(ENERGY_USE, false);
                        if(this.nextRepairTick >= 2){
                            this.nextRepairTick = 0;
                            this.slots[SLOT_INPUT].setItemDamage(this.slots[SLOT_INPUT].getItemDamage()-1);
                        }
                    }
                }
            }
            else{
                this.nextRepairTick = 0;
            }

            if(this.lastEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return i == SLOT_INPUT;
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getItemDamageToScale(int i){
        if(this.slots[SLOT_INPUT] != null){
            return (this.slots[SLOT_INPUT].getMaxDamage()-this.slots[SLOT_INPUT].getItemDamage())*i/this.slots[SLOT_INPUT].getMaxDamage();
        }
        return 0;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return slot == SLOT_OUTPUT;
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
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
}
