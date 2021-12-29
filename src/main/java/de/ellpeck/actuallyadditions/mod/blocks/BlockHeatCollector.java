/*
 * This file ("BlockHeatCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityHeatCollector;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockHeatCollector extends BlockContainerBase {
    public BlockHeatCollector() {
        super(ActuallyBlocks.defaultPickProps(0, 2.5F, 10.0F));
    }

    //@Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityHeatCollector();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.HEAT_COLLECTOR_SHAPE;
    }
}
