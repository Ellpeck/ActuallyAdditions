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

import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.List;

public class TileEntityRangedCollector extends TileEntityInventoryBase implements IButtonReactor {

    public static final int RANGE = 6;
    public FilterSettings filter = new FilterSettings(12, true, true, false, false, 0, -1000);

    public TileEntityRangedCollector() {
        super(6, "rangedCollector");
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
        List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.pos.getX() - RANGE, this.pos.getY() - RANGE, this.pos.getZ() - RANGE, this.pos.getX() + RANGE, this.pos.getY() + RANGE, this.pos.getZ() + RANGE));
        if (!items.isEmpty()) {
            for (EntityItem item : items) {
                if (!item.isDead && !item.cannotPickup() && StackUtil.isValid(item.getItem())) {
                    ItemStack toAdd = item.getItem().copy();
                    if (this.filter.check(toAdd)) {
                        ArrayList<ItemStack> checkList = new ArrayList<>();
                        checkList.add(toAdd);
                        if (StackUtil.canAddAll(this.inv, checkList, false)) {
                            StackUtil.addAll(this.inv, checkList, false);
                            ((WorldServer) this.world).spawnParticle(EnumParticleTypes.CLOUD, false, item.posX, item.posY + 0.45F, item.posZ, 5, 0, 0, 0, 0.03D);
                            item.setDead();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
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
}
