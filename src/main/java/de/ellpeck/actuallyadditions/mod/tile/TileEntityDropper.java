/*
 * This file ("TileEntityDropper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerDropper;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityDropper extends TileEntityInventoryBase implements INamedContainerProvider {

    private int currentTime;

    public TileEntityDropper() {
        super(ActuallyBlocks.DROPPER.getTileEntityType(),  9);
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("CurrentTime", this.currentTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentTime = compound.getInt("CurrentTime");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (!this.isRedstonePowered && !this.isPulseMode) {
                if (this.currentTime > 0) {
                    this.currentTime--;
                    if (this.currentTime <= 0) {
                        this.doWork();
                    }
                } else {
                    this.currentTime = 5;
                }
            }
        }
    }

    private void doWork() {
        ItemStack theoreticalRemove = this.removeFromInventory(false);
        if (StackUtil.isValid(theoreticalRemove)) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            ItemStack drop = theoreticalRemove.copy();
            drop.setCount(1);
            WorldUtil.dropItemAtSide(WorldUtil.getDirectionByPistonRotation(state), this.level, this.worldPosition, drop);
            this.removeFromInventory(true);
        }
    }

    public ItemStack removeFromInventory(boolean actuallyDo) {
        for (int i = 0; i < this.inv.getSlots(); i++) {
            if (StackUtil.isValid(this.inv.getStackInSlot(i))) {
                ItemStack slot = this.inv.getStackInSlot(i).copy();
                if (actuallyDo) {
                    this.inv.setStackInSlot(i, StackUtil.shrink(this.inv.getStackInSlot(i), 1));
                    this.setChanged();
                }
                return slot;
            }
        }
        return StackUtil.getEmpty();
    }

    @Override
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        this.doWork();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.actuallyadditions.dropper");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerDropper(windowId, playerInventory, this);
    }
}
