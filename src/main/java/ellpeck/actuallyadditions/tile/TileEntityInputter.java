package ellpeck.actuallyadditions.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
            int maxSize = theInventory.getInventoryStackLimit();
            ISidedInventory theSided = null;
            if(theInventory instanceof ISidedInventory) theSided = (ISidedInventory)theInventory;

            ItemStack theStack = null;
            for(int i = (theSlotToPull != -1 ? theSlotToPull : 0); i < (theSlotToPull != -1 ? theSlotToPull+1 : placeToPullSlotAmount); i++){
                ItemStack tempStack = theInventory.getStackInSlot(i);
                if(tempStack != null){
                    if(tempStack.getMaxStackSize() < this.getInventoryStackLimit()) maxSize = tempStack.getMaxStackSize();
                    else maxSize = this.getInventoryStackLimit();
                }
                if(tempStack != null && (this.slots[0] == null || (tempStack.isItemEqual(this.slots[0]) && this.slots[0].stackSize < maxSize))){
                    if(theSided != null){
                        for(int j = 0; j < 5; j++){
                            if(theSided.canExtractItem(i, tempStack, j)){
                                theStack = tempStack;
                                theSlotToPull = i;
                                break;
                            }
                        }
                    }
                    else{
                        theStack = tempStack;
                        theSlotToPull = i;
                        break;
                    }
                }
            }
            if(theStack != null){
                if(this.slots[0] != null){
                    if(theStack.isItemEqual(this.slots[0])){
                        if(theStack.stackSize <= maxSize - this.slots[0].stackSize){
                            this.slots[0].stackSize += theStack.stackSize;
                            theInventory.setInventorySlotContents(theSlotToPull, null);
                        }
                        else if(theStack.stackSize > maxSize - this.slots[0].stackSize){
                            theInventory.decrStackSize(theSlotToPull, maxSize - this.slots[0].stackSize);
                            this.slots[0].stackSize = maxSize;
                        }
                    }
                }
                else{
                    ItemStack toBePut = theStack.copy();
                    if(maxSize < toBePut.stackSize) toBePut.stackSize = maxSize;
                    this.setInventorySlotContents(0, toBePut);
                    if(theStack.stackSize == toBePut.stackSize) theInventory.setInventorySlotContents(theSlotToPull, null);
                    else theInventory.decrStackSize(theSlotToPull, toBePut.stackSize);
                }
            }
        }
    }

    public void put(){
        if(this.placeToPutSlotAmount > 0){
            IInventory theInventory = (IInventory)placeToPut;
            int theSlotToPut = this.slotToPut;
            int maxSize = theInventory.getInventoryStackLimit();
            ISidedInventory theSided = null;
            if(theInventory instanceof ISidedInventory) theSided = (ISidedInventory)theInventory;
            boolean can = false;

            if(this.slots[0] != null){
                ItemStack theStack = null;
                for(int i = (theSlotToPut != -1 ? theSlotToPut : 0); i < (theSlotToPut != -1 ? theSlotToPut+1 : placeToPutSlotAmount); i++){
                    ItemStack tempStack = theInventory.getStackInSlot(i);
                    if(tempStack != null){
                        if(tempStack.getMaxStackSize() < theInventory.getInventoryStackLimit()) maxSize = tempStack.getMaxStackSize();
                        else maxSize = theInventory.getInventoryStackLimit();
                    }
                    if(tempStack == null || (theInventory.isItemValidForSlot(i, this.slots[0]) && tempStack.isItemEqual(this.slots[0]) && tempStack.stackSize < maxSize)){
                        if(theSided != null){
                            for(int j = 0; j < 5; j++){
                                if(theSided.canInsertItem(i, this.slots[0], j)){
                                    theStack = tempStack;
                                    theSlotToPut = i;
                                    can = true;
                                    break;
                                }
                            }
                        }
                        else{
                            theStack = tempStack;
                            theSlotToPut = i;
                            can = true;
                            break;
                        }
                    }
                }
                if(can){
                    if(theStack != null){
                        if(theStack.isItemEqual(this.slots[0])){
                            if(this.slots[0].stackSize <= maxSize - theStack.stackSize){
                                theStack.stackSize += this.slots[0].stackSize;
                                this.slots[0] = null;
                            }
                            else if(this.slots[0].stackSize > maxSize - theStack.stackSize){
                                this.decrStackSize(0, maxSize - theStack.stackSize);
                                theStack.stackSize = maxSize;
                            }
                        }
                    }
                    else{
                        ItemStack toBePut = this.slots[0].copy();
                        if(maxSize < toBePut.stackSize) toBePut.stackSize = maxSize;
                        theInventory.setInventorySlotContents(theSlotToPut, toBePut);
                        if(this.slots[0].stackSize == toBePut.stackSize) this.slots[0] = null;
                        else this.decrStackSize(0, toBePut.stackSize);
                    }
                }
            }
        }
    }

    public void initVars(){
        this.placeToPull = getTileEntityFromSide(this.sideToPull, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        this.placeToPut = getTileEntityFromSide(this.sideToPut, this.worldObj, this.xCoord, this.yCoord, this.zCoord);

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

    public static TileEntity getTileEntityFromSide(int side, World world, int x, int y, int z){
        if(side == 0) return world.getTileEntity(x, y+1, z);
        if(side == 1) return world.getTileEntity(x, y-1, z);
        if(side == 2) return world.getTileEntity(x, y, z-1);
        if(side == 3) return world.getTileEntity(x-1, y, z);
        if(side == 4) return world.getTileEntity(x, y, z+1);
        if(side == 5) return world.getTileEntity(x+1, y, z);
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
