/*
 * This file ("TileEntityDirectionalBreaker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerDirectionalBreaker;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityLongRangeBreaker extends TileEntityInventoryBase implements MenuProvider {

    public static final int RANGE = 8;
    public static final int ENERGY_USE = 5;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(10000, 20, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int lastEnergy;
    private int currentTime;

    public TileEntityLongRangeBreaker(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.LONG_RANGE_BREAKER.getTileEntityType(), pos, state, 9);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("CurrentTime", this.currentTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentTime = compound.getInt("CurrentTime");
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLongRangeBreaker tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLongRangeBreaker tile) {
            tile.serverTick();

            if (!tile.isRedstonePowered && !tile.isPulseMode) {
                if (tile.currentTime > 0) {
                    tile.currentTime--;
                    if (tile.currentTime <= 0) {
                        tile.doWork();
                    }
                } else {
                    tile.currentTime = 15;
                }
            }

            if (tile.storage.getEnergyStored() != tile.lastEnergy && tile.sendUpdateWithInterval()) {
                tile.lastEnergy = tile.storage.getEnergyStored();
            }
        }
    }

    private void doWork() {
        if (this.storage.getEnergyStored() >= ENERGY_USE * RANGE) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            Direction sideToManipulate = WorldUtil.getDirectionByPistonRotation(state);

            for (int i = 0; i < RANGE; i++) {
                BlockPos coordsBlock = this.worldPosition.relative(sideToManipulate, i + 1);
                BlockState breakState = this.level.getBlockState(coordsBlock);
                Block blockToBreak = breakState.getBlock();
                if (blockToBreak != null && !this.level.isEmptyBlock(coordsBlock) && this.level.getBlockState(coordsBlock).getDestroySpeed(this.level, coordsBlock) > -1.0F) {
                    List<ItemStack> drops = Block.getDrops(breakState, (ServerLevel) this.level, coordsBlock, this.level.getBlockEntity(coordsBlock));
                    float chance = WorldUtil.fireFakeHarvestEventsForDropChance(this, drops, this.level, coordsBlock);

                    if (chance > 0 && this.level.random.nextFloat() <= chance) {
                        if (StackUtil.canAddAll(this.inv, drops, false)) {
                            this.level.levelEvent(2001, coordsBlock, Block.getId(this.level.getBlockState(coordsBlock)));
                            this.level.setBlockAndUpdate(coordsBlock, Blocks.AIR.defaultBlockState());
                            StackUtil.addAll(this.inv, drops, false);
                            this.storage.extractEnergyInternal(ENERGY_USE, false);
                            this.setChanged();
                        }
                    }
                }
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation;
    }

    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        this.doWork();
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
        return new ContainerDirectionalBreaker(windowId, playerInventory, this);
    }
}
