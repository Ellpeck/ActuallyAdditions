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

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFilter;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityLaserRelayItemWhitelist extends TileEntityLaserRelayItem implements IButtonReactor, INamedContainerProvider {

    public FilterSettings leftFilter = new FilterSettings(12, true, true, false, false, 0, -1000);
    public FilterSettings rightFilter = new FilterSettings(12, true, true, false, false, 0, -2000);

    public TileEntityLaserRelayItemWhitelist() {
        super(ActuallyTiles.LASERRELAYITEMWHITELIST_TILE.get());
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
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.leftFilter.writeToNBT(compound, "LeftFilter");
        this.rightFilter.writeToNBT(compound, "RightFilter");
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.leftFilter.readFromNBT(compound, "LeftFilter");
        this.rightFilter.readFromNBT(compound, "RightFilter");
    }

    @Override
    public void onButtonPressed(int buttonID, PlayerEntity player) {
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
            handler.getNormalHandler().ifPresent(itemHandler -> {
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    ItemStack stack = itemHandler.getStackInSlot(i);
                    if (StackUtil.isValid(stack)) {
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

        if (!FilterSettings.check(copy, usedSettings.filterInventory, true, usedSettings.respectMeta, usedSettings.respectNBT, usedSettings.respectMod, usedSettings.respectOredict)) {
            for (int k = 0; k < usedSettings.filterInventory.getSlots(); k++) {
                ItemStack slot = usedSettings.filterInventory.getStackInSlot(k);
                if (StackUtil.isValid(slot)) {
                    if (SlotFilter.isFilter(slot)) {
                        ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerFilter.SLOT_AMOUNT);
                        ItemDrill.loadSlotsFromNBT(inv, slot);

                        boolean did = false;
                        for (int j = 0; j < inv.getSlots(); j++) {
                            if (!StackUtil.isValid(inv.getStackInSlot(j))) {
                                inv.setStackInSlot(j, copy);
                                did = true;
                                break;
                            }
                        }

                        if (did) {
                            ItemDrill.writeSlotsToNBT(inv, slot);
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
    public void updateEntity() {
        super.updateEntity();

        if (!this.level.isClientSide) {
            if ((this.leftFilter.needsUpdateSend() || this.rightFilter.needsUpdateSend()) && this.sendUpdateWithInterval()) {
                this.leftFilter.updateLasts();
                this.rightFilter.updateLasts();
            }
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerLaserRelayItemWhitelist(windowId, playerInventory, this);
    }
}
