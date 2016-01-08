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

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockLaserRelay extends BlockContainerBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 5);

    public BlockLaserRelay(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axis, List list, Entity entity){
        this.setBlockBoundsBasedOnState(world, pos);
        super.addCollisionBoxesToList(world, pos, state, axis, list, entity);
    }

    @Override
    public int getRenderType(){
        return AssetUtil.TESR_RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase base){
        return this.getStateFromMeta(meta);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos){
        int meta = PosUtil.getMetadata(pos, world);

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
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i){
        return new TileEntityLaserRelay();
    }
}