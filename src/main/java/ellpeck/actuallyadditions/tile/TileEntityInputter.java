package ellpeck.actuallyadditions.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityInputter extends TileEntityInventoryBase{

    public int sideToPut = -1;
    public int slotToPut = -1;
    public int placeToPutSlotAmount;
    public TileEntity placeToPut;

    public int sideToPull = -1;
    public int slotToPull = -1;
    public int placeToPullSlotAmount;
    public TileEntity placeToPull;

    public TileEntityInputter(){
        super(1, "tileEntityInputter");
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            this.initVars();

            if(!(this.sideToPull == this.sideToPut && this.slotToPull == this.slotToPut)){
                if(sideToPull != -1) this.pull();
                if(sideToPut != -1) this.put();
            }
        }
    }

    public void pull(){
        if(this.placeToPullSlotAmount > 0){
            IInventory theInventory = (IInventory)placeToPull;
            int theSlotToPull = this.slotToPull;

            ItemStack theStack = null;
            if(theSlotToPull != -1) theStack = theInventory.getStackInSlot(theSlotToPull);
            else{
                for(int i = 0; i < this.placeToPullSlotAmount; i++){
                    ItemStack tempStack = theInventory.getStackInSlot(i);
                    if(tempStack != null && (this.slots[0] == null || (tempStack.isItemEqual(this.slots[0]) && this.slots[0].stackSize < this.getInventoryStackLimit()))){
                        theStack = tempStack;
                        theSlotToPull = i;
                        break;
                    }
                }
            }
            if(theSlotToPull != -1 && theStack != null){
                if(this.slots[0] != null){
                    if(theStack.isItemEqual(this.slots[0])){
                        if(theStack.stackSize <= this.getInventoryStackLimit() - this.slots[0].stackSize){
                            this.slots[0].stackSize += theStack.stackSize;
                            theInventory.setInventorySlotContents(theSlotToPull, null);
                        }
                        else if(theStack.stackSize > this.getInventoryStackLimit() - this.slots[0].stackSize){
                            theStack.stackSize -= (this.getInventoryStackLimit() - this.slots[0].stackSize);
                            this.slots[0].stackSize = this.getInventoryStackLimit();
                        }
                    }
                }
                else{
                    ItemStack toBePut = theStack.copy();
                    if(theInventory.getInventoryStackLimit() < toBePut.stackSize) toBePut.stackSize = theInventory.getInventoryStackLimit();
                    this.setInventorySlotContents(0, toBePut);
                    if(theStack.stackSize == toBePut.stackSize) theInventory.setInventorySlotContents(theSlotToPull, null);
                    else theStack.stackSize -= toBePut.stackSize;
                }
            }
        }
    }

    public void put(){
        if(this.placeToPutSlotAmount > 0){
            IInventory theInventory = (IInventory)placeToPut;
            int theSlotToPut = this.slotToPut;

            if(this.slots[0] != null){
                ItemStack theStack = null;
                if(theSlotToPut != -1) theStack = theInventory.getStackInSlot(theSlotToPut);
                else{
                    for(int i = 0; i < this.placeToPutSlotAmount; i++){
                        ItemStack tempStack = theInventory.getStackInSlot(i);
                        if(tempStack == null || (theInventory.isItemValidForSlot(i, this.slots[0]) && tempStack.isItemEqual(this.slots[0]) && tempStack.stackSize < theInventory.getInventoryStackLimit())){
                            theStack = tempStack;
                            theSlotToPut = i;
                            break;
                        }
                    }
                }
                if(theSlotToPut != -1 && theInventory.isItemValidForSlot(theSlotToPut, this.slots[0])){
                    if(theStack != null){
                        if(theStack.isItemEqual(this.slots[0])){
                            if(this.slots[0].stackSize <= theInventory.getInventoryStackLimit() - theStack.stackSize){
                                theStack.stackSize += this.slots[0].stackSize;
                                this.slots[0] = null;
                            }
                            else if(this.slots[0].stackSize > theInventory.getInventoryStackLimit() - theStack.stackSize){
                                this.slots[0].stackSize -= (theInventory.getInventoryStackLimit() - theStack.stackSize);
                                theStack.stackSize = theInventory.getInventoryStackLimit();
                            }
                        }
                    }
                    else{
                        ItemStack toBePut = this.slots[0].copy();
                        if(theInventory.getInventoryStackLimit() < toBePut.stackSize) toBePut.stackSize = theInventory.getInventoryStackLimit();
                        theInventory.setInventorySlotContents(theSlotToPut, toBePut);
                        if(this.slots[0].stackSize == toBePut.stackSize) this.slots[0] = null;
                        else this.slots[0].stackSize -= toBePut.stackSize;
                    }
                }
            }
        }
    }

    public void initVars(){
        this.placeToPull = this.getTileEntityFromSide(this.sideToPull);
        this.placeToPut = this.getTileEntityFromSide(this.sideToPut);

        if(this.placeToPull != null && this.placeToPull instanceof IInventory){
            this.placeToPullSlotAmount = ((IInventory)this.placeToPull).getSizeInventory();
        }
        else{
            this.placeToPullSlotAmount = 0;
            this.slotToPull = -1;
        }

        if(this.placeToPut != null && this.placeToPut instanceof IInventory){
            this.placeToPutSlotAmount = ((IInventory)this.placeToPut).getSizeInventory();
        }
        else{
            this.placeToPutSlotAmount = 0;
            this.slotToPut = -1;
        }
    }

    public TileEntity getTileEntityFromSide(int side){
        if(side == 0) return worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
        if(side == 1) return worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
        if(side == 2) return worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
        if(side == 3) return worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
        if(side == 4) return worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
        if(side == 5) return worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
        else return null;
    }

    public void onButtonPressed(int buttonID){
        if(buttonID == 0) this.sideToPut++;
        if(buttonID == 1) this.sideToPut--;
        if(buttonID == 2) this.slotToPut++;
        if(buttonID == 3) this.slotToPut--;

        if(buttonID == 4) this.sideToPull++;
        if(buttonID == 5) this.sideToPull--;
        if(buttonID == 6) this.slotToPull++;
        if(buttonID == 7) this.slotToPull--;

        if(this.sideToPut >= 6) this.sideToPut = -1;
        else if(this.sideToPut < -1) this.sideToPut = 5;
        else if(this.sideToPull >= 6) this.sideToPull = -1;
        else if(this.sideToPull < -1) this.sideToPull = 5;
        else if(this.slotToPut >= this.placeToPutSlotAmount) this.slotToPut = -1;
        else if(this.slotToPut < -1) this.slotToPut = this.placeToPutSlotAmount-1;
        else if(this.slotToPull >= this.placeToPullSlotAmount) this.slotToPull = -1;
        else if(this.slotToPull < -1) this.slotToPull = this.placeToPullSlotAmount-1;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("SideToPut", this.sideToPut);
        compound.setInteger("SlotToPut", this.slotToPut);
        compound.setInteger("SideToPull", this.sideToPull);
        compound.setInteger("SlotToPull", this.slotToPull);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.sideToPut = compound.getInteger("SideToPut");
        this.slotToPut = compound.getInteger("SlotToPut");
        this.sideToPull = compound.getInteger("SideToPull");
        this.slotToPull = compound.getInteger("SlotToPull");
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return true;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return true;
    }
}
