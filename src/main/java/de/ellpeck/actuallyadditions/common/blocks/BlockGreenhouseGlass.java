package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Random;

public class BlockGreenhouseGlass extends Block {

    public BlockGreenhouseGlass() {
        super(Block.Properties.create(Material.GLASS)
                .hardnessAndResistance(0.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.GLASS)
                .tickRandomly());
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public int getLightOpacity(IBlockState state) {
        return 0;
    }

    @Override
    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        IBlockState otherState = world.getBlockState(pos.offset(side));
        Block block = otherState.getBlock();

        return state != otherState || block != this && super.shouldSideBeRendered(state, world, pos, side);

    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote) return;
        if (world.canBlockSeeSky(pos) && world.isDaytime()) {
            Triple<BlockPos, IBlockState, IGrowable> trip = firstBlock(world, pos);
            boolean once = false;
            if (trip != null) for (int i = 0; i < 3; i++) {
                IBlockState growState = i == 0 ? trip.getMiddle() : world.getBlockState(trip.getLeft());
                if (growState.getBlock() == trip.getRight() && trip.getRight().canGrow(world, trip.getLeft(), growState, false)) {
                    trip.getRight().grow(world, rand, trip.getLeft(), growState);
                    once = true;
                }
            }
            if (once) world.playEvent(2005, trip.getMiddle().isOpaqueCube() ? trip.getLeft().up() : trip.getLeft(), 0);
        }
    }

    public Triple<BlockPos, IBlockState, IGrowable> firstBlock(World world, BlockPos glassPos) {
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos(glassPos);
        while (true) {
            mut.setPos(mut.getX(), mut.getY() - 1, mut.getZ());
            if (mut.getY() < 0) return null;
            IBlockState state = world.getBlockState(mut);
            if (state.isOpaqueCube() || state.getBlock() instanceof IGrowable || state.getBlock() == this) {
                if (state.getBlock() instanceof IGrowable) return Triple.of(mut.toImmutable(), state, (IGrowable) state.getBlock());
                else return null;
            }
        }
    }
}
