/*
 * This file ("TileEntityOilGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.OilGenRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntListValues;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerOilGenerator;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityOilGenerator extends TileEntityBase implements ISharingEnergyProvider, ISharingFluidHandler, INamedContainerProvider {

    int[] i = ConfigIntListValues.OIL_POWER.getValue();

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 0, Math.max(Math.max(this.i[0], this.i[1]), Math.max(this.i[2], this.i[3])) + 20);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    public final FluidTank tank = new FluidTank(2 * Util.BUCKET) {
        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return FluidStack.EMPTY;
        }

        //@Override
        public boolean canFillFluidType(FluidStack stack) {
            Fluid fluid = stack == null
                ? null
                : stack.getFluid();
            return fluid != null && getRecipeForFluid(fluid.getRegistryName().toString()) != null;
        }
    };
    public final LazyOptional<IFluidHandler> lazyTank = LazyOptional.of(() -> this.tank);
    public int currentEnergyProduce;
    public int currentBurnTime;
    public int maxBurnTime;
    private int lastEnergy;
    private int lastTank;
    private int lastBurnTime;
    private int lastMaxBurnTime;
    private int lastEnergyProduce;
    private int lastCompare;

    public TileEntityOilGenerator() {
        super(ActuallyBlocks.OIL_GENERATOR.getTileEntityType());
    }

    private static OilGenRecipe getRecipeForFluid(String fluidName) {
        if (fluidName != null) {
            for (OilGenRecipe recipe : ActuallyAdditionsAPI.OIL_GENERATOR_RECIPES) {
                if (recipe != null && fluidName.equals(recipe.fluidName)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurningScaled(int i) {
        return this.currentBurnTime * i / this.maxBurnTime;
    }

    private OilGenRecipe getRecipeForCurrentFluid() {
        FluidStack stack = this.tank.getFluid();
        if (stack != null) {
            Fluid fluid = stack.getFluid();
            if (fluid != null) {
                return getRecipeForFluid(fluid.getRegistryName().toString());
            }
        }
        return null;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("BurnTime", this.currentBurnTime);
            compound.putInt("CurrentEnergy", this.currentEnergyProduce);
            compound.putInt("MaxBurnTime", this.maxBurnTime);
        }
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.currentBurnTime = compound.getInt("BurnTime");
            this.currentEnergyProduce = compound.getInt("CurrentEnergy");
            this.maxBurnTime = compound.getInt("MaxBurnTime");
        }
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            boolean flag = this.currentBurnTime > 0;

            if (this.currentBurnTime > 0 && this.currentEnergyProduce > 0) {
                this.currentBurnTime--;

                this.storage.receiveEnergyInternal(this.currentEnergyProduce, false);
            } else if (!this.isRedstonePowered) {
                int fuelUsed = 50;

                OilGenRecipe recipe = this.getRecipeForCurrentFluid();
                if (recipe != null && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored() && this.tank.getFluidAmount() >= fuelUsed) {
                    this.currentEnergyProduce = recipe.genAmount;
                    this.maxBurnTime = recipe.genTime;
                    this.currentBurnTime = this.maxBurnTime;

                    this.tank.drain(fuelUsed, IFluidHandler.FluidAction.EXECUTE);
                } else {
                    this.currentEnergyProduce = 0;
                    this.currentBurnTime = 0;
                    this.maxBurnTime = 0;
                }
            }

            if (flag != this.currentBurnTime > 0 || this.lastCompare != this.getComparatorStrength()) {
                this.lastCompare = this.getComparatorStrength();

                this.setChanged();
            }

            if ((this.storage.getEnergyStored() != this.lastEnergy || this.tank.getFluidAmount() != this.lastTank || this.lastBurnTime != this.currentBurnTime || this.lastEnergyProduce != this.currentEnergyProduce || this.lastMaxBurnTime != this.maxBurnTime) && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastTank = this.tank.getFluidAmount();
                this.lastBurnTime = this.currentBurnTime;
                this.lastEnergyProduce = this.currentEnergyProduce;
                this.lastMaxBurnTime = this.maxBurnTime;
            }
        }
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.storage.getEnergyStored() / (float) this.storage.getMaxEnergyStored() * 15F;
        return (int) calc;
    }

    @Override
    public LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        return this.lazyTank;
    }

    @Override
    public int getMaxFluidAmountToSplitShare() {
        return 0;
    }

    @Override
    public boolean doesShareFluid() {
        return false;
    }

    @Override
    public Direction[] getFluidShareSides() {
        return null;
    }

    @Override
    public int getEnergyToSplitShare() {
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy() {
        return true;
    }

    @Override
    public Direction[] getEnergyShareSides() {
        return Direction.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile) {
        return true;
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
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerOilGenerator(windowId, playerInventory, this);
    }
}
