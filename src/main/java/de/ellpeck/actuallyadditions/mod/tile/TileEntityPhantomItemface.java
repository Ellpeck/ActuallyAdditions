/*
 * This file ("TileEntityPhantomItemface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.capabilities.Capabilities;
import net.neoforged.neoforge.common.capabilities.Capability;

public class TileEntityPhantomItemface extends TileEntityPhantomface {

    public TileEntityPhantomItemface(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.PHANTOM_ITEMFACE.getTileEntityType(), pos, state);
        this.type = BlockPhantom.Type.ITEMFACE;
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomItemface tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomItemface tile) {
            tile.serverTick();
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || this.isBoundThingInRange();
    }

    @Override
    public boolean isBoundThingInRange() {
        if (super.isBoundThingInRange()) {
            BlockEntity tile = this.level.getBlockEntity(this.getBoundPosition());
            if (tile != null) {
                for (Direction facing : Direction.values()) {
                    if (tile.getCapability(Capabilities.ITEM_HANDLER, facing).isPresent()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected boolean isCapabilitySupported(Capability<?> capability) {
        return capability == Capabilities.ITEM_HANDLER;
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || this.isBoundThingInRange();
    }
}
