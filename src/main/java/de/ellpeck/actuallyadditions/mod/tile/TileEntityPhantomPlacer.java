/*
 * This file ("TileEntityPhantomPlacer.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.inventory.ContainerPhantomPlacer;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileEntityPhantomPlacer extends TileEntityInventoryBase implements IPhantomTile, IButtonReactor, MenuProvider {

    public static final int RANGE = 3;
    public BlockPos boundPosition;
    public int currentTime;
    public int range;
    public boolean isBreaker;
    public int side;
    private int oldRange;

    public TileEntityPhantomPlacer(BlockEntityType<?> type, BlockPos pos, BlockState state, int slots) {
        super(type, pos, state, slots);
    }

    public TileEntityPhantomPlacer(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.PHANTOM_PLACER.getTileEntityType(), pos, state, 9);
        this.isBreaker = false;
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
            if (!this.isBreaker) {
                compound.putInt("Side", this.side);
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
            if (!this.isBreaker) {
                this.side = compound.getInt("Side");
            }
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomPlacer tile) {
            tile.clientTick();

            if (tile.boundPosition != null) {
                tile.renderParticles();
            }
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomPlacer tile) {
            tile.serverTick();

            tile.range = TileEntityPhantomface.upgradeRange(RANGE, tile.level, tile.worldPosition);

            if (!tile.hasBoundPosition()) {
                tile.boundPosition = null;
            }

            if (tile.isBoundThingInRange()) {
                if (!tile.isRedstonePowered && !tile.isPulseMode) {
                    if (tile.currentTime > 0) {
                        tile.currentTime--;
                        if (tile.currentTime <= 0) {
                            tile.doWork();
                        }
                    } else {
                        tile.currentTime = 30;
                    }
                }
            }

            if (tile.oldRange != tile.range) {
                tile.oldRange = tile.range;

                tile.sendUpdate();
            }
        }
    }

    // TODO: [port] I have no clue what the cowboy logic is trying to do here. Confirm this still works
    @Override
    public boolean hasBoundPosition() {
        if (this.boundPosition != null) {
            if (this.level.getBlockEntity(this.boundPosition) instanceof IPhantomTile || this.getBlockPos().getX() == this.boundPosition.getX() && this.getBlockPos().getY() == this.boundPosition.getY() && this.getBlockPos().getZ() == this.boundPosition.getZ() && this.level.dimensionType() == this.level.dimensionType()) {
                this.boundPosition = null;
                return false;
            }
            return this.level.dimensionType() == this.level.dimensionType();
        }
        return false;
    }

    private void doWork() {
        if (this.isBoundThingInRange()) {
            if (this.isBreaker) {
                Block blockToBreak = this.level.getBlockState(this.boundPosition).getBlock();
                if (blockToBreak != null && this.level.getBlockState(this.boundPosition).getDestroySpeed(this.level, this.boundPosition) > -1.0F) {
                    List<ItemStack> drops = Block.getDrops(this.level.getBlockState(this.boundPosition), (ServerLevel) this.level, this.worldPosition, this.level.getBlockEntity(this.worldPosition));

                    if (StackUtil.canAddAll(this.inv, drops, false)) {
                        this.level.levelEvent(2001, this.boundPosition, Block.getId(this.level.getBlockState(this.boundPosition)));
                        this.level.setBlockAndUpdate(this.boundPosition, Blocks.AIR.defaultBlockState());
                        StackUtil.addAll(this.inv, drops, false);
                        this.setChanged();
                    }
                }
            } else {
                int theSlot = StackUtil.findFirstFilled(this.inv);
                if (theSlot == -1) {
                    return;
                }
                this.inv.setStackInSlot(theSlot, WorldUtil.useItemAtSide(WorldUtil.getDirectionBySidesInOrder(this.side), this.level, this.boundPosition, this.inv.getStackInSlot(theSlot)));
            }
        }
    }

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
        return this.hasBoundPosition() && this.boundPosition.distSqr(this.worldPosition) <= this.range * this.range;
    }

    @Override
    public BlockPos getBoundPosition() {
        return this.boundPosition;
    }

    @Override
    public void setBoundPosition(BlockPos pos) {
        this.boundPosition = pos;
    }

    @Override
    public int getRange() {
        return this.range;
    }

    @Override
    public IAcceptor getAcceptor() {
        return ItemStackHandlerAA.ACCEPT_TRUE;
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation;
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
    public void onButtonPressed(int buttonID, Player player) {
        if (this.side + 1 >= Direction.values().length) {
            this.side = 0;
        } else {
            this.side++;
        }

        this.sendUpdate();
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.phantomPlacer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerPhantomPlacer(windowId, playerInventory, this);
    }
}
