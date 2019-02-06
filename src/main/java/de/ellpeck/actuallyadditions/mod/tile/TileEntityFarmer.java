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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityFarmer extends TileEntityInventoryBase implements IFarmer {

    private static final List<IFarmerBehavior> SORTED_FARMER_BEHAVIORS = new ArrayList<>();
    public final CustomEnergyStorage storage = new CustomEnergyStorage(100000, 1000, 0);

    private int waitTime;
    private int checkX;
    private int checkY;

    private int lastEnergy;

    public TileEntityFarmer() {
        super(12, "farmer");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.setInteger("WaitTime", this.waitTime);
        }
        if (type == NBTType.SAVE_TILE) {
            compound.setInteger("CheckX", this.checkX);
            compound.setInteger("CheckY", this.checkY);
        }
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.waitTime = compound.getInteger("WaitTime");
        }
        if (type == NBTType.SAVE_TILE) {
            this.checkX = compound.getInteger("CheckX");
            this.checkY = compound.getInteger("CheckY");
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
                        if (area % 2 == 0) area++;
                        int radius = area / 2;

                        IBlockState state = this.world.getBlockState(this.pos);
                        BlockPos center = this.pos.offset(state.getValue(BlockHorizontal.FACING), radius + 1);

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

        if (!sorted) sort();

        for (IFarmerBehavior behavior : SORTED_FARMER_BEHAVIORS) {
            FarmerResult harvestResult = behavior.tryHarvestPlant(this.world, query, this);
            if (harvestResult == FarmerResult.STOP_PROCESSING) return;
            for (int i = 0; i < 6; i++) { //Process seed slots only
                ItemStack stack = this.inv.getStackInSlot(i);
                IBlockState state = world.getBlockState(query);
                if (StackUtil.isValid(stack) && state.getBlock().isReplaceable(world, query)) {
                    FarmerResult plantResult = behavior.tryPlantSeed(stack, this.world, query, this);
                    if (plantResult == FarmerResult.SUCCESS) {
                        this.inv.getStackInSlot(i).shrink(1);
                        return;
                    } else if (plantResult == FarmerResult.STOP_PROCESSING) return;
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
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
    }

    @Override
    public EnumFacing getOrientation() {
        IBlockState state = this.world.getBlockState(this.pos);
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
        return StackUtil.canAddAll(inv, stacks, 0, 6, false);
    }

    @Override
    public boolean canAddToOutput(List<ItemStack> stacks) {
        return StackUtil.canAddAll(inv, stacks, 6, 12, false);
    }

    @Override
    public void addToSeeds(List<ItemStack> stacks) {
        StackUtil.addAll(inv, stacks, 0, 6, false);
    }

    @Override
    public void addToOutput(List<ItemStack> stacks) {
        StackUtil.addAll(inv, stacks, 6, 12, false);
    }
}
