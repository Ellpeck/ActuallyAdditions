/*
 * This file ("BlockSmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;


import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockSmileyCloud extends BlockContainerBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 3);

    public BlockSmileyCloud(String name){
        super(Material.cloth, name);
        this.setHardness(0.5F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeCloth);
        this.setTickRandomly(true);
    }
    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean isFullCube(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand){
        if(Util.RANDOM.nextInt(30) == 0){
            for(int i = 0; i < 2; i++){
                double d = Util.RANDOM.nextGaussian()*0.02D;
                double d1 = Util.RANDOM.nextGaussian()*0.02D;
                double d2 = Util.RANDOM.nextGaussian()*0.02D;
                world.spawnParticle(EnumParticleTypes.HEART, pos.getX()+Util.RANDOM.nextFloat(), pos.getY()+0.65+Util.RANDOM.nextFloat(), pos.getZ()+Util.RANDOM.nextFloat(), d, d1, d2);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing f6, float f7, float f8, float f9){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileEntitySmileyCloud){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.CLOUD.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());

                player.triggerAchievement(TheAchievements.NAME_SMILEY_CLOUD.ach);
            }
        }
        return true;
    }

    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axis, List list, Entity entity){
        this.setBlockBoundsBasedOnState(world, pos);
        super.addCollisionBoxesToList(world, pos, state, axis, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos){
        int meta = PosUtil.getMetadata(pos, world);
        float f = 0.0625F;

        if(meta == 0){
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F-f*3F);
        }
        if(meta == 1){
            this.setBlockBounds(0F, 0F, 0F, 1F-f*3F, 1F, 1F);
        }
        if(meta == 2){
            this.setBlockBounds(0F, 0F, f*3F, 1F, 1F, 1F);
        }
        if(meta == 3){
            this.setBlockBounds(f*3F, 0F, 0F, 1F, 1F, 1F);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntitySmileyCloud();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        this.dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0){
            PosUtil.setMetadata(pos, world, 0, 2);
        }
        if(rotation == 1){
            PosUtil.setMetadata(pos, world, 3, 2);
        }
        if(rotation == 2){
            PosUtil.setMetadata(pos, world, 1, 2);
        }
        if(rotation == 3){
            PosUtil.setMetadata(pos, world, 2, 2);
        }

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }
}
