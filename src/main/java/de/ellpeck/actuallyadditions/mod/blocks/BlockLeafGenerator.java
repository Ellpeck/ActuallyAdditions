/*
 * This file ("BlockLeafGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLeafGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class BlockLeafGenerator extends DirectionalBlock.Container {

    public BlockLeafGenerator() {
        super(Properties.create(Material.IRON).hardnessAndResistance(5.0F, 10.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL));
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityLeafGenerator();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case EAST:
                return Shapes.LeafGeneratorShapes.SHAPE_E;
            case SOUTH:
                return Shapes.LeafGeneratorShapes.SHAPE_S;
            case WEST:
                return Shapes.LeafGeneratorShapes.SHAPE_W;
            default:
                return Shapes.LeafGeneratorShapes.SHAPE_N;
        }
    }
}
