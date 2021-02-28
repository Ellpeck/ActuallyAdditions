/*
 * This file ("BlockCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockCoffeeMachine extends DirectionalBlock.Container {
    public BlockCoffeeMachine() {
        super(ActuallyBlocks.defaultPickProps(0));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileEntityCoffeeMachine tile = (TileEntityCoffeeMachine) world.getTileEntity(pos);
            if (tile != null) {
                if (!this.tryUseItemOnTank(player, hand, tile.tank)) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, tile, pos);
                }
            }
            return ActionResultType.PASS;
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityCoffeeMachine();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case EAST:
                return Shapes.CoffeeMachineShapes.EAST;
            case SOUTH:
                return Shapes.CoffeeMachineShapes.SOUTH;
            case WEST:
                return Shapes.CoffeeMachineShapes.WEST;
            default:
                return Shapes.CoffeeMachineShapes.NORTH;
        }
    }
}
