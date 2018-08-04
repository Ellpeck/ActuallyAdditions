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

import java.util.Random;

import org.apache.commons.lang3.tuple.Triple;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGreenhouseGlass extends BlockBase {

	public BlockGreenhouseGlass(String name) {
		super(Material.GLASS, name);
		this.setHarvestLevel("pickaxe", 0);
		this.setHardness(0.5F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.GLASS);
		this.setTickRandomly(true);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
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

			for (int i = 0; i < 3; i++)
				if (trip != null && trip.getRight().canGrow(world, trip.getLeft(), trip.getMiddle(), false)) {
					trip.getRight().grow(world, rand, trip.getLeft(), trip.getMiddle());
					once = true;
				}

			if (once) world.playEvent(2005, trip.getMiddle().isOpaqueCube() ? trip.getLeft().up() : trip.getLeft(), 0);
		}
	}

	public static Triple<BlockPos, IBlockState, IGrowable> firstBlock(World world, BlockPos glassPos) {
		BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos(glassPos);
		while (true) {
			mut.setPos(mut.getX(), mut.getY() - 1, mut.getZ());
			IBlockState state = world.getBlockState(mut);
			if (!state.getBlock().isAir(state, world, mut)) {
				if (state.getBlock() instanceof IGrowable) return Triple.of(mut.toImmutable(), state, (IGrowable) state.getBlock());
				else return null;
			}
		}
	}
}
