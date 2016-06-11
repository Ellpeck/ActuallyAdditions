/*
 * This file ("TileEntityLaserRelayItemWhitelist.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFilter;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.items.ItemFilter;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class TileEntityLaserRelayItemWhitelist extends TileEntityLaserRelayItem implements IButtonReactor{

    public final IInventory filterInventory;
    public boolean isLeftWhitelist;
    public boolean isRightWhitelist;
    private ItemStack[] slots = new ItemStack[24];
    private boolean lastLeftWhitelist;
    private boolean lastRightWhitelist;

    public TileEntityLaserRelayItemWhitelist(){
        super("laserRelayItemWhitelist");

        this.filterInventory = new IInventory(){

            private TileEntityLaserRelayItemWhitelist tile;

            private IInventory setTile(TileEntityLaserRelayItemWhitelist tile){
                this.tile = tile;
                return this;
            }


            @Override
            public String getName(){
                return this.tile.name;
            }

            @Override
            public int getInventoryStackLimit(){
                return 64;
            }

            @Override
            public void markDirty(){

            }

            @Override
            public boolean isUseableByPlayer(EntityPlayer player){
                return this.tile.canPlayerUse(player);
            }

            @Override
            public void openInventory(EntityPlayer player){

            }

            @Override
            public void closeInventory(EntityPlayer player){

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
                int length = this.tile.slots.length;
                this.tile.slots = new ItemStack[length];
            }

            @Override
            public void setInventorySlotContents(int i, ItemStack stack){
                this.tile.slots[i] = stack;
                this.markDirty();
            }

            @Override
            public int getSizeInventory(){
                return this.tile.slots.length;
            }

            @Override
            public ItemStack getStackInSlot(int i){
                if(i < this.getSizeInventory()){
                    return this.tile.slots[i];
                }
                return null;
            }

            @Override
            public ItemStack decrStackSize(int i, int j){
                if(this.tile.slots[i] != null){
                    ItemStack stackAt;
                    if(this.tile.slots[i].stackSize <= j){
                        stackAt = this.tile.slots[i];
                        this.tile.slots[i] = null;
                        this.markDirty();
                        return stackAt;
                    }
                    else{
                        stackAt = this.tile.slots[i].splitStack(j);
                        if(this.tile.slots[i].stackSize <= 0){
                            this.tile.slots[i] = null;
                        }
                        this.markDirty();
                        return stackAt;
                    }
                }
                return null;
            }

            @Override
            public ItemStack removeStackFromSlot(int index){
                ItemStack stack = this.tile.slots[index];
                this.tile.slots[index] = null;
                return stack;
            }

            @Override
            public boolean hasCustomName(){
                return false;
            }


            @Override
            public ITextComponent getDisplayName(){
                return new TextComponentTranslation(this.getName());
            }

            @Override
            public boolean isItemValidForSlot(int index, ItemStack stack){
                return false;
            }
        }.setTile(this);
    }

    @Override
    public boolean isWhitelisted(ItemStack stack, boolean output){
        int slotStart = output ? 12 : 0;
        return checkFilter(stack, output ? this.isRightWhitelist : this.isLeftWhitelist, this.slots, slotStart, slotStart+12);
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.writeSyncableNBT(compound, isForSync);
        if(!isForSync){
            TileEntityInventoryBase.saveSlots(this.slots, compound);
        }
        compound.setBoolean("LeftWhitelist", this.isLeftWhitelist);
        compound.setBoolean("RightWhitelist", this.isRightWhitelist);
    }

    public static boolean checkFilter(ItemStack stack, boolean isWhitelist, ItemStack[] slots, int start, int end){
        if(stack != null){
            for(int i = start; i < end; i++){
                if(slots[i] != null){
                    if(slots[i].isItemEqual(stack)){
                        return isWhitelist;
                    }
                    else if(slots[i].getItem() instanceof ItemFilter){
                        ItemStack[] filterSlots = new ItemStack[ContainerFilter.SLOT_AMOUNT];
                        ItemDrill.loadSlotsFromNBT(filterSlots, slots[i]);
                        if(filterSlots != null && filterSlots.length > 0){
                            for(ItemStack filterSlot : filterSlots){
                                if(filterSlot != null && filterSlot.isItemEqual(stack)){
                                    return isWhitelist;
                                }
                            }
                        }
                    }
                }
            }
        }
        return !isWhitelist;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.readSyncableNBT(compound, isForSync);
        if(!isForSync){
            TileEntityInventoryBase.loadSlots(this.slots, compound);
        }
        this.isLeftWhitelist = compound.getBoolean("LeftWhitelist");
        this.isRightWhitelist = compound.getBoolean("RightWhitelist");
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == 0){
            this.isLeftWhitelist = !this.isLeftWhitelist;
        }
        else if(buttonID == 1){
            this.isRightWhitelist = !this.isRightWhitelist;
        }
        else if(buttonID == 2){
            this.addWhitelistSmart(false);
        }
        else if(buttonID == 3){
            this.addWhitelistSmart(true);
        }
    }

    private void addWhitelistSmart(boolean output){
        int slotStart = output ? 12 : 0;
        int slotStop = slotStart+12;

        List<IItemHandler> handlers = this.handlersAround;
        for(IItemHandler handler : handlers){
            for(int i = 0; i < handler.getSlots(); i++){
                ItemStack stack = handler.getStackInSlot(i);
                if(stack != null){
                    ItemStack copy = stack.copy();
                    copy.stackSize = 1;

                    if(!checkFilter(copy, true, this.slots, slotStart, slotStop)){
                        for(int k = slotStart; k < slotStop; k++){
                            if(this.slots[k] != null){
                                if(this.slots[k].getItem() instanceof ItemFilter){
                                    ItemStack[] filterSlots = new ItemStack[ContainerFilter.SLOT_AMOUNT];
                                    ItemDrill.loadSlotsFromNBT(filterSlots, this.slots[k]);

                                    boolean did = false;
                                    if(filterSlots != null && filterSlots.length > 0){
                                        for(int j = 0; j < filterSlots.length; j++){
                                            if(filterSlots[j] == null || filterSlots[j].stackSize <= 0){
                                                filterSlots[j] = copy;
                                                did = true;
                                                break;
                                            }
                                        }
                                    }

                                    if(did){
                                        ItemDrill.writeSlotsToNBT(filterSlots, this.slots[k]);
                                        break;
                                    }
                                }
                            }
                            else{
                                this.slots[k] = copy;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            if((this.isLeftWhitelist != this.lastLeftWhitelist || this.isRightWhitelist != this.lastRightWhitelist) && this.sendUpdateWithInterval()){
                this.lastLeftWhitelist = this.isLeftWhitelist;
                this.lastRightWhitelist = this.isRightWhitelist;
            }
        }
    }
}
