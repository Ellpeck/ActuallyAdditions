/*
 * This file ("BlockLavaFactoryController.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.blocks.blockhuds.LavaFactoryControllerHud;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLavaFactoryController;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;


public class BlockLavaFactoryController extends DirectionalBlock.Container implements IHudDisplay {
    private static final IBlockHud HUD = new LavaFactoryControllerHud();

    public BlockLavaFactoryController() {
        super(ActuallyBlocks.defaultPickProps(4.5F, 20.0F).sound(SoundType.METAL));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityLavaFactoryController(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityLavaFactoryController::clientTick : TileEntityLavaFactoryController::serverTick;
    }

    @Override
    public IBlockHud getHud() {
        return HUD;
    }



/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return VoxelShapes.LavaFactoryShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.LavaFactoryShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.LavaFactoryShapes.SHAPE_W;
            default:
                return VoxelShapes.LavaFactoryShapes.SHAPE_N;
        }
    }*/
}
