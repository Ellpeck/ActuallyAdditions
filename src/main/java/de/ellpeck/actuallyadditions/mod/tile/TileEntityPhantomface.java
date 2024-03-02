/*
 * This file ("TileEntityPhantomface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileEntityPhantomface extends TileEntityInventoryBase implements IPhantomTile {
    public static final int RANGE = 16;
    public BlockPos boundPosition;
    public BlockPhantom.Type type;
    public int range;
    private int rangeBefore;
    private BlockPos boundPosBefore;
    private Block boundBlockBefore;
    private int lastStrength;

    public TileEntityPhantomface(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, 0);
    }


    public static int upgradeRange(int defaultRange, Level world, BlockPos pos) {
        int newRange = defaultRange;
        for (int i = 0; i < 3; i++) {
            Block block = world.getBlockState(pos.above(1 + i)).getBlock();
            if (block == ActuallyBlocks.PHANTOM_BOOSTER.get()) {
                newRange = newRange * 2;
            } else {
                break;
            }
        }
        return newRange;
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("Range", this.range);
            if (this.boundPosition != null) {
                compound.putInt("xOfTileStored", this.boundPosition.getX());
                compound.putInt("yOfTileStored", this.boundPosition.getY());
                compound.putInt("zOfTileStored", this.boundPosition.getZ());
            }
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            int x = compound.getInt("xOfTileStored");
            int y = compound.getInt("yOfTileStored");
            int z = compound.getInt("zOfTileStored");
            this.range = compound.getInt("Range");
            if (!(x == 0 && y == 0 && z == 0)) {
                this.boundPosition = new BlockPos(x, y, z);
                this.setChanged();
            }
        }
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        this.range = upgradeRange(RANGE, this.level, this.getBlockPos());

        if (!this.hasBoundPosition()) {
            this.boundPosition = null;
        }

        if (this.doesNeedUpdateSend()) {
            this.onUpdateSent();
        }

        int strength = this.getComparatorStrength();
        if (this.lastStrength != strength) {
            this.lastStrength = strength;

            this.setChanged();
        }
    }

    @Override
    protected void clientTick() {
        super.clientTick();
        if (this.boundPosition != null) {
            this.renderParticles();
        }
    }

    protected boolean doesNeedUpdateSend() {
        return this.boundPosition != this.boundPosBefore || this.boundPosition != null && this.level.getBlockState(this.boundPosition).getBlock() != this.boundBlockBefore || this.rangeBefore != this.range;
    }

    protected void onUpdateSent() {
        this.rangeBefore = this.range;
        this.boundPosBefore = this.boundPosition;
        this.boundBlockBefore = this.boundPosition == null
            ? null
            : this.level.getBlockState(this.boundPosition).getBlock();

        if (this.boundPosition != null) {
            this.level.updateNeighborsAt(this.worldPosition, this.level.getBlockState(this.boundPosition).getBlock());
        }

        this.sendUpdate();
        this.setChanged();
    }

    @Override
    public boolean hasBoundPosition() {
        if (this.boundPosition != null) {
            if (this.level.getBlockEntity(this.boundPosition) instanceof IPhantomTile || this.getBlockPos().getX() == this.boundPosition.getX() && this.getBlockPos().getY() == this.boundPosition.getY() && this.getBlockPos().getZ() == this.boundPosition.getZ()) {
                this.boundPosition = null;
                return false;
            }
            return true;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void renderParticles() {
        if (this.level.random.nextInt(2) == 0) {
            double d1 = this.boundPosition.getY() + this.level.random.nextFloat();
            int i1 = this.level.random.nextInt(2) * 2 - 1;
            int j1 = this.level.random.nextInt(2) * 2 - 1;
            double d4 = (this.level.random.nextFloat() - 0.5D) * 0.125D;
            double d2 = this.boundPosition.getZ() + 0.5D + 0.25D * j1;
            double d5 = this.level.random.nextFloat() * 1.0F * j1;
            double d0 = this.boundPosition.getX() + 0.5D + 0.25D * i1;
            double d3 = this.level.random.nextFloat() * 1.0F * i1;
            this.level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public boolean isBoundThingInRange() {
        return this.hasBoundPosition() && this.boundPosition.distSqr(this.getBlockPos()) <= this.range * this.range;
    }

    @Override
    public BlockPos getBoundPosition() {
        return this.boundPosition;
    }

    @Override
    public void setBoundPosition(BlockPos pos) {
        this.boundPosition = pos;
    }

    //@Override
    public int getGuiID() {
        return -1;
    }

    @Override
    public int getRange() {
        return this.range;
    }

    protected abstract boolean isCapabilitySupported(Capability<?> capability);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (this.isBoundThingInRange() && this.isCapabilitySupported(capability)) {
            BlockEntity tile = this.level.getBlockEntity(this.getBoundPosition());
            if (tile != null) {
                return tile.getCapability(capability, side);
            }
        }

        return LazyOptional.empty();
    }

    @Override
    public int getComparatorStrength() {
        if (this.isBoundThingInRange()) {
            BlockPos pos = this.getBoundPosition();
            BlockState state = this.level.getBlockState(pos);

            if (state.hasAnalogOutputSignal()) {
                return state.getAnalogOutputSignal(this.level, pos);
            }
        }
        return 0;
    }
}
