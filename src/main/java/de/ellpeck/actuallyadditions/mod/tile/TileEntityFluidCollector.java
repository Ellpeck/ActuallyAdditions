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
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityFluidCollector extends TileEntityBase implements ISharingFluidHandler, MenuProvider {

    public boolean isPlacer;
    public final AATank tank = new AATank(8 * FluidType.BUCKET_VOLUME) {
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
    private int lastTankAmount;
    private int currentTime;
    private int lastCompare;

    public TileEntityFluidCollector(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public TileEntityFluidCollector(BlockPos pos, BlockState state) {
        this(ActuallyBlocks.FLUID_COLLECTOR.getTileEntityType(), pos, state);
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
        if (!this.isPlacer && FluidType.BUCKET_VOLUME <= this.tank.getCapacity() - this.tank.getFluidAmount()) {
            if (blockToBreak instanceof LiquidBlock liquidBlock && liquidBlock.fluid != null && stateToBreak.getFluidState().isSource() ) {
                if (this.tank.fillInternal(new FluidStack(liquidBlock.fluid, FluidType.BUCKET_VOLUME), IFluidHandler.FluidAction.SIMULATE) >= FluidType.BUCKET_VOLUME) {
                    this.tank.fillInternal(new FluidStack(liquidBlock.fluid, FluidType.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE);
                    this.level.setBlockAndUpdate(coordsBlock, Blocks.AIR.defaultBlockState());
                }
            }
        } else if (this.isPlacer && blockToBreak.defaultBlockState().canBeReplaced()) {
            if (this.tank.getFluidAmount() >= FluidType.BUCKET_VOLUME) {
                FluidStack stack = this.tank.getFluid();
                Block fluid = stack.getFluid().defaultFluidState().createLegacyBlock().getBlock();
                if (fluid != null) {
                    BlockPos offsetPos = this.worldPosition.relative(sideToManipulate);
                    boolean placeable = !stateToBreak.getFluidState().isSource() && blockToBreak.defaultBlockState().canBeReplaced();
                    if (placeable) {
                        this.tank.drainInternal(FluidType.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
                        // TODO: [port] validate this check is still valid.
                        if (this.level.dimensionType().ultraWarm() && stack.getFluid().is(FluidTags.WATER)) {
                            this.level.playSound(null, offsetPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.8F);

                            if (this.level instanceof ServerLevel) {
                                for (int l = 0; l < 8; ++l) {
                                    ((ServerLevel) this.level).sendParticles(ParticleTypes.SMOKE, offsetPos.getX() + Math.random(), offsetPos.getY() + Math.random(), offsetPos.getZ() + Math.random(), 1, 0.0D, 0.0D, 0.0D, 0);
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
    public IFluidHandler getFluidHandler(Direction facing) {
        return this.tank;
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.writeSyncableNBT(compound, lookupProvider, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("CurrentTime", this.currentTime);
        }
        this.tank.writeToNBT(lookupProvider, compound);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.readSyncableNBT(compound, lookupProvider, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentTime = compound.getInt("CurrentTime");
        }
        this.tank.readFromNBT(lookupProvider, compound);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFluidCollector tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFluidCollector tile) {
            tile.serverTick();
        }
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!isRedstonePowered && !isPulseMode) {
            if (currentTime > 0) {
                currentTime--;
                if (currentTime <= 0) {
                    doWork();
                }
            } else {
                currentTime = 15;
            }
        }

        if (lastCompare != getComparatorStrength()) {
            lastCompare = getComparatorStrength();

            setChanged();
        }

        if (lastTankAmount != tank.getFluidAmount() && sendUpdateWithInterval()) {
            lastTankAmount = tank.getFluidAmount();
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
    public Component getDisplayName() {
        return Component.translatable(isPlacer ? "container.actuallyadditions.fluidPlacer" : "container.actuallyadditions.fluidCollector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerFluidCollector(windowId, playerInventory, this);
    }
}
