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
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFurnaceDouble extends TileEntityInventoryBase implements IEnergyReceiver, IButtonReactor{

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

    public boolean isAutoSplit;
    private boolean lastAutoSplit;

    public TileEntityFurnaceDouble(){
        super(4, "furnaceDouble");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("FirstSmeltTime", this.firstSmeltTime);
            compound.setInteger("SecondSmeltTime", this.secondSmeltTime);
            compound.setBoolean("IsAutoSplit", this.isAutoSplit);
        }
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.firstSmeltTime = compound.getInteger("FirstSmeltTime");
            this.secondSmeltTime = compound.getInteger("SecondSmeltTime");
            this.isAutoSplit = compound.getBoolean("IsAutoSplit");
        }
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(this.isAutoSplit){
                autoSplit(this.slots, SLOT_INPUT_1, SLOT_INPUT_2);
            }

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
                IBlockState state = this.worldObj.getBlockState(this.pos);
                Block block = state.getBlock();
                int meta = block.getMetaFromState(state);
                if(meta > 3){
                    if(!this.canSmeltOn(SLOT_INPUT_1, SLOT_OUTPUT_1) && !this.canSmeltOn(SLOT_INPUT_2, SLOT_OUTPUT_2)){
                        this.worldObj.setBlockState(this.pos, block.getStateFromMeta(meta-4), 2);
                    }
                }
                else{
                    this.worldObj.setBlockState(this.pos, block.getStateFromMeta(meta+4), 2);
                }
            }

            if((this.lastEnergy != this.storage.getEnergyStored() || this.lastFirstSmelt != this.firstSmeltTime || this.lastSecondSmelt != this.secondSmeltTime || this.isAutoSplit != this.lastAutoSplit) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastFirstSmelt = this.firstSmeltTime;
                this.lastAutoSplit = this.isAutoSplit;
                this.lastSecondSmelt = this.secondSmeltTime;
            }
        }
    }

    public static void autoSplit(ItemStack[] slots, int slot1, int slot2){
        ItemStack first = slots[slot1];
        ItemStack second = slots[slot2];

        if(first != null || second != null){
            ItemStack toSplit = null;
            if(first == null && second != null){
                toSplit = second;
            }
            else if(second == null && first != null){
                toSplit = first;
            }
            else if(ItemUtil.canBeStacked(first, second)){
                if(first.stackSize < first.getMaxStackSize() || second.stackSize < second.getMaxStackSize()){
                    if(!((first.stackSize <= second.stackSize+1 && first.stackSize >= second.stackSize-1) || (second.stackSize <= first.stackSize+1 && second.stackSize >= first.stackSize-1))){
                        toSplit = first;
                        toSplit.stackSize += second.stackSize;
                    }
                }
            }

            if(toSplit != null && toSplit.stackSize > 1){
                ItemStack splitFirst = toSplit.copy();
                ItemStack secondSplit = splitFirst.splitStack(splitFirst.stackSize/2);

                slots[slot1] = splitFirst;
                slots[slot2] = secondSplit;
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
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
    public int getFirstTimeToScale(int i){
        return this.firstSmeltTime*i/SMELT_TIME;
    }

    @SideOnly(Side.CLIENT)
    public int getSecondTimeToScale(int i){
        return this.secondSmeltTime*i/SMELT_TIME;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
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
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == 0){
            this.isAutoSplit = !this.isAutoSplit;
            this.markDirty();
        }
    }
}
