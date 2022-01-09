/*
 * This file ("TileEntityFarmer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFarmer;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileEntityFarmer extends TileEntityInventoryBase implements IFarmer, INamedContainerProvider {

    private static final List<IFarmerBehavior> SORTED_FARMER_BEHAVIORS = new ArrayList<>();
    public final CustomEnergyStorage storage = new CustomEnergyStorage(100000, 1000, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int waitTime;
    private int checkX;
    private int checkY;

    private int lastEnergy;

    public TileEntityFarmer() {
        super(ActuallyBlocks.FARMER.getTileEntityType(), 12);
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("WaitTime", this.waitTime);
        }
        if (type == NBTType.SAVE_TILE) {
            compound.putInt("CheckX", this.checkX);
            compound.putInt("CheckY", this.checkY);
        }
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.waitTime = compound.getInt("WaitTime");
        }
        if (type == NBTType.SAVE_TILE) {
            this.checkX = compound.getInt("CheckX");
            this.checkY = compound.getInt("CheckY");
        }
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (!this.isRedstonePowered && this.storage.getEnergyStored() > 0) {
                if (this.waitTime > 0) {
                    this.waitTime--;

                    if (this.waitTime <= 0) {
                        int area = CommonConfig.MACHINES.FARMER_AREA.get();
                        if (area % 2 == 0) {
                            area++;
                        }
                        int radius = area / 2;

                        BlockState state = this.level.getBlockState(this.worldPosition);
                        BlockPos center = this.worldPosition.relative(state.getValue(BlockStateProperties.HORIZONTAL_FACING), radius + 1);

                        BlockPos query = center.offset(this.checkX, 0, this.checkY);
                        this.checkBehaviors(query);

                        this.checkX++;
                        if (this.checkX > radius) {
                            this.checkX = -radius;
                            this.checkY++;
                            if (this.checkY > radius) {
                                this.checkY = -radius;
                            }
                        }
                    }
                } else {
                    this.waitTime = 5;
                }
            }

            if (this.lastEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
            }
        }
    }

    private static boolean sorted = false;

    private static void sort() {
        SORTED_FARMER_BEHAVIORS.clear();
        SORTED_FARMER_BEHAVIORS.addAll(ActuallyAdditionsAPI.FARMER_BEHAVIORS);
        Collections.sort(SORTED_FARMER_BEHAVIORS, (b1, b2) -> b2.getPrioInt().compareTo(b1.getPrioInt()));
        sorted = true;
    }

    private void checkBehaviors(BlockPos query) {
        if (!sorted) {
            sort();
        }

        for (IFarmerBehavior behavior : SORTED_FARMER_BEHAVIORS) {
            FarmerResult harvestResult = behavior.tryHarvestPlant((ServerWorld) level, query, this);
            if (harvestResult == FarmerResult.STOP_PROCESSING) {
                return;
            }
            for (int i = 0; i < 6; i++) { //Process seed slots only
                ItemStack stack = this.inv.getStackInSlot(i);
                BlockState state = this.level.getBlockState(query);
                if (StackUtil.isValid(stack) && state.getMaterial().isReplaceable()) {
                    FarmerResult plantResult = behavior.tryPlantSeed(stack, this.level, query, this);
                    if (plantResult == FarmerResult.SUCCESS) {
                        this.inv.getStackInSlot(i).shrink(1);
                        return;
                    } else if (plantResult == FarmerResult.STOP_PROCESSING) {
                        return;
                    }
                }
            }

        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || slot < 6;
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot >= 6;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public Direction getOrientation() {
        BlockState state = this.level.getBlockState(this.worldPosition);
        return WorldUtil.getDirectionByPistonRotation(state);
    }

    @Override
    public BlockPos getPosition() {
        return this.worldPosition;
    }

    @Override
    public int getX() {
        return this.worldPosition.getX();
    }

    @Override
    public int getY() {
        return this.worldPosition.getY();
    }

    @Override
    public int getZ() {
        return this.worldPosition.getZ();
    }

    @Override
    public World getWorldObject() {
        return this.level;
    }

    @Override
    public void extractEnergy(int amount) {
        this.storage.extractEnergyInternal(amount, false);
    }

    @Override
    public int getEnergy() {
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean canAddToSeeds(List<ItemStack> stacks) {
        return StackUtil.canAddAll(this.inv, stacks, 0, 6, false);
    }

    @Override
    public boolean canAddToOutput(List<ItemStack> stacks) {
        return StackUtil.canAddAll(this.inv, stacks, 6, 12, false);
    }

    @Override
    public void addToSeeds(List<ItemStack> stacks) {
        StackUtil.addAll(this.inv, stacks, 0, 6, false);
    }

    @Override
    public void addToOutput(List<ItemStack> stacks) {
        StackUtil.addAll(this.inv, stacks, 6, 12, false);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.actuallyadditions.farmer");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_) {
        return new ContainerFarmer(windowId, playerInventory, this);
    }
}
