package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.network.gui.IButtonReactor;
import ellpeck.actuallyadditions.network.gui.INumberReactor;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityInputter extends TileEntityInventoryBase implements IButtonReactor, INumberReactor{

    @Override
    public void onNumberReceived(int text, int textID, EntityPlayer player){
        if(text != -1){
            if(textID == 0) this.slotToPutStart = text;
            if(textID == 1) this.slotToPutEnd = text;
            if(textID == 2) this.slotToPullStart = text;
            if(textID == 3) this.slotToPullEnd = text;
        }
        this.markDirty();
    }

    public static class TileEntityInputterAdvanced extends TileEntityInputter{

        public TileEntityInputterAdvanced(){
            super(25, "inputterAdvanced");
            this.isAdvanced = true;
        }

    }

    public static final int PUT_FILTER_START = 13;
    public static final int PULL_FILTER_START = 1;

    public static final int WHITELIST_PULL_BUTTON_ID = 87;
    public static final int WHITELIST_PUT_BUTTON_ID = 88;
    public static final int OKAY_BUTTON_ID = 133;

    public int sideToPut = -1;

    public int slotToPutStart;
    public int slotToPutEnd;

    public TileEntity placeToPut;

    public int sideToPull = -1;

    public int slotToPullStart;
    public int slotToPullEnd;

    public TileEntity placeToPull;

    public boolean isAdvanced;

    public boolean isPullWhitelist = true;
    public boolean isPutWhitelist = true;

    public TileEntityInputter(int slots, String name){
        super(slots, name);
    }

    public TileEntityInputter(){
        super(1, "inputter");
        this.isAdvanced = false;
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            this.initVars();

            if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                if(!(this.sideToPull == this.sideToPut && this.slotToPullStart == this.slotToPutStart && this.slotToPullEnd == this.slotToPutEnd)){
                    if(sideToPull != -1 && this.placeToPull instanceof IInventory) this.pull();
                    if(sideToPut != -1 && this.placeToPut instanceof IInventory) this.put();
                }
            }
        }
    }

    public void pull(){
        IInventory theInventory = (IInventory)placeToPull;
        if(theInventory.getSizeInventory() > 0){
            int theSlotToPull = this.slotToPullStart;
            int maxSize = theInventory.getInventoryStackLimit();
            ISidedInventory theSided = null;
            if(theInventory instanceof ISidedInventory) theSided = (ISidedInventory)theInventory;
            boolean can = false;

            ItemStack theStack = null;
            for(int i = theSlotToPull; i < this.slotToPullEnd; i++){
                if(i >= theInventory.getSizeInventory()) return;

                ItemStack tempStack = theInventory.getStackInSlot(i);
                if(tempStack != null){
                    if(tempStack.getMaxStackSize() < this.getInventoryStackLimit()) maxSize = tempStack.getMaxStackSize();
                    else maxSize = this.getInventoryStackLimit();
                }
                if(tempStack != null && (this.slots[0] == null || (tempStack.isItemEqual(this.slots[0]) && this.slots[0].stackSize < maxSize)) && this.checkFilters(tempStack, true, isPullWhitelist)){
                    if(theSided != null){
                        for(int j = 0; j < 5; j++){
                            if(theSided.canExtractItem(i, tempStack, j)){
                                theStack = tempStack;
                                theSlotToPull = i;
                                can = true;
                                break;
                            }
                        }
                    }
                    else{
                        theStack = tempStack;
                        theSlotToPull = i;
                        can = true;
                    }
                }
                if(can) break;
            }
            if(can){
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
        IInventory theInventory = (IInventory)placeToPut;
        if(theInventory.getSizeInventory() > 0){
            int theSlotToPut = this.slotToPutStart;
            int maxSize = theInventory.getInventoryStackLimit();
            ISidedInventory theSided = null;
            if(theInventory instanceof ISidedInventory) theSided = (ISidedInventory)theInventory;
            boolean can = false;

            if(this.slots[0] != null){
                ItemStack theStack = null;
                for(int i = theSlotToPut; i < this.slotToPutEnd; i++){
                    if(i >= theInventory.getSizeInventory()) return;

                    ItemStack tempStack = theInventory.getStackInSlot(i);
                    if(tempStack != null){
                        if(tempStack.getMaxStackSize() < theInventory.getInventoryStackLimit()) maxSize = tempStack.getMaxStackSize();
                        else maxSize = theInventory.getInventoryStackLimit();
                    }
                    if((tempStack == null || (theInventory.isItemValidForSlot(i, this.slots[0]) && tempStack.isItemEqual(this.slots[0]) && tempStack.stackSize < maxSize)) && this.checkFilters(this.slots[0], false, isPutWhitelist)){
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
                        }
                    }
                    if(can) break;
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

    public boolean checkFilters(ItemStack stack, boolean isPull, boolean isWhitelist){
        if(!this.isAdvanced) return true;

        int slotStart = isPull ? PULL_FILTER_START : PUT_FILTER_START;
        int slotStop = slotStart+12;

        for(int i = slotStart; i < slotStop; i++){
            if(this.slots[i] != null && this.slots[i].isItemEqual(stack)) return isWhitelist;
        }
        return !isWhitelist;
    }

    public void initVars(){

        this.placeToPull = WorldUtil.getTileEntityFromSide(WorldUtil.getDirectionByRotatingSide(this.sideToPull), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        this.placeToPut = WorldUtil.getTileEntityFromSide(WorldUtil.getDirectionByRotatingSide(this.sideToPut), this.worldObj, this.xCoord, this.yCoord, this.zCoord);

        if(this.placeToPull instanceof IInventory){
            if(this.slotToPullEnd <= 0) this.slotToPullEnd = ((IInventory)this.placeToPull).getSizeInventory();
        }
        if(this.placeToPut instanceof IInventory){
            if(this.slotToPutEnd <= 0) this.slotToPutEnd = ((IInventory)this.placeToPut).getSizeInventory();
        }
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == WHITELIST_PULL_BUTTON_ID){
            this.isPullWhitelist = !this.isPullWhitelist;
            return;
        }
        if(buttonID == WHITELIST_PUT_BUTTON_ID){
            this.isPutWhitelist = !this.isPutWhitelist;
            return;
        }

        if(buttonID == 0 || buttonID == 1){
            this.slotToPutStart = 0;
            this.slotToPutEnd = 0;
        }
        if(buttonID == 2 || buttonID == 3){
            this.slotToPullStart = 0;
            this.slotToPullEnd = 0;
        }

        if(buttonID == 0) this.sideToPut++;
        if(buttonID == 1) this.sideToPut--;

        if(buttonID == 2) this.sideToPull++;
        if(buttonID == 3) this.sideToPull--;

        if(this.sideToPut >= 6) this.sideToPut = -1;
        else if(this.sideToPut < -1) this.sideToPut = 5;
        else if(this.sideToPull >= 6) this.sideToPull = -1;
        else if(this.sideToPull < -1) this.sideToPull = 5;

        this.markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("SideToPut", this.sideToPut);
        compound.setInteger("SlotToPut", this.slotToPutStart);
        compound.setInteger("SlotToPutEnd", this.slotToPutEnd);
        compound.setInteger("SideToPull", this.sideToPull);
        compound.setInteger("SlotToPull", this.slotToPullStart);
        compound.setInteger("SlotToPullEnd", this.slotToPullEnd);
        compound.setBoolean("PullWhitelist", this.isPullWhitelist);
        compound.setBoolean("PutWhitelist", this.isPutWhitelist);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.sideToPut = compound.getInteger("SideToPut");
        this.slotToPutStart = compound.getInteger("SlotToPut");
        this.slotToPutEnd = compound.getInteger("SlotToPutEnd");
        this.sideToPull = compound.getInteger("SideToPull");
        this.slotToPullStart = compound.getInteger("SlotToPull");
        this.slotToPullEnd = compound.getInteger("SlotToPullEnd");
        this.isPullWhitelist = compound.getBoolean("PullWhitelist");
        this.isPutWhitelist = compound.getBoolean("PutWhitelist");
        super.readFromNBT(compound);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return i == 0;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == 0;
    }
}