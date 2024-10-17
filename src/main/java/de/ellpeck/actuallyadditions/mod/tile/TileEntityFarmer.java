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
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFarmer;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileEntityFarmer extends TileEntityInventoryBase implements IFarmer, MenuProvider {

    private static final List<IFarmerBehavior> SORTED_FARMER_BEHAVIORS = new ArrayList<>();
    public final CustomEnergyStorage storage = new CustomEnergyStorage(100000, 1000, 0);
    private int waitTime;
    private int checkX;
    private int checkY;

    private int lastEnergy;

    public TileEntityFarmer(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.FARMER.getTileEntityType(), pos, state, 12);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("WaitTime", this.waitTime);
        }
        if (type == NBTType.SAVE_TILE) {
            compound.putInt("CheckX", this.checkX);
            compound.putInt("CheckY", this.checkY);
        }
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, lookupProvider, type);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.waitTime = compound.getInt("WaitTime");
        }
        if (type == NBTType.SAVE_TILE) {
            this.checkX = compound.getInt("CheckX");
            this.checkY = compound.getInt("CheckY");
        }
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, lookupProvider, type);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFarmer tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFarmer tile) {
            tile.serverTick();

            if (!tile.isRedstonePowered && tile.storage.getEnergyStored() > 0) {
                if (tile.waitTime > 0) {
                    tile.waitTime--;

                    if (tile.waitTime <= 0) {
                        int area = CommonConfig.Machines.FARMER_AREA.get();
                        if (area % 2 == 0) {
                            area++;
                        }
                        int radius = area / 2;

                        BlockPos center = pos.relative(state.getValue(BlockStateProperties.HORIZONTAL_FACING), radius + 1);

                        BlockPos query = center.offset(tile.checkX, 0, tile.checkY);
                        tile.checkBehaviors(query);

                        tile.checkX++;
                        if (tile.checkX > radius) {
                            tile.checkX = -radius;
                            tile.checkY++;
                            if (tile.checkY > radius) {
                                tile.checkY = -radius;
                            }
                        }
                    }
                } else {
                    tile.waitTime = 5;
                }
            }

            if (tile.lastEnergy != tile.storage.getEnergyStored() && tile.sendUpdateWithInterval()) {
                tile.lastEnergy = tile.storage.getEnergyStored();
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
            FarmerResult harvestResult = behavior.tryHarvestPlant((ServerLevel) level, query, this);
            if (harvestResult == FarmerResult.STOP_PROCESSING) {
                return;
            }
            for (int i = 0; i < 6; i++) { //Process seed slots only
                ItemStack stack = this.inv.getStackInSlot(i);
                BlockState state = this.level.getBlockState(query);
                if (!stack.isEmpty() && state.canBeReplaced()) {
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
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
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
    public Level getWorldObject() {
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
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.farmer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player p_createMenu_3_) {
        return new ContainerFarmer(windowId, playerInventory, this);
    }
}
