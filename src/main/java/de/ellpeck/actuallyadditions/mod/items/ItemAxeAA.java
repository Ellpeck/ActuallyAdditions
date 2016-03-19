/*
 * This file ("ItemAxeAA.java") is part of the Actually Additions Mod for Minecraft.
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
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class ItemAxeAA extends ItemToolAA{

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder, Blocks.wooden_button, Blocks.wooden_pressure_plate);

    public ItemAxeAA(Item.ToolMaterial material, String repairItem, String unlocalizedName, EnumRarity rarity){
        super(6.0F, -3.0F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
    }

    public ItemAxeAA(Item.ToolMaterial material, ItemStack repairItem, String unlocalizedName, EnumRarity rarity){
        super(6.0F, -3.0F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
    }

    public float getStrVsBlock(ItemStack stack, IBlockState state){
        Material material = state.getMaterial();
        return material != Material.wood && material != Material.plants && material != Material.vine ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
    }
}
