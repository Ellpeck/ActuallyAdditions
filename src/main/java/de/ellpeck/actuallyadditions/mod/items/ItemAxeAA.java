/*
 * This file ("ItemAxeAA.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.Set;

public class ItemAxeAA extends ItemToolAA{

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);

    public ItemAxeAA(Item.ToolMaterial material, String repairItem, String unlocalizedName, EnumRarity rarity){
        super(6.0F, -3.0F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
        this.setHarvestLevel("axe", material.getHarvestLevel());
    }

    public ItemAxeAA(Item.ToolMaterial material, ItemStack repairItem, String unlocalizedName, EnumRarity rarity){
        super(6.0F, -3.0F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
        this.setHarvestLevel("axe", material.getHarvestLevel());
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state){
        Material material = state.getMaterial();
        return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack){
        return Collections.singleton("axe");
    }
}
