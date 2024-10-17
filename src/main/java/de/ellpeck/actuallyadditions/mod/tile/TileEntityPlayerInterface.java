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

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.PlayerInvWrapper;

import java.util.Optional;
import java.util.UUID;

public class TileEntityPlayerInterface extends TileEntityBase implements IEnergyDisplay {

    public static final int DEFAULT_RANGE = 32;
    private final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 50, 0);
    public UUID connectedPlayer;
    public String playerName;
    private IItemHandler playerHandler;
    private Player oldPlayer;
    private int oldEnergy;
    private int range;

    public TileEntityPlayerInterface(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.PLAYER_INTERFACE.getTileEntityType(), pos, state);
    }

    private Player getPlayer() {
        if (this.connectedPlayer != null && this.level != null) {
            Player player = this.level.getPlayerByUUID(this.connectedPlayer);
            if (player != null) {
                if (player.distanceToSqr(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ()) <= this.range) {
                    return player;
                }
            }
        }
        return null;
    }

    @Override
    public IItemHandler getItemHandler(Direction facing) {
        Player player = this.getPlayer();

        if (this.oldPlayer != player) {
            this.oldPlayer = player;

            this.playerHandler = player == null
                ? null
                : new PlayerInvWrapper(player.getInventory());

            this.invalidateCapabilities();
        }

        return this.playerHandler;
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPlayerInterface tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPlayerInterface tile) {
            tile.serverTick();

            boolean changed = false;

            tile.range = TileEntityPhantomface.upgradeRange(DEFAULT_RANGE, level, pos);

            Player player = tile.getPlayer();
            if (player != null) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    if (tile.storage.getEnergyStored() > 0) {
                        ItemStack slot = player.getInventory().getItem(i);
                        if (!slot.isEmpty() && slot.getCount() == 1) {

                            int received = Optional.ofNullable(slot.getCapability(Capabilities.EnergyStorage.ITEM))
                                    .map(cap -> cap.receiveEnergy(tile.storage.getEnergyStored(), false)).orElse(0);
                            if (received > 0) {
                                tile.storage.extractEnergyInternal(received, false);
                            }
                        }
                    } else {
                        break;
                    }
                }
            }

            if (changed) {
                tile.setChanged();
                tile.sendUpdate();
            }

            if (tile.storage.getEnergyStored() != tile.oldEnergy && tile.sendUpdateWithInterval()) {
                tile.oldEnergy = tile.storage.getEnergyStored();
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.writeSyncableNBT(compound, lookupProvider, type);

        this.storage.writeToNBT(compound);
        if (this.connectedPlayer != null && type != NBTType.SAVE_BLOCK) {
            compound.putUUID("Player", this.connectedPlayer);
            compound.putString("PlayerName", this.playerName);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.readSyncableNBT(compound, lookupProvider, type);

        this.storage.readFromNBT(compound);
        if (compound.contains("Player") && type != NBTType.SAVE_BLOCK) {
            this.connectedPlayer = compound.getUUID("Player");
            this.playerName = compound.getString("PlayerName");
        }
    }

    @Override
    public CustomEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    public boolean needsHoldShift() {
        return false;
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }
}
