/*
 * This file ("TileEntityCoalGenerator.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.crafting.SingleItem;
import de.ellpeck.actuallyadditions.mod.crafting.SolidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCoalGenerator;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class TileEntityCoalGenerator extends TileEntityInventoryBase implements INamedContainerProvider, ISharingEnergyProvider, IEnergyDisplay {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(60000, 0, 80);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    public int maxBurnTime;
    public int currentBurnTime;
    private int lastEnergy;
    private int lastBurnTime;
    private int lastCurrentBurnTime;
    private int lastCompare;
    private SolidFuelRecipe currentRecipe = null;

    public TileEntityCoalGenerator() {
        super(ActuallyBlocks.COAL_GENERATOR.getTileEntityType(), 1);
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurningScaled(int i) {
        return this.currentBurnTime * i / this.maxBurnTime;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("BurnTime", this.currentBurnTime);
            compound.putInt("MaxBurnTime", this.maxBurnTime);
            if (currentRecipe != null)
                compound.putString("currentRecipe", currentRecipe.getId().toString());
        }
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.currentBurnTime = compound.getInt("BurnTime");
            this.maxBurnTime = compound.getInt("MaxBurnTime");
            if (compound.contains("currentRecipe")) {
                ResourceLocation id = new ResourceLocation(compound.getString("currentRecipe"));
                for (SolidFuelRecipe fuelRecipe : ActuallyAdditionsAPI.SOLID_FUEL_RECIPES) {
                    if (fuelRecipe.getId().equals(id)) {
                        this.currentRecipe = fuelRecipe;
                        break;
                    }
                }
            }
        }
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            boolean flag = this.currentBurnTime > 0;

            if (this.currentBurnTime > 0 && currentRecipe != null) {
                this.currentBurnTime--;
                int produce = currentRecipe.getTotalEnergy() / currentRecipe.getBurnTime();
                if (produce > 0) {
                    this.storage.addEnergyRaw(produce);
                }
            }

            if (!this.isRedstonePowered && this.currentBurnTime <= 0 && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
                ItemStack stack = this.inv.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    for (SolidFuelRecipe fuelRecipe : ActuallyAdditionsAPI.SOLID_FUEL_RECIPES) {
                        if (fuelRecipe.matches(stack)) {
                            this.currentRecipe = fuelRecipe;
                            this.maxBurnTime = fuelRecipe.getBurnTime();
                            this.currentBurnTime = this.maxBurnTime;
                            this.inv.setStackInSlot(0, StackUtil.shrinkForContainer(stack, 1));
                            break;
                        }
                    }
                }
            }

            if (flag != this.currentBurnTime > 0 || this.lastCompare != this.getComparatorStrength()) {
                this.lastCompare = this.getComparatorStrength();
                this.setChanged();
            }

            if ((this.storage.getEnergyStored() != this.lastEnergy || this.currentBurnTime != this.lastCurrentBurnTime || this.lastBurnTime != this.maxBurnTime) && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastCurrentBurnTime = this.currentBurnTime;
                this.lastBurnTime = this.currentBurnTime;
            }
        }
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.storage.getEnergyStored() / (float) this.storage.getMaxEnergyStored() * 15F;
        return (int) calc;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> {
            for (SolidFuelRecipe recipe : ActuallyAdditionsAPI.SOLID_FUEL_RECIPES) {
                if (recipe.matches(stack))return true;
            }
            return false;
        };
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> {
            if (!automation) {
                return true;
            }
            return ForgeHooks.getBurnTime(this.inv.getStackInSlot(0)) <= 0;
        };
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
        return new TranslationTextComponent("container.actuallyadditions.coalGenerator");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerCoalGenerator(windowId, playerInventory, this);
    }

    @Override
    public CustomEnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public boolean needsHoldShift() {
        return false;
    }
}
