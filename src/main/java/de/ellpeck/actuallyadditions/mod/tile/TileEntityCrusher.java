/*
 * This file ("TileEntityGrinder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.AASounds;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import de.ellpeck.actuallyadditions.mod.inventory.CrusherContainer;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class TileEntityCrusher extends TileEntityInventoryBase implements IButtonReactor, MenuProvider {

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1_1 = 1;
    public static final int SLOT_OUTPUT_1_2 = 2;
    public static final int SLOT_INPUT_2 = 3;
    public static final int SLOT_OUTPUT_2_1 = 4;
    public static final int SLOT_OUTPUT_2_2 = 5;
    public static final int ENERGY_USE = 40;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(60000, 100, 0);
    public int firstCrushTime;
    public int secondCrushTime;
    public boolean isDouble;
    public boolean isAutoSplit;
    private int lastEnergy;
    private int lastFirstCrush;
    private int lastSecondCrush;
    private boolean lastAutoSplit;
    private boolean lastCrushed;

    public TileEntityCrusher(BlockEntityType<?> type, BlockPos pos, BlockState state, int slots) {
        super(type, pos, state, slots);
    }

    public TileEntityCrusher(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.CRUSHER.getTileEntityType(), pos, state, 3);
        this.isDouble = false;
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("FirstCrushTime", this.firstCrushTime);
            compound.putInt("SecondCrushTime", this.secondCrushTime);
            compound.putBoolean("IsAutoSplit", this.isAutoSplit);
        }
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, lookupProvider, type);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.firstCrushTime = compound.getInt("FirstCrushTime");
            this.secondCrushTime = compound.getInt("SecondCrushTime");
            this.isAutoSplit = compound.getBoolean("IsAutoSplit");
        }
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, lookupProvider, type);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityCrusher tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityCrusher tile) {
            tile.serverTick();
        }
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (isDouble && isAutoSplit) {
            TileEntityPoweredFurnace.autoSplit(inv, SLOT_INPUT_1, SLOT_INPUT_2);
        }

        boolean crushed = false;

        boolean canCrushOnFirst = canCrushOn(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
        boolean canCrushOnSecond = false;
        if (isDouble) {
            canCrushOnSecond = canCrushOn(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
        }

        boolean shouldPlaySound = false;

        if (canCrushOnFirst) {
            if (storage.getEnergyStored() >= ENERGY_USE) {
                if (firstCrushTime % 20 == 0) {
                    shouldPlaySound = true;
                }
                firstCrushTime++;
                if (firstCrushTime >= getMaxCrushTime()) {
                    finishCrushing(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
                    firstCrushTime = 0;
                }
                storage.extractEnergyInternal(ENERGY_USE, false);
            }
            crushed = storage.getEnergyStored() >= ENERGY_USE;
        } else {
            firstCrushTime = 0;
        }

        if (isDouble) {
            if (canCrushOnSecond) {
                if (storage.getEnergyStored() >= ENERGY_USE) {
                    if (secondCrushTime % 20 == 0) {
                        shouldPlaySound = true;
                    }
                    secondCrushTime++;
                    if (secondCrushTime >= getMaxCrushTime()) {
                        finishCrushing(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
                        secondCrushTime = 0;
                    }
                    storage.extractEnergyInternal(ENERGY_USE, false);
                }
                crushed = storage.getEnergyStored() >= ENERGY_USE;
            } else {
                secondCrushTime = 0;
            }
        }

        boolean current = getBlockState().getValue(BlockStateProperties.LIT);
        boolean changeTo = current;
        if (lastCrushed != crushed) {
            changeTo = crushed;
        }
        if (isRedstonePowered) {
            changeTo = true;
        }
        if (!crushed && !isRedstonePowered) {
            changeTo = false;
        }

        if (changeTo != current) {
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.LIT, changeTo));
        }

        lastCrushed = crushed;

        if ((lastEnergy != storage.getEnergyStored() || lastFirstCrush != firstCrushTime || lastSecondCrush != secondCrushTime || isAutoSplit != lastAutoSplit) && sendUpdateWithInterval()) {
            lastEnergy = storage.getEnergyStored();
            lastFirstCrush = firstCrushTime;
            lastSecondCrush = secondCrushTime;
            lastAutoSplit = isAutoSplit;
        }

        if (shouldPlaySound) {
            level.playSound(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), AASounds.CRUSHER.get(), SoundSource.BLOCKS, 0.025F, 1.0F);
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || (slot == SLOT_INPUT_1 || slot == SLOT_INPUT_2); /*CrusherRecipeRegistry.getRecipeFromInput(stack) != null*/ //TODO
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT_1_1 || slot == SLOT_OUTPUT_1_2 || slot == SLOT_OUTPUT_2_1 || slot == SLOT_OUTPUT_2_2;
    }

    public static Optional<RecipeHolder<CrushingRecipe>> getRecipeForInput(ItemStack itemStack) {
        return ActuallyAdditionsAPI.CRUSHER_RECIPES.stream().filter($ -> $.value().matches(itemStack)).findFirst();
    }
    public boolean canCrushOn(int theInput, int theFirstOutput, int theSecondOutput) {
        ItemStack inputStack = this.inv.getStackInSlot(theInput);
        if (!inputStack.isEmpty()) {
            Optional<RecipeHolder<CrushingRecipe>> recipeOpt = getRecipeForInput(inputStack);
            if (recipeOpt.isEmpty()) {
                return false;
            }
            CrushingRecipe recipe = recipeOpt.get().value();
            ItemStack outputOne = recipe.getOutputOne();
            ItemStack outputTwo = recipe.getOutputTwo();
            if (!outputOne.isEmpty()) {
                return (this.inv.getStackInSlot(theFirstOutput).isEmpty() || ItemStack.isSameItem(this.inv.getStackInSlot(theFirstOutput), outputOne) && this.inv.getStackInSlot(theFirstOutput).getCount() <= this.inv.getStackInSlot(theFirstOutput).getMaxStackSize() - outputOne.getCount()) && (outputTwo.isEmpty() || this.inv.getStackInSlot(theSecondOutput).isEmpty() || ItemStack.isSameItem(this.inv.getStackInSlot(theSecondOutput), outputTwo) && this.inv.getStackInSlot(theSecondOutput).getCount() <= this.inv.getStackInSlot(theSecondOutput).getMaxStackSize() - outputTwo.getCount());
            }
        }
        return false;
    }

    private int getMaxCrushTime() {
        return this.isDouble
            ? 150
            : 100;
    }

    public void finishCrushing(int theInput, int theFirstOutput, int theSecondOutput) {
        Optional<RecipeHolder<CrushingRecipe>> recipeOpt = getRecipeForInput(this.inv.getStackInSlot(theInput));
        if (recipeOpt.isEmpty()) {
            return;
        }
        CrushingRecipe recipe = recipeOpt.get().value();

        ItemStack outputOne = recipe.getOutputOne();
        if (!outputOne.isEmpty()) {
            if (this.inv.getStackInSlot(theFirstOutput).isEmpty()) {
                this.inv.setStackInSlot(theFirstOutput, outputOne.copy());
            } else if (this.inv.getStackInSlot(theFirstOutput).getItem() == outputOne.getItem()) {
                this.inv.setStackInSlot(theFirstOutput, StackUtil.grow(this.inv.getStackInSlot(theFirstOutput), outputOne.getCount()));
            }
        }

        ItemStack outputTwo = recipe.getOutputTwo();
        if (!outputTwo.isEmpty()) {
            float rand = this.level.random.nextFloat();
            if (rand <= recipe.getSecondChance()) {
                if (this.inv.getStackInSlot(theSecondOutput).isEmpty()) {
                    this.inv.setStackInSlot(theSecondOutput, outputTwo.copy());
                } else if (this.inv.getStackInSlot(theSecondOutput).getItem() == outputTwo.getItem()) {
                    this.inv.setStackInSlot(theSecondOutput, StackUtil.grow(this.inv.getStackInSlot(theSecondOutput), outputTwo.getCount()));
                }
            }
        }

        this.inv.getStackInSlot(theInput).shrink(1);
    }

    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    public int getFirstTimeToScale(int i) {
        return this.firstCrushTime * i / this.getMaxCrushTime();
    }

    public int getSecondTimeToScale(int i) {
        return this.secondCrushTime * i / this.getMaxCrushTime();
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

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.crusher");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new CrusherContainer(windowId, playerInventory, this);
    }

    @Override
    protected void applyImplicitComponents(@Nonnull DataComponentInput input) {
        super.applyImplicitComponents(input);

        storage.setEnergyStored(input.getOrDefault(ActuallyComponents.ENERGY_STORAGE, 0));
    }

    @Override
    protected void collectImplicitComponents(@Nonnull DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);

        builder.set(ActuallyComponents.ENERGY_STORAGE, storage.getEnergyStored());
    }
}
