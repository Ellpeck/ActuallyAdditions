package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityCoalGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCoalGenerator extends BlockContainerBase {
    public BlockCoalGenerator() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE)
                .tickRandomly());
    }

//    @Override
//    public boolean isFullCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean isOpaqueCube(IBlockState state) {
//        return false;
//    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityCoalGenerator();
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityCoalGenerator) {
            if (((TileEntityCoalGenerator) tile).currentBurnTime > 0) {
                for (int i = 0; i < 5; i++) {
                    // todo: check count and speed params
                    world.spawnParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5F, (double) pos.getY() + 1.0F, (double) pos.getZ() + 0.5F, i,0.0D, 0.0D, 0.0D, 1);
                }
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileEntityCoalGenerator press = (TileEntityCoalGenerator) world.getTileEntity(pos);
            if (press != null) {
                // todo: add back
//                player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.COAL_GENERATOR.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }

            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

//    @Override
//    public IBlockState withRotation(IBlockState state, Rotation rot) {
//        return state.withProperty(BlockHorizontal.FACING, rot.rotate(state.getValue(BlockHorizontal.FACING)));
//    }
//
//    @Override
//    public IBlockState withMirror(IBlockState state, Mirror mirror) {
//        return this.withRotation(state, mirror.toRotation(state.getValue(BlockHorizontal.FACING)));
//    }
}
