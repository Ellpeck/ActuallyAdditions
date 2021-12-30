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

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import de.ellpeck.actuallyadditions.mod.inventory.CrusherContainer;
import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileEntityCrusher extends TileEntityInventoryBase implements IButtonReactor, INamedContainerProvider {

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_OUTPUT_1_1 = 1;
    public static final int SLOT_OUTPUT_1_2 = 2;
    public static final int SLOT_INPUT_2 = 3;
    public static final int SLOT_OUTPUT_2_1 = 4;
    public static final int SLOT_OUTPUT_2_2 = 5;
    public static final int ENERGY_USE = 40;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(60000, 100, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    public int firstCrushTime;
    public int secondCrushTime;
    public boolean isDouble;
    public boolean isAutoSplit;
    private int lastEnergy;
    private int lastFirstCrush;
    private int lastSecondCrush;
    private boolean lastAutoSplit;
    private boolean lastCrushed;

    public TileEntityCrusher(TileEntityType<?> type, int slots) {
        super(type, slots);
    }

    public TileEntityCrusher() {
        super(ActuallyBlocks.CRUSHER.getTileEntityType(), 3);
        this.isDouble = false;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("FirstCrushTime", this.firstCrushTime);
            compound.putInt("SecondCrushTime", this.secondCrushTime);
            compound.putBoolean("IsAutoSplit", this.isAutoSplit);
        }
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.firstCrushTime = compound.getInt("FirstCrushTime");
            this.secondCrushTime = compound.getInt("SecondCrushTime");
            this.isAutoSplit = compound.getBoolean("IsAutoSplit");
        }
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (this.isDouble && this.isAutoSplit) {
                TileEntityPoweredFurnace.autoSplit(this.inv, SLOT_INPUT_1, SLOT_INPUT_2);
            }

            boolean crushed = false;

            boolean canCrushOnFirst = this.canCrushOn(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
            boolean canCrushOnSecond = false;
            if (this.isDouble) {
                canCrushOnSecond = this.canCrushOn(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
            }

            boolean shouldPlaySound = false;

            if (canCrushOnFirst) {
                if (this.storage.getEnergyStored() >= ENERGY_USE) {
                    if (this.firstCrushTime % 20 == 0) {
                        shouldPlaySound = true;
                    }
                    this.firstCrushTime++;
                    if (this.firstCrushTime >= this.getMaxCrushTime()) {
                        this.finishCrushing(SLOT_INPUT_1, SLOT_OUTPUT_1_1, SLOT_OUTPUT_1_2);
                        this.firstCrushTime = 0;
                    }
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                }
                crushed = this.storage.getEnergyStored() >= ENERGY_USE;
            } else {
                this.firstCrushTime = 0;
            }

            if (this.isDouble) {
                if (canCrushOnSecond) {
                    if (this.storage.getEnergyStored() >= ENERGY_USE) {
                        if (this.secondCrushTime % 20 == 0) {
                            shouldPlaySound = true;
                        }
                        this.secondCrushTime++;
                        if (this.secondCrushTime >= this.getMaxCrushTime()) {
                            this.finishCrushing(SLOT_INPUT_2, SLOT_OUTPUT_2_1, SLOT_OUTPUT_2_2);
                            this.secondCrushTime = 0;
                        }
                        this.storage.extractEnergyInternal(ENERGY_USE, false);
                    }
                    crushed = this.storage.getEnergyStored() >= ENERGY_USE;
                } else {
                    this.secondCrushTime = 0;
                }
            }

            BlockState currState = this.level.getBlockState(this.worldPosition);
            boolean current = currState.getValue(BlockStateProperties.LIT);
            boolean changeTo = current;
            if (this.lastCrushed != crushed) {
                changeTo = crushed;
            }
            if (this.isRedstonePowered) {
                changeTo = true;
            }
            if (!crushed && !this.isRedstonePowered) {
                changeTo = false;
            }

            if (changeTo != current) {
                this.level.setBlockAndUpdate(this.worldPosition, currState.setValue(BlockStateProperties.LIT, changeTo));
            }

            this.lastCrushed = crushed;

            if ((this.lastEnergy != this.storage.getEnergyStored() || this.lastFirstCrush != this.firstCrushTime || this.lastSecondCrush != this.secondCrushTime || this.isAutoSplit != this.lastAutoSplit) && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastFirstCrush = this.firstCrushTime;
                this.lastSecondCrush = this.secondCrushTime;
                this.lastAutoSplit = this.isAutoSplit;
            }

            if (shouldPlaySound) {
                this.level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), SoundHandler.crusher, SoundCategory.BLOCKS, 0.025F, 1.0F);
            }
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

    public boolean canCrushOn(int theInput, int theFirstOutput, int theSecondOutput) {
        if (StackUtil.isValid(this.inv.getStackInSlot(theInput))) {
            CrushingRecipe recipe = null;//CrusherRecipeRegistry.getRecipeFromInput(this.inv.getStackInSlot(theInput)); //TODO
            if (recipe == null) {
                return false;
            }
            ItemStack outputOne = recipe.getOutputOne();
            ItemStack outputTwo = recipe.getOutputTwo();
            if (StackUtil.isValid(outputOne)) {
                /* //TODO
                if (outputOne.getDamage() == Util.WILDCARD) {
                    outputOne.setDamage(0);
                }
                if (StackUtil.isValid(outputTwo) && outputTwo.getDamage() == Util.WILDCARD) {
                    outputTwo.setDamage(0);
                }
                 */
                if ((!StackUtil.isValid(this.inv.getStackInSlot(theFirstOutput)) || this.inv.getStackInSlot(theFirstOutput).sameItem(outputOne) && this.inv.getStackInSlot(theFirstOutput).getCount() <= this.inv.getStackInSlot(theFirstOutput).getMaxStackSize() - outputOne.getCount()) && (!StackUtil.isValid(outputTwo) || !StackUtil.isValid(this.inv.getStackInSlot(theSecondOutput)) || this.inv.getStackInSlot(theSecondOutput).sameItem(outputTwo) && this.inv.getStackInSlot(theSecondOutput).getCount() <= this.inv.getStackInSlot(theSecondOutput).getMaxStackSize() - outputTwo.getCount())) {
                    return true;
                }
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
        CrushingRecipe recipe = CrusherRecipeRegistry.getRecipeFromInput(this.inv.getStackInSlot(theInput));//TODO
        if (recipe == null) {
            return;
        }
        ItemStack outputOne = recipe.getOutputOne();
        if (StackUtil.isValid(outputOne)) {
            /* //TODO
            if (outputOne.getDamage() == Util.WILDCARD) {
                outputOne.setDamage(0);
            }
            */
            if (!StackUtil.isValid(this.inv.getStackInSlot(theFirstOutput))) {
                this.inv.setStackInSlot(theFirstOutput, outputOne.copy());
            } else if (this.inv.getStackInSlot(theFirstOutput).getItem() == outputOne.getItem()) {
                this.inv.setStackInSlot(theFirstOutput, StackUtil.grow(this.inv.getStackInSlot(theFirstOutput), outputOne.getCount()));
            }
        }

        ItemStack outputTwo = recipe.getOutputTwo();
        if (StackUtil.isValid(outputTwo)) {
            /* //TODO
            if (outputTwo.getDamage() == Util.WILDCARD) {
                outputTwo.setDamage(0);
            }
             */
            float rand = this.level.random.nextFloat();
            if (rand <= recipe.getSecondChance()) {
                if (!StackUtil.isValid(this.inv.getStackInSlot(theSecondOutput))) {
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
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        if (buttonID == 0) {
            this.isAutoSplit = !this.isAutoSplit;
            this.setChanged();
        }
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.actuallyadditions.crusher");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CrusherContainer(windowId, playerInventory, this);
    }
}
