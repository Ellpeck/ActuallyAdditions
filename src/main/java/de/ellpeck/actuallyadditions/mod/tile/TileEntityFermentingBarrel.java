/*
 * This file ("TileEntityFermentingBarrel.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFermentingBarrel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityFermentingBarrel extends TileEntityBase implements ISharingFluidHandler, INamedContainerProvider {

    private static final int PROCESS_TIME = 100;

    public final FermentingBarrelMultiTank tanks = new FermentingBarrelMultiTank();
    public final LazyOptional<IFluidHandler> fluidOptional = LazyOptional.of(()->tanks);

    public int currentProcessTime;
    private int lastCanola;
    private int lastOil;
    private int lastProcessTime;
    private int lastCompare;

    public TileEntityFermentingBarrel() {
        super(ActuallyBlocks.FERMENTING_BARREL.getTileEntityType());
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        compound.putInt("ProcessTime", this.currentProcessTime);
        compound.put("tanks", tanks.writeNBT());
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        this.currentProcessTime = compound.getInt("ProcessTime");
        if (compound.contains("tanks")) {
            //TODO read
        }
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            int produce = 80;
            if (this.tanks.getFluidInTank(0).getAmount() >= produce && produce <= this.tanks.getTankCapacity(1) - this.tanks.getFluidInTank(1).getAmount()) {
                this.currentProcessTime++;
                if (this.currentProcessTime >= PROCESS_TIME) {
                    this.currentProcessTime = 0;

                    this.tanks.oilTank.getFluid().grow(produce);
                    this.tanks.canolaTank.getFluid().shrink(produce);
                }
            } else {
                this.currentProcessTime = 0;
            }

            int compare = this.getComparatorStrength();
            if (compare != this.lastCompare) {
                this.lastCompare = compare;

                this.setChanged();
            }

            if ((this.tanks.getFluidInTank(0).getAmount() != this.lastCanola || this.tanks.getFluidInTank(1).getAmount() != this.lastOil || this.currentProcessTime != this.lastProcessTime) && this.sendUpdateWithInterval()) {
                this.lastProcessTime = this.currentProcessTime;
                this.lastCanola = this.tanks.getFluidInTank(0).getAmount();
                this.lastOil = this.tanks.getFluidInTank(1).getAmount();
            }
        }
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.tanks.getFluidInTank(1).getAmount() / (float) this.tanks.getTankCapacity(1) * 15F;
        return (int) calc;
    }

    @OnlyIn(Dist.CLIENT)
    public int getProcessScaled(int i) {
        return this.currentProcessTime * i / PROCESS_TIME;
    }

    @OnlyIn(Dist.CLIENT)
    public int getOilTankScaled(int i) {
        return this.tanks.getFluidInTank(1).getAmount() * i / this.tanks.getTankCapacity(1);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCanolaTankScaled(int i) {
        return this.tanks.getFluidInTank(0).getAmount() * i / this.tanks.getTankCapacity(0);
    }

    @Override
    public LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        return fluidOptional;
    }

    @Override
    public int getMaxFluidAmountToSplitShare() {
        return tanks.getFluidInTank(1).getAmount();
    }

    @Override
    public boolean doesShareFluid() {
        return true;
    }

    @Override
    public Direction[] getFluidShareSides() {
        return Direction.values();
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_) {
        return new ContainerFermentingBarrel(windowId, playerInventory, this);
    }


    public class FermentingBarrelMultiTank implements IFluidHandler {

        private int capacity = FluidAttributes.BUCKET_VOLUME * 2;
        public FluidTank canolaTank = new FluidTank(capacity);
        public FluidTank oilTank = new FluidTank(capacity);

        @Override
        public int getTanks() {
            return 2;
        }

        @Nonnull
        @Override
        public FluidStack getFluidInTank(int tank) {
            return tank == 0 ? canolaTank.getFluid() : oilTank.getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return capacity;
        }

        @Override
        public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
            return tank == 0? stack.getFluid() == InitFluids.CANOLA_OIL.get():stack.getFluid() == InitFluids.REFINED_CANOLA_OIL.get();
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource.isEmpty() || resource.getFluid() != InitFluids.CANOLA_OIL.get())
            return 0;

            if(action.simulate())
            {
                if (canolaTank.isEmpty())
                    return Math.min(capacity, resource.getAmount());
                else
                    return Math.min(capacity - canolaTank.getFluid().getAmount(), resource.getAmount());
            }
            else {
                if (canolaTank.isEmpty()) {
                    canolaTank.fill(new FluidStack(resource, Math.min(capacity, resource.getAmount())), FluidAction.EXECUTE);
                    //TODO need to set the BE dirty.
                    return canolaTank.getFluid().getAmount();
                }
                else {
                    int filledAmt = capacity - canolaTank.getFluid().getAmount();
                    if (resource.getAmount() < filledAmt) {
                        canolaTank.getFluid().grow(resource.getAmount());
                        filledAmt = resource.getAmount();
                    }
                    else
                        canolaTank.getFluid().setAmount(capacity);

                    if (filledAmt > 0){
                        //TODO set BE dirty
                    }
                    return filledAmt;
                }
            }
        }

        public CompoundNBT writeNBT() {
            CompoundNBT canolaNBT = new CompoundNBT();
            canolaTank.writeToNBT(canolaNBT);
            CompoundNBT oilNBT = new CompoundNBT();
            oilTank.writeToNBT(oilNBT);

            CompoundNBT nbt = new CompoundNBT();
            nbt.put("canolaTank", canolaNBT);
            nbt.put("oilTank", oilNBT);
            return nbt;
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource.isEmpty() || resource.getFluid() != InitFluids.REFINED_CANOLA_OIL.get())
                return FluidStack.EMPTY;

            return drain(resource.getAmount(), action);
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            int drained = maxDrain;
            if (oilTank.getFluid().getAmount() < drained)
            {
                drained = oilTank.getFluid().getAmount();
            }
            FluidStack stack = new FluidStack(oilTank.getFluid(), drained);
            if (action.execute() && drained > 0)
            {
                oilTank.getFluid().shrink(drained);
                //TODO set BE dirty
            }
            return stack;
        }
    }
}
