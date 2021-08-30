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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityLongRangeBreaker extends TileEntityInventoryBase implements INamedContainerProvider {

    public static final int RANGE = 8;
    public static final int ENERGY_USE = 5;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(10000, 20, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int lastEnergy;
    private int currentTime;

    public TileEntityLongRangeBreaker() {
        super(ActuallyBlocks.LONG_RANGE_BREAKER.getTileEntityType(), 9);
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("CurrentTime", this.currentTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentTime = compound.getInt("CurrentTime");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (!this.isRedstonePowered && !this.isPulseMode) {
                if (this.currentTime > 0) {
                    this.currentTime--;
                    if (this.currentTime <= 0) {
                        this.doWork();
                    }
                } else {
                    this.currentTime = 15;
                }
            }

            if (this.storage.getEnergyStored() != this.lastEnergy && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
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
                    List<ItemStack> drops = Block.getDrops(breakState, (ServerWorld) this.level, coordsBlock, this.level.getBlockEntity(coordsBlock));
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
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerDirectionalBreaker(windowId, playerInventory, this);
    }
}
