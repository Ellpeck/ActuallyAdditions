/*
 * This file ("BlockSmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.ActuallyAdditions;
import de.ellpeck.actuallyadditions.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.tile.TileEntitySmileyCloud;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import de.ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockSmileyCloud extends BlockContainerBase{

    public BlockSmileyCloud(String name){
        super(Material.cloth, name);
        this.setHardness(0.5F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeCloth);
        this.setTickRandomly(true);
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.smileyCloudRenderId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand){
        if(Util.RANDOM.nextInt(30) == 0){
            for(int i = 0; i < 2; i++){
                double d = Util.RANDOM.nextGaussian()*0.02D;
                double d1 = Util.RANDOM.nextGaussian()*0.02D;
                double d2 = Util.RANDOM.nextGaussian()*0.02D;
                world.spawnParticle("heart", x+Util.RANDOM.nextFloat(), y+0.65+Util.RANDOM.nextFloat(), z+Util.RANDOM.nextFloat(), d, d1, d2);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int f6, float f7, float f8, float f9){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileEntitySmileyCloud){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.CLOUD.ordinal(), world, x, y, z);

                player.triggerAchievement(TheAchievements.NAME_SMILEY_CLOUD.ach);
            }
        }
        return true;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity){
        this.setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
        int meta = world.getBlockMetadata(x, y, z);
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
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.wool.getIcon(0, 0);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntitySmileyCloud();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0){
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if(rotation == 1){
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        }
        if(rotation == 2){
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }
        if(rotation == 3){
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        super.onBlockPlacedBy(world, x, y, z, player, stack);
    }
}
