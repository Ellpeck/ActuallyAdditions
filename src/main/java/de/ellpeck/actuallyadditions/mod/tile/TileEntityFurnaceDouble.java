/*
 * This file ("TileEntityFurnaceDouble.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class TileEntityFurnaceDouble extends TileEntityInventoryBase implements IEnergyReceiver, IEnergySaver{

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1 = 1;
    public static final int SLOT_INPUT_2 = 2;
    public static final int SLOT_OUTPUT_2 = 3;
    public static final int ENERGY_USE = 25;
    private static final int SMELT_TIME = 80;
    public final EnergyStorage storage = new EnergyStorage(30000);
    public int firstSmeltTime;
    public int secondSmeltTime;
    private int lastEnergy;
    private int lastFirstSmelt;
    private int lastSecondSmelt;

    public TileEntityFurnaceDouble(){
        super(4, "furnaceDouble");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("FirstSmeltTime", this.firstSmeltTime);
        compound.setInteger("SecondSmeltTime", this.secondSmeltTime);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.firstSmeltTime = compound.getInteger("FirstSmeltTime");
        this.secondSmeltTime = compound.getInteger("SecondSmeltTime");
        this.storage.readFromNBT(compound);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            boolean flag = this.firstSmeltTime > 0 || this.secondSmeltTime > 0;

            boolean canSmeltOnFirst = this.canSmeltOn(SLOT_INPUT_1, SLOT_OUTPUT_1);
            boolean canSmeltOnSecond = this.canSmeltOn(SLOT_INPUT_2, SLOT_OUTPUT_2);

            if(canSmeltOnFirst){
                if(this.storage.getEnergyStored() >= ENERGY_USE){
                    this.firstSmeltTime++;
                    if(this.firstSmeltTime >= SMELT_TIME){
                        this.finishBurning(SLOT_INPUT_1, SLOT_OUTPUT_1);
                        this.firstSmeltTime = 0;
                    }
                    this.storage.extractEnergy(ENERGY_USE, false);
                }
            }
            else{
                this.firstSmeltTime = 0;
            }

            if(canSmeltOnSecond){
                if(this.storage.getEnergyStored() >= ENERGY_USE){
                    this.secondSmeltTime++;
                    if(this.secondSmeltTime >= SMELT_TIME){
                        this.finishBurning(SLOT_INPUT_2, SLOT_OUTPUT_2);
                        this.secondSmeltTime = 0;
                    }
                    this.storage.extractEnergy(ENERGY_USE, false);
                }
            }
            else{
                this.secondSmeltTime = 0;
            }

            if(flag != (this.firstSmeltTime > 0 || this.secondSmeltTime > 0)){
                this.markDirty();
                int meta = PosUtil.getMetadata(this.pos, this.worldObj);
                if(meta > 3){
                    if(!this.canSmeltOn(SLOT_INPUT_1, SLOT_OUTPUT_1) && !this.canSmeltOn(SLOT_INPUT_2, SLOT_OUTPUT_2)){
                        PosUtil.setMetadata(this.pos, this.worldObj, meta-4, 2);
                    }
                }
                else{
                    PosUtil.setMetadata(this.pos, this.worldObj, meta+4, 2);
                }
            }

            if((this.lastEnergy != this.storage.getEnergyStored() || this.lastFirstSmelt != this.firstSmeltTime || this.lastSecondSmelt != this.secondSmeltTime) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastFirstSmelt = this.firstSmeltTime;
                this.lastSecondSmelt = this.secondSmeltTime;
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, @Nonnull ItemStack stack){
        return (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && FurnaceRecipes.instance().getSmeltingResult(stack) != null;
    }

    public boolean canSmeltOn(int theInput, int theOutput){
        if(this.slots[theInput] != null){
            ItemStack output = FurnaceRecipes.instance().getSmeltingResult(this.slots[theInput]);
            if(this.slots[theInput] != null){
                if(output != null){
                    if(this.slots[theOutput] == null || (this.slots[theOutput].isItemEqual(output) && this.slots[theOutput].stackSize <= this.slots[theOutput].getMaxStackSize()-output.stackSize)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void finishBurning(int theInput, int theOutput){
        ItemStack output = FurnaceRecipes.instance().getSmeltingResult(this.slots[theInput]);
        if(this.slots[theOutput] == null){
            this.slots[theOutput] = output.copy();
        }
        else if(this.slots[theOutput].getItem() == output.getItem()){
            this.slots[theOutput].stackSize += output.stackSize;
        }

        this.slots[theInput].stackSize--;
        if(this.slots[theInput].stackSize <= 0){
            this.slots[theInput] = null;
        }
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getFirstTimeToScale(int i){
        return this.firstSmeltTime*i/SMELT_TIME;
    }

    @SideOnly(Side.CLIENT)
    public int getSecondTimeToScale(int i){
        return this.secondSmeltTime*i/SMELT_TIME;
    }

    @Override
    public boolean canInsertItem(int slot, @Nonnull ItemStack stack, @Nonnull EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, @Nonnull ItemStack stack, @Nonnull EnumFacing side){
        return slot == SLOT_OUTPUT_1 || slot == SLOT_OUTPUT_2;
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

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }
}
