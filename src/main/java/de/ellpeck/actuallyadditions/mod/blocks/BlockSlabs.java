/*
 * This file ("BlockSlabs.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSlabs extends BlockBase {

    public static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

    private final IBlockState fullBlockState;

    public BlockSlabs(String name, Block fullBlock) {
        this(name, fullBlock.getDefaultState());
    }

    public BlockSlabs(String name, IBlockState fullBlockState) {
        super(fullBlockState.getMaterial(), name);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.fullBlockState = fullBlockState;
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
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (facing.ordinal() == 1) { return this.getStateFromMeta(meta); }
        if (facing.ordinal() == 0 || hitY >= 0.5F) { return this.getStateFromMeta(meta + 1); }
        return this.getStateFromMeta(meta);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP ? AABB_TOP_HALF : AABB_BOTTOM_HALF;
    }

    @Override
    protected ItemBlockBase getItemBlock() {
        return new TheItemBlock(this);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockSlab.HALF, meta == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.BOTTOM ? 0 : 1;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockSlab.HALF);
    }

    public static class TheItemBlock extends ItemBlockBase {

        public TheItemBlock(Block block) {
            super(block);
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
            ItemStack stack = player.getHeldItem(hand);
            if (StackUtil.isValid(stack) && player.canPlayerEdit(pos.offset(facing), facing, stack)) {
                IBlockState state = world.getBlockState(pos);

                if (state.getBlock() == this.block) {
                    BlockSlabs theBlock = (BlockSlabs) this.block;
                    if (facing == EnumFacing.UP && state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.BOTTOM || facing == EnumFacing.DOWN && state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP) {
                        IBlockState newState = theBlock.fullBlockState;
                        AxisAlignedBB bound = newState.getCollisionBoundingBox(world, pos);

                        if (bound != Block.NULL_AABB && world.checkNoEntityCollision(bound.offset(pos)) && world.setBlockState(pos, newState, 11)) {
                            SoundType soundtype = theBlock.fullBlockState.getBlock().getSoundType(theBlock.fullBlockState, world, pos, player);
                            world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                            player.setHeldItem(hand, StackUtil.shrink(stack, 1));
                        }

                        return EnumActionResult.SUCCESS;
                    }
                }

                return this.tryPlace(player, stack, hand, world, pos.offset(facing)) ? EnumActionResult.SUCCESS : super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
            } else {
                return EnumActionResult.FAIL;
            }
        }

        @Override
        @SideOnly(Side.CLIENT)
        public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
            IBlockState state = worldIn.getBlockState(pos);

            if (state.getBlock() == this.block) {
                if (side == EnumFacing.UP && state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.BOTTOM || side == EnumFacing.DOWN && state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP) { return true; }
            }

            return worldIn.getBlockState(pos.offset(side)).getBlock() == this.block || super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
        }

        private boolean tryPlace(EntityPlayer player, ItemStack stack, EnumHand hand, World world, BlockPos pos) {
            IBlockState iblockstate = world.getBlockState(pos);

            if (iblockstate.getBlock() == this.block) {
                BlockSlabs theBlock = (BlockSlabs) this.block;
                IBlockState newState = theBlock.fullBlockState;
                AxisAlignedBB bound = newState.getCollisionBoundingBox(world, pos);

                if (bound != Block.NULL_AABB && world.checkNoEntityCollision(bound.offset(pos)) && world.setBlockState(pos, newState, 11)) {
                    SoundType soundtype = theBlock.fullBlockState.getBlock().getSoundType(theBlock.fullBlockState, world, pos, player);
                    world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

                    player.setHeldItem(hand, StackUtil.shrink(stack, 1));
                }

                return true;
            }

            return false;
        }

        @Override
        public String getTranslationKey(ItemStack stack) {
            return this.getTranslationKey();
        }
    }
}
