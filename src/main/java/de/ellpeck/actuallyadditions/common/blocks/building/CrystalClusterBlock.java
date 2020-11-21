package de.ellpeck.actuallyadditions.common.blocks.building;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import de.ellpeck.actuallyadditions.common.blocks.types.Crystals;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class CrystalClusterBlock extends ActuallyBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public CrystalClusterBlock(Crystals crystal) {
        super(Properties.create(Material.GLASS)
                .setLightLevel((e) -> 7)
                .sound(SoundType.GLASS)
                .hardnessAndResistance(0.25f, 1.0f));

        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }
}
