/*
 * This file ("TileEntityBioReactor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBioReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IPlantable;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityBioReactor extends TileEntityInventoryBase implements MenuProvider, ISharingEnergyProvider {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(200000, 0, 800);

    public int burnTime;
    public int maxBurnTime;
    public int producePerTick;

    private int lastBurnTime;
    private int lastProducePerTick;

    public TileEntityBioReactor(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.BIOREACTOR.getTileEntityType(),  pos, state, 8);
    }

    public static boolean isValidItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            if (item.isEdible()) {
                return true;
            } else if (item instanceof BlockItem) {
                return isValid(Block.byItem(item));
            }
        }
        return false;
    }

    private static boolean isValid(Object o) {
        return o instanceof IPlantable || o instanceof BonemealableBlock;
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityBioReactor tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityBioReactor tile) {
            tile.serverTick();

            if (tile.burnTime <= 0) {
                List<Item> types = null;

                if (!tile.isRedstonePowered && tile.storage.getEnergyStored() < tile.storage.getMaxEnergyStored()) {
                    for (int i = 0; i < tile.inv.getSlots(); i++) {
                        ItemStack stack = tile.inv.getStackInSlot(i);
                        if (!stack.isEmpty()) {
                            Item item = stack.getItem();
                            if (isValidItem(stack) && (types == null || !types.contains(item))) {
                                if (types == null) {
                                    types = new ArrayList<>();
                                }
                                types.add(item);

                                tile.inv.setStackInSlot(i, StackUtil.shrink(stack, 1));
                            }
                        }
                    }

                    tile.setChanged();
                }

                if (types != null && !types.isEmpty()) {
                    int amount = types.size();
                    tile.producePerTick = (int) Math.pow(amount * 2, 2);

                    tile.maxBurnTime = 200 - (int) Math.pow(1.8, amount);
                    tile.burnTime = tile.maxBurnTime;
                } else {
                    tile.burnTime = 0;
                    tile.maxBurnTime = 0;
                    tile.producePerTick = 0;
                }
            } else {
                tile.burnTime--;
                tile.storage.receiveEnergyInternal(tile.producePerTick, false);
            }

            if ((tile.lastBurnTime != tile.burnTime || tile.lastProducePerTick != tile.producePerTick) && tile.sendUpdateWithInterval()) {
                tile.lastBurnTime = tile.burnTime;
                tile.lastProducePerTick = tile.producePerTick;
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.storage.writeToNBT(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("MaxBurnTime", this.maxBurnTime);
        compound.putInt("ProducePerTick", this.producePerTick);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.storage.readFromNBT(compound);
        this.burnTime = compound.getInt("BurnTime");
        this.maxBurnTime = compound.getInt("MaxBurnTime");
        this.producePerTick = compound.getInt("ProducePerTick");
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> isValidItem(stack);
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation;
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
    public boolean canShareTo(BlockEntity tile) {
        return true;
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.storage.getEnergyStored() / (float) this.storage.getMaxEnergyStored() * 15F;
        return (int) calc;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.bioReactor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
        return new ContainerBioReactor(windowId, playerInventory, this);
    }
}
