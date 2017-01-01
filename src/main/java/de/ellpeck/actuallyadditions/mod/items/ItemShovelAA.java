/*
 * This file ("ItemShovelAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import com.google.common.collect.Sets;
import de.ellpeck.actuallyadditions.mod.items.base.ItemToolAA;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Set;

public class ItemShovelAA extends ItemToolAA{

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH);

    public ItemShovelAA(Item.ToolMaterial material, String repairItem, String unlocalizedName, EnumRarity rarity){
        super(1.5F, -3.0F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
        this.setHarvestLevel("shovel", material.getHarvestLevel());
    }

    public ItemShovelAA(Item.ToolMaterial material, ItemStack repairItem, String unlocalizedName, EnumRarity rarity){
        super(1.5F, -3.0F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
        this.setHarvestLevel("shovel", material.getHarvestLevel());
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn){
        Block block = blockIn.getBlock();
        return block == Blocks.SNOW_LAYER || block == Blocks.SNOW;
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        return Items.IRON_SHOVEL.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack){
        return Collections.singleton("shovel");
    }
}
