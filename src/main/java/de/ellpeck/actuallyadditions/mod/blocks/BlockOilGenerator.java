/*
 * This file ("BlockOilGenerator.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityOilGenerator;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockOilGenerator extends BlockContainerBase{

    public BlockOilGenerator(String name){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityOilGenerator();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand){
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityOilGenerator){
            if(((TileEntityOilGenerator)tile).currentBurnTime > 0){
                for(int i = 0; i < 5; i++){
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)pos.getX()+0.5F, (double)pos.getY()+1.0F, (double)pos.getZ()+0.5F, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityOilGenerator generator = (TileEntityOilGenerator)world.getTileEntity(pos);
            if(generator != null){
                if(!this.tryUseItemOnTank(player, hand, generator.tank)){
                    player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.OIL_GENERATOR.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        world.setBlockState(pos, state.withProperty(BlockHorizontal.FACING, player.getHorizontalFacing().getOpposite()), 2);

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, BlockHorizontal.FACING);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot){
        return state.withProperty(BlockHorizontal.FACING, rot.rotate(state.getValue(BlockHorizontal.FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror){
        return this.withRotation(state, mirror.toRotation(state.getValue(BlockHorizontal.FACING)));
    }
}
