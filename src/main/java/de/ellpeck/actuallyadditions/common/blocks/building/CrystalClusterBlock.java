package de.ellpeck.actuallyadditions.common.blocks.building;

import de.ellpeck.actuallyadditions.common.blocks.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.common.blocks.types.Crystals;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class CrystalClusterBlock extends FullyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public CrystalClusterBlock(Crystals crystal) {
        super(Properties.create(Material.GLASS)
                .setLightLevel((e) -> 7)
                .sound(SoundType.GLASS)
                .hardnessAndResistance(0.25f, 1.0f));
    }

    @Override
    public BlockState getBaseConstructorState() {
        return this.stateContainer.getBaseState().with(FACING, Direction.UP);
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }
}
