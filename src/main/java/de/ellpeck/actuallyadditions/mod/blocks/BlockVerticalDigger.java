/*
 * This file ("BlockMiner.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.blocks.blockhuds.IBlockHud;
import de.ellpeck.actuallyadditions.mod.blocks.blockhuds.MinerHud;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityVerticalDigger;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;


public class BlockVerticalDigger extends DirectionalBlock.Container implements IHudDisplay {
    private static final IBlockHud HUD = new MinerHud();

    public BlockVerticalDigger() {
        super(ActuallyBlocks.defaultPickProps(8F, 30F));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level world, BlockPos pos, Player player, BlockHitResult pHitResult) {
        return this.openGui(world, player, pos, TileEntityVerticalDigger.class);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityVerticalDigger(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityVerticalDigger::clientTick : TileEntityVerticalDigger::serverTick;
    }

    @Override
    public IBlockHud getHud() {
        return HUD;
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return VoxelShapes.MinerShapes.SHAPE_N;
            case EAST:
                return VoxelShapes.MinerShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.MinerShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.MinerShapes.SHAPE_W;
            default:
                return VoxelShapes.MinerShapes.SHAPE_N;
        }
    }*/
}
