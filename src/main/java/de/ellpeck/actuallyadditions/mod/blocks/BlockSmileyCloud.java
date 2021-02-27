/*
 * This file ("BlockSmileyCloud.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.relauncher.OnlyIn;

import java.util.Random;

public class BlockSmileyCloud extends BlockContainerBase {

    public BlockSmileyCloud() {
        super(Material.CLOTH, this.name);
        this.setHardness(0.5F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.CLOTH);
        this.setTickRandomly(true);
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (world.rand.nextInt(30) == 0) {
            for (int i = 0; i < 2; i++) {
                double d = world.rand.nextGaussian() * 0.02D;
                double d1 = world.rand.nextGaussian() * 0.02D;
                double d2 = world.rand.nextGaussian() * 0.02D;
                world.spawnParticle(EnumParticleTypes.HEART, pos.getX() + world.rand.nextFloat(), pos.getY() + 0.65 + world.rand.nextFloat(), pos.getZ() + world.rand.nextFloat(), d, d1, d2);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction f6, float f7, float f8, float f9) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntitySmileyCloud) {
                player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.CLOUD.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());

                //TheAchievements.NAME_SMILEY_CLOUD.get(player);
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySmileyCloud();
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, EntityLivingBase player, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(BlockHorizontal.FACING, player.getHorizontalFacing().getOpposite()), 2);

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockHorizontal.FACING, Direction.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockHorizontal.FACING);
    }

    @Override
    public BlockState withRotation(BlockState state, Rotation rot) {
        return state.withProperty(BlockHorizontal.FACING, rot.rotate(state.getValue(BlockHorizontal.FACING)));
    }

    @Override
    public BlockState withMirror(BlockState state, Mirror mirror) {
        return this.withRotation(state, mirror.toRotation(state.getValue(BlockHorizontal.FACING)));
    }
}
