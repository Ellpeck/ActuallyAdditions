package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class LampBlock extends ActuallyBlock {
    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public LampBlock() {
        super(Properties.create(Material.ROCK));
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);
        if (hand == Hand.MAIN_HAND && stack.isEmpty()) {
            world.setBlockState(pos, state.getBlock().getDefaultState().with(LIT, !state.getBlockState().get(LIT)), 2);
            return ActionResultType.SUCCESS;
        }

        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(LIT) ? 15 : 0;
    }
}
