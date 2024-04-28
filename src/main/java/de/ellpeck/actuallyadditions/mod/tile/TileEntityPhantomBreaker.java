/*
 * This file ("TileEntityPhantomBreaker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TileEntityPhantomBreaker extends TileEntityPhantomPlacer {

    public TileEntityPhantomBreaker(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.PHANTOM_BREAKER.getTileEntityType(), pos, state, 9);
        this.isBreaker = true;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation;
    }

    @Override
    public IRemover getRemover() {
        return ItemStackHandlerAA.REMOVE_TRUE;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.phantomBreaker");
    }
}
