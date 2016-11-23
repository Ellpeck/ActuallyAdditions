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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.util.UUID;

public class TileEntityPlayerInterface extends TileEntityInventoryBase implements ICustomEnergyReceiver, IEnergyDisplay{

    public static final int DEFAULT_RANGE = 32;
    private final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 50);
    public UUID connectedPlayer;
    public String playerName;
    private int oldEnergy;
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
                        if(StackUtil.isValid(slot)){
                            Item item = slot.getItem();

                            int received = 0;
                            if(item instanceof IEnergyContainerItem){
                                received = ((IEnergyContainerItem)item).receiveEnergy(slot, this.storage.getEnergyStored(), false);
                            }
                            else if(ActuallyAdditions.teslaLoaded && slot.hasCapability(TeslaUtil.teslaConsumer, null)){
                                ITeslaConsumer cap = slot.getCapability(TeslaUtil.teslaConsumer, null);
                                if(cap != null){
                                    received = (int)cap.givePower(this.storage.getEnergyStored(), false);
                                }
                            }

                            if(received > 0){
                                this.storage.extractEnergyInternal(received, false);
                            }
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

            if(this.storage.getEnergyStored() != this.oldEnergy && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);

        this.storage.writeToNBT(compound);
        if(this.connectedPlayer != null && type != NBTType.SAVE_BLOCK){
            compound.setUniqueId("Player", this.connectedPlayer);
            compound.setString("PlayerName", this.playerName);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);

        this.storage.readFromNBT(compound);
        if(compound.hasKey("PlayerLeast") && type != NBTType.SAVE_BLOCK){
            this.connectedPlayer = compound.getUniqueId("Player");
            this.playerName = compound.getString("PlayerName");
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
            return player.inventory.decrStackSize(i, j);
        }
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index){
        EntityPlayer player = this.getPlayer();
        if(player != null){
            return player.inventory.removeStackFromSlot(index);
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

    @Override
    public CustomEnergyStorage getEnergyStorage(){
        return this.storage;
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }
}
