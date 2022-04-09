/*
 * This file ("TileEntityFluidCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.fluids.AATank;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFluidCollector;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityFluidCollector extends TileEntityBase implements ISharingFluidHandler, INamedContainerProvider {

    public boolean isPlacer;
    public final AATank tank = new AATank(8 * FluidAttributes.BUCKET_VOLUME) {
        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!TileEntityFluidCollector.this.isPlacer) {
                return 0;
            }
            return super.fill(resource, action);
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (TileEntityFluidCollector.this.isPlacer) {
                return FluidStack.EMPTY;
            }
            return super.drain(maxDrain, action);
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (TileEntityFluidCollector.this.isPlacer) {
                return FluidStack.EMPTY;
            }
            return super.drain(resource, action);
        }
    };
    public final LazyOptional<IFluidHandler> lazyTank = LazyOptional.of(() -> this.tank);
    private int lastTankAmount;
    private int currentTime;
    private int lastCompare;

    public TileEntityFluidCollector(TileEntityType<?> type) {
        super(type);
    }

    public TileEntityFluidCollector() {
        this(ActuallyBlocks.FLUID_COLLECTOR.getTileEntityType());
        this.isPlacer = false;
    }

    @Override
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        this.doWork();
    }

    // TODO: [port] big old check on this entire functionality, I've not worked with fluids before
    private void doWork() {
        BlockState state = this.level.getBlockState(this.worldPosition);
        Direction sideToManipulate = WorldUtil.getDirectionByPistonRotation(state);
        BlockPos coordsBlock = this.worldPosition.relative(sideToManipulate);

        BlockState stateToBreak = this.level.getBlockState(coordsBlock);
        Block blockToBreak = stateToBreak.getBlock();
        if (!this.isPlacer && FluidAttributes.BUCKET_VOLUME <= this.tank.getCapacity() - this.tank.getFluidAmount()) {
            if (blockToBreak instanceof FlowingFluidBlock && stateToBreak.getFluidState().isSource() && ((FlowingFluidBlock) blockToBreak).getFluid() != null) {
                if (this.tank.fillInternal(new FluidStack(((FlowingFluidBlock) blockToBreak).getFluid(), FluidAttributes.BUCKET_VOLUME), IFluidHandler.FluidAction.SIMULATE) >= FluidAttributes.BUCKET_VOLUME) {
                    this.tank.fillInternal(new FluidStack(((FlowingFluidBlock) blockToBreak).getFluid(), FluidAttributes.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE);
                    this.level.setBlockAndUpdate(coordsBlock, Blocks.AIR.defaultBlockState());
                }
            }
        } else if (this.isPlacer && blockToBreak.defaultBlockState().getMaterial().isReplaceable()) {
            if (this.tank.getFluidAmount() >= FluidAttributes.BUCKET_VOLUME) {
                FluidStack stack = this.tank.getFluid();
                Block fluid = stack.getFluid().defaultFluidState().createLegacyBlock().getBlock();
                if (fluid != null) {
                    BlockPos offsetPos = this.worldPosition.relative(sideToManipulate);
                    boolean placeable = !(blockToBreak instanceof IFluidBlock) && blockToBreak.defaultBlockState().getMaterial().isReplaceable();
                    if (placeable) {
                        this.tank.drainInternal(FluidAttributes.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
                        // TODO: [port] validate this check is still valid.
                        if (this.level.dimensionType().ultraWarm() && fluid.defaultBlockState().getMaterial() == Material.WATER) {
                            this.level.playSound(null, offsetPos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.8F);

                            if (this.level instanceof ServerWorld) {
                                for (int l = 0; l < 8; ++l) {
                                    ((ServerWorld) this.level).sendParticles(ParticleTypes.SMOKE, offsetPos.getX() + Math.random(), offsetPos.getY() + Math.random(), offsetPos.getZ() + Math.random(), 1, 0.0D, 0.0D, 0.0D, 0);
                                }
                            }
                        } else {
                            this.level.setBlock(offsetPos, fluid.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.tank.getFluidAmount() / (float) this.tank.getCapacity() * 15F;
        return (int) calc;
    }

    @Override
    public LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        return this.lazyTank;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("CurrentTime", this.currentTime);
        }
        this.tank.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentTime = compound.getInt("CurrentTime");
        }
        this.tank.readFromNBT(compound);
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

            if (this.lastCompare != this.getComparatorStrength()) {
                this.lastCompare = this.getComparatorStrength();

                this.setChanged();
            }

            if (this.lastTankAmount != this.tank.getFluidAmount() && this.sendUpdateWithInterval()) {
                this.lastTankAmount = this.tank.getFluidAmount();
            }
        }
    }

    @Override
    public int getMaxFluidAmountToSplitShare() {
        return this.tank.getFluidAmount();
    }

    @Override
    public boolean doesShareFluid() {
        return !this.isPlacer;
    }

    @Override
    public Direction[] getFluidShareSides() {
        return Direction.values();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(isPlacer ? "container.actuallyadditions.fluidPlacer" : "container.actuallyadditions.fluidCollector");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerFluidCollector(windowId, playerInventory, this);
    }
}
