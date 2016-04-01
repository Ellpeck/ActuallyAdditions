/*
 * This file ("BlockSlabs.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockSlabs extends BlockBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 1);
    private Block fullBlock;
    private int meta;

    public BlockSlabs(String name, Block fullBlock){
        this(name, fullBlock, 0);
    }

    public BlockSlabs(String name, Block fullBlock, int meta){
        super(fullBlock.getMaterial(fullBlock.getDefaultState()), name);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.fullBlock = fullBlock;
        this.meta = meta;
    }

    /*@Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axis, List list, Entity entity){
        this.setBlockBoundsBasedOnState(world, pos);
        super.addCollisionBoxesToList(world, pos, state, axis, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos){
        int meta = PosUtil.getMetadata(pos, world);
        float minY = meta == 1 ? 0.5F : 0.0F;
        float maxY = meta == 1 ? 1.0F : 0.5F;
        this.setBlockBounds(0.0F, minY, 0F, 1.0F, maxY, 1.0F);
    }

    @Override
    public void setBlockBoundsForItemRender(){
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }*/

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        if(facing.ordinal() == 1){
            return this.getStateFromMeta(meta);
        }
        if(facing.ordinal() == 0 || hitY >= 0.5F){
            return this.getStateFromMeta(meta+1);
        }
        return this.getStateFromMeta(meta);
    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock(){
        return TheItemBlock.class;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.COMMON;
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
            if(PosUtil.getBlock(pos, world) == this.block && ((side.ordinal() == 1 && PosUtil.getMetadata(pos, world) == 0) || (side.ordinal() == 0 && PosUtil.getMetadata(pos, world) == 1))){
                if(PosUtil.setBlock(pos, world, ((BlockSlabs)this.block).fullBlock, ((BlockSlabs)this.block).meta, 3)){
                    //TODO Fix sounds
                    //world.playSoundEffect(pos.getX()+0.5F, pos.getY()+0.5F, pos.getZ()+0.5F, this.block.stepSound.getBreakSound(), (this.block.stepSound.getVolume()+1.0F)/2.0F, this.block.stepSound.frequency*0.8F);
                    stack.stackSize--;
                    return EnumActionResult.SUCCESS;
                }
            }
            return super.onItemUse(stack, player, world, pos, hand, side, hitX, hitY, hitZ);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }
    }
}
