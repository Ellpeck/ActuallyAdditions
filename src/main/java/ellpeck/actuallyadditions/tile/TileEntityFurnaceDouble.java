package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceDouble extends TileEntityInventoryBase implements IEnergyReceiver{

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1 = 1;
    public static final int SLOT_INPUT_2 = 2;
    public static final int SLOT_OUTPUT_2 = 3;

    public EnergyStorage storage = new EnergyStorage(30000);

    public static int energyUsePerTick = ConfigIntValues.FURNACE_ENERGY_USED.getValue();

    public int maxBurnTime = this.getStandardSpeed();

    public int firstSmeltTime;
    public int secondSmeltTime;

    public TileEntityFurnaceDouble(){
        super(4, "furnaceDouble");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){

            boolean canSmeltOnFirst = this.canSmeltOn(SLOT_INPUT_1, SLOT_OUTPUT_1);
            boolean canSmeltOnSecond = this.canSmeltOn(SLOT_INPUT_2, SLOT_OUTPUT_2);


            if(canSmeltOnFirst){
                if(this.storage.getEnergyStored() >= energyUsePerTick){
                    this.firstSmeltTime++;
                    if(this.firstSmeltTime >= maxBurnTime){
                        this.finishBurning(SLOT_INPUT_1, SLOT_OUTPUT_1);
                        this.firstSmeltTime = 0;
                    }
                }
            }
            else this.firstSmeltTime = 0;

            if(canSmeltOnSecond){
                if(this.storage.getEnergyStored() >= energyUsePerTick){
                    this.secondSmeltTime++;
                    if(this.secondSmeltTime >= maxBurnTime){
                        this.finishBurning(SLOT_INPUT_2, SLOT_OUTPUT_2);
                        this.secondSmeltTime = 0;
                    }
                }
            }
            else this.secondSmeltTime = 0;

            if(this.storage.getEnergyStored() >= energyUsePerTick && this.firstSmeltTime > 0 || this.secondSmeltTime > 0) this.storage.extractEnergy(energyUsePerTick, false);
        }

    }

    public boolean canSmeltOn(int theInput, int theOutput){
        if(this.slots[theInput] != null){
            ItemStack output = FurnaceRecipes.smelting().getSmeltingResult(this.slots[theInput]);
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
        ItemStack output = FurnaceRecipes.smelting().getSmeltingResult(this.slots[theInput]);
        if (this.slots[theOutput] == null) this.slots[theOutput] = output.copy();
        else if(this.slots[theOutput].getItem() == output.getItem()) this.slots[theOutput].stackSize += output.stackSize;

        this.slots[theInput].stackSize--;
        if (this.slots[theInput].stackSize <= 0) this.slots[theInput] = null;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("FirstSmeltTime", this.firstSmeltTime);
        compound.setInteger("SecondSmeltTime", this.secondSmeltTime);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.firstSmeltTime = compound.getInteger("FirstSmeltTime");
        this.secondSmeltTime = compound.getInteger("SecondSmeltTime");
        this.storage.readFromNBT(compound);
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.getEnergyStored(ForgeDirection.UNKNOWN) * i / this.getMaxEnergyStored(ForgeDirection.UNKNOWN);
    }

    @SideOnly(Side.CLIENT)
    public int getFirstTimeToScale(int i){
        return this.firstSmeltTime * i / this.maxBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public int getSecondTimeToScale(int i){
        return this.secondSmeltTime * i / this.maxBurnTime;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && FurnaceRecipes.smelting().getSmeltingResult(stack) != null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT_1 || slot == SLOT_OUTPUT_2;
    }

    public int getStandardSpeed(){
        return ConfigIntValues.FURNACE_DOUBLE_SMELT_TIME.getValue();
    }

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
}
