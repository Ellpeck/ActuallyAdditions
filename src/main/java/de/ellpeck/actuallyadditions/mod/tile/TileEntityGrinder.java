/*
 * This file ("TileEntityGrinder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;


import cofh.api.energy.EnergyStorage;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class TileEntityGrinder extends TileEntityInventoryBase implements ICustomEnergyReceiver, IButtonReactor{

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1_1 = 1;
    public static final int SLOT_OUTPUT_1_2 = 2;
    public static final int SLOT_INPUT_2 = 3;
    public static final int SLOT_OUTPUT_2_1 = 4;
    public static final int SLOT_OUTPUT_2_2 = 5;
    public static final int ENERGY_USE = 40;
    public final EnergyStorage storage = new EnergyStorage(60000);
    public int firstCrushTime;
    public int secondCrushTime;
    public boolean isDouble;
    public boolean isAutoSplit;
    private int lastEnergy;
    private int lastFirstCrush;
    private int lastSecondCrush;
    private boolean lastAutoSplit;

    public TileEntityGrinder(int slots, String name){
        super(slots, name);
    }

    public TileEntityGrinder(){
        super(3, "grinder");
        this.isDouble = false;
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
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("FirstCrushTime", this.firstCrushTime);
            compound.setInteger("SecondCrushTime", this.secondCrushTime);
            compound.setBoolean("IsAutoSplit", this.isAutoSplit);
        }
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            this.firstCrushTime = compound.getInteger("FirstCrushTime");
            this.secondCrushTime = compound.getInteger("SecondCrushTime");
            this.isAutoSplit = compound.getBoolean("IsAutoSplit");
        }
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(this.isDouble && this.isAutoSplit){
                TileEntityFurnaceDouble.autoSplit(this.slots, SLOT_INPUT_1, SLOT_INPUT_2);
            }

            boolean flag = this.firstCrushTime > 0 || this.secondCrushTime > 0;

            boolean canCrushOnFirst = this.canCrushOn(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
            boolean canCrushOnSecond = false;
            if(this.isDouble){
                canCrushOnSecond = this.canCrushOn(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
            }

            boolean shouldPlaySound = false;

            if(canCrushOnFirst){
                if(this.storage.getEnergyStored() >= ENERGY_USE){
                    if(this.firstCrushTime%30 == 0){
                        shouldPlaySound = true;
                    }
                    this.firstCrushTime++;
                    if(this.firstCrushTime >= this.getMaxCrushTime()){
                        this.finishCrushing(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
                        this.firstCrushTime = 0;
                    }
                    this.storage.extractEnergy(ENERGY_USE, false);
                }
            }
            else{
                this.firstCrushTime = 0;
            }

            if(this.isDouble){
                if(canCrushOnSecond){
                    if(this.storage.getEnergyStored() >= ENERGY_USE){
                        if(this.secondCrushTime%30 == 0){
                            shouldPlaySound = true;
                        }
                        this.secondCrushTime++;
                        if(this.secondCrushTime >= this.getMaxCrushTime()){
                            this.finishCrushing(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
                            this.secondCrushTime = 0;
                        }
                        this.storage.extractEnergy(ENERGY_USE, false);
                    }
                }
                else{
                    this.secondCrushTime = 0;
                }
            }

            if(flag != (this.firstCrushTime > 0 || this.secondCrushTime > 0)){
                this.markDirty();
                IBlockState state = this.worldObj.getBlockState(this.pos);
                Block block = state.getBlock();
                int meta = block.getMetaFromState(state);
                if(meta == 1){
                    if(!this.canCrushOn(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2) && (!this.isDouble || !this.canCrushOn(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2))){
                        this.worldObj.setBlockState(this.pos, block.getStateFromMeta(0), 2);
                    }
                }
                else{
                    this.worldObj.setBlockState(this.pos, block.getStateFromMeta(1), 2);
                }
            }

            if((this.lastEnergy != this.storage.getEnergyStored() || this.lastFirstCrush != this.firstCrushTime || this.lastSecondCrush != this.secondCrushTime || this.isAutoSplit != this.lastAutoSplit) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastFirstCrush = this.firstCrushTime;
                this.lastSecondCrush = this.secondCrushTime;
                this.lastAutoSplit = this.isAutoSplit;
            }

            if(shouldPlaySound && !ConfigBoolValues.LESS_SOUND.isEnabled()){
                this.worldObj.playSound(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), SoundHandler.crusher, SoundCategory.BLOCKS, 0.15F, 1.0F);
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && CrusherRecipeRegistry.getRecipeFromInput(stack) != null;
    }

    public boolean canCrushOn(int theInput, int theFirstOutput, int theSecondOutput){
        if(this.slots[theInput] != null){
            List<ItemStack> outputOnes = CrusherRecipeRegistry.getOutputOnes(this.slots[theInput]);
            if(outputOnes != null && !outputOnes.isEmpty()){
                ItemStack outputOne = outputOnes.get(0);
                List<ItemStack> outputTwos = CrusherRecipeRegistry.getOutputTwos(this.slots[theInput]);
                ItemStack outputTwo = outputTwos == null ? null : outputTwos.get(0);
                if(outputOne != null){
                    if(outputOne.getItemDamage() == Util.WILDCARD){
                        outputOne.setItemDamage(0);
                    }
                    if(outputTwo != null && outputTwo.getItemDamage() == Util.WILDCARD){
                        outputTwo.setItemDamage(0);
                    }
                    if((this.slots[theFirstOutput] == null || (this.slots[theFirstOutput].isItemEqual(outputOne) && this.slots[theFirstOutput].stackSize <= this.slots[theFirstOutput].getMaxStackSize()-outputOne.stackSize)) && (outputTwo == null || (this.slots[theSecondOutput] == null || (this.slots[theSecondOutput].isItemEqual(outputTwo) && this.slots[theSecondOutput].stackSize <= this.slots[theSecondOutput].getMaxStackSize()-outputTwo.stackSize)))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int getMaxCrushTime(){
        return this.isDouble ? 150 : 100;
    }

    public void finishCrushing(int theInput, int theFirstOutput, int theSecondOutput){
        List<ItemStack> outputOnes = CrusherRecipeRegistry.getOutputOnes(this.slots[theInput]);
        if(outputOnes != null){
            ItemStack outputOne = outputOnes.get(0);
            if(outputOne != null){
                if(outputOne.getItemDamage() == Util.WILDCARD){
                    outputOne.setItemDamage(0);
                }
                if(this.slots[theFirstOutput] == null){
                    this.slots[theFirstOutput] = outputOne.copy();
                }
                else if(this.slots[theFirstOutput].getItem() == outputOne.getItem()){
                    this.slots[theFirstOutput].stackSize += outputOne.stackSize;
                }
            }
        }

        List<ItemStack> outputTwos = CrusherRecipeRegistry.getOutputTwos(this.slots[theInput]);
        if(outputTwos != null){
            ItemStack outputTwo = outputTwos.get(0);
            if(outputTwo != null){
                if(outputTwo.getItemDamage() == Util.WILDCARD){
                    outputTwo.setItemDamage(0);
                }
                int rand = Util.RANDOM.nextInt(100)+1;
                if(rand <= CrusherRecipeRegistry.getOutputTwoChance(this.slots[theInput])){
                    if(this.slots[theSecondOutput] == null){
                        this.slots[theSecondOutput] = outputTwo.copy();
                    }
                    else if(this.slots[theSecondOutput].getItem() == outputTwo.getItem()){
                        this.slots[theSecondOutput].stackSize += outputTwo.stackSize;
                    }
                }
            }
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
        return this.firstCrushTime*i/this.getMaxCrushTime();
    }

    @SideOnly(Side.CLIENT)
    public int getSecondTimeToScale(int i){
        return this.secondCrushTime*i/this.getMaxCrushTime();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return slot == SLOT_OUTPUT_1_1 || slot == SLOT_OUTPUT_1_2 || slot == SLOT_OUTPUT_2_1 || slot == SLOT_OUTPUT_2_2;
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == 0){
            this.isAutoSplit = !this.isAutoSplit;
            this.markDirty();
        }
    }
}
