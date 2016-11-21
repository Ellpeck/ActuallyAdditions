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
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityItemRepairer extends TileEntityInventoryBase implements ICustomEnergyReceiver{

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int ENERGY_USE = 2500;
    public final EnergyStorage storage = new EnergyStorage(300000, 6000);
    public int nextRepairTick;
    private int lastEnergy;

    public TileEntityItemRepairer(){
        super(2, "repairer");
    }

    public static boolean canBeRepaired(ItemStack stack){
        if(StackUtil.isValid(stack)){
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
            ItemStack input = this.slots.get(SLOT_INPUT);
            if(!StackUtil.isValid(this.slots.get(SLOT_OUTPUT)) && canBeRepaired(input)){
                if(input.getItemDamage() <= 0){
                    this.slots.set(SLOT_OUTPUT, input.copy());
                    this.slots.set(SLOT_INPUT, StackUtil.getNull());
                    this.nextRepairTick = 0;
                }
                else{
                    if(this.storage.getEnergyStored() >= ENERGY_USE){
                        this.nextRepairTick++;
                        this.storage.extractEnergy(ENERGY_USE, false);
                        if(this.nextRepairTick >= 4){
                            this.nextRepairTick = 0;
                            input.setItemDamage(input.getItemDamage()-1);

                            if(input.hasTagCompound()){
                                //TiCon un-break tools
                                if("tconstruct".equalsIgnoreCase(input.getItem().getRegistryName().getResourceDomain())){
                                    NBTTagCompound stats = input.getTagCompound().getCompoundTag("Stats");
                                    stats.removeTag("Broken");
                                }
                            }
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
        if(StackUtil.isValid(this.slots.get(SLOT_INPUT))){
            return (this.slots.get(SLOT_INPUT).getMaxDamage()-this.slots.get(SLOT_INPUT).getItemDamage())*i/this.slots.get(SLOT_INPUT).getMaxDamage();
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
