/*
 * This file ("BlockLampPowerer.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLampPowerer extends BlockBase{

    @SideOnly(Side.CLIENT)
    private IIcon frontIcon;

    public BlockLampPowerer(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        int meta = world.getBlockMetadata(x, y, z);
        if(side == meta){
            return this.frontIcon;
        }
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        if(side == 3){
            return this.frontIcon;
        }
        return this.blockIcon;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
        this.updateLamp(world, x, y, z);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z){
        this.updateLamp(world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = BlockPistonBase.determineOrientation(world, x, y, z, player);
        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
        this.frontIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Front");
    }

    private void updateLamp(World world, int x, int y, int z){
        if(!world.isRemote){
            Position coords = WorldUtil.getCoordsFromSide(ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)), x, y, z, 0);
            if(coords != null && coords.getBlock(world) instanceof BlockColoredLamp){
                if(world.isBlockIndirectlyGettingPowered(x, y, z)){
                    if(!((BlockColoredLamp)coords.getBlock(world)).isOn){
                        world.setBlock(coords.getX(), coords.getY(), coords.getZ(), InitBlocks.blockColoredLampOn, world.getBlockMetadata(coords.getX(), coords.getY(), coords.getZ()), 2);
                    }
                }
                else{
                    if(((BlockColoredLamp)coords.getBlock(world)).isOn){
                        world.setBlock(coords.getX(), coords.getY(), coords.getZ(), InitBlocks.blockColoredLamp, world.getBlockMetadata(coords.getX(), coords.getY(), coords.getZ()), 2);
                    }
                }
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }
}
