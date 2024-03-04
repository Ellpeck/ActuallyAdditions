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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TileEntityBatteryBox extends TileEntityInventoryBase implements ISharingEnergyProvider {

    private int lastEnergyStored;
    private int lastCompare;

    public TileEntityBatteryBox(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.BATTERY_BOX.getTileEntityType(),  pos, state, 1);
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        ItemStack stack = this.inv.getStackInSlot(0);
        if (stack.getItem() instanceof ItemBattery) {
            return stack.getCapability(Capabilities.EnergyStorage.ITEM, null);
        }
        return null;
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityBatteryBox tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityBatteryBox tile) {
            tile.serverTick();

            Optional<IEnergyStorage> cap = Optional.ofNullable(tile.getEnergyStorage(null));
            int currStorage = cap.map(storage -> {
                ItemStack stack = tile.inv.getStackInSlot(0);
                if (!stack.isEmpty() && ItemUtil.isEnabled(stack)) {
                    if (storage.getEnergyStored() > 0) {
                        List<TileEntityBatteryBox> tiles = new ArrayList<>();
                        tile.energyPushOffLoop(tile, tiles);

                        if (!tiles.isEmpty()) {
                            int amount = tiles.size();

                            int energyPer = storage.getEnergyStored() / amount;
                            if (energyPer <= 0) {
                                energyPer = storage.getEnergyStored();
                            }
                            int maxPer = storage.extractEnergy(energyPer, true);

                            for (TileEntityBatteryBox te : tiles) {
                                ItemStack battery = te.inv.getStackInSlot(0);
                                if (!battery.isEmpty() && !ItemUtil.isEnabled(battery)) {
                                    int received = Optional.ofNullable(level.getCapability(Capabilities.EnergyStorage.BLOCK, te.getBlockPos(), null)).map(e -> e.receiveEnergy(maxPer, false)).orElse(0);
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

            if (tile.lastCompare != tile.getComparatorStrength()) {
                tile.lastCompare = tile.getComparatorStrength();
                tile.setChanged();
            }

            if (tile.lastEnergyStored != currStorage && tile.sendUpdateWithInterval()) {
                tile.lastEnergyStored = currStorage;
            }
        }
    }

    @Override
    public void serverTick() {
        super.serverTick();


    }

    @Override
    public int getComparatorStrength() {
        return Optional.ofNullable(this.getEnergyStorage(null))
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
        if (!stack.isEmpty()) {
            ItemUtil.changeEnabled(stack);
            this.setChanged();
        }
    }

    private void energyPushOffLoop(TileEntityBatteryBox startTile, List<TileEntityBatteryBox> pushOffTo) {
        if (pushOffTo.size() >= 15) {
            return;
        }

        for (BlockEntity tile : startTile.tilesAround) {
            if (tile instanceof TileEntityBatteryBox box) {
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
        return Optional.ofNullable(this.getEnergyStorage(null))
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
    public boolean canShareTo(BlockEntity tile) {
        return !(tile instanceof TileEntityBatteryBox);
    }
}
