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
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockLampPowerer extends BlockBase{

    public BlockLampPowerer(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock){
        this.updateLamp(world, Position.fromBlockPos(pos));
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        this.updateLamp(world, Position.fromBlockPos(pos));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        int rotation = BlockPistonBase.getFacingFromEntity(world, pos, player).ordinal();
        Position.fromBlockPos(pos).setMetadata(world, rotation, 2);

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    private void updateLamp(World world, Position pos){
        if(!world.isRemote){
            Position coords = WorldUtil.getCoordsFromSide(WorldUtil.getDirectionByPistonRotation(pos.getMetadata(world)), pos, 0);
            if(coords != null && coords.getBlock(world) instanceof BlockColoredLamp){
                if(world.isBlockIndirectlyGettingPowered(pos) > 0){
                    if(!((BlockColoredLamp)coords.getBlock(world)).isOn){
                        pos.setBlock(world, InitBlocks.blockColoredLampOn, coords.getMetadata(world), 2);
                    }
                }
                else{
                    if(((BlockColoredLamp)coords.getBlock(world)).isOn){
                        pos.setBlock(world, InitBlocks.blockColoredLamp, coords.getMetadata(world), 2);
                    }
                }
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }
}
