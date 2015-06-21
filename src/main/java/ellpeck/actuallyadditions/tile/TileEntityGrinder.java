package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.recipe.GrinderRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class TileEntityGrinder extends TileEntityInventoryBase implements IEnergyReceiver{

    public EnergyStorage storage = new EnergyStorage(60000);

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

    public static class TileEntityGrinderDouble extends TileEntityGrinder{

        public TileEntityGrinderDouble(){
            super(6, "grinderDouble");
            this.isDouble = true;
            this.maxCrushTime = ConfigIntValues.GRINDER_DOUBLE_CRUSH_TIME.getValue();
            energyUsePerTick = ConfigIntValues.GRINDER_DOUBLE_ENERGY_USED.getValue();
        }

    }

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1_1 = 1;
    public static final int SLOT_OUTPUT_1_2 = 2;
    public static final int SLOT_INPUT_2 = 3;
    public static final int SLOT_OUTPUT_2_1 = 4;
    public static final int SLOT_OUTPUT_2_2 = 5;

    public int energyUsePerTick;

    public int maxCrushTime;

    public int firstCrushTime;
    public int secondCrushTime;

    public boolean isDouble;

    public TileEntityGrinder(int slots, String name){
        super(slots, name);
    }

    public TileEntityGrinder(){
        super(3, "grinder");
        this.isDouble = false;
        this.maxCrushTime = ConfigIntValues.GRINDER_CRUSH_TIME.getValue();
        energyUsePerTick = ConfigIntValues.GRINDER_ENERGY_USED.getValue();
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
                if(this.storage.getEnergyStored() >= energyUsePerTick){
                    this.firstCrushTime++;
                    if(this.firstCrushTime >= maxCrushTime){
                        this.finishCrushing(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
                        this.firstCrushTime = 0;
                    }
                }
            }
            else this.firstCrushTime = 0;

            if(this.isDouble){
                if(canCrushOnSecond){
                    if(this.storage.getEnergyStored() >= energyUsePerTick){
                        this.secondCrushTime++;
                        if(this.secondCrushTime >= maxCrushTime){
                            this.finishCrushing(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
                            this.secondCrushTime = 0;
                        }
                    }
                }
                else this.secondCrushTime = 0;
            }

            if(this.storage.getEnergyStored() >= energyUsePerTick && this.firstCrushTime > 0 || this.secondCrushTime > 0) this.storage.extractEnergy(energyUsePerTick, false);

            if(flag != (this.firstCrushTime > 0 || this.secondCrushTime > 0)){
                int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                if(meta == 1){
                    if(!this.canCrushOn(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2) && (!this.isDouble || !this.canCrushOn(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2))) worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
                }
                else worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
            }
        }
    }

    public boolean canCrushOn(int theInput, int theFirstOutput, int theSecondOutput){
        if(this.slots[theInput] != null){
            ItemStack outputOne = GrinderRecipes.getOutput(this.slots[theInput], false);
            ItemStack outputTwo = GrinderRecipes.getOutput(this.slots[theInput], true);
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
        ItemStack outputOnFirst = GrinderRecipes.getOutput(this.slots[theInput], false);
        if(outputOnFirst != null){
            if(this.slots[theFirstOutput] == null) this.slots[theFirstOutput] = outputOnFirst.copy();
            else if(this.slots[theFirstOutput].getItem() == outputOnFirst.getItem()) this.slots[theFirstOutput].stackSize += outputOnFirst.stackSize;
        }

        int chance = GrinderRecipes.getSecondChance(this.slots[theInput]);
        ItemStack outputOnSecond = GrinderRecipes.getOutput(this.slots[theInput], true);
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
        return this.getEnergyStored(ForgeDirection.UNKNOWN) * i / this.getMaxEnergyStored(ForgeDirection.UNKNOWN);
    }

    @SideOnly(Side.CLIENT)
    public int getFirstTimeToScale(int i){
        return this.firstCrushTime * i / this.maxCrushTime;
    }

    @SideOnly(Side.CLIENT)
    public int getSecondTimeToScale(int i){
        return this.secondCrushTime * i / this.maxCrushTime;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && GrinderRecipes.getOutput(stack, false) != null;
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
