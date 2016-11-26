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


import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityGrinder extends TileEntityInventoryBase implements IButtonReactor{

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1_1 = 1;
    public static final int SLOT_OUTPUT_1_2 = 2;
    public static final int SLOT_INPUT_2 = 3;
    public static final int SLOT_OUTPUT_2_1 = 4;
    public static final int SLOT_OUTPUT_2_2 = 5;
    public static final int ENERGY_USE = 40;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(60000, 100, 0);
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
        if(!this.world.isRemote){
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
                    if(this.firstCrushTime%20 == 0){
                        shouldPlaySound = true;
                    }
                    this.firstCrushTime++;
                    if(this.firstCrushTime >= this.getMaxCrushTime()){
                        this.finishCrushing(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
                        this.firstCrushTime = 0;
                    }
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                }
            }
            else{
                this.firstCrushTime = 0;
            }

            if(this.isDouble){
                if(canCrushOnSecond){
                    if(this.storage.getEnergyStored() >= ENERGY_USE){
                        if(this.secondCrushTime%20 == 0){
                            shouldPlaySound = true;
                        }
                        this.secondCrushTime++;
                        if(this.secondCrushTime >= this.getMaxCrushTime()){
                            this.finishCrushing(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
                            this.secondCrushTime = 0;
                        }
                        this.storage.extractEnergyInternal(ENERGY_USE, false);
                    }
                }
                else{
                    this.secondCrushTime = 0;
                }
            }

            if((this.lastEnergy != this.storage.getEnergyStored() || this.lastFirstCrush != this.firstCrushTime || this.lastSecondCrush != this.secondCrushTime || this.isAutoSplit != this.lastAutoSplit) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastFirstCrush = this.firstCrushTime;
                this.lastSecondCrush = this.secondCrushTime;
                this.lastAutoSplit = this.isAutoSplit;
            }

            if(shouldPlaySound){
                this.world.playSound(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), SoundHandler.crusher, SoundCategory.BLOCKS, 0.15F, 1.0F);
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && CrusherRecipeRegistry.getRecipeFromInput(stack) != null;
    }

    public boolean canCrushOn(int theInput, int theFirstOutput, int theSecondOutput){
        if(StackUtil.isValid(this.slots.get(theInput))){
            ItemStack outputOne = CrusherRecipeRegistry.getOutputOnes(this.slots.get(theInput));
            ItemStack outputTwo = CrusherRecipeRegistry.getOutputTwos(this.slots.get(theInput));
            if(StackUtil.isValid(outputOne)){
                if(outputOne.getItemDamage() == Util.WILDCARD){
                    outputOne.setItemDamage(0);
                }
                if(StackUtil.isValid(outputTwo) && outputTwo.getItemDamage() == Util.WILDCARD){
                    outputTwo.setItemDamage(0);
                }
                if((!StackUtil.isValid(this.slots.get(theFirstOutput)) || (this.slots.get(theFirstOutput).isItemEqual(outputOne) && StackUtil.getStackSize(this.slots.get(theFirstOutput)) <= this.slots.get(theFirstOutput).getMaxStackSize()-StackUtil.getStackSize(outputOne))) && (!StackUtil.isValid(outputTwo) || (!StackUtil.isValid(this.slots.get(theSecondOutput)) || (this.slots.get(theSecondOutput).isItemEqual(outputTwo) && StackUtil.getStackSize(this.slots.get(theSecondOutput)) <= this.slots.get(theSecondOutput).getMaxStackSize()-StackUtil.getStackSize(outputTwo))))){
                    return true;
                }
            }
        }
        return false;
    }

    private int getMaxCrushTime(){
        return this.isDouble ? 150 : 100;
    }

    public void finishCrushing(int theInput, int theFirstOutput, int theSecondOutput){
        ItemStack outputOne = CrusherRecipeRegistry.getOutputOnes(this.slots.get(theInput));
        if(StackUtil.isValid(outputOne)){
            if(outputOne.getItemDamage() == Util.WILDCARD){
                outputOne.setItemDamage(0);
            }
            if(!StackUtil.isValid(this.slots.get(theFirstOutput))){
                this.slots.set(theFirstOutput, outputOne.copy());
            }
            else if(this.slots.get(theFirstOutput).getItem() == outputOne.getItem()){
                this.slots.set(theFirstOutput, StackUtil.addStackSize(this.slots.get(theFirstOutput), StackUtil.getStackSize(outputOne)));
            }
        }

        ItemStack outputTwo = CrusherRecipeRegistry.getOutputTwos(this.slots.get(theInput));
        if(StackUtil.isValid(outputTwo)){
            if(outputTwo.getItemDamage() == Util.WILDCARD){
                outputTwo.setItemDamage(0);
            }
            int rand = this.world.rand.nextInt(100)+1;
            if(rand <= CrusherRecipeRegistry.getOutputTwoChance(this.slots.get(theInput))){
                if(!StackUtil.isValid(this.slots.get(theSecondOutput))){
                    this.slots.set(theSecondOutput, outputTwo.copy());
                }
                else if(this.slots.get(theSecondOutput).getItem() == outputTwo.getItem()){
                    this.slots.set(theSecondOutput, StackUtil.addStackSize(this.slots.get(theSecondOutput), StackUtil.getStackSize(outputTwo)));
                }
            }
        }

        this.slots.set(theInput, StackUtil.addStackSize(this.slots.get(theInput), -1));
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

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
