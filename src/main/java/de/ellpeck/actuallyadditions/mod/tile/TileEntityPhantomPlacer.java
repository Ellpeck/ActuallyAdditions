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
import de.ellpeck.actuallyadditions.mod.inventory.ContainerPhantomPlacer;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityPhantomPlacer extends TileEntityInventoryBase implements IPhantomTile, IButtonReactor, INamedContainerProvider {

    public static final int RANGE = 3;
    public BlockPos boundPosition;
    public int currentTime;
    public int range;
    public boolean isBreaker;
    public int side;
    private int oldRange;

    public TileEntityPhantomPlacer(TileEntityType<?> type, int slots) {
        super(type, slots);
    }

    public TileEntityPhantomPlacer() {
        super(ActuallyTiles.PHANTOMPLACER_TILE.get(), 9);
        this.isBreaker = false;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
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
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            int x = compound.getInt("xOfTileStored");
            int y = compound.getInt("yOfTileStored");
            int z = compound.getInt("zOfTileStored");
            this.range = compound.getInt("Range");
            if (!(x == 0 && y == 0 && z == 0)) {
                this.boundPosition = new BlockPos(x, y, z);
                this.markDirty();
            }
            if (!this.isBreaker) {
                this.side = compound.getInt("Side");
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            this.range = TileEntityPhantomface.upgradeRange(RANGE, this.world, this.pos);

            if (!this.hasBoundPosition()) {
                this.boundPosition = null;
            }

            if (this.isBoundThingInRange()) {
                if (!this.isRedstonePowered && !this.isPulseMode) {
                    if (this.currentTime > 0) {
                        this.currentTime--;
                        if (this.currentTime <= 0) {
                            this.doWork();
                        }
                    } else {
                        this.currentTime = 30;
                    }
                }
            }

            if (this.oldRange != this.range) {
                this.oldRange = this.range;

                this.sendUpdate();
            }
        } else {
            if (this.boundPosition != null) {
                this.renderParticles();
            }
        }
    }

    // TODO: [port] I have no clue what the cowboy logic is trying to do here. Confirm this still works
    @Override
    public boolean hasBoundPosition() {
        if (this.boundPosition != null) {
            if (this.world.getTileEntity(this.boundPosition) instanceof IPhantomTile || this.getPos().getX() == this.boundPosition.getX() && this.getPos().getY() == this.boundPosition.getY() && this.getPos().getZ() == this.boundPosition.getZ() && this.world.getDimensionType() == this.world.getDimensionType()) {
                this.boundPosition = null;
                return false;
            }
            return this.world.getDimensionType() == this.world.getDimensionType();
        }
        return false;
    }

    private void doWork() {
        if (this.isBoundThingInRange()) {
            if (this.isBreaker) {
                Block blockToBreak = this.world.getBlockState(this.boundPosition).getBlock();
                if (blockToBreak != null && this.world.getBlockState(this.boundPosition).getBlockHardness(this.world, this.boundPosition) > -1.0F) {
                    List<ItemStack> drops = Block.getDrops(this.world.getBlockState(this.boundPosition), (ServerWorld) this.world, this.pos, this.world.getTileEntity(this.pos));

                    if (StackUtil.canAddAll(this.inv, drops, false)) {
                        this.world.playEvent(2001, this.boundPosition, Block.getStateId(this.world.getBlockState(this.boundPosition)));
                        this.world.setBlockState(this.boundPosition, Blocks.AIR.getDefaultState());
                        StackUtil.addAll(this.inv, drops, false);
                        this.markDirty();
                    }
                }
            } else {
                int theSlot = StackUtil.findFirstFilled(this.inv);
                if (theSlot == -1) {
                    return;
                }
                this.inv.setStackInSlot(theSlot, WorldUtil.useItemAtSide(WorldUtil.getDirectionBySidesInOrder(this.side), this.world, this.boundPosition, this.inv.getStackInSlot(theSlot)));
            }
        }
    }

    public void renderParticles() {
        if (this.world.rand.nextInt(2) == 0) {
            double d1 = this.boundPosition.getY() + this.world.rand.nextFloat();
            int i1 = this.world.rand.nextInt(2) * 2 - 1;
            int j1 = this.world.rand.nextInt(2) * 2 - 1;
            double d4 = (this.world.rand.nextFloat() - 0.5D) * 0.125D;
            double d2 = this.boundPosition.getZ() + 0.5D + 0.25D * j1;
            double d5 = this.world.rand.nextFloat() * 1.0F * j1;
            double d0 = this.boundPosition.getX() + 0.5D + 0.25D * i1;
            double d3 = this.world.rand.nextFloat() * 1.0F * i1;
            this.world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public boolean isBoundThingInRange() {
        return this.hasBoundPosition() && this.boundPosition.distanceSq(this.pos) <= this.range * this.range;
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
    public int getGuiID() {
        return GuiHandler.GuiTypes.PHANTOM_PLACER.ordinal();
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
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        if (this.side + 1 >= Direction.values().length) {
            this.side = 0;
        } else {
            this.side++;
        }

        this.sendUpdate();
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerPhantomPlacer(windowId, playerInventory, this);
    }
}
