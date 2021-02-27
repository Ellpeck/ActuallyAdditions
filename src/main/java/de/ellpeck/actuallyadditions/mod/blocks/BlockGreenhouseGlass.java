/*
 * This file ("BlockGreenhouseGlass.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.relauncher.OnlyIn;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Random;

public class BlockGreenhouseGlass extends BlockBase {

    public BlockGreenhouseGlass() {
        super(Material.GLASS, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(0.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.GLASS);
        this.setTickRandomly(true);
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public int getLightOpacity(BlockState state) {
        return 0;
    }

    @Override
    @Deprecated
    @OnlyIn(Dist.CLIENT)
    public boolean shouldSideBeRendered(BlockState state, IBlockAccess world, BlockPos pos, Direction side) {
        BlockState otherState = world.getBlockState(pos.offset(side));
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
    public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
        if (world.isRemote) {
            return;
        }
        if (world.canBlockSeeSky(pos) && world.isDaytime()) {
            Triple<BlockPos, BlockState, IGrowable> trip = this.firstBlock(world, pos);
            boolean once = false;
            if (trip != null) {
                for (int i = 0; i < 3; i++) {
                    BlockState growState = i == 0
                        ? trip.getMiddle()
                        : world.getBlockState(trip.getLeft());
                    if (growState.getBlock() == trip.getRight() && trip.getRight().canGrow(world, trip.getLeft(), growState, false)) {
                        trip.getRight().grow(world, rand, trip.getLeft(), growState);
                        once = true;
                    }
                }
            }
            if (once) {
                world.playEvent(2005, trip.getMiddle().isOpaqueCube()
                    ? trip.getLeft().up()
                    : trip.getLeft(), 0);
            }
        }
    }

    public Triple<BlockPos, BlockState, IGrowable> firstBlock(World world, BlockPos glassPos) {
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos(glassPos);
        while (true) {
            mut.setPos(mut.getX(), mut.getY() - 1, mut.getZ());
            if (mut.getY() < 0) {
                return null;
            }
            BlockState state = world.getBlockState(mut);
            if (state.isOpaqueCube() || state.getBlock() instanceof IGrowable || state.getBlock() == this) {
                if (state.getBlock() instanceof IGrowable) {
                    return Triple.of(mut.toImmutable(), state, (IGrowable) state.getBlock());
                } else {
                    return null;
                }
            }
        }
    }
}
