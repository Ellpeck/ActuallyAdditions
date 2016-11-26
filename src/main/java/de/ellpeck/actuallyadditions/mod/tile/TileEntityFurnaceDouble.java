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

import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFurnaceDouble extends TileEntityInventoryBase implements IButtonReactor{

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1 = 1;
    public static final int SLOT_INPUT_2 = 2;
    public static final int SLOT_OUTPUT_2 = 3;
    public static final int ENERGY_USE = 25;
    private static final int SMELT_TIME = 80;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 80, 0);
    public int firstSmeltTime;
    public int secondSmeltTime;
    public boolean isAutoSplit;
    private int lastEnergy;
    private int lastFirstSmelt;
    private int lastSecondSmelt;
    private boolean lastAutoSplit;

    public TileEntityFurnaceDouble(){
        super(4, "furnaceDouble");
    }

    public static void autoSplit(NonNullList<ItemStack> slots, int slot1, int slot2){
        ItemStack first = slots.get(slot1);
        ItemStack second = slots.get(slot2);

        if(StackUtil.isValid(first) || StackUtil.isValid(second)){
            ItemStack toSplit = StackUtil.getNull();
            if(!StackUtil.isValid(first) && StackUtil.isValid(second) && StackUtil.getStackSize(second) > 1){
                toSplit = second;
            }
            else if(!StackUtil.isValid(second) && StackUtil.isValid(first) && StackUtil.getStackSize(first) > 1){
                toSplit = first;
            }
            else if(ItemUtil.canBeStacked(first, second)){
                if(StackUtil.getStackSize(first) < first.getMaxStackSize() || StackUtil.getStackSize(second) < second.getMaxStackSize()){
                    if(!((StackUtil.getStackSize(first) <= StackUtil.getStackSize(second)+1 && StackUtil.getStackSize(first) >= StackUtil.getStackSize(second)-1) || (StackUtil.getStackSize(second) <= StackUtil.getStackSize(first)+1 && StackUtil.getStackSize(second) >= StackUtil.getStackSize(first)-1))){
                        toSplit = first;
                        toSplit = StackUtil.addStackSize(toSplit, StackUtil.getStackSize(second));
                    }
                }
            }

            if(StackUtil.isValid(toSplit)){
                ItemStack splitFirst = toSplit.copy();
                ItemStack secondSplit = splitFirst.splitStack(StackUtil.getStackSize(splitFirst)/2);
                slots.set(slot1, StackUtil.validateCheck(splitFirst));
                slots.set(slot2, StackUtil.validateCheck(secondSplit));
            }
        }
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
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
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
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                }
            }
            else{
                this.secondSmeltTime = 0;
            }

            if((this.lastEnergy != this.storage.getEnergyStored() || this.lastFirstSmelt != this.firstSmeltTime || this.lastSecondSmelt != this.secondSmeltTime || this.isAutoSplit != this.lastAutoSplit) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastFirstSmelt = this.firstSmeltTime;
                this.lastAutoSplit = this.isAutoSplit;
                this.lastSecondSmelt = this.secondSmeltTime;
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && StackUtil.isValid(FurnaceRecipes.instance().getSmeltingResult(stack));
    }

    public boolean canSmeltOn(int theInput, int theOutput){
        if(StackUtil.isValid(this.slots.get(theInput))){
            ItemStack output = FurnaceRecipes.instance().getSmeltingResult(this.slots.get(theInput));
            if(StackUtil.isValid(output)){
                if(!StackUtil.isValid(this.slots.get(theOutput)) || (this.slots.get(theOutput).isItemEqual(output) && StackUtil.getStackSize(this.slots.get(theOutput)) <= this.slots.get(theOutput).getMaxStackSize()-StackUtil.getStackSize(output))){
                    return true;
                }
            }

        }
        return false;
    }

    public void finishBurning(int theInput, int theOutput){
        ItemStack output = FurnaceRecipes.instance().getSmeltingResult(this.slots.get(theInput));
        if(!StackUtil.isValid(this.slots.get(theOutput))){
            this.slots.set(theOutput, output.copy());
        }
        else if(this.slots.get(theOutput).getItem() == output.getItem()){
            this.slots.set(theOutput, StackUtil.addStackSize(this.slots.get(theOutput), StackUtil.getStackSize(output)));
        }

        this.slots.set(theInput, StackUtil.addStackSize(this.slots.get(theInput), -1));
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
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == 0){
            this.isAutoSplit = !this.isAutoSplit;
            this.markDirty();
        }
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
