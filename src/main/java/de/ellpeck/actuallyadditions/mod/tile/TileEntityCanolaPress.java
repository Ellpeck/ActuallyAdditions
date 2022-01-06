/*
 * This file ("TileEntityCanolaPress.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.crafting.PressingRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.SingleItem;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.fluids.OutputOnlyFluidTank;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCanolaPress;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.Optional;

public class TileEntityCanolaPress extends TileEntityInventoryBase implements INamedContainerProvider, ISharingFluidHandler {

    //public static final int PRODUCE = 80;
    public static final int ENERGY_USE = 35;
    private static final int TIME = 30;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(40000, 100, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);

    public final OutputOnlyFluidTank tank = new OutputOnlyFluidTank(2 * FluidAttributes.BUCKET_VOLUME);
    public final LazyOptional<IFluidHandler> lazyFluid = LazyOptional.of(() -> this.tank);

    public int currentProcessTime;
    private int lastEnergyStored;
    private int lastTankAmount;
    private int lastProcessTime;

    public TileEntityCanolaPress() {
        super(ActuallyBlocks.CANOLA_PRESS.getTileEntityType(), 1);
    }

//    public static boolean isCanola(ItemStack stack) {
//        return stack.getItem() == ActuallyBlocks.CANOLA.getItem();
//    }

    @OnlyIn(Dist.CLIENT)
    public int getTankScaled(int i) {
        return this.tank.getFluidAmount() * i / this.tank.getCapacity();
    }

    @OnlyIn(Dist.CLIENT)
    public int getProcessScaled(int i) {
        return this.currentProcessTime * i / TIME;
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("ProcessTime", this.currentProcessTime);
        }
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.currentProcessTime = compound.getInt("ProcessTime");
        }
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            Optional<PressingRecipe> recipe = getRecipeForInput(this.inv.getStackInSlot(0));
            recipe.ifPresent(r -> {
                if ((r.getOutput().isFluidEqual(this.tank.getFluid()) || this.tank.isEmpty()) && r.getOutput().getAmount() <= this.tank.getCapacity() - this.tank.getFluidAmount()) {
                    if (this.storage.getEnergyStored() >= ENERGY_USE) {
                        this.currentProcessTime++;
                        this.storage.extractEnergyInternal(ENERGY_USE, false);
                        if (this.currentProcessTime >= TIME) {
                            this.currentProcessTime = 0;

                            this.inv.setStackInSlot(0, StackUtil.shrink(this.inv.getStackInSlot(0), 1));
                            FluidStack produced = r.getOutput().copy();
                            this.tank.fillInternal(produced, IFluidHandler.FluidAction.EXECUTE);
                            this.setChanged();
                        }
                    }
                }
            });
            if (!recipe.isPresent())
                this.currentProcessTime = 0;

            if ((this.storage.getEnergyStored() != this.lastEnergyStored || this.tank.getFluidAmount() != this.lastTankAmount | this.currentProcessTime != this.lastProcessTime) && this.sendUpdateWithInterval()) {
                this.lastEnergyStored = this.storage.getEnergyStored();
                this.lastProcessTime = this.currentProcessTime;
                this.lastTankAmount = this.tank.getFluidAmount();
            }
        }
    }

    public boolean validInput(ItemStack stack) {
        return getRecipeForInput(stack).isPresent();
    }

    public Optional<PressingRecipe> getRecipeForInput(ItemStack stack) {
        return ActuallyAdditionsAPI.PRESSING_RECIPES.stream().filter(recipe -> recipe.matches(new SingleItem(stack), null)).findFirst();
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> slot == 0 && validInput(stack);
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation;
    }

    @Override
    public LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        return this.lazyFluid;
    }

    @Override
    public int getMaxFluidAmountToSplitShare() {
        return this.tank.getFluidAmount();
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
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.actuallyadditions.canola_press");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerCanolaPress(windowId, playerInventory, this);
    }
}
