/*
 * This file ("TileEntityFurnaceDouble.java") is part of the Actually Additions Mod for Minecraft.
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
    public int firstSmeltTime;
    public int secondSmeltTime;
    private int lastEnergy;
    private int lastFirstSmelt;
    private int lastSecondSmelt;

    private static final int SMELT_TIME = 80;

    public static final int ENERGY_USE = 25;

    public TileEntityFurnaceDouble(){
        super(4, "furnaceDouble");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
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
                }
            }
            else{
                this.secondSmeltTime = 0;
            }

            if(this.storage.getEnergyStored() >= ENERGY_USE && (this.firstSmeltTime > 0 || this.secondSmeltTime > 0)){
                this.storage.extractEnergy(ENERGY_USE, false);
            }

            if(flag != (this.firstSmeltTime > 0 || this.secondSmeltTime > 0)){
                this.markDirty();
                int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                if(meta > 3){
                    if(!this.canSmeltOn(SLOT_INPUT_1, SLOT_OUTPUT_1) && !this.canSmeltOn(SLOT_INPUT_2, SLOT_OUTPUT_2)){
                        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta-4, 2);
                    }
                }
                else{
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta+4, 2);
                }
            }

            if((lastEnergy != this.storage.getEnergyStored() || this.lastFirstSmelt != this.firstSmeltTime || this.lastSecondSmelt != this.secondSmeltTime) && this.trySendUpdate()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastFirstSmelt = this.firstSmeltTime;
                this.lastSecondSmelt = this.secondSmeltTime;
            }
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
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && FurnaceRecipes.smelting().getSmeltingResult(stack) != null;
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
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT_1 || slot == SLOT_OUTPUT_2;
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
