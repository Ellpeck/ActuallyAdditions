/*
 * This file ("TileEntityFishingNet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

public class TileEntityFishingNet extends TileEntityBase {

    public int timeUntilNextDrop;

    public TileEntityFishingNet() {
        super(ActuallyTiles.FISHINGNET_TILE.get());
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("TimeUntilNextDrop", this.timeUntilNextDrop);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.timeUntilNextDrop = compound.getInt("TimeUntilNextDrop");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.world.isRemote) {
            if (!this.isRedstonePowered) {
                if (this.world.getBlockState(this.pos.down()).getMaterial() == Material.WATER) {
                    if (this.timeUntilNextDrop > 0) {
                        this.timeUntilNextDrop--;
                        if (this.timeUntilNextDrop <= 0) {
                            // TODO: [port] come back to this as the loot table may be wrong
                            LootContext.Builder builder = new LootContext.Builder((ServerWorld) this.world);
                            List<ItemStack> fishables = this.world.getServer().getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_FISHING).generate(builder.build(LootParameterSets.FISHING));
                            for (ItemStack fishable : fishables) {
                                ItemStack leftover = this.storeInContainer(fishable);
                                if (StackUtil.isValid(leftover)) {
                                    ItemEntity item = new ItemEntity(this.world, this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5, leftover.copy());
                                    item.lifespan = 2000;
                                    this.world.addEntity(item);
                                }
                            }
                        }
                    } else {
                        int time = 15000;
                        this.timeUntilNextDrop = time + this.world.rand.nextInt(time / 2);
                    }
                }
            }
        }
    }

    private ItemStack storeInContainer(ItemStack stack) {
        for (Direction side : Direction.values()) {
            TileEntity tile = this.tilesAround[side.ordinal()];
            if (tile != null) {
                // TODO: [port] come back and make sure this works
                ItemStack copyStack = stack.copy();
                stack = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()).map(cap -> {
                    ItemStack localStack = ItemStack.EMPTY;
                    for (int i = 0; i < cap.getSlots(); i++) {
                        localStack = cap.insertItem(i, copyStack, false);

                        if (!StackUtil.isValid(localStack)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    return localStack;
                }).orElse(ItemStack.EMPTY);
            }
        }
        return stack;
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return true;
    }
}
