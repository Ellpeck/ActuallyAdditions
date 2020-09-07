package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.OilGenRecipe;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntListValues;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityOilGenerator extends TileEntityBase implements ISharingEnergyProvider, ISharingFluidHandler {

    int[] i = ConfigIntListValues.OIL_POWER.getValue();

    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 0, Math.max(Math.max(this.i[0], this.i[1]), Math.max(this.i[2], this.i[3])) + 20);
    public final FluidTank tank = new FluidTank(2 * Util.BUCKET) {
        @Override
        public boolean canDrain() {
            return false;
        }

        @Override
        public boolean canFillFluidType(FluidStack stack) {
            Fluid fluid = stack == null ? null : stack.getFluid();
            return fluid != null && getRecipeForFluid(fluid.getName()) != null;
        }
    };
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
        super("oilGenerator");
    }

    private static OilGenRecipe getRecipeForFluid(String fluidName) {
        if (fluidName != null) {
            for (OilGenRecipe recipe : ActuallyAdditionsAPI.OIL_GENERATOR_RECIPES) {
                if (recipe != null && fluidName.equals(recipe.fluidName)) { return recipe; }
            }
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public int getBurningScaled(int i) {
        return this.currentBurnTime * i / this.maxBurnTime;
    }

    private OilGenRecipe getRecipeForCurrentFluid() {
        FluidStack stack = this.tank.getFluid();
        if (stack != null) {
            Fluid fluid = stack.getFluid();
            if (fluid != null) { return getRecipeForFluid(fluid.getName()); }
        }
        return null;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.setInteger("BurnTime", this.currentBurnTime);
            compound.setInteger("CurrentEnergy", this.currentEnergyProduce);
            compound.setInteger("MaxBurnTime", this.maxBurnTime);
        }
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.currentBurnTime = compound.getInteger("BurnTime");
            this.currentEnergyProduce = compound.getInteger("CurrentEnergy");
            this.maxBurnTime = compound.getInteger("MaxBurnTime");
        }
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
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

                    this.tank.drainInternal(fuelUsed, true);
                } else {
                    this.currentEnergyProduce = 0;
                    this.currentBurnTime = 0;
                    this.maxBurnTime = 0;
                }
            }

            if (flag != this.currentBurnTime > 0 || this.lastCompare != this.getComparatorStrength()) {
                this.lastCompare = this.getComparatorStrength();

                this.markDirty();
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
    public IFluidHandler getFluidHandler(EnumFacing facing) {
        return this.tank;
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
    public EnumFacing[] getFluidShareSides() {
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
    public EnumFacing[] getEnergyShareSides() {
        return EnumFacing.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile) {
        return true;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
    }
}
