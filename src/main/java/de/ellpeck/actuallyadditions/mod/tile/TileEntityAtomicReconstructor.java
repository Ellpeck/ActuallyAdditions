/*
 * This file ("TileEntityAtomicReconstructor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityAtomicReconstructor extends TileEntityInventoryBase implements IEnergyDisplay, IAtomicReconstructor {

    public static final int ENERGY_USE = 1000;
    public final CustomEnergyStorage storage;
    public final LazyOptional<IEnergyStorage> lazyEnergy;
    public int counter;
    private int currentTime;
    private int oldEnergy;

    public TileEntityAtomicReconstructor() {
        super(ActuallyTiles.ATOMICRECONSTRUCTOR_TILE.get(), 1);
        int power = ConfigIntValues.RECONSTRUCTOR_POWER.getValue();
        int recieve = MathHelper.ceil(power * 0.016666F);
        this.storage = new CustomEnergyStorage(power, recieve, 0);
        this.lazyEnergy = LazyOptional.of(() -> this.storage);
    }

    public static void shootLaser(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, Lens currentLens) {
        world.playSound(null, startX, startY, startZ, SoundHandler.reconstructor, SoundCategory.BLOCKS, 0.35F, 1.0F);
        AssetUtil.spawnLaserWithTimeServer(world, startX, startY, startZ, endX, endY, endZ, currentLens.getColor(), 25, 0, 0.2F, 0.8F);
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("CurrentTime", this.currentTime);
            compound.putInt("Counter", this.counter);
        }
        this.storage.writeToNBT(compound);
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentTime = compound.getInt("CurrentTime");
            this.counter = compound.getInt("Counter");
        }
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (!this.isRedstonePowered && !this.isPulseMode) {
                if (this.currentTime > 0) {
                    this.currentTime--;
                    if (this.currentTime <= 0) {
                        ActuallyAdditionsAPI.methodHandler.invokeReconstructor(this);
                    }
                } else {
                    this.currentTime = 100;
                }
            }

            if (this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
                this.level.updateNeighbourForOutputSignal(this.worldPosition, ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get());
            }
        }

    }

    @Override
    public Lens getLens() {
        Item item = this.inv.getStackInSlot(0).getItem();
        if (item instanceof ILensItem) {
            return ((ILensItem) item).getLens();
        }
        return this.counter >= 500
            ? ActuallyAdditionsAPI.lensDisruption
            : ActuallyAdditionsAPI.lensDefaultConversion;
    }

    @Override
    public Direction getOrientation() {
        BlockState state = this.level.getBlockState(this.worldPosition);
        return WorldUtil.getDirectionByPistonRotation(state);
    }

    @Override
    public BlockPos getPosition() {
        return this.worldPosition;
    }

    @Override
    public int getX() {
        return this.getBlockPos().getX();
    }

    @Override
    public int getY() {
        return this.getBlockPos().getY();
    }

    @Override
    public int getZ() {
        return this.getBlockPos().getZ();
    }

    @Override
    public World getWorldObject() {
        return this.getLevel();
    }

    @Override
    public void extractEnergy(int amount) {
        this.storage.extractEnergyInternal(amount, false);
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> StackUtil.isValid(stack) && stack.getItem() instanceof ILensItem;
    }

    @Override
    public int getEnergy() {
        return this.storage.getEnergyStored();
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
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        ActuallyAdditionsAPI.methodHandler.invokeReconstructor(this);
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }
}
