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

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ItemBattery;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class TileEntityBatteryBox extends TileEntityInventoryBase implements ISharingEnergyProvider {

    private int lastEnergyStored;
    private int lastCompare;

    public TileEntityBatteryBox() {
        super(ActuallyBlocks.BATTERY_BOX.getTileEntityType(),  1);
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        ItemStack stack = this.inv.getStackInSlot(0);
        if (StackUtil.isValid(stack) && stack.getItem() instanceof ItemBattery) {
            return stack.getCapability(CapabilityEnergy.ENERGY, null);
        }
        return LazyOptional.empty();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.level.isClientSide) {
            LazyOptional<IEnergyStorage> cap = this.getEnergyStorage(null);
            int currStorage = cap.map(storage -> {
                ItemStack stack = this.inv.getStackInSlot(0);
                if (StackUtil.isValid(stack) && ItemUtil.isEnabled(stack)) {
                    if (storage.getEnergyStored() > 0) {
                        List<TileEntityBatteryBox> tiles = new ArrayList<>();
                        this.energyPushOffLoop(this, tiles);

                        if (!tiles.isEmpty()) {
                            int amount = tiles.size();

                            int energyPer = storage.getEnergyStored() / amount;
                            if (energyPer <= 0) {
                                energyPer = storage.getEnergyStored();
                            }
                            int maxPer = storage.extractEnergy(energyPer, true);

                            for (TileEntityBatteryBox tile : tiles) {
                                ItemStack battery = tile.inv.getStackInSlot(0);
                                if (StackUtil.isValid(battery) && !ItemUtil.isEnabled(battery)) {
                                    int received = tile.getCapability(CapabilityEnergy.ENERGY, null).map(e -> e.receiveEnergy(maxPer, false)).orElse(0);
                                    storage.extractEnergy(received, false);

                                    if (storage.getEnergyStored() <= 0) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                return storage.getEnergyStored();
            }).orElse(0);

            if (this.lastCompare != this.getComparatorStrength()) {
                this.lastCompare = this.getComparatorStrength();
                this.setChanged();
            }

            if (this.lastEnergyStored != currStorage && this.sendUpdateWithInterval()) {
                this.lastEnergyStored = currStorage;
            }
        }
    }

    @Override
    public int getComparatorStrength() {
        return this.getEnergyStorage(null)
            .map(cap -> (int) ((float) cap.getEnergyStored() / (float) cap.getMaxEnergyStored() * 15F))
            .orElse(0);
    }

    @Override
    public boolean respondsToPulses() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        ItemStack stack = this.inv.getStackInSlot(0);
        if (StackUtil.isValid(stack)) {
            ItemUtil.changeEnabled(stack);
            this.setChanged();
        }
    }

    private void energyPushOffLoop(TileEntityBatteryBox startTile, List<TileEntityBatteryBox> pushOffTo) {
        if (pushOffTo.size() >= 15) {
            return;
        }

        for (TileEntity tile : startTile.tilesAround) {
            if (tile instanceof TileEntityBatteryBox) {
                TileEntityBatteryBox box = (TileEntityBatteryBox) tile;
                if (!pushOffTo.contains(box)) {
                    pushOffTo.add(box);

                    this.energyPushOffLoop(box, pushOffTo);
                }
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> stack.getItem() instanceof ItemBattery;
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public int getEnergyToSplitShare() {
        return this.getEnergyStorage(null)
            .map(IEnergyStorage::getEnergyStored)
            .orElse(0);
    }

    @Override
    public boolean doesShareEnergy() {
        return true;
    }

    @Override
    public Direction[] getEnergyShareSides() {
        return Direction.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile) {
        return !(tile instanceof TileEntityBatteryBox);
    }
}
