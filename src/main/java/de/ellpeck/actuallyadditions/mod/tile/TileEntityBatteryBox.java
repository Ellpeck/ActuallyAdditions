/*
 * This file ("TileEntityBatteryBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.items.ItemBattery;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class TileEntityBatteryBox extends TileEntityInventoryBase implements ISharingEnergyProvider{

    private int lastEnergyStored;
    private int lastCompare;

    public TileEntityBatteryBox(){
        super(1, "batteryBox");
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        ItemStack stack = this.slots.getStackInSlot(0);
        if(StackUtil.isValid(stack) && stack.getItem() instanceof ItemBattery){
            if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
                return stack.getCapability(CapabilityEnergy.ENERGY, null);
            }
        }
        return null;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.world.isRemote){
            int currStorage = 0;

            IEnergyStorage storage = this.getEnergyStorage(null);
            if(storage != null){
                ItemStack stack = this.slots.getStackInSlot(0);
                if(StackUtil.isValid(stack) && ItemUtil.isEnabled(stack)){
                    if(storage.getEnergyStored() > 0){
                        List<TileEntityBatteryBox> tiles = new ArrayList<TileEntityBatteryBox>();
                        this.energyPushOffLoop(this, tiles);

                        if(!tiles.isEmpty()){
                            int amount = tiles.size();

                            int energyPer = storage.getEnergyStored()/amount;
                            if(energyPer <= 0){
                                energyPer = storage.getEnergyStored();
                            }
                            int maxPer = storage.extractEnergy(energyPer, true);

                            for(TileEntityBatteryBox tile : tiles){
                                ItemStack battery = tile.slots.getStackInSlot(0);
                                if(StackUtil.isValid(battery) && !ItemUtil.isEnabled(battery)){
                                    if(tile.hasCapability(CapabilityEnergy.ENERGY, null)){
                                        IEnergyStorage cap = tile.getCapability(CapabilityEnergy.ENERGY, null);
                                        if(cap != null){
                                            int received = cap.receiveEnergy(maxPer, false);
                                            storage.extractEnergy(received, false);

                                            if(storage.getEnergyStored() <= 0){
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                currStorage = storage.getEnergyStored();
            }

            if(this.lastCompare != this.getComparatorStrength()){
                this.lastCompare = this.getComparatorStrength();
                this.markDirty();
            }

            if(this.lastEnergyStored != currStorage && this.sendUpdateWithInterval()){
                this.lastEnergyStored = currStorage;
            }
        }
    }

    @Override
    public int getComparatorStrength(){
        IEnergyStorage storage = this.getEnergyStorage(null);
        if(storage != null){
            float calc = ((float)storage.getEnergyStored()/(float)storage.getMaxEnergyStored())*15F;
            return (int)calc;
        }
        else{
            return 0;
        }
    }

    @Override
    public boolean respondsToPulses(){
        return true;
    }

    @Override
    public void activateOnPulse(){
        ItemStack stack = this.slots.getStackInSlot(0);
        if(StackUtil.isValid(stack)){
            ItemUtil.changeEnabled(stack);
            this.markDirty();
        }
    }

    private void energyPushOffLoop(TileEntityBatteryBox startTile, List<TileEntityBatteryBox> pushOffTo){
        if(pushOffTo.size() >= 15){
            return;
        }

        for(TileEntity tile : startTile.tilesAround){
            if(tile instanceof TileEntityBatteryBox){
                TileEntityBatteryBox box = (TileEntityBatteryBox)tile;
                if(!pushOffTo.contains(box)){
                    pushOffTo.add(box);

                    this.energyPushOffLoop(box, pushOffTo);
                }
            }
        }
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack){
        return StackUtil.isValid(stack) && stack.getItem() instanceof ItemBattery;
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public int getEnergyToSplitShare(){
        IEnergyStorage storage = this.getEnergyStorage(null);
        if(storage != null){
            return storage.getEnergyStored();
        }
        else{
            return 0;
        }
    }

    @Override
    public boolean doesShareEnergy(){
        return true;
    }

    @Override
    public EnumFacing[] getEnergyShareSides(){
        return EnumFacing.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile){
        return !(tile instanceof TileEntityBatteryBox);
    }
}
