/*
 * This file ("BlockLaserRelay.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockLaserRelay extends BlockContainerBase{

    public BlockLaserRelay(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity){
        this.setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.laserRelayRenderId;
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
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata){
        return side;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
        int meta = world.getBlockMetadata(x, y, z);

        float pixel = 1F/16F;
        if(meta == 0){
            this.setBlockBounds(3*pixel, 3*pixel, 3*pixel, 1F-3*pixel, 1F, 1F-3*pixel);
        }
        else if(meta == 1){
            this.setBlockBounds(3*pixel, 0F, 3*pixel, 1F-3*pixel, 1F-3*pixel, 1F-3*pixel);
        }
        else if(meta == 2){
            this.setBlockBounds(3*pixel, 3*pixel, 3*pixel, 1F-3*pixel, 1F-3*pixel, 1F);
        }
        else if(meta == 3){
            this.setBlockBounds(3*pixel, 3*pixel, 0F, 1F-3*pixel, 1F-3*pixel, 1F-3*pixel);
        }
        else if(meta == 4){
            this.setBlockBounds(3*pixel, 3*pixel, 3*pixel, 1F, 1F-3*pixel, 1F-3*pixel);
        }
        else if(meta == 5){
            this.setBlockBounds(0F, 3*pixel, 3*pixel, 1F-3*pixel, 1F-3*pixel, 1F-3*pixel);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.stone.getIcon(0, 0);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i){
        return new TileEntityLaserRelay();
    }
}