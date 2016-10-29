/*
 * This file ("TileEntityInputter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;


import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.network.gui.INumberReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityInputter extends TileEntityInventoryBase implements IButtonReactor, INumberReactor{

    public static final int PUT_FILTER_START = 13;
    public static final int PULL_FILTER_START = 1;
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
    public FilterSettings leftFilter = new FilterSettings(PULL_FILTER_START, PULL_FILTER_START+12, true, true, false, 0, -1000);
    public FilterSettings rightFilter = new FilterSettings(PUT_FILTER_START, PUT_FILTER_START+12, true, true, false, 0, -2000);
    private int lastPutSide;
    private int lastPutStart;
    private int lastPutEnd;
    private int lastPullSide;
    private int lastPullStart;
    private int lastPullEnd;

    public TileEntityInputter(int slots, String name){
        super(slots, name);
    }

    public TileEntityInputter(){
        super(1, "inputter");
        this.isAdvanced = false;
    }

    @Override
    public void onNumberReceived(int text, int textID, EntityPlayer player){
        if(text != -1){
            if(textID == 0){
                this.slotToPutStart = Math.max(text, 0);
            }
            if(textID == 1){
                this.slotToPutEnd = Math.max(text, 0);
            }

            if(textID == 2){
                this.slotToPullStart = Math.max(text, 0);
            }
            if(textID == 3){
                this.slotToPullEnd = Math.max(text, 0);
            }
        }
        this.markDirty();
    }

    private boolean newPulling(){
        for(EnumFacing side : EnumFacing.values()){
            if(this.placeToPull.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)){
                IItemHandler cap = this.placeToPull.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
                if(cap != null){
                    for(int i = Math.max(this.slotToPullStart, 0); i < Math.min(this.slotToPullEnd, cap.getSlots()); i++){
                        if(this.checkBothFilters(cap.getStackInSlot(i), false)){
                            if(WorldUtil.doItemInteraction(i, 0, this.placeToPull, this, side, null)){
                                return true;
                            }
                        }
                    }
                }
            }

            if(this.placeToPull instanceof TileEntityItemViewer){
                break;
            }
        }
        return false;
    }

    private boolean newPutting(){
        if(this.checkBothFilters(this.slots[0], true)){
            for(EnumFacing side : EnumFacing.values()){
                if(this.placeToPut.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)){
                    IItemHandler cap = this.placeToPut.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
                    if(cap != null){
                        for(int i = Math.max(this.slotToPutStart, 0); i < Math.min(this.slotToPutEnd, cap.getSlots()); i++){
                            if(WorldUtil.doItemInteraction(0, i, this, this.placeToPut, null, side)){
                                return true;
                            }
                        }
                    }
                }

                if(this.placeToPut instanceof TileEntityItemViewer){
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Pulls Items from the specified Slots on the specified Side
     */
    private void pull(){
        if(this.newPulling() || !(this.placeToPull instanceof IInventory)){
            return;
        }

        //The Inventory to pull from
        IInventory theInventory = (IInventory)this.placeToPull;
        //Does the Inventory even have Slots!?
        if(theInventory.getSizeInventory() > 0){
            //The slot currently pulling from (for later)
            int theSlotToPull = this.slotToPullStart;
            //The amount of Items that can fit into one slot of the inventory
            int maxSize = theInventory.getInventoryStackLimit();
            //If the Inventory is ISided, deal with that
            ISidedInventory theSided = null;
            if(theInventory instanceof ISidedInventory){
                theSided = (ISidedInventory)theInventory;
            }
            //If can be pulled (for later)
            boolean can = false;

            //The Stack that is pulled (for later)
            ItemStack theStack = null;
            //Go through all of the specified Slots
            for(int i = Math.max(theSlotToPull, 0); i < Math.min(this.slotToPullEnd, theInventory.getSizeInventory()); i++){
                //Temporary Stack for storage
                ItemStack tempStack = theInventory.getStackInSlot(i);
                if(tempStack != null){
                    //Set maxSize to the max Size of the temporary stack if it's smaller than the Inventory's Max Size
                    if(tempStack.getMaxStackSize() < this.getInventoryStackLimit()){
                        maxSize = tempStack.getMaxStackSize();
                    }
                    else{
                        maxSize = this.getInventoryStackLimit();
                    }
                }
                //If ESD has enough Space & Item in question is on whitelist
                if(tempStack != null && (this.slots[0] == null || (ItemUtil.canBeStacked(tempStack, this.slots[0]) && this.slots[0].stackSize < maxSize)) && this.checkBothFilters(tempStack, false)){
                    //Deal with ISided
                    if(theSided != null){
                        //Check if Item can be inserted from any Side (Because Sidedness gets ignored!)
                        for(int j = 0; j <= 5; j++){
                            if(theSided.canExtractItem(i, tempStack, EnumFacing.values()[j])){
                                theStack = tempStack;
                                theSlotToPull = i;
                                can = true;
                                break;
                            }
                        }
                    }
                    //Deal with IInventory
                    else{
                        theStack = tempStack;
                        theSlotToPull = i;
                        can = true;
                    }
                }
                //Stop if it can already pull
                if(can){
                    break;
                }
            }
            //If pull can be done
            if(can){
                //If ESD already has Items
                if(this.slots[0] != null){
                    if(ItemUtil.canBeStacked(theStack, this.slots[0])){
                        //If the StackSize is smaller than the space the ESD has left
                        if(theStack.stackSize <= maxSize-this.slots[0].stackSize){
                            this.slots[0].stackSize += theStack.stackSize;
                            theInventory.setInventorySlotContents(theSlotToPull, null);
                        }
                        //If the StackSize is bigger than what fits into the Inventory
                        else if(theStack.stackSize > maxSize-this.slots[0].stackSize){
                            theInventory.decrStackSize(theSlotToPull, maxSize-this.slots[0].stackSize);
                            this.slots[0].stackSize = maxSize;
                        }
                    }
                }
                //If ESD is empty
                else{
                    ItemStack toBePut = theStack.copy();
                    if(maxSize < toBePut.stackSize){
                        toBePut.stackSize = maxSize;
                    }
                    //Actually puts the Item
                    this.setInventorySlotContents(0, toBePut);
                    //Removes the Item from the inventory getting pulled from
                    if(theStack.stackSize == toBePut.stackSize){
                        theInventory.setInventorySlotContents(theSlotToPull, null);
                    }
                    else{
                        theInventory.decrStackSize(theSlotToPull, toBePut.stackSize);
                    }
                }
            }
        }
    }

    /**
     * Puts Items into the specified Slots at the specified Side
     * (Check pull() for Description, similar to this)
     */
    private void put(){
        if(this.newPutting() || !(this.placeToPut instanceof IInventory)){
            return;
        }

        IInventory theInventory = (IInventory)this.placeToPut;
        if(theInventory.getSizeInventory() > 0){
            int theSlotToPut = this.slotToPutStart;
            int maxSize = theInventory.getInventoryStackLimit();
            ISidedInventory theSided = null;
            if(theInventory instanceof ISidedInventory){
                theSided = (ISidedInventory)theInventory;
            }
            boolean can = false;

            if(this.slots[0] != null){
                ItemStack theStack = null;
                for(int i = Math.max(theSlotToPut, 0); i < Math.min(this.slotToPutEnd, theInventory.getSizeInventory()); i++){
                    ItemStack tempStack = theInventory.getStackInSlot(i);
                    if(tempStack != null){
                        if(tempStack.getMaxStackSize() < theInventory.getInventoryStackLimit()){
                            maxSize = tempStack.getMaxStackSize();
                        }
                        else{
                            maxSize = theInventory.getInventoryStackLimit();
                        }
                    }
                    if(theInventory.isItemValidForSlot(i, this.slots[0]) && (tempStack == null || (ItemUtil.canBeStacked(tempStack, this.slots[0]) && tempStack.stackSize < maxSize)) && this.checkBothFilters(this.slots[0], true)){
                        if(theSided != null){
                            for(int j = 0; j <= 5; j++){
                                if(theSided.canInsertItem(i, this.slots[0], EnumFacing.values()[j])){
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
                    if(can){
                        break;
                    }
                }
                if(can){
                    if(theStack != null){
                        ItemStack copiedStack = theStack.copy();
                        if(ItemUtil.canBeStacked(copiedStack, this.slots[0])){
                            if(this.slots[0].stackSize <= maxSize-copiedStack.stackSize){
                                copiedStack.stackSize += this.slots[0].stackSize;
                                this.slots[0] = null;
                                theInventory.setInventorySlotContents(theSlotToPut, copiedStack);
                            }
                            else if(this.slots[0].stackSize > maxSize-copiedStack.stackSize){
                                this.decrStackSize(0, maxSize-copiedStack.stackSize);
                                copiedStack.stackSize = maxSize;
                                theInventory.setInventorySlotContents(theSlotToPut, copiedStack);
                            }
                        }
                    }
                    else{
                        ItemStack toBePut = this.slots[0].copy();
                        if(maxSize < toBePut.stackSize){
                            toBePut.stackSize = maxSize;
                        }
                        theInventory.setInventorySlotContents(theSlotToPut, toBePut);
                        if(this.slots[0].stackSize == toBePut.stackSize){
                            this.slots[0] = null;
                        }
                        else{
                            this.decrStackSize(0, toBePut.stackSize);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if one of the filters contains the ItemStack
     *
     * @param stack The ItemStack
     * @return If the Item is filtered correctly
     */
    private boolean checkBothFilters(ItemStack stack, boolean output){
        return !this.isAdvanced || (output ? this.rightFilter : this.leftFilter).check(stack, this.slots);
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart(){
        return true;
    }

    /**
     * Sets all of the relevant variables
     */
    @Override
    public void saveDataOnChangeOrWorldStart(){
        if(this.sideToPull != -1){
            EnumFacing side = WorldUtil.getDirectionBySidesInOrder(this.sideToPull);
            this.placeToPull = this.worldObj.getTileEntity(this.pos.offset(side));

            if(this.slotToPullEnd <= 0 && this.placeToPull != null){
                if(this.placeToPull instanceof IInventory){
                    this.slotToPullEnd = ((IInventory)this.placeToPull).getSizeInventory();
                }
                else{
                    if(this.placeToPull.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)){
                        IItemHandler cap = this.placeToPull.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                        if(cap != null){
                            this.slotToPullEnd = cap.getSlots();
                        }
                    }
                }
            }
        }

        if(this.sideToPut != -1){
            EnumFacing side = WorldUtil.getDirectionBySidesInOrder(this.sideToPut);
            this.placeToPut = this.worldObj.getTileEntity(this.pos.offset(side));

            if(this.slotToPutEnd <= 0 && this.placeToPut != null){
                if(this.placeToPut instanceof IInventory){
                    this.slotToPutEnd = ((IInventory)this.placeToPut).getSizeInventory();
                }
                else{
                    if(this.placeToPut.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)){
                        IItemHandler cap = this.placeToPut.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                        if(cap != null){
                            this.slotToPutEnd = cap.getSlots();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        this.leftFilter.onButtonPressed(buttonID);
        this.rightFilter.onButtonPressed(buttonID);

        //Reset the Slots
        if(buttonID == 0 || buttonID == 1){
            this.slotToPutStart = 0;
            this.slotToPutEnd = 0;
        }
        if(buttonID == 2 || buttonID == 3){
            this.slotToPullStart = 0;
            this.slotToPullEnd = 0;
        }

        if(buttonID == 0){
            this.sideToPut++;
        }
        if(buttonID == 1){
            this.sideToPut--;
        }

        if(buttonID == 2){
            this.sideToPull++;
        }
        if(buttonID == 3){
            this.sideToPull--;
        }

        if(this.sideToPut >= 6){
            this.sideToPut = -1;
        }
        else if(this.sideToPut < -1){
            this.sideToPut = 5;
        }
        else if(this.sideToPull >= 6){
            this.sideToPull = -1;
        }
        else if(this.sideToPull < -1){
            this.sideToPull = 5;
        }

        this.markDirty();
        this.saveDataOnChangeOrWorldStart();
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("SideToPut", this.sideToPut);
            compound.setInteger("SlotToPut", this.slotToPutStart);
            compound.setInteger("SlotToPutEnd", this.slotToPutEnd);
            compound.setInteger("SideToPull", this.sideToPull);
            compound.setInteger("SlotToPull", this.slotToPullStart);
            compound.setInteger("SlotToPullEnd", this.slotToPullEnd);

            this.leftFilter.writeToNBT(compound, "LeftFilter");
            this.rightFilter.writeToNBT(compound, "RightFilter");
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            this.sideToPut = compound.getInteger("SideToPut");
            this.slotToPutStart = compound.getInteger("SlotToPut");
            this.slotToPutEnd = compound.getInteger("SlotToPutEnd");
            this.sideToPull = compound.getInteger("SideToPull");
            this.slotToPullStart = compound.getInteger("SlotToPull");
            this.slotToPullEnd = compound.getInteger("SlotToPullEnd");

            this.leftFilter.readFromNBT(compound, "LeftFilter");
            this.rightFilter.readFromNBT(compound, "RightFilter");
        }
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){

            //Is Block not powered by Redstone?
            if(!this.isRedstonePowered){
                if(!(this.sideToPull == this.sideToPut && this.slotToPullStart == this.slotToPutStart && this.slotToPullEnd == this.slotToPutEnd)){
                    if(this.sideToPull != -1 && this.placeToPull != null){
                        this.pull();
                    }
                    if(this.slots[0] != null && this.sideToPut != -1 && this.placeToPut != null){
                        this.put();
                    }
                }
            }

            //Update the Client
            if((this.sideToPut != this.lastPutSide || this.sideToPull != this.lastPullSide || this.slotToPullStart != this.lastPullStart || this.slotToPullEnd != this.lastPullEnd || this.slotToPutStart != this.lastPutStart || this.slotToPutEnd != this.lastPutEnd || this.leftFilter.needsUpdateSend() || this.rightFilter.needsUpdateSend()) && this.sendUpdateWithInterval()){
                this.lastPutSide = this.sideToPut;
                this.lastPullSide = this.sideToPull;
                this.lastPullStart = this.slotToPullStart;
                this.lastPullEnd = this.slotToPullEnd;
                this.lastPutStart = this.slotToPutStart;
                this.lastPutEnd = this.slotToPutEnd;
                this.leftFilter.updateLasts();
                this.rightFilter.updateLasts();
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return i == 0;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return slot == 0;
    }
}