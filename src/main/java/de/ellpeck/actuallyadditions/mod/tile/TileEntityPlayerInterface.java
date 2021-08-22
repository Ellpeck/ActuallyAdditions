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

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

import java.util.UUID;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityPlayerInterface extends TileEntityBase implements IEnergyDisplay {

    public static final int DEFAULT_RANGE = 32;
    private final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 50, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    public UUID connectedPlayer;
    public String playerName;
    private IItemHandler playerHandler;
    private PlayerEntity oldPlayer;
    private int oldEnergy;
    private int range;

    public TileEntityPlayerInterface() {
        super(ActuallyTiles.PLAYERINTERFACE_TILE.get());
    }

    private PlayerEntity getPlayer() {
        if (this.connectedPlayer != null && this.level != null) {
            PlayerEntity player = this.level.getPlayerByUUID(this.connectedPlayer);
            if (player != null) {
                if (player.distanceToSqr(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ()) <= this.range) {
                    return player;
                }
            }
        }
        return null;
    }

    // TODO: [port] this might not be a stable way of doing this.
    @Override
    public LazyOptional<IItemHandler> getItemHandler(Direction facing) {
        PlayerEntity player = this.getPlayer();

        if (this.oldPlayer != player) {
            this.oldPlayer = player;

            this.playerHandler = player == null
                ? null
                : new PlayerInvWrapper(player.inventory);
        }

        return LazyOptional.of(() -> this.playerHandler);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            boolean changed = false;

            this.range = TileEntityPhantomface.upgradeRange(DEFAULT_RANGE, this.level, this.worldPosition);

            PlayerEntity player = this.getPlayer();
            if (player != null) {
                for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                    if (this.storage.getEnergyStored() > 0) {
                        ItemStack slot = player.inventory.getItem(i);
                        if (StackUtil.isValid(slot) && slot.getCount() == 1) {

                            int received = slot.getCapability(CapabilityEnergy.ENERGY).map(cap -> cap.receiveEnergy(this.storage.getEnergyStored(), false)).orElse(0);
                            if (received > 0) {
                                this.storage.extractEnergyInternal(received, false);
                            }
                        }
                    } else {
                        break;
                    }
                }
            }

            if (changed) {
                this.setChanged();
                this.sendUpdate();
            }

            if (this.storage.getEnergyStored() != this.oldEnergy && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.storage.writeToNBT(compound);
        if (this.connectedPlayer != null && type != NBTType.SAVE_BLOCK) {
            compound.putUUID("Player", this.connectedPlayer);
            compound.putString("PlayerName", this.playerName);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.storage.readFromNBT(compound);
        if (compound.contains("PlayerLeast") && type != NBTType.SAVE_BLOCK) {
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
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }
}
