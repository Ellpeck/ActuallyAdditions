/*
 * This file ("BlockLampPowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockLampPowerer extends BlockBase {

    public BlockLampPowerer() {
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos otherPos) {
        this.updateLamp(worldIn, pos);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, BlockState state) {
        this.updateLamp(world, pos);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, EntityLivingBase player, ItemStack stack) {
        int rotation = Direction.getDirectionFromEntityLiving(pos, player).ordinal();
        world.setBlockState(pos, this.getStateFromMeta(rotation), 2);

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    private void updateLamp(World world, BlockPos pos) {
        if (!world.isRemote) {
            BlockState state = world.getBlockState(pos);
            BlockPos coords = pos.offset(WorldUtil.getDirectionByPistonRotation(state));
            this.updateLampsAtPos(world, coords, world.getRedstonePowerFromNeighbors(pos) > 0, new ArrayList<BlockPos>());
        }
    }

    private void updateLampsAtPos(World world, BlockPos pos, boolean powered, List<BlockPos> updatedAlready) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof BlockColoredLamp) {
            boolean isOn = ((BlockColoredLamp) block).isOn;
            if (powered) {
                if (!isOn) {
                    world.setBlockState(pos, ActuallyBlocks.blockColoredLampOn.getDefaultState().withProperty(BlockColoredLamp.TYPE, state.getValue(BlockColoredLamp.TYPE)), 2);
                }
            } else {
                if (isOn) {
                    world.setBlockState(pos, ActuallyBlocks.blockColoredLamp.getDefaultState().withProperty(BlockColoredLamp.TYPE, state.getValue(BlockColoredLamp.TYPE)), 2);
                }
            }

            this.updateSurrounding(world, pos, powered, updatedAlready);
        }
    }

    private void updateSurrounding(World world, BlockPos pos, boolean powered, List<BlockPos> updatedAlready) {
        for (Direction side : Direction.values()) {
            BlockPos offset = pos.offset(side);
            if (!updatedAlready.contains(offset)) {
                updatedAlready.add(pos);
                this.updateLampsAtPos(world, offset, powered, updatedAlready);
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockDirectional.FACING, Direction.byIndex(meta));
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(BlockDirectional.FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockDirectional.FACING);
    }

    @Override
    public BlockState withRotation(BlockState state, Rotation rot) {
        return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
    }

    @Override
    public BlockState withMirror(BlockState state, Mirror mirror) {
        return this.withRotation(state, mirror.toRotation(state.getValue(BlockDirectional.FACING)));
    }
}
