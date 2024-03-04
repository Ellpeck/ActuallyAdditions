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

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.SingleItem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class TileEntityPoweredFurnace extends TileEntityInventoryBase implements IButtonReactor, MenuProvider {

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1 = 1;
    public static final int SLOT_INPUT_2 = 2;
    public static final int SLOT_OUTPUT_2 = 3;
    public static final int ENERGY_USE = 25;
    private static final int SMELT_TIME = 80;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 150, 0);
    public int firstSmeltTime;
    public int secondSmeltTime;
    public boolean isAutoSplit;
    private int lastEnergy;
    private int lastFirstSmelt;
    private int lastSecondSmelt;
    private boolean lastAutoSplit;
    private boolean lastSmelted;

    public TileEntityPoweredFurnace(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.POWERED_FURNACE.getTileEntityType(), pos, state, 4);
    }

    public static void autoSplit(ItemStackHandlerAA inv, int slot1, int slot2) {
        ItemStack first = inv.getStackInSlot(slot1);
        ItemStack second = inv.getStackInSlot(slot2);

        if (!first.isEmpty() || !second.isEmpty()) {
            ItemStack toSplit = ItemStack.EMPTY;
            if (first.isEmpty() && !second.isEmpty() && second.getCount() > 1) {
                toSplit = second;
            } else if (second.isEmpty() && !first.isEmpty() && first.getCount() > 1) {
                toSplit = first;
            } else if (ItemUtil.canBeStacked(first, second)) {
                if (first.getCount() < first.getMaxStackSize() || second.getCount() < second.getMaxStackSize()) {
                    if (!(first.getCount() <= second.getCount() + 1 && first.getCount() >= second.getCount() - 1 || second.getCount() <= first.getCount() + 1 && second.getCount() >= first.getCount() - 1)) {
                        toSplit = first;
                        toSplit.grow(second.getCount());
                    }
                }
            }

            if (!toSplit.isEmpty()) {
                ItemStack splitFirst = toSplit.copy();
                ItemStack secondSplit = splitFirst.split(splitFirst.getCount() / 2);
                inv.setStackInSlot(slot1, splitFirst);
                inv.setStackInSlot(slot2, secondSplit);
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("FirstSmeltTime", this.firstSmeltTime);
            compound.putInt("SecondSmeltTime", this.secondSmeltTime);
            compound.putBoolean("IsAutoSplit", this.isAutoSplit);
        }
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.firstSmeltTime = compound.getInt("FirstSmeltTime");
            this.secondSmeltTime = compound.getInt("SecondSmeltTime");
            this.isAutoSplit = compound.getBoolean("IsAutoSplit");
        }
        this.storage.readFromNBT(compound);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPoweredFurnace tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPoweredFurnace tile) {
            tile.serverTick();
            
            if (tile.isAutoSplit) {
                autoSplit(tile.inv, SLOT_INPUT_1, SLOT_INPUT_2);
            }
            //TODO all tile logic needs redone someday

            boolean smelted = false;

            boolean canSmeltOnFirst = tile.canSmeltOn(SLOT_INPUT_1, SLOT_OUTPUT_1);
            boolean canSmeltOnSecond = tile.canSmeltOn(SLOT_INPUT_2, SLOT_OUTPUT_2);

            if (canSmeltOnFirst) {
                if (tile.storage.getEnergyStored() >= ENERGY_USE) {
                    tile.firstSmeltTime++;
                    if (tile.firstSmeltTime >= SMELT_TIME) {
                        tile.finishBurning(SLOT_INPUT_1, SLOT_OUTPUT_1);
                        tile.firstSmeltTime = 0;
                    }
                    tile.storage.extractEnergyInternal(ENERGY_USE, false);
                }
                smelted = true;
            } else {
                tile.firstSmeltTime = 0;
            }

            if (canSmeltOnSecond) {
                if (tile.storage.getEnergyStored() >= ENERGY_USE) {
                    tile.secondSmeltTime++;
                    if (tile.secondSmeltTime >= SMELT_TIME) {
                        tile.finishBurning(SLOT_INPUT_2, SLOT_OUTPUT_2);
                        tile.secondSmeltTime = 0;
                    }
                    tile.storage.extractEnergyInternal(ENERGY_USE, false);
                }
                smelted = true;
            } else {
                tile.secondSmeltTime = 0;
            }

            boolean current = state.getValue(BlockStateProperties.LIT);
            boolean changeTo = current;
            if (tile.lastSmelted != smelted) {
                changeTo = smelted;
            }
            if (tile.isRedstonePowered) {
                changeTo = true;
            }
            if (!smelted && !tile.isRedstonePowered) {
                changeTo = false;
            }

            if (changeTo != current) {
                tile.level.setBlock(tile.worldPosition, state.setValue(BlockStateProperties.LIT, changeTo), Block.UPDATE_ALL);
            }

            tile.lastSmelted = smelted;

            if ((tile.lastEnergy != tile.storage.getEnergyStored() || tile.lastFirstSmelt != tile.firstSmeltTime || tile.lastSecondSmelt != tile.secondSmeltTime || tile.isAutoSplit != tile.lastAutoSplit) && tile.sendUpdateWithInterval()) {
                tile.lastEnergy = tile.storage.getEnergyStored();
                tile.lastFirstSmelt = tile.firstSmeltTime;
                tile.lastAutoSplit = tile.isAutoSplit;
                tile.lastSecondSmelt = tile.secondSmeltTime;
            }
        }
    }

    public boolean validInput(ItemStack stack) {
        return getOutputForInput(stack).isPresent();
    }

    public Optional<ItemStack> getOutputForInput(ItemStack stack) {
        return level.getServer().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleItem(stack), level).map(recipe -> recipe.value().getResultItem(this.level.registryAccess()));
    }

    public Optional<RecipeHolder<SmeltingRecipe>> getRecipeForInput(ItemStack stack) {
        return level.getServer().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleItem(stack), level);
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || (slot == SLOT_INPUT_1 || slot == SLOT_INPUT_2) && validInput(stack);
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT_1 || slot == SLOT_OUTPUT_2;
    }

    public boolean canSmeltOn(int theInput, int theOutput) {
        ItemStack input = this.inv.getStackInSlot(theInput);
        ItemStack output = this.inv.getStackInSlot(theOutput);
        if (!input.isEmpty()) {
            Optional<RecipeHolder<SmeltingRecipe>> recipe = getRecipeForInput(input);
            return recipe.map($ -> output.isEmpty() || ItemStack.isSameItem(output, $.value().getResultItem(this.level.registryAccess())) && output.getCount() <= output.getMaxStackSize() - $.value().getResultItem(this.level.registryAccess()).getCount()).orElse(false);
        }
        return false;
    }

    public void finishBurning(int theInput, int theOutput) {
        ItemStack output = getOutputForInput(inv.getStackInSlot(theInput)).orElse(ItemStack.EMPTY);
        if (inv.getStackInSlot(theOutput).isEmpty()) {
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
    public void onButtonPressed(int buttonID, Player player) {
        if (buttonID == 0) {
            this.isAutoSplit = !this.isAutoSplit;
            this.setChanged();
        }
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.powered_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory playerInventory, @Nonnull Player player) {
        return new ContainerFurnaceDouble(windowId, playerInventory, this);
    }
}
