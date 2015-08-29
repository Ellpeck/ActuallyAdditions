/*
 * This file ("TileEntityGrinder.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.network.sync.IPacketSyncerToClient;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class TileEntityGrinder extends TileEntityInventoryBase implements IEnergyReceiver, IPacketSyncerToClient{

    public EnergyStorage storage = new EnergyStorage(60000);
    private int lastEnergy;

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }

    @Override
    public int[] getValues(){
        return new int[]{this.storage.getEnergyStored(), this.firstCrushTime, this.secondCrushTime};
    }

    @Override
    public void setValues(int[] values){
        this.storage.setEnergyStored(values[0]);
        this.firstCrushTime = values[1];
        this.secondCrushTime = values[2];
    }

    @Override
    public void sendUpdate(){
        PacketSyncerToClient.sendPacket(this);
    }

    public static class TileEntityGrinderDouble extends TileEntityGrinder{

        public TileEntityGrinderDouble(){
            super(6, "grinderDouble");
            this.isDouble = true;
        }

    }

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1_1 = 1;
    public static final int SLOT_OUTPUT_1_2 = 2;
    public static final int SLOT_INPUT_2 = 3;
    public static final int SLOT_OUTPUT_2_1 = 4;
    public static final int SLOT_OUTPUT_2_2 = 5;

    public int firstCrushTime;
    private int lastFirstCrush;
    public int secondCrushTime;
    private int lastSecondCrush;

    public boolean isDouble;

    public TileEntityGrinder(int slots, String name){
        super(slots, name);
    }

    public TileEntityGrinder(){
        super(3, "grinder");
        this.isDouble = false;
    }

    private int getMaxCrushTime(){
        return this.isDouble ? ConfigIntValues.GRINDER_DOUBLE_CRUSH_TIME.getValue() : ConfigIntValues.GRINDER_CRUSH_TIME.getValue();
    }

    private int getEnergyUse(){
        return this.isDouble ? ConfigIntValues.GRINDER_DOUBLE_ENERGY_USED.getValue() : ConfigIntValues.GRINDER_ENERGY_USED.getValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            boolean flag = this.firstCrushTime > 0 || this.secondCrushTime > 0;

            boolean canCrushOnFirst = this.canCrushOn(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
            boolean canCrushOnSecond = false;
            if(this.isDouble) canCrushOnSecond = this.canCrushOn(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);

            if(canCrushOnFirst){
                if(this.storage.getEnergyStored() >= getEnergyUse()){
                    this.firstCrushTime++;
                    if(this.firstCrushTime >= getMaxCrushTime()){
                        this.finishCrushing(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
                        this.firstCrushTime = 0;
                    }
                }
            }
            else this.firstCrushTime = 0;

            if(this.isDouble){
                if(canCrushOnSecond){
                    if(this.storage.getEnergyStored() >= getEnergyUse()){
                        this.secondCrushTime++;
                        if(this.secondCrushTime >= getMaxCrushTime()){
                            this.finishCrushing(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
                            this.secondCrushTime = 0;
                        }
                    }
                }
                else this.secondCrushTime = 0;
            }

            if(this.storage.getEnergyStored() >= getEnergyUse() && this.firstCrushTime > 0 || this.secondCrushTime > 0) this.storage.extractEnergy(getEnergyUse(), false);

            if(flag != (this.firstCrushTime > 0 || this.secondCrushTime > 0)){
                this.markDirty();
                int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                if(meta == 1){
                    if(!this.canCrushOn(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2) && (!this.isDouble || !this.canCrushOn(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2))) worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
                }
                else worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
            }

            if(lastEnergy != this.storage.getEnergyStored() || this.lastFirstCrush != this.firstCrushTime || this.lastSecondCrush != this.secondCrushTime){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastFirstCrush = this.firstCrushTime;
                this.lastSecondCrush = this.secondCrushTime;
                this.sendUpdate();
            }
        }
    }

    public boolean canCrushOn(int theInput, int theFirstOutput, int theSecondOutput){
        if(this.slots[theInput] != null){
            ItemStack outputOne = CrusherRecipeManualRegistry.getOutput(this.slots[theInput], false);
            ItemStack outputTwo = CrusherRecipeManualRegistry.getOutput(this.slots[theInput], true);
            if(this.slots[theInput] != null){
                if(outputOne != null){
                    if((this.slots[theFirstOutput] == null || (this.slots[theFirstOutput].isItemEqual(outputOne) && this.slots[theFirstOutput].stackSize <= this.slots[theFirstOutput].getMaxStackSize()-outputOne.stackSize)) && (outputTwo == null || (this.slots[theSecondOutput] == null || (this.slots[theSecondOutput].isItemEqual(outputTwo) && this.slots[theSecondOutput].stackSize <= this.slots[theSecondOutput].getMaxStackSize()-outputTwo.stackSize)))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void finishCrushing(int theInput, int theFirstOutput, int theSecondOutput){
        ItemStack outputOnFirst = CrusherRecipeManualRegistry.getOutput(this.slots[theInput], false);
        if(outputOnFirst != null){
            if(this.slots[theFirstOutput] == null) this.slots[theFirstOutput] = outputOnFirst.copy();
            else if(this.slots[theFirstOutput].getItem() == outputOnFirst.getItem()) this.slots[theFirstOutput].stackSize += outputOnFirst.stackSize;
        }

        int chance = CrusherRecipeManualRegistry.getSecondChance(this.slots[theInput]);
        ItemStack outputOnSecond = CrusherRecipeManualRegistry.getOutput(this.slots[theInput], true);
        if(outputOnSecond != null){
            int rand = new Random().nextInt(100) + 1;
            if(rand <= chance){
                if(this.slots[theSecondOutput] == null) this.slots[theSecondOutput] = outputOnSecond.copy();
                else if(this.slots[theSecondOutput].getItem() == outputOnSecond.getItem()) this.slots[theSecondOutput].stackSize += outputOnSecond.stackSize;
            }
        }

        this.slots[theInput].stackSize--;
        if (this.slots[theInput].stackSize <= 0) this.slots[theInput] = null;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        compound.setInteger("FirstCrushTime", this.firstCrushTime);
        compound.setInteger("SecondCrushTime", this.secondCrushTime);
        this.storage.writeToNBT(compound);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.firstCrushTime = compound.getInteger("FirstCrushTime");
        this.secondCrushTime = compound.getInteger("SecondCrushTime");
        this.storage.readFromNBT(compound);
        super.readFromNBT(compound);
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getFirstTimeToScale(int i){
        return this.firstCrushTime * i / this.getMaxCrushTime();
    }

    @SideOnly(Side.CLIENT)
    public int getSecondTimeToScale(int i){
        return this.secondCrushTime * i / this.getMaxCrushTime();
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && CrusherRecipeManualRegistry.getOutput(stack, false) != null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT_1_1 || slot == SLOT_OUTPUT_1_2 || slot == SLOT_OUTPUT_2_1 || slot == SLOT_OUTPUT_2_2;
    }
}
