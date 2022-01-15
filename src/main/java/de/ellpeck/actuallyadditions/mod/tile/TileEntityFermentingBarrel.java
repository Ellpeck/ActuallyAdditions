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

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.FermentingRecipe;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFermentingBarrel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class TileEntityFermentingBarrel extends TileEntityBase implements ISharingFluidHandler, INamedContainerProvider {
    public final FermentingBarrelMultiTank tanks = new FermentingBarrelMultiTank();
    public final LazyOptional<IFluidHandler> fluidOptional = LazyOptional.of(()->tanks);

    public int currentProcessTime;
    private int lastInput;
    private int lastOutput;
    private int lastProcessTime;
    private int lastCompare;
    private FermentingRecipe currentRecipe;

    public TileEntityFermentingBarrel() {
        super(ActuallyBlocks.FERMENTING_BARREL.getTileEntityType());
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        compound.putInt("ProcessTime", this.currentProcessTime);
        compound.put("tanks", tanks.writeNBT());
        if (currentRecipe != null)
            compound.putString("currentRecipe", currentRecipe.getId().toString());
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        this.currentProcessTime = compound.getInt("ProcessTime");
        if (compound.contains("tanks")) {
            tanks.readNBT(compound.getCompound("tanks"));
        }
        if (compound.contains("currentRecipe")) {
            this.currentRecipe = ActuallyAdditionsAPI.FERMENTING_RECIPES.stream().filter(recipe -> recipe.getId().toString().equals(compound.getString("currentRecipe"))).findFirst().orElse(null);
        }
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (this.level.isClientSide)
            return;

        if (currentRecipe == null) {
            this.currentRecipe = ActuallyAdditionsAPI.FERMENTING_RECIPES.stream().filter(recipe -> recipe.matches(this.tanks.getFluidInTank(0), this.tanks.getFluidInTank(1))).findFirst().orElse(null);
        } else {
            if (this.tanks.getFluidInTank(0).getAmount() >= currentRecipe.getInput().getAmount() &&
                this.tanks.getFluidInTank(0).getFluid().isSame(currentRecipe.getInput().getFluid()) &&
                (this.tanks.getFluidInTank(1).getFluid().isSame(currentRecipe.getOutput().getFluid()) || tanks.getFluidInTank(1).isEmpty()) &&
                currentRecipe.getOutput().getAmount() <= this.tanks.getTankCapacity(1) - this.tanks.getFluidInTank(1).getAmount()) {

                this.currentProcessTime++;
                if (this.currentProcessTime >= currentRecipe.getTime()) {
                    this.currentProcessTime = 0;

                    this.tanks.outputTank.fill(currentRecipe.getOutput().copy(), IFluidHandler.FluidAction.EXECUTE);
                    this.tanks.inputTank.getFluid().shrink(currentRecipe.getInput().getAmount());
                }
            } else {
                this.currentProcessTime = 0;
                currentRecipe = null;
            }
        }
        int compare = this.getComparatorStrength();
        if (compare != this.lastCompare) {
            this.lastCompare = compare;

            this.setChanged();
        }

        if ((this.tanks.getFluidInTank(0).getAmount() != this.lastInput || this.tanks.getFluidInTank(1).getAmount() != this.lastOutput || this.currentProcessTime != this.lastProcessTime) && this.sendUpdateWithInterval()) {
            this.lastProcessTime = this.currentProcessTime;
            this.lastInput = this.tanks.getFluidInTank(0).getAmount();
            this.lastOutput = this.tanks.getFluidInTank(1).getAmount();
        }
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.tanks.getFluidInTank(1).getAmount() / (float) this.tanks.getTankCapacity(1) * 15F;
        return (int) calc;
    }

    @OnlyIn(Dist.CLIENT)
    public int getProcessScaled(int i) {
        if (currentRecipe != null)
            return this.currentProcessTime * i / currentRecipe.getTime();
        else
            return this.currentProcessTime * i / 100;
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
        return new TranslationTextComponent("container.actuallyadditions.fermenting_barrel");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_) {
        return new ContainerFermentingBarrel(windowId, playerInventory, this);
    }

    public static boolean validInput(FluidStack stack) {
        return getRecipeForInput(stack).isPresent();
    }

    public static Optional<FermentingRecipe> getRecipeForInput(FluidStack stack) {
        return ActuallyAdditionsAPI.FERMENTING_RECIPES.stream().filter(recipe -> recipe.matches(stack)).findFirst();
    }


    public class FermentingBarrelMultiTank implements IFluidHandler {

        private int capacity = FluidAttributes.BUCKET_VOLUME * 2;
        public FluidTank inputTank = new FluidTank(capacity);
        public FluidTank outputTank = new FluidTank(capacity);

        @Override
        public int getTanks() {
            return 2;
        }

        @Nonnull
        @Override
        public FluidStack getFluidInTank(int tank) {
            return tank == 0 ? inputTank.getFluid() : outputTank.getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return capacity;
        }

        @Override
        public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
            return tank == 0 && TileEntityFermentingBarrel.validInput(stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource.isEmpty() || !validInput(resource))
                return 0;

            if(action.simulate())
            {
                if (inputTank.isEmpty())
                    return Math.min(capacity, resource.getAmount());
                else
                    return Math.min(capacity - inputTank.getFluid().getAmount(), resource.getAmount());
            }
            else {
                if (inputTank.isEmpty()) {
                    inputTank.fill(new FluidStack(resource, Math.min(capacity, resource.getAmount())), FluidAction.EXECUTE);
                    //TODO need to set the BE dirty.
                    return inputTank.getFluid().getAmount();
                }
                else {
                    int filledAmt = capacity - inputTank.getFluid().getAmount();
                    if (resource.getAmount() < filledAmt) {
                        inputTank.getFluid().grow(resource.getAmount());
                        filledAmt = resource.getAmount();
                    }
                    else
                        inputTank.getFluid().setAmount(capacity);

                    if (filledAmt > 0){
                        //TODO set BE dirty
                    }
                    return filledAmt;
                }
            }
        }

        public CompoundNBT writeNBT() {
            CompoundNBT inputNBT = new CompoundNBT();
            inputTank.writeToNBT(inputNBT);
            CompoundNBT outputNBT = new CompoundNBT();
            outputTank.writeToNBT(outputNBT);

            CompoundNBT nbt = new CompoundNBT();
            nbt.put("inputTank", inputNBT);
            nbt.put("outputTank", outputNBT);
            return nbt;
        }

        public void readNBT(CompoundNBT nbt) {
            inputTank.readFromNBT(nbt.getCompound("inputTank"));
            outputTank.readFromNBT(nbt.getCompound("outputTank"));
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource.isEmpty() || resource.getFluid() != outputTank.getFluid().getFluid())
                return FluidStack.EMPTY;

            return drain(resource.getAmount(), action);
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            int drained = maxDrain;
            if (outputTank.getFluid().getAmount() < drained)
            {
                drained = outputTank.getFluid().getAmount();
            }
            FluidStack stack = new FluidStack(outputTank.getFluid(), drained);
            if (action.execute() && drained > 0)
            {
                outputTank.getFluid().shrink(drained);
                //TODO set BE dirty
            }
            return stack;
        }
    }
}
