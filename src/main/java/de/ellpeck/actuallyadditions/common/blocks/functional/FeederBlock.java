package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import de.ellpeck.actuallyadditions.common.tiles.ActuallyTiles;
import de.ellpeck.actuallyadditions.common.tiles.FeederTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class FeederBlock extends ActuallyBlock {

    public FeederBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape FEEDER_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(11, 14, 11, 12, 15, 12), Block.makeCuboidShape(11, 14, 4, 12, 15, 5),
            Block.makeCuboidShape(4, 14, 11, 5, 15, 12), Block.makeCuboidShape(4, 14, 4, 5, 15, 5),
            Block.makeCuboidShape(4, 13, 4, 12, 14, 12), Block.makeCuboidShape(4, 14, 12, 12, 15, 15),
            Block.makeCuboidShape(4, 14, 1, 12, 15, 4), Block.makeCuboidShape(1, 14, 1, 4, 15, 15),
            Block.makeCuboidShape(12, 14, 1, 15, 15, 15), Block.makeCuboidShape(1, 1, 1, 15, 2, 15),
            Block.makeCuboidShape(14, 2, 1, 15, 14, 15), Block.makeCuboidShape(1, 2, 1, 2, 14, 15),
            Block.makeCuboidShape(2, 2, 14, 14, 14, 15), Block.makeCuboidShape(2, 2, 1, 14, 14, 2)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ActuallyTiles.FEEDER_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }

        TileEntity te = worldIn.getTileEntity(pos);
        if (! (te instanceof FeederTileEntity)) {
            return ActionResultType.FAIL;
        }

        NetworkHooks.openGui((ServerPlayerEntity) player, (FeederTileEntity) te, pos);
        return ActionResultType.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return FEEDER_SHAPE;
    }
}
