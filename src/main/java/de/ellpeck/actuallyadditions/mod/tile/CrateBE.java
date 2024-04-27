package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.Crate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CrateBE extends TileEntityBase{
    private final Crate.Size size;
    public CrateBE(BlockPos pos, BlockState state, Crate.Size size) {
        super(ActuallyBlocks.CRATE_SMALL.getTileEntityType(), pos, state);
        this.size = size;
    }

    public Crate.Size getSize() {
        return size;
    }
}
