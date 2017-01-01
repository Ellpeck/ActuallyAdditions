/*
 * This file ("TileEntityItemRepairer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityItemRepairer extends TileEntityInventoryBase{

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int ENERGY_USE = 2500;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 6000, 0);
    public int nextRepairTick;
    private int lastEnergy;

    public TileEntityItemRepairer(){
        super(2, "repairer");
    }

    public static boolean canBeRepaired(ItemStack stack){
        if(StackUtil.isValid(stack)){
            Item item = stack.getItem();
            if(item != null){
                if(item.isRepairable() && item.getMaxDamage(stack) > 0){
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
        if(!this.world.isRemote){
            ItemStack input = this.slots.getStackInSlot(SLOT_INPUT);
            if(!StackUtil.isValid(this.slots.getStackInSlot(SLOT_OUTPUT)) && canBeRepaired(input)){
                if(input.getItemDamage() <= 0){
                    this.slots.setStackInSlot(SLOT_OUTPUT, input.copy());
                    this.slots.setStackInSlot(SLOT_INPUT, StackUtil.getNull());
                    this.nextRepairTick = 0;
                }
                else{
                    if(this.storage.getEnergyStored() >= ENERGY_USE){
                        this.nextRepairTick++;
                        this.storage.extractEnergyInternal(ENERGY_USE, false);
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
        if(StackUtil.isValid(this.slots.getStackInSlot(SLOT_INPUT))){
            return (this.slots.getStackInSlot(SLOT_INPUT).getMaxDamage()-this.slots.getStackInSlot(SLOT_INPUT).getItemDamage())*i/this.slots.getStackInSlot(SLOT_INPUT).getMaxDamage();
        }
        return 0;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return slot == SLOT_OUTPUT;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
