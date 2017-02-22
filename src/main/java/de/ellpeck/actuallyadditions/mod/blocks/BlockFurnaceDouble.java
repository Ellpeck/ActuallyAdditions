/*
 * This file ("BlockFurnaceDouble.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockFurnaceDouble extends BlockContainerBase{

    public static final PropertyBool IS_ON = PropertyBool.create("on");

    public BlockFurnaceDouble(String name){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
    }


    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityFurnaceDouble();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand){
        if(state.getValue(IS_ON)){
            for(int i = 0; i < 5; i++){
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)pos.getX()+0.5F, (double)pos.getY()+1.0F, (double)pos.getZ()+0.5F, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityFurnaceDouble furnace = (TileEntityFurnaceDouble)world.getTileEntity(pos);
            if(furnace != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.FURNACE_DOUBLE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos){
        return state.getValue(IS_ON) ? 12 : 0;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        world.setBlockState(pos, state.withProperty(BlockHorizontal.FACING, player.getHorizontalFacing().getOpposite()), 2);

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        boolean isOn = meta >= 4;
        EnumFacing facing = EnumFacing.getHorizontal(isOn ? meta-4 : meta);
        return this.getDefaultState().withProperty(BlockHorizontal.FACING, facing).withProperty(IS_ON, isOn);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        int meta = state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
        return state.getValue(IS_ON) ? meta+4 : meta;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, BlockHorizontal.FACING, IS_ON);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot){
        return state.withProperty(BlockHorizontal.FACING, rot.rotate(state.getValue(BlockHorizontal.FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror){
        return this.withRotation(state, mirror.toRotation(state.getValue(BlockHorizontal.FACING)));
    }

    @Override
    protected ItemBlockBase getItemBlock(){
        return new TheItemBlock(this);
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
        }

        @Override
        public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
            tooltip.add(TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".previouslyDoubleFurnace"));
        }
    }
}
