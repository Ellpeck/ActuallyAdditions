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


import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLampPowerer extends BlockBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 5);

    public BlockLampPowerer(String name){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock){
        this.updateLamp(world, pos);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        this.updateLamp(world, pos);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        int rotation = BlockPistonBase.getFacingFromEntity(pos, player).ordinal();
        PosUtil.setMetadata(pos, world, rotation, 2);

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    private void updateLamp(World world, BlockPos pos){
        if(!world.isRemote){
            BlockPos coords = WorldUtil.getCoordsFromSide(WorldUtil.getDirectionByPistonRotation(PosUtil.getMetadata(pos, world)), pos, 0);
            if(coords != null && PosUtil.getBlock(coords, world) instanceof BlockColoredLamp){
                if(world.isBlockIndirectlyGettingPowered(pos) > 0){
                    if(!((BlockColoredLamp)PosUtil.getBlock(coords, world)).isOn){
                        PosUtil.setBlock(coords, world, InitBlocks.blockColoredLampOn, PosUtil.getMetadata(coords, world), 2);
                    }
                }
                else{
                    if(((BlockColoredLamp)PosUtil.getBlock(coords, world)).isOn){
                        PosUtil.setBlock(coords, world, InitBlocks.blockColoredLamp, PosUtil.getMetadata(coords, world), 2);
                    }
                }
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }
}
