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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import java.util.HashMap;
import java.util.Map;

public abstract class TileEntityInventoryBase extends TileEntityBase implements ISidedInventory{

    public ItemStack slots[];
    public String name;

    private Map<EnumFacing, IItemHandler> itemHandlers = new HashMap<EnumFacing, IItemHandler>();

    public TileEntityInventoryBase(int slots, String name){
        this.initializeSlots(slots);
        this.name = "container."+ModUtil.MOD_ID_LOWER+"."+name;

        for(EnumFacing facing : EnumFacing.values()){
            this.itemHandlers.put(facing, new SidedInvWrapper(this, facing));
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
                for(int currentIndex = 0; currentIndex < slots.length; currentIndex++){
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte)currentIndex);
                    if(slots[currentIndex] != null){
                        slots[currentIndex].writeToNBT(tagCompound);
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
                    if(slotIndex >= 0 && slotIndex < slots.length){
                        slots[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
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
            int[] theInt = new int[slots.length];
            for(int i = 0; i < theInt.length; i++){
                theInt[i] = i;
            }
            return theInt;
        }
        else{
            return new int[0];
        }
    }    @Override
    public int getInventoryStackLimit(){
        return 64;
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
        return this.getCapability(capability, facing) != null;
    }    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        return player.getDistanceSq(this.getPos().getX()+0.5D, this.pos.getY()+0.5D, this.pos.getZ()+0.5D) <= 64 && !this.isInvalid() && this.worldObj.getTileEntity(this.pos) == this;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)this.itemHandlers.get(facing);
        }
        else{
            return super.getCapability(capability, facing);
        }
    }    @Override
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
        return slots.length;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        if(i < this.getSizeInventory()){
            return slots[i];
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        if(slots[i] != null){
            ItemStack stackAt;
            if(slots[i].stackSize <= j){
                stackAt = slots[i];
                slots[i] = null;
                this.markDirty();
                return stackAt;
            }
            else{
                stackAt = slots[i].splitStack(j);
                if(slots[i].stackSize == 0){
                    slots[i] = null;
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
    public IChatComponent getDisplayName(){
        return new ChatComponentText(StringUtil.localize(this.getName()));
    }





}
