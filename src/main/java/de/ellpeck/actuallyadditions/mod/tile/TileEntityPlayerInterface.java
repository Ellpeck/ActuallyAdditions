/*
 * This file ("TileEntityPlayerInterface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.util.UUID;

public class TileEntityPlayerInterface extends TileEntityInventoryBase implements IEnergyReceiver{

    public static final int DEFAULT_RANGE = 32;
    public UUID connectedPlayer;
    private EnergyStorage storage = new EnergyStorage(30000);
    private int range;

    public TileEntityPlayerInterface(){
        super(0, "playerInterface");
    }

    private EntityPlayer getPlayer(){
        if(this.connectedPlayer != null){
            EntityPlayer player = this.worldObj.getPlayerEntityByUUID(this.connectedPlayer);
            if(player != null){
                if(player.getDistance(this.pos.getX(), this.pos.getY(), this.pos.getZ()) <= this.range){
                    return player;
                }
            }
        }
        return null;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            boolean changed = false;

            this.range = TileEntityPhantomface.upgradeRange(DEFAULT_RANGE, this.worldObj, this.pos);

            EntityPlayer player = this.getPlayer();
            if(player != null){
                for(int i = 0; i < player.inventory.getSizeInventory(); i++){
                    if(this.storage.getEnergyStored() > 0){
                        ItemStack slot = player.inventory.getStackInSlot(i);
                        if(slot != null && slot.getItem() instanceof IEnergyContainerItem){
                            int received = ((IEnergyContainerItem)slot.getItem()).receiveEnergy(slot, this.storage.getEnergyStored(), false);
                            this.storage.extractEnergy(received, false);
                            changed = true;
                        }
                    }
                    else{
                        break;
                    }
                }
            }

            if(changed){
                this.markDirty();
                this.sendUpdate();
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        this.storage.writeToNBT(compound);
        if(this.connectedPlayer != null){
            compound.setUniqueId("Player", this.connectedPlayer);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        this.storage.readFromNBT(compound);
        if(compound.hasKey("PlayerLeast")){
            this.connectedPlayer = compound.getUniqueId("Player");
        }
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return true;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side){
        if(this.getPlayer() != null){
            int[] theInt = new int[this.getSizeInventory()];
            for(int i = 0; i < theInt.length; i++){
                theInt[i] = i;
            }
            return theInt;
        }
        return new int[0];
    }

    @Override
    public int getInventoryStackLimit(){
        EntityPlayer player = this.getPlayer();
        return player != null ? player.inventory.getInventoryStackLimit() : 0;
    }

    @Override
    public void clear(){
        EntityPlayer player = this.getPlayer();
        if(player != null){
            player.inventory.clear();
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack){
        EntityPlayer player = this.getPlayer();
        if(player != null){
            player.inventory.setInventorySlotContents(i, stack);
        }
    }

    @Override
    public int getSizeInventory(){
        EntityPlayer player = this.getPlayer();
        if(player != null){
            return player.inventory.getSizeInventory();
        }
        else{
            return 0;
        }
    }

    @Override
    public ItemStack getStackInSlot(int i){
        EntityPlayer player = this.getPlayer();
        if(player != null){
            return player.inventory.getStackInSlot(i);
        }
        else{
            return null;
        }
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        EntityPlayer player = this.getPlayer();
        if(player != null){
            ItemStack stack = player.inventory.decrStackSize(i, j);
            if(stack != null){
                return stack;
            }
        }
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index){
        EntityPlayer player = this.getPlayer();
        if(player != null){
            ItemStack stack = player.inventory.removeStackFromSlot(index);
            if(stack != null){
                return stack;
            }
        }
        return null;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        EntityPlayer player = this.getPlayer();
        return player != null && player.inventory.isItemValidForSlot(i, stack);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return true;
    }
}
