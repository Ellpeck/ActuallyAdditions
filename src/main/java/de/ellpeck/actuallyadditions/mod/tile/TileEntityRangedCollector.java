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

import de.ellpeck.actuallyadditions.mod.inventory.ContainerRangedCollector;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityRangedCollector extends TileEntityInventoryBase implements IButtonReactor, INamedContainerProvider {

    public static final int RANGE = 6;
    public FilterSettings filter = new FilterSettings(12, true, true, false, false, 0, -1000);

    public TileEntityRangedCollector() {
        super(ActuallyTiles.RANGEDCOLLECTOR_TILE.get(), 6);
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.filter.writeToNBT(compound, "Filter");
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.filter.readFromNBT(compound, "Filter");
    }

    @Override
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        List<ItemEntity> items = this.level.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(this.worldPosition.getX() - RANGE, this.worldPosition.getY() - RANGE, this.worldPosition.getZ() - RANGE, this.worldPosition.getX() + RANGE, this.worldPosition.getY() + RANGE, this.worldPosition.getZ() + RANGE));
        if (!items.isEmpty()) {
            for (ItemEntity item : items) {
                if (item.isAlive() && !item.hasPickUpDelay() && StackUtil.isValid(item.getItem())) {
                    ItemStack toAdd = item.getItem().copy();
                    if (this.filter.check(toAdd)) {
                        ArrayList<ItemStack> checkList = new ArrayList<>();
                        checkList.add(toAdd);
                        if (StackUtil.canAddAll(this.inv, checkList, false)) {
                            StackUtil.addAll(this.inv, checkList, false);
                            ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, item.getX(), item.getY() + 0.45F, item.getZ(), 5, 0, 0, 0, 0.03D);
                            item.remove();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (!this.isRedstonePowered && !this.isPulseMode) {
                this.activateOnPulse();
            }

            if (this.filter.needsUpdateSend() && this.sendUpdateWithInterval()) {
                this.filter.updateLasts();
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation;
    }

    @Override
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        this.filter.onButtonPressed(buttonID);
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerRangedCollector(windowId, playerInventory, this);
    }
}
