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
import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.crafting.FermentingRecipe;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFermentingBarrel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class TileEntityFermentingBarrel extends TileEntityBase implements ISharingFluidHandler, MenuProvider {
    public final FermentingBarrelMultiTank tanks = new FermentingBarrelMultiTank();

    public int currentProcessTime;
    private int lastInput;
    private int lastOutput;
    private int lastProcessTime;
    private int lastCompare;
    private RecipeHolder<FermentingRecipe> currentRecipe;

    public TileEntityFermentingBarrel(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.FERMENTING_BARREL.getTileEntityType(), pos, state);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        compound.putInt("ProcessTime", this.currentProcessTime);
        compound.put("tanks", tanks.writeNBT(lookupProvider));
        if (currentRecipe != null)
            compound.putString("currentRecipe", currentRecipe.id().toString());
        super.writeSyncableNBT(compound, lookupProvider, type);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        this.currentProcessTime = compound.getInt("ProcessTime");
        if (compound.contains("tanks")) {
            tanks.readNBT(lookupProvider, compound.getCompound("tanks"));
        }
        if (compound.contains("currentRecipe")) {
            this.currentRecipe = ActuallyAdditionsAPI.FERMENTING_RECIPES.stream().filter(recipe -> recipe.id().toString().equals(compound.getString("currentRecipe"))).findFirst().orElse(null);
        }
        super.readSyncableNBT(compound, lookupProvider, type);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFermentingBarrel tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFermentingBarrel tile) {
            tile.serverTick();

            if (tile.currentRecipe == null) {
                //No recipe currently selected, check for one every 20 ticks
                if (tile.ticksElapsed % 20 == 0)
                    tile.currentRecipe = ActuallyAdditionsAPI.FERMENTING_RECIPES.stream().filter(recipe -> recipe.value().matches(tile.tanks.getFluidInTank(0), tile.tanks.getFluidInTank(1))).findFirst().orElse(null);
            } else {
                FermentingRecipe recipe = tile.currentRecipe.value();
                if (tile.tanks.getFluidInTank(0).getAmount() >= recipe.getInput().getAmount() &&
                        tile.tanks.getFluidInTank(0).getFluid().isSame(recipe.getInput().getFluid()) &&
                        (tile.tanks.getFluidInTank(1).getFluid().isSame(recipe.getOutput().getFluid()) || tile.tanks.getFluidInTank(1).isEmpty()) &&
                        recipe.getOutput().getAmount() <= tile.tanks.getTankCapacity(1) - tile.tanks.getFluidInTank(1).getAmount()) {

                    tile.currentProcessTime++;
                    if (tile.currentProcessTime >= recipe.getTime()) {
                        tile.currentProcessTime = 0;

                        tile.tanks.outputTank.fill(recipe.getOutput().copy(), IFluidHandler.FluidAction.EXECUTE);
                        tile.tanks.inputTank.getFluid().shrink(recipe.getInput().getAmount());
                    }
                } else {
                    tile.currentProcessTime = 0;
                    tile.currentRecipe = null;
                }
            }
            int compare = tile.getComparatorStrength();
            if (compare != tile.lastCompare) {
                tile.lastCompare = compare;

                tile.setChanged();
            }

            if ((tile.tanks.getFluidInTank(0).getAmount() != tile.lastInput || tile.tanks.getFluidInTank(1).getAmount() != tile.lastOutput || tile.currentProcessTime != tile.lastProcessTime) && tile.sendUpdateWithInterval()) {
                tile.lastProcessTime = tile.currentProcessTime;
                tile.lastInput = tile.tanks.getFluidInTank(0).getAmount();
                tile.lastOutput = tile.tanks.getFluidInTank(1).getAmount();
            }
        }
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);

        tanks.inputTank.setFluid(componentInput.getOrDefault(ActuallyComponents.FLUID_A, ActuallyComponents.FluidContents.EMPTY).inner());
        tanks.outputTank.setFluid(componentInput.getOrDefault(ActuallyComponents.FLUID_B, ActuallyComponents.FluidContents.EMPTY).inner());
    }

    @Override
    protected void collectImplicitComponents(@Nonnull DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);

        builder.set(ActuallyComponents.FLUID_A, ActuallyComponents.FluidContents.of(tanks.inputTank.getFluid()));
        builder.set(ActuallyComponents.FLUID_B, ActuallyComponents.FluidContents.of(tanks.outputTank.getFluid()));
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.tanks.getFluidInTank(1).getAmount() / (float) this.tanks.getTankCapacity(1) * 15F;
        return (int) calc;
    }

    
    public int getProcessScaled(int i) {
        if (currentRecipe != null)
            return this.currentProcessTime * i / currentRecipe.value().getTime();
        else
            return this.currentProcessTime * i / 100;
    }

    
    public int getOilTankScaled(int i) {
        return this.tanks.getFluidInTank(1).getAmount() * i / this.tanks.getTankCapacity(1);
    }

    
    public int getCanolaTankScaled(int i) {
        return this.tanks.getFluidInTank(0).getAmount() * i / this.tanks.getTankCapacity(0);
    }

    @Override
    public IFluidHandler getFluidHandler(Direction facing) {
        return tanks;
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

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.fermenting_barrel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory playerInventory, @Nonnull Player p_createMenu_3_) {
        return new ContainerFermentingBarrel(windowId, playerInventory, this);
    }

    public static boolean validInput(FluidStack stack) {
        return getRecipeForInput(stack).isPresent();
    }

    public static Optional<RecipeHolder<FermentingRecipe>> getRecipeForInput(FluidStack stack) {
        return ActuallyAdditionsAPI.FERMENTING_RECIPES.stream().filter(recipe -> recipe.value().matches(stack)).findFirst();
    }


    public static class FermentingBarrelMultiTank implements IFluidHandler {

        private final int capacity = FluidType.BUCKET_VOLUME * 2;
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
                    inputTank.fill(new FluidStack(resource.getFluidHolder(), Math.min(capacity, resource.getAmount())), FluidAction.EXECUTE);
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

        public CompoundTag writeNBT(HolderLookup.Provider lookupProvider) {
            CompoundTag inputNBT = new CompoundTag();
            inputTank.writeToNBT(lookupProvider, inputNBT);
            CompoundTag outputNBT = new CompoundTag();
            outputTank.writeToNBT(lookupProvider, outputNBT);

            CompoundTag nbt = new CompoundTag();
            nbt.put("inputTank", inputNBT);
            nbt.put("outputTank", outputNBT);
            return nbt;
        }

        public void readNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
            inputTank.readFromNBT(lookupProvider, nbt.getCompound("inputTank"));
            outputTank.readFromNBT(lookupProvider, nbt.getCompound("outputTank"));
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
            FluidStack stack = new FluidStack(outputTank.getFluid().getFluidHolder(), drained);
            if (action.execute() && drained > 0)
            {
                outputTank.getFluid().shrink(drained);
                //TODO set BE dirty
            }
            return stack;
        }
    }
}
