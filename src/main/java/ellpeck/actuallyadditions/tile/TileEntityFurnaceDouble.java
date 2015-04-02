package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.ConfigValues;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityFurnaceDouble extends TileEntityInventoryBase implements IPowerAcceptor{

    public static final int SLOT_COAL = 0;
    public static final int SLOT_INPUT_1 = 1;
    public static final int SLOT_OUTPUT_1 = 2;
    public static final int SLOT_INPUT_2 = 3;
    public static final int SLOT_OUTPUT_2 = 4;

    public int coalTime;
    public int coalTimeLeft;

    public final int maxBurnTime = ConfigValues.furnaceDoubleSmeltTime;

    public int firstSmeltTime;
    public int secondSmeltTime;

    public TileEntityFurnaceDouble(){
        super(5, "tileEntityFurnaceDouble");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            boolean theFlag = this.coalTimeLeft > 0;

            if(this.coalTimeLeft > 0) this.coalTimeLeft--;

            boolean canSmeltOnFirst = this.canSmeltOn(SLOT_INPUT_1, SLOT_OUTPUT_1);
            boolean canSmeltOnSecond = this.canSmeltOn(SLOT_INPUT_2, SLOT_OUTPUT_2);

            if((canSmeltOnFirst || canSmeltOnSecond) && this.coalTimeLeft <= 0 && this.slots[SLOT_COAL] != null){
                this.coalTime = TileEntityFurnace.getItemBurnTime(this.slots[SLOT_COAL]);
                this.coalTimeLeft = this.coalTime;
                if(this.coalTime > 0){
                    this.slots[SLOT_COAL].stackSize--;
                    if(this.slots[SLOT_COAL].stackSize <= 0) this.slots[SLOT_COAL] = this.slots[SLOT_COAL].getItem().getContainerItem(this.slots[SLOT_COAL]);
                }
            }

            if(this.coalTimeLeft > 0){
                if(canSmeltOnFirst){
                    this.firstSmeltTime++;
                    if(this.firstSmeltTime >= maxBurnTime){
                        this.finishBurning(SLOT_INPUT_1, SLOT_OUTPUT_1);
                        this.firstSmeltTime = 0;
                    }
                }
                else this.firstSmeltTime = 0;

                if(canSmeltOnSecond){
                    this.secondSmeltTime++;
                    if(this.secondSmeltTime >= maxBurnTime){
                        this.finishBurning(SLOT_INPUT_2, SLOT_OUTPUT_2);
                        this.secondSmeltTime = 0;
                    }
                }
                else this.secondSmeltTime = 0;
            }
            else{
                this.firstSmeltTime = 0;
                this.secondSmeltTime = 0;
                this.coalTime = 0;
            }

            if(theFlag != this.coalTimeLeft > 0){
                int metaBefore = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, (this.coalTimeLeft > 0 ? metaBefore+4 : metaBefore-4), 2);
                this.markDirty();
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
        if (this.slots[theOutput] == null) this.slots[theOutput] = output.copy();
        else if(this.slots[theOutput].getItem() == output.getItem()) this.slots[theOutput].stackSize += output.stackSize;

        this.slots[theInput].stackSize--;
        if (this.slots[theInput].stackSize <= 0) this.slots[theInput] = null;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("CoalTime", this.coalTime);
        compound.setInteger("CoalTimeLeft", this.coalTimeLeft);
        compound.setInteger("FirstSmeltTime", this.firstSmeltTime);
        compound.setInteger("SecondSmeltTime", this.secondSmeltTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.coalTime = compound.getInteger("CoalTime");
        this.coalTimeLeft = compound.getInteger("CoalTimeLeft");
        this.firstSmeltTime = compound.getInteger("FirstSmeltTime");
        this.secondSmeltTime = compound.getInteger("SecondSmeltTime");
    }

    @SideOnly(Side.CLIENT)
    public int getCoalTimeToScale(int i){
        return this.coalTimeLeft * i / this.coalTime;
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
        return i == SLOT_COAL && TileEntityFurnace.getItemBurnTime(stack) > 0 || (i == SLOT_INPUT_1 || i == SLOT_INPUT_2) && FurnaceRecipes.smelting().getSmeltingResult(stack) != null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT_1 || slot == SLOT_OUTPUT_2 || (slot == SLOT_COAL && stack.getItem() == Items.bucket);
    }

    @Override
    public void setBlockMetadataToOn(){
        int metaBefore = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metaBefore+4, 2);
    }

    @Override
    public void setPower(int power){
        this.coalTimeLeft = power;
    }

    @Override
    public void setItemPower(int power){
        this.coalTime = power;
    }

    @Override
    public int getItemPower(){
        return this.coalTime;
    }
}
