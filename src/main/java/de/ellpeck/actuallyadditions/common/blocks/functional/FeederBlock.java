package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.BlockShapes;
import de.ellpeck.actuallyadditions.common.blocks.FunctionalBlock;
import de.ellpeck.actuallyadditions.common.tiles.FeederTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.network.NetworkHooks;

public class FeederBlock extends FunctionalBlock {

    public FeederBlock() {
        super(Properties.create(Material.ROCK), FeederTileEntity::new, FeederTileEntity.class);
    }

    @Override
    public ActionResultType onRightClick(ActivatedContext context) {
        if (context.getWorld().isRemote) {
            return ActionResultType.SUCCESS;
        }

        NetworkHooks.openGui((ServerPlayerEntity) context.getPlayer(), (FeederTileEntity) context.getTile(), context.getPos());
        return ActionResultType.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BlockShapes.FEEDER_SHAPE;
    }
}
