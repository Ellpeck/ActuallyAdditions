/*
 * This file ("TileEntityLaserRelayItemWhitelist.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFilter;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.items.DrillItem;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;

public class TileEntityLaserRelayItemAdvanced extends TileEntityLaserRelayItem implements IButtonReactor, MenuProvider {

    public FilterSettings leftFilter = new FilterSettings(12, true, false, false, false);
    public FilterSettings rightFilter = new FilterSettings(12, true, false, false, false);

    public TileEntityLaserRelayItemAdvanced(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.LASER_RELAY_ITEM_ADVANCED.getTileEntityType(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayItemAdvanced tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayItemAdvanced tile) {
            tile.serverTick();

            if ((tile.leftFilter.needsUpdateSend() || tile.rightFilter.needsUpdateSend()) && tile.sendUpdateWithInterval()) {
                tile.leftFilter.updateLasts();
                tile.rightFilter.updateLasts();
            }
        }
    }

    @Override
    public int getPriority() {
        return super.getPriority() + 10;
    }

    @Override
    public boolean isWhitelisted(ItemStack stack, boolean output) {
        return output
            ? this.rightFilter.check(stack)
            : this.leftFilter.check(stack);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.leftFilter.writeToNBT(compound, "LeftFilter");
        this.rightFilter.writeToNBT(compound, "RightFilter");
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.leftFilter.readFromNBT(compound, "LeftFilter");
        this.rightFilter.readFromNBT(compound, "RightFilter");
    }

    @Override
    public void onButtonPressed(int buttonID, Player player) {
        this.leftFilter.onButtonPressed(buttonID);
        this.rightFilter.onButtonPressed(buttonID);
        if (buttonID == 2) {
            this.addWhitelistSmart(false);
        } else if (buttonID == 3) {
            this.addWhitelistSmart(true);
        }
    }

    private void addWhitelistSmart(boolean output) {
        for (SlotlessableItemHandlerWrapper handler : this.handlersAround.values()) {
            Optional.ofNullable(handler.getNormalHandler()).ifPresent(itemHandler -> {
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    ItemStack stack = itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        this.addWhitelistSmart(output, stack);
                    }
                }
            });
        }
    }

    private void addWhitelistSmart(boolean output, ItemStack stack) {
        FilterSettings usedSettings = output
            ? this.rightFilter
            : this.leftFilter;
        ItemStack copy = stack.copy();
        copy.setCount(1);

        if (!FilterSettings.check(copy, usedSettings.filterInventory, true, usedSettings.respectMod, usedSettings.matchDamage, usedSettings.matchNBT)) {
            for (int k = 0; k < usedSettings.filterInventory.getSlots(); k++) {
                ItemStack slot = usedSettings.filterInventory.getStackInSlot(k);
                if (!slot.isEmpty()) {
                    if (SlotFilter.isFilter(slot)) {
                        ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerFilter.SLOT_AMOUNT);
                        DrillItem.loadSlotsFromNBT(inv, slot);

                        boolean did = false;
                        for (int j = 0; j < inv.getSlots(); j++) {
                            if (inv.getStackInSlot(j).isEmpty()) {
                                inv.setStackInSlot(j, copy);
                                did = true;
                                break;
                            }
                        }

                        if (did) {
                            DrillItem.writeSlotsToNBT(inv, slot);
                            break;
                        }
                    }
                } else {
                    usedSettings.filterInventory.setStackInSlot(k, copy);
                    break;
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.laserRelayAdvanced");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerLaserRelayItemWhitelist(windowId, playerInventory, this);
    }
}
