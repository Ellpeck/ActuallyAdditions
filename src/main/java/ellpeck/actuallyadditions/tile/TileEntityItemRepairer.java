/*
 * This file ("TileEntityItemRepairer.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.network.sync.IPacketSyncerToClient;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityItemRepairer extends TileEntityInventoryBase implements IEnergyReceiver, IPacketSyncerToClient{

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;

    public EnergyStorage storage = new EnergyStorage(300000);
    private int lastEnergy;

    public int nextRepairTick;

    public TileEntityItemRepairer(){
        super(2, "repairer");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(this.slots[SLOT_OUTPUT] == null && canBeRepaired(this.slots[SLOT_INPUT])){
                if(this.slots[SLOT_INPUT].getItemDamage() <= 0){
                    this.slots[SLOT_OUTPUT] = this.slots[SLOT_INPUT].copy();
                    this.slots[SLOT_INPUT] = null;
                    this.nextRepairTick = 0;
                }
                else{
                    if(this.storage.getEnergyStored() >= ConfigIntValues.REPAIRER_ENERGY_USED.getValue()){
                        this.nextRepairTick++;
                        this.storage.extractEnergy(ConfigIntValues.REPAIRER_ENERGY_USED.getValue(), false);
                        if(this.nextRepairTick >= ConfigIntValues.REPAIRER_SPEED_SLOWDOWN.getValue()){
                            this.nextRepairTick = 0;
                            this.slots[SLOT_INPUT].setItemDamage(this.slots[SLOT_INPUT].getItemDamage() - 1);
                        }
                    }
                }
            }
            else this.nextRepairTick = 0;

            if(this.lastEnergy != this.storage.getEnergyStored()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.sendUpdate();
            }
        }
    }

    public static boolean canBeRepaired(ItemStack stack){
        return stack != null && stack.getItem().isRepairable();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        compound.setInteger("NextRepairTick", this.nextRepairTick);
        super.writeToNBT(compound);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.nextRepairTick = compound.getInteger("NextRepairTick");
        super.readFromNBT(compound);
        this.storage.readFromNBT(compound);
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getItemDamageToScale(int i){
        if(this.slots[SLOT_INPUT] != null){
            return (this.slots[SLOT_INPUT].getMaxDamage()-this.slots[SLOT_INPUT].getItemDamage()) * i / this.slots[SLOT_INPUT].getMaxDamage();
        }
        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return i == SLOT_INPUT;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }

    @Override
    public int[] getValues(){
        return new int[]{this.storage.getEnergyStored()};
    }

    @Override
    public void setValues(int[] values){
        this.storage.setEnergyStored(values[0]);
    }

    @Override
    public void sendUpdate(){
        PacketSyncerToClient.sendPacket(this);
    }
}
