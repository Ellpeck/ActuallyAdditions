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
    private ItemStack[] slots = new ItemStack[24];
    public FilterSettings leftFilter = new FilterSettings(0, 12, true, true, false, -1000);
    public FilterSettings rightFilter = new FilterSettings(12, 24, true, true, false, -2000);

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
        return output ? this.rightFilter.check(stack, this.slots) : this.leftFilter.check(stack, this.slots);
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type == NBTType.SAVE_TILE){
            TileEntityInventoryBase.saveSlots(this.slots, compound);
        }
        if(type != NBTType.SAVE_BLOCK){
            this.leftFilter.writeToNBT(compound, "LeftFilter");
            this.rightFilter.writeToNBT(compound, "RightFilter");
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type == NBTType.SAVE_TILE){
            TileEntityInventoryBase.loadSlots(this.slots, compound);
        }
        if(type != NBTType.SAVE_BLOCK){
            this.leftFilter.readFromNBT(compound, "LeftFilter");
            this.rightFilter.readFromNBT(compound, "RightFilter");
        }
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        this.leftFilter.onButtonPressed(buttonID);
        this.rightFilter.onButtonPressed(buttonID);
        if(buttonID == 2){
            this.addWhitelistSmart(false);
        }
        else if(buttonID == 3){
            this.addWhitelistSmart(true);
        }
    }

    private void addWhitelistSmart(boolean output){
        FilterSettings usedSettings = output ? this.rightFilter : this.leftFilter;
        List<IItemHandler> handlers = this.handlersAround;
        for(IItemHandler handler : handlers){
            for(int i = 0; i < handler.getSlots(); i++){
                ItemStack stack = handler.getStackInSlot(i);
                if(stack != null){
                    ItemStack copy = stack.copy();
                    copy.stackSize = 1;

                    if(!FilterSettings.check(copy, this.slots, usedSettings.startSlot, usedSettings.endSlot, true, usedSettings.respectMeta, usedSettings.respectNBT)){
                        for(int k = usedSettings.startSlot; k < usedSettings.endSlot; k++){
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
            if((this.leftFilter.needsUpdateSend() || this.rightFilter.needsUpdateSend()) && this.sendUpdateWithInterval()){
                this.leftFilter.updateLasts();
                this.rightFilter.updateLasts();
            }
        }
    }
}
