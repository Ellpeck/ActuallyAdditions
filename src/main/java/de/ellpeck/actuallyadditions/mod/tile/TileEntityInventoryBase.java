/*
 * This file ("TileEntityInventoryBase.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public abstract class TileEntityInventoryBase extends TileEntityBase implements ISidedInventory{

    public ItemStack slots[];
    private SidedInvWrapper[] invWrappers = new SidedInvWrapper[6];

    public TileEntityInventoryBase(int slots, String name){
        super(name);

        this.initializeSlots(slots);

        for(int i = 0; i < this.invWrappers.length; i++){
            this.invWrappers[i] = new SidedInvWrapper(this, EnumFacing.values()[i]);
        }
    }

    public void initializeSlots(int itemAmount){
        this.slots = new ItemStack[itemAmount];
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.writeSyncableNBT(compound, isForSync);
        if(!isForSync || this.shouldSyncSlots()){
            if(this.slots.length > 0){
                NBTTagList tagList = new NBTTagList();
                for(int currentIndex = 0; currentIndex < this.slots.length; currentIndex++){
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte)currentIndex);
                    if(this.slots[currentIndex] != null){
                        this.slots[currentIndex].writeToNBT(tagCompound);
                    }
                    tagList.appendTag(tagCompound);
                }
                compound.setTag("Items", tagList);
            }
        }
    }

    public boolean shouldSyncSlots(){
        return false;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.readSyncableNBT(compound, isForSync);
        if(!isForSync || this.shouldSyncSlots()){
            if(this.slots.length > 0){
                NBTTagList tagList = compound.getTagList("Items", 10);
                for(int i = 0; i < tagList.tagCount(); i++){
                    NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                    byte slotIndex = tagCompound.getByte("Slot");
                    if(slotIndex >= 0 && slotIndex < this.slots.length){
                        this.slots[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
            }
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side){
        if(this.slots.length > 0){
            int[] theInt = new int[this.slots.length];
            for(int i = 0; i < theInt.length; i++){
                theInt[i] = i;
            }
            return theInt;
        }
        else{
            return new int[0];
        }
    }

    @Override
    public int getInventoryStackLimit(){
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        return this.canPlayerUse(player);
    }

    @Override
    public void openInventory(EntityPlayer player){

    }

    @Override
    public void closeInventory(EntityPlayer player){

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return false;
    }

    @Override
    public int getField(int id){
        return 0;
    }

    @Override
    public void setField(int id, int value){

    }

    @Override
    public int getFieldCount(){
        return 0;
    }

    @Override
    public void clear(){
        this.initializeSlots(this.slots.length);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack){
        this.slots[i] = stack;
        this.markDirty();
    }

    @Override
    public int getSizeInventory(){
        return this.slots.length;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        if(i < this.getSizeInventory()){
            return this.slots[i];
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        if(this.slots[i] != null){
            ItemStack stackAt;
            if(this.slots[i].stackSize <= j){
                stackAt = this.slots[i];
                this.slots[i] = null;
                this.markDirty();
                return stackAt;
            }
            else{
                stackAt = this.slots[i].splitStack(j);
                if(this.slots[i].stackSize == 0){
                    this.slots[i] = null;
                }
                this.markDirty();
                return stackAt;
            }
        }
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index){
        ItemStack stack = this.slots[index];
        this.slots[index] = null;
        return stack;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public boolean hasCustomName(){
        return false;
    }

    @Override
    public ITextComponent getDisplayName(){
        return new TextComponentString(StringUtil.localize(this.getName()));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return this.getCapability(capability, facing) != null;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(this.hasInvWrapperCapabilities() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)this.invWrappers[facing.ordinal()];
        }
        else{
            return super.getCapability(capability, facing);
        }
    }

    public boolean hasInvWrapperCapabilities(){
        return true;
    }
}
