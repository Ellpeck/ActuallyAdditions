/*
 * This file ("BlockSlabs.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockSlabs extends Block implements IActAddItemOrBlock{

    private String name;
    private Block fullBlock;

    public BlockSlabs(String name, Block fullBlock){
        super(Material.rock);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.fullBlock = fullBlock;
        this.name = name;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return this.fullBlock.getIcon(0, 0);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity){
        this.setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public int onBlockPlaced(World par1World, int blockX, int blockY, int blockZ, int side, float hitX, float hitY, float hitZ, int meta){
        if(side == 1){
            return meta;
        }
        if(side == 0 || hitY >= 0.5F){
            return meta+1;
        }
        return meta;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
        int meta = world.getBlockMetadata(x, y, z);
        float minY = meta == 1 ? 0.5F : 0.0F;
        float maxY = meta == 1 ? 1.0F : 0.5F;
        this.setBlockBounds(0.0F, minY, 0F, 1.0F, maxY, 1.0F);
    }

    @Override
    public void setBlockBoundsForItemRender(){
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){

    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.common;
    }
}
