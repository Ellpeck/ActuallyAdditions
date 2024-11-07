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
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.crafting.LiquidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerOilGenerator;
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
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityOilGenerator extends TileEntityBase implements ISharingEnergyProvider, ISharingFluidHandler, MenuProvider {
    public final CustomEnergyStorage storage = new CustomEnergyStorage(50000, 0, CommonConfig.Machines.OIL_GENERATOR_TRANSFER.get());
    public final FluidTank tank = new FluidTank(2 * FluidType.BUCKET_VOLUME, fluid -> getRecipeForFluid(fluid) != null) {
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
    public int fuelUsage;

    public TileEntityOilGenerator(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.OIL_GENERATOR.getTileEntityType(), pos, state);
    }

    private static RecipeHolder<LiquidFuelRecipe> getRecipeForFluid(FluidStack fluid) {
        if (fluid != null) {
            for (RecipeHolder<LiquidFuelRecipe> recipe : ActuallyAdditionsAPI.LIQUID_FUEL_RECIPES) {
                if (recipe != null && recipe.value().matches(fluid)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    
    public int getBurningScaled(int i) {
        return this.currentBurnTime * i / this.maxBurnTime;
    }

    private RecipeHolder<LiquidFuelRecipe> getRecipeForCurrentFluid() {
        FluidStack stack = this.tank.getFluid();
        if (!stack.isEmpty()) {
            return getRecipeForFluid(stack);
        }
        return null;
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("BurnTime", this.currentBurnTime);
            compound.putInt("CurrentEnergy", this.currentEnergyProduce);
            compound.putInt("MaxBurnTime", this.maxBurnTime);
            compound.putInt("FuelUsage", this.fuelUsage);
        }
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(lookupProvider, compound);
        super.writeSyncableNBT(compound, lookupProvider, type);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.currentBurnTime = compound.getInt("BurnTime");
            this.currentEnergyProduce = compound.getInt("CurrentEnergy");
            this.maxBurnTime = compound.getInt("MaxBurnTime");
            this.fuelUsage = compound.getInt("FuelUsage");
        }
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(lookupProvider, compound);
        super.readSyncableNBT(compound, lookupProvider, type);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityOilGenerator tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityOilGenerator tile) {
            tile.serverTick();

            boolean flag = tile.currentBurnTime > 0;

            if (tile.currentBurnTime > 0 && tile.currentEnergyProduce > 0) {
                tile.currentBurnTime--;

                tile.storage.receiveEnergyInternal(tile.currentEnergyProduce, false);
            } else if (!tile.isRedstonePowered) {

                RecipeHolder<LiquidFuelRecipe> recipeHolder = tile.getRecipeForCurrentFluid();
                if (recipeHolder != null && tile.storage.getEnergyStored() < tile.storage.getMaxEnergyStored() && tile.tank.getFluidAmount() >= recipeHolder.value().getFuelAmount()) {
                    LiquidFuelRecipe recipe = recipeHolder.value();
                    tile.fuelUsage = recipe.getFuelAmount();
                    tile.currentEnergyProduce = recipe.getTotalEnergy() / recipe.getBurnTime();
                    tile.maxBurnTime = recipe.getBurnTime();
                    tile.currentBurnTime = tile.maxBurnTime;

                    tile.tank.getFluid().shrink(tile.fuelUsage);
                } else {
                    tile.currentEnergyProduce = 0;
                    tile.currentBurnTime = 0;
                    tile.maxBurnTime = 0;
                    tile.fuelUsage = 0;
                }
            }

            if (flag != tile.currentBurnTime > 0 || tile.lastCompare != tile.getComparatorStrength()) {
                tile.lastCompare = tile.getComparatorStrength();

                tile.setChanged();
            }

            if ((tile.storage.getEnergyStored() != tile.lastEnergy || tile.tank.getFluidAmount() != tile.lastTank || tile.lastBurnTime != tile.currentBurnTime || tile.lastEnergyProduce != tile.currentEnergyProduce || tile.lastMaxBurnTime != tile.maxBurnTime) && tile.sendUpdateWithInterval()) {
                tile.lastEnergy = tile.storage.getEnergyStored();
                tile.lastTank = tile.tank.getFluidAmount();
                tile.lastBurnTime = tile.currentBurnTime;
                tile.lastEnergyProduce = tile.currentEnergyProduce;
                tile.lastMaxBurnTime = tile.maxBurnTime;
            }
        }
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.storage.getEnergyStored() / (float) this.storage.getMaxEnergyStored() * 15F;
        return (int) calc;
    }

    @Override
    public IFluidHandler getFluidHandler(Direction facing) {
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
    public boolean canShareTo(BlockEntity tile) {
        return true;
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.oilGenerator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerOilGenerator(windowId, playerInventory, this);
    }

    @Override
    protected void applyImplicitComponents(@Nonnull DataComponentInput input) {
        super.applyImplicitComponents(input);

        storage.setEnergyStored(input.getOrDefault(ActuallyComponents.ENERGY_STORAGE, 0));
        tank.setFluid(input.getOrDefault(ActuallyComponents.FLUID_A, ActuallyComponents.FluidContents.EMPTY).get());
    }

    @Override
    protected void collectImplicitComponents(@Nonnull DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);

        builder.set(ActuallyComponents.ENERGY_STORAGE, storage.getEnergyStored());
        builder.set(ActuallyComponents.FLUID_A, ActuallyComponents.FluidContents.of(tank.getFluid()));
    }
}
