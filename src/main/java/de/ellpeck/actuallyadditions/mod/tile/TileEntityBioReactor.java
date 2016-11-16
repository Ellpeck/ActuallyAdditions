/*
 * This file ("TileEntityBioReactor.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;

public class TileEntityBioReactor extends TileEntityInventoryBase implements ISharingEnergyProvider{

    public final EnergyStorage storage = new EnergyStorage(200000);

    public int burnTime;
    public int maxBurnTime;
    public int producePerTick;

    private int lastBurnTime;
    private int lastProducePerTick;

    public TileEntityBioReactor(){
        super(8, "bioReactor");
    }

    public static boolean isValidItem(ItemStack stack){
        if(StackUtil.isValid(stack)){
            Item item = stack.getItem();
            if(isValid(item)){
                return true;
            }
            else if(item instanceof ItemBlock){
                return isValid(Block.getBlockFromItem(item));
            }
        }
        return false;
    }

    private static boolean isValid(Object o){
        return o instanceof IPlantable || o instanceof IGrowable || o instanceof ItemFood;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(this.burnTime <= 0){
            List<Item> types = null;

            if(this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()){
                for(int i = 0; i < this.slots.length; i++){
                    ItemStack stack = this.slots[i];
                    if(StackUtil.isValid(stack)){
                        Item item = stack.getItem();
                        if(isValidItem(stack) && (types == null || !types.contains(item))){
                            if(types == null){
                                types = new ArrayList<Item>();
                            }
                            types.add(item);

                            this.slots[i] = StackUtil.addStackSize(stack, -1);
                        }
                    }
                }

                this.markDirty();
            }

            if(types != null && !types.isEmpty()){
                int amount = types.size();
                this.producePerTick = (int)Math.pow(amount*2, 2);

                this.maxBurnTime = 200-(int)Math.pow(1.8, amount);
                this.burnTime = this.maxBurnTime;
            }
            else{
                this.burnTime = 0;
                this.maxBurnTime = 0;
                this.producePerTick = 0;
            }
        }
        else{
            this.burnTime--;
            this.storage.receiveEnergy(this.producePerTick, false);
        }

        if((this.lastBurnTime != this.burnTime || this.lastProducePerTick != this.producePerTick) && this.sendUpdateWithInterval()){
            this.lastBurnTime = this.burnTime;
            this.lastProducePerTick = this.producePerTick;
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);

        this.storage.writeToNBT(compound);
        compound.setInteger("BurnTime", this.burnTime);
        compound.setInteger("MaxBurnTime", this.maxBurnTime);
        compound.setInteger("ProducePerTick", this.producePerTick);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);

        this.storage.readFromNBT(compound);
        this.burnTime = compound.getInteger("BurnTime");
        this.maxBurnTime = compound.getInteger("MaxBurnTime");
        this.producePerTick = compound.getInteger("ProducePerTick");
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction){
        return this.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return isValidItem(stack);
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
