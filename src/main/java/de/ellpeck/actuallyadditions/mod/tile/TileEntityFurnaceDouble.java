/*
 * This file ("TileEntityFurnaceDouble.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.datafix.fixes.FurnaceRecipes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileEntityFurnaceDouble extends TileEntityInventoryBase implements IButtonReactor, INamedContainerProvider {

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1 = 1;
    public static final int SLOT_INPUT_2 = 2;
    public static final int SLOT_OUTPUT_2 = 3;
    public static final int ENERGY_USE = 25;
    private static final int SMELT_TIME = 80;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 150, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    public int firstSmeltTime;
    public int secondSmeltTime;
    public boolean isAutoSplit;
    private int lastEnergy;
    private int lastFirstSmelt;
    private int lastSecondSmelt;
    private boolean lastAutoSplit;
    private boolean lastSmelted;

    public TileEntityFurnaceDouble() {
        super(ActuallyTiles.FURNACE_DOUBLE_TILE.get(), 4);
    }

    public static void autoSplit(ItemStackHandlerAA inv, int slot1, int slot2) {
        ItemStack first = inv.getStackInSlot(slot1);
        ItemStack second = inv.getStackInSlot(slot2);

        if (StackUtil.isValid(first) || StackUtil.isValid(second)) {
            ItemStack toSplit = StackUtil.getEmpty();
            if (!StackUtil.isValid(first) && StackUtil.isValid(second) && second.getCount() > 1) {
                toSplit = second;
            } else if (!StackUtil.isValid(second) && StackUtil.isValid(first) && first.getCount() > 1) {
                toSplit = first;
            } else if (ItemUtil.canBeStacked(first, second)) {
                if (first.getCount() < first.getMaxStackSize() || second.getCount() < second.getMaxStackSize()) {
                    if (!(first.getCount() <= second.getCount() + 1 && first.getCount() >= second.getCount() - 1 || second.getCount() <= first.getCount() + 1 && second.getCount() >= first.getCount() - 1)) {
                        toSplit = first;
                        toSplit.grow(second.getCount());
                    }
                }
            }

            if (StackUtil.isValid(toSplit)) {
                ItemStack splitFirst = toSplit.copy();
                ItemStack secondSplit = splitFirst.split(splitFirst.getCount() / 2);
                inv.setStackInSlot(slot1, splitFirst);
                inv.setStackInSlot(slot2, secondSplit);
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("FirstSmeltTime", this.firstSmeltTime);
            compound.putInt("SecondSmeltTime", this.secondSmeltTime);
            compound.putBoolean("IsAutoSplit", this.isAutoSplit);
        }
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.firstSmeltTime = compound.getInt("FirstSmeltTime");
            this.secondSmeltTime = compound.getInt("SecondSmeltTime");
            this.isAutoSplit = compound.getBoolean("IsAutoSplit");
        }
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            if (this.isAutoSplit) {
                autoSplit(this.inv, SLOT_INPUT_1, SLOT_INPUT_2);
            }

            boolean smelted = false;

            boolean canSmeltOnFirst = this.canSmeltOn(SLOT_INPUT_1, SLOT_OUTPUT_1);
            boolean canSmeltOnSecond = this.canSmeltOn(SLOT_INPUT_2, SLOT_OUTPUT_2);

            if (canSmeltOnFirst) {
                if (this.storage.getEnergyStored() >= ENERGY_USE) {
                    this.firstSmeltTime++;
                    if (this.firstSmeltTime >= SMELT_TIME) {
                        this.finishBurning(SLOT_INPUT_1, SLOT_OUTPUT_1);
                        this.firstSmeltTime = 0;
                    }
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                }
                smelted = true;
            } else {
                this.firstSmeltTime = 0;
            }

            if (canSmeltOnSecond) {
                if (this.storage.getEnergyStored() >= ENERGY_USE) {
                    this.secondSmeltTime++;
                    if (this.secondSmeltTime >= SMELT_TIME) {
                        this.finishBurning(SLOT_INPUT_2, SLOT_OUTPUT_2);
                        this.secondSmeltTime = 0;
                    }
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                }
                smelted = true;
            } else {
                this.secondSmeltTime = 0;
            }

            BlockState currState = this.world.getBlockState(this.pos);
            boolean current = currState.get(BlockStateProperties.LIT);
            boolean changeTo = current;
            if (this.lastSmelted != smelted) {
                changeTo = smelted;
            }
            if (this.isRedstonePowered) {
                changeTo = true;
            }
            if (!smelted && !this.isRedstonePowered) {
                changeTo = false;
            }

            if (changeTo != current) {
                this.world.setBlockState(this.pos, currState.with(BlockStateProperties.LIT, changeTo));
            }

            this.lastSmelted = smelted;

            if ((this.lastEnergy != this.storage.getEnergyStored() || this.lastFirstSmelt != this.firstSmeltTime || this.lastSecondSmelt != this.secondSmeltTime || this.isAutoSplit != this.lastAutoSplit) && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastFirstSmelt = this.firstSmeltTime;
                this.lastAutoSplit = this.isAutoSplit;
                this.lastSecondSmelt = this.secondSmeltTime;
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || (slot == SLOT_INPUT_1 || slot == SLOT_INPUT_2) && StackUtil.isValid(FurnaceRecipes.instance().getSmeltingResult(stack));
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT_1 || slot == SLOT_OUTPUT_2;
    }

    public boolean canSmeltOn(int theInput, int theOutput) {
        if (StackUtil.isValid(this.inv.getStackInSlot(theInput))) {
            ItemStack output = FurnaceRecipes.instance().getSmeltingResult(this.inv.getStackInSlot(theInput));
            if (StackUtil.isValid(output)) {
                if (!StackUtil.isValid(this.inv.getStackInSlot(theOutput)) || this.inv.getStackInSlot(theOutput).isItemEqual(output) && this.inv.getStackInSlot(theOutput).getCount() <= this.inv.getStackInSlot(theOutput).getMaxStackSize() - output.getCount()) {
                    return true;
                }
            }

        }
        return false;
    }

    public void finishBurning(int theInput, int theOutput) {
        ItemStack output = FurnaceRecipe.instance().getSmeltingResult(this.inv.getStackInSlot(theInput));
        if (!StackUtil.isValid(this.inv.getStackInSlot(theOutput))) {
            this.inv.setStackInSlot(theOutput, output.copy());
        } else if (this.inv.getStackInSlot(theOutput).getItem() == output.getItem()) {
            this.inv.getStackInSlot(theOutput).grow(output.getCount());
        }

        this.inv.getStackInSlot(theInput).shrink(1);
    }

    public int getFirstTimeToScale(int i) {
        return this.firstSmeltTime * i / SMELT_TIME;
    }

    public int getSecondTimeToScale(int i) {
        return this.secondSmeltTime * i / SMELT_TIME;
    }

    @Override
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        if (buttonID == 0) {
            this.isAutoSplit = !this.isAutoSplit;
            this.markDirty();
        }
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
        return new ContainerFurnaceDouble(windowId, playerInventory, this);
    }
}
