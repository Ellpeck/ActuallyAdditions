/*
 * This file ("TileEntityBreaker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBreaker;
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
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.IFluidBlock;

import javax.annotation.Nullable;
import java.util.List;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityBreaker extends TileEntityInventoryBase implements INamedContainerProvider {

    public boolean isPlacer;
    private int currentTime;

    public TileEntityBreaker(TileEntityType<?> type, int slots) {
        super(type, slots);
    }

    public TileEntityBreaker() {
        super(ActuallyBlocks.BREAKER.getTileEntityType(), 9);
        this.isPlacer = false;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("CurrentTime", this.currentTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
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
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation;
    }

    private void doWork() {
        Direction side = WorldUtil.getDirectionByPistonRotation(this.level.getBlockState(this.worldPosition));
        BlockPos breakCoords = this.worldPosition.relative(side);
        BlockState stateToBreak = this.level.getBlockState(breakCoords);
        Block blockToBreak = stateToBreak.getBlock();

        if (!this.isPlacer && blockToBreak != Blocks.AIR && !(blockToBreak instanceof IFluidBlock) && stateToBreak.getDestroySpeed(this.level, breakCoords) >= 0.0F) {
            List<ItemStack> drops = Block.getDrops(stateToBreak, (ServerWorld) this.level, breakCoords, this.level.getBlockEntity(breakCoords));
            float chance = WorldUtil.fireFakeHarvestEventsForDropChance(this, drops, this.level, breakCoords);

            if (chance > 0 && this.level.random.nextFloat() <= chance) {
                if (StackUtil.canAddAll(this.inv, drops, false)) {
                    this.level.destroyBlock(breakCoords, false);
                    StackUtil.addAll(this.inv, drops, false);
                    this.setChanged();
                }
            }
        } else if (this.isPlacer) {
            int slot = StackUtil.findFirstFilled(this.inv);
            if (slot == -1) {
                return;
            }
            this.inv.setStackInSlot(slot, WorldUtil.useItemAtSide(side, this.level, this.worldPosition, this.inv.getStackInSlot(slot)));
        }
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
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerBreaker(windowId, playerInventory, this);
    }
}
