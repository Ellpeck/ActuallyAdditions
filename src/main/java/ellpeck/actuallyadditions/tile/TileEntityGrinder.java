package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.recipe.GrinderRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

import java.util.Random;

public class TileEntityGrinder extends TileEntityInventoryBase{

    public static final int SLOT_COAL = 0;
    public static final int SLOT_INPUT_1 = 1;
    public static final int SLOT_OUTPUT_1_1 = 2;
    public static final int SLOT_OUTPUT_1_2 = 3;
    public static final int SLOT_INPUT_2 = 4;
    public static final int SLOT_OUTPUT_2_1 = 5;
    public static final int SLOT_OUTPUT_2_2 = 6;

    public int coalTime;
    public int coalTimeLeft;

    public int maxCrushTime;

    public int firstCrushTime;
    public int secondCrushTime;

    public boolean isDouble;

    public TileEntityGrinder(){
        super(0, "");
    }

    public TileEntityGrinder(boolean isDouble){
        super(isDouble ? 7 : 4, isDouble ? "tileEntityGrinderDouble" : "tileEntityGrinder");
        this.maxCrushTime = isDouble ? ConfigValues.grinderDoubleCrushTime : ConfigValues.grinderCrushTime;
        this.isDouble = isDouble;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            boolean theFlag = this.coalTimeLeft > 0;

            if(this.coalTimeLeft > 0) this.coalTimeLeft--;

            boolean canCrushOnFirst = this.canCrushOn(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
            boolean canCrushOnSecond = false;
            if(this.isDouble) canCrushOnSecond = this.canCrushOn(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);

            if((canCrushOnFirst || canCrushOnSecond) && this.coalTimeLeft <= 0 && this.slots[SLOT_COAL] != null){
                this.coalTime = TileEntityFurnace.getItemBurnTime(this.slots[SLOT_COAL]);
                this.coalTimeLeft = this.coalTime;
                if(this.coalTime > 0){
                    this.slots[SLOT_COAL].stackSize--;
                    if(this.slots[SLOT_COAL].stackSize <= 0) this.slots[SLOT_COAL] = this.slots[SLOT_COAL].getItem().getContainerItem(this.slots[SLOT_COAL]);
                }
            }

            if(this.coalTimeLeft > 0){
                if(canCrushOnFirst){
                    this.firstCrushTime++;
                    if(this.firstCrushTime >= maxCrushTime){
                        this.finishCrushing(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
                        this.firstCrushTime = 0;
                    }
                }
                else this.firstCrushTime = 0;

                if(this.isDouble){
                    if(canCrushOnSecond){
                        this.secondCrushTime++;
                        if(this.secondCrushTime >= maxCrushTime){
                            this.finishCrushing(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
                            this.secondCrushTime = 0;
                        }
                    }
                    else this.secondCrushTime = 0;
                }
            }
            else{
                this.firstCrushTime = 0;
                this.secondCrushTime = 0;
                this.coalTime = 0;
            }

            if(theFlag != this.coalTimeLeft > 0){
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, (this.coalTimeLeft > 0 ? 1 : 0), 2);
                this.markDirty();
            }
        }
    }

    public boolean canCrushOn(int theInput, int theFirstOutput, int theSecondOutput){
        if(this.slots[theInput] != null){
            ItemStack outputOne = GrinderRecipes.instance().getOutput(this.slots[theInput], false);
            ItemStack outputTwo = GrinderRecipes.instance().getOutput(this.slots[theInput], true);
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
        ItemStack outputOnFirst = GrinderRecipes.instance().getOutput(this.slots[theInput], false);
        if (this.slots[theFirstOutput] == null) this.slots[theFirstOutput] = outputOnFirst.copy();
        else if(this.slots[theFirstOutput].getItem() == outputOnFirst.getItem()) this.slots[theFirstOutput].stackSize += outputOnFirst.stackSize;

        int chance = GrinderRecipes.instance().getSecondChance(this.slots[theInput]);
        ItemStack outputOnSecond = GrinderRecipes.instance().getOutput(this.slots[theInput], true);
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
        compound.setInteger("CoalTime", this.coalTime);
        compound.setInteger("CoalTimeLeft", this.coalTimeLeft);
        compound.setInteger("FirstCrushTime", this.firstCrushTime);
        compound.setInteger("SecondCrushTime", this.secondCrushTime);
        compound.setInteger("MaxCrushTime", this.maxCrushTime);
        compound.setBoolean("IsDouble", this.isDouble);
        compound.setString("Name", this.name);
        compound.setInteger("Slots", this.slots.length);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.coalTime = compound.getInteger("CoalTime");
        this.coalTimeLeft = compound.getInteger("CoalTimeLeft");
        this.firstCrushTime = compound.getInteger("FirstCrushTime");
        this.secondCrushTime = compound.getInteger("SecondCrushTime");
        this.maxCrushTime = compound.getInteger("MaxCrushTime");
        this.isDouble = compound.getBoolean("IsDouble");
        this.name = compound.getString("Name");
        this.initializeSlots(compound.getInteger("Slots"));
        super.readFromNBT(compound);
    }

    @SideOnly(Side.CLIENT)
    public int getCoalTimeToScale(int i){
        return this.coalTimeLeft * i / this.coalTime;
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
        return i == SLOT_COAL && TileEntityFurnace.getItemBurnTime(stack) > 0 || (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && GrinderRecipes.instance().getOutput(stack, false) != null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT_1_1 || slot == SLOT_OUTPUT_1_2 || slot == SLOT_OUTPUT_2_1 || slot == SLOT_OUTPUT_2_2 || (slot == SLOT_COAL && stack.getItem() == Items.bucket);
    }
}
