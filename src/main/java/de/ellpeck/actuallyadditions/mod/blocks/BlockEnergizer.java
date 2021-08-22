/*
 * This file ("BlockEnergizer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnergizer;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnervator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockEnergizer extends BlockContainerBase {
    private final boolean isEnergizer;

    public BlockEnergizer(boolean isEnergizer) {
        super(ActuallyBlocks.defaultPickProps(0, 2.0F, 10.0F));
        this.isEnergizer = isEnergizer;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return this.isEnergizer
            ? new TileEntityEnergizer()
            : new TileEntityEnervator();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (this.isEnergizer) {
            return this.openGui(world, player, pos, TileEntityEnergizer.class);
        } else {
            return this.openGui(world, player, pos, TileEntityEnervator.class);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.ENERGIZER_SHAPE;
    }
}
