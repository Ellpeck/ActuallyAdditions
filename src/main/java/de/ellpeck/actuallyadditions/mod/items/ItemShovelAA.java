/*
 * This file ("ItemShovelAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import com.google.common.collect.Sets;
import de.ellpeck.actuallyadditions.mod.items.base.ItemToolAA;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class ItemShovelAA extends ItemToolAA{

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand, Blocks.grass_path);

    public ItemShovelAA(Item.ToolMaterial material, String repairItem, String unlocalizedName, EnumRarity rarity){
        super(1.5F, -3.0F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
    }

    public ItemShovelAA(Item.ToolMaterial material, ItemStack repairItem, String unlocalizedName, EnumRarity rarity){
        super(1.5F, -3.0F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
    }

    public float getStrVsBlock(ItemStack stack, IBlockState state){
        Material material = state.getMaterial();
        return material != Material.wood && material != Material.plants && material != Material.vine ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
    }

    public boolean canHarvestBlock(IBlockState blockIn){
        Block block = blockIn.getBlock();
        return block == Blocks.snow_layer || block == Blocks.snow;
    }

    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!playerIn.canPlayerEdit(pos.offset(facing), facing, stack)){
            return EnumActionResult.FAIL;
        }
        else{
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if(facing != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getMaterial() == Material.air && block == Blocks.grass){
                IBlockState iblockstate1 = Blocks.grass_path.getDefaultState();
                worldIn.playSound(playerIn, pos, SoundEvents.item_shovel_flatten, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if(!worldIn.isRemote){
                    worldIn.setBlockState(pos, iblockstate1, 11);
                    stack.damageItem(1, playerIn);
                }

                return EnumActionResult.SUCCESS;
            }
            else{
                return EnumActionResult.PASS;
            }
        }
    }
}
