/*
 * This file ("TileEntityPlayerInterface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

import java.util.UUID;

public class TileEntityPlayerInterface extends TileEntityBase implements IEnergyDisplay{

    public static final int DEFAULT_RANGE = 32;
    private final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 50, 0);
    public UUID connectedPlayer;
    public String playerName;
    private IItemHandler playerHandler;
    private EntityPlayer oldPlayer;
    private int oldEnergy;
    private int range;

    public TileEntityPlayerInterface(){
        super("playerInterface");
    }

    private EntityPlayer getPlayer(){
        if(this.connectedPlayer != null){
            EntityPlayer player = this.world.getPlayerEntityByUUID(this.connectedPlayer);
            if(player != null){
                if(player.getDistance(this.pos.getX(), this.pos.getY(), this.pos.getZ()) <= this.range){
                    return player;
                }
            }
        }
        return null;
    }

    @Override
    public IItemHandler getItemHandler(EnumFacing facing){
        EntityPlayer player = this.getPlayer();

        if(this.oldPlayer != player){
            this.oldPlayer = player;

            this.playerHandler = player == null ? null : new PlayerInvWrapper(player.inventory);
        }

        return this.playerHandler;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            boolean changed = false;

            this.range = TileEntityPhantomface.upgradeRange(DEFAULT_RANGE, this.world, this.pos);

            EntityPlayer player = this.getPlayer();
            if(player != null){
                for(int i = 0; i < player.inventory.getSizeInventory(); i++){
                    if(this.storage.getEnergyStored() > 0){
                        ItemStack slot = player.inventory.getStackInSlot(i);
                        if(StackUtil.isValid(slot)){

                            int received = 0;
                            if(slot.hasCapability(CapabilityEnergy.ENERGY, null)){
                                IEnergyStorage cap = slot.getCapability(CapabilityEnergy.ENERGY, null);
                                if(cap != null){
                                    received = cap.receiveEnergy(this.storage.getEnergyStored(), false);
                                }
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
    public CustomEnergyStorage getEnergyStorage(){
        return this.storage;
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
