/*
 * This file ("TileEntityGrinderDouble.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCrusherDouble extends TileEntityCrusher {

    public TileEntityCrusherDouble(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.CRUSHER_DOUBLE.getTileEntityType(),  pos, state, 6);
        this.isDouble = true;
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityCrusherDouble tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityCrusherDouble tile) {
            tile.serverTick();
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.crusher_double");
    }
}
