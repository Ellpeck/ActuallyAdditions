/*
 * This file ("BlockGreenhouseGlass.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGreenhouseGlass;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGreenhouseGlass extends BlockContainerBase{

    public BlockGreenhouseGlass(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(0.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public boolean isFullCube(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side){
        IBlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        return worldIn.getBlockState(pos.offset(side.getOpposite())) != state || block != this && block != this && super.shouldSideBeRendered(worldIn, pos, side);

    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer(){
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityGreenhouseGlass();
    }
}
