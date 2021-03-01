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
import net.minecraft.world.World;
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
        super(ActuallyTiles.FARMER_TILE.get(), 12);
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
        if (!this.world.isRemote) {
            if (!this.isRedstonePowered && this.storage.getEnergyStored() > 0) {
                if (this.waitTime > 0) {
                    this.waitTime--;

                    if (this.waitTime <= 0) {
                        int area = ConfigIntValues.FARMER_AREA.getValue();
                        if (area % 2 == 0) {
                            area++;
                        }
                        int radius = area / 2;

                        BlockState state = this.world.getBlockState(this.pos);
                        BlockPos center = this.pos.offset(state.get(BlockStateProperties.HORIZONTAL_FACING), radius + 1);

                        BlockPos query = center.add(this.checkX, 0, this.checkY);
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
    }

    private void checkBehaviors(BlockPos query) {
        if (!sorted) {
            sort();
        }

        for (IFarmerBehavior behavior : SORTED_FARMER_BEHAVIORS) {
            FarmerResult harvestResult = behavior.tryHarvestPlant(this.world, query, this);
            if (harvestResult == FarmerResult.STOP_PROCESSING) {
                return;
            }
            for (int i = 0; i < 6; i++) { //Process seed slots only
                ItemStack stack = this.inv.getStackInSlot(i);
                BlockState state = this.world.getBlockState(query);
                if (StackUtil.isValid(stack) && state.getMaterial().isReplaceable()) {
                    FarmerResult plantResult = behavior.tryPlantSeed(stack, this.world, query, this);
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
        BlockState state = this.world.getBlockState(this.pos);
        return WorldUtil.getDirectionByPistonRotation(state);
    }

    @Override
    public BlockPos getPosition() {
        return this.pos;
    }

    @Override
    public int getX() {
        return this.pos.getX();
    }

    @Override
    public int getY() {
        return this.pos.getY();
    }

    @Override
    public int getZ() {
        return this.pos.getZ();
    }

    @Override
    public World getWorldObject() {
        return this.world;
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
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_) {
        return new ContainerFarmer(windowId, playerInventory, this);
    }
}
