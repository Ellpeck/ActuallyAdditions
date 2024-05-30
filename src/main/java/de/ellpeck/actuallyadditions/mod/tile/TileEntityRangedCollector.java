/*
 * This file ("TileEntityRangedCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerRangedCollector;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityRangedCollector extends TileEntityInventoryBase implements IButtonReactor, MenuProvider {

    public static final int RANGE = 6;
    public FilterSettings filter = new FilterSettings(12, true, false, false, false);

    public TileEntityRangedCollector(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.RANGED_COLLECTOR.getTileEntityType(), pos, state, 6);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.filter.writeToNBT(compound, "Filter");
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.filter.readFromNBT(compound, "Filter");
    }

    @Override
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        List<ItemEntity> items = this.level.getEntitiesOfClass(ItemEntity.class, new AABB(this.worldPosition.getX() - RANGE, this.worldPosition.getY() - RANGE, this.worldPosition.getZ() - RANGE, this.worldPosition.getX() + RANGE, this.worldPosition.getY() + RANGE, this.worldPosition.getZ() + RANGE));
        if (!items.isEmpty()) {
            for (ItemEntity item : items) {
                if (item.isAlive() && !item.hasPickUpDelay() && !item.getItem().isEmpty()) {
                    ItemStack toAdd = item.getItem().copy();
                    if (this.filter.check(toAdd)) {
                        ArrayList<ItemStack> checkList = new ArrayList<>();
                        checkList.add(toAdd);
                        if (StackUtil.canAddAll(this.inv, checkList, false)) {
                            StackUtil.addAll(this.inv, checkList, false);
                            ((ServerLevel) this.level).sendParticles(ParticleTypes.CLOUD, item.getX(), item.getY() + 0.45F, item.getZ(), 5, 0, 0, 0, 0.03D);
                            item.discard();
                        }
                    }
                }
            }
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityRangedCollector tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityRangedCollector tile) {
            tile.serverTick();

            if (!tile.isRedstonePowered && !tile.isPulseMode) {
                tile.activateOnPulse();
            }

            if (tile.filter.needsUpdateSend() && tile.sendUpdateWithInterval()) {
                tile.filter.updateLasts();
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation;
    }

    @Override
    public void onButtonPressed(int buttonID, Player player) {
        this.filter.onButtonPressed(buttonID);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.rangedCollector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory playerInventory, @Nonnull Player player) {
        return new ContainerRangedCollector(windowId, playerInventory, this);
    }
}
