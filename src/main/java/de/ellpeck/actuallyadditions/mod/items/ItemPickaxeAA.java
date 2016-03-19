/*
 * This file ("ItemPickaxeAA.java") is part of the Actually Additions Mod for Minecraft.
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

public class ItemPickaxeAA extends ItemToolAA{

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.activator_rail, Blocks.coal_ore, Blocks.cobblestone, Blocks.detector_rail, Blocks.diamond_block, Blocks.diamond_ore, Blocks.double_stone_slab, Blocks.golden_rail, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.packed_ice, Blocks.rail, Blocks.redstone_ore, Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab, Blocks.stone_button, Blocks.stone_pressure_plate);

    public ItemPickaxeAA(Item.ToolMaterial material, String repairItem, String unlocalizedName, EnumRarity rarity){
        super(1.0F, -2.8F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
    }

    public ItemPickaxeAA(Item.ToolMaterial material, ItemStack repairItem, String unlocalizedName, EnumRarity rarity){
        super(1.0F, -2.8F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
    }

    @Override
    public boolean canHarvestBlock(IBlockState state) {
        Block blockIn = state.getBlock();
        return blockIn == Blocks.obsidian ? toolMaterial.getHarvestLevel() == 3 : blockIn != Blocks.diamond_block && blockIn != Blocks.diamond_ore ? blockIn != Blocks.emerald_ore && blockIn != Blocks.emerald_block ? blockIn != Blocks.gold_block && blockIn != Blocks.gold_ore ? blockIn != Blocks.iron_block && blockIn != Blocks.iron_ore ? blockIn != Blocks.lapis_block && blockIn != Blocks.lapis_ore ? blockIn != Blocks.redstone_ore && blockIn != Blocks.lit_redstone_ore ? state.getMaterial() == Material.rock || (state.getMaterial() == Material.iron || state.getMaterial() == Material.anvil) : toolMaterial.getHarvestLevel() >= 2 : toolMaterial.getHarvestLevel() >= 1 : toolMaterial.getHarvestLevel() >= 1 : toolMaterial.getHarvestLevel() >= 2 : toolMaterial.getHarvestLevel() >= 2 : toolMaterial.getHarvestLevel() >= 2;
    }

    public float getStrVsBlock(ItemStack stack, IBlockState state){
        Material material = state.getMaterial();
        return material != Material.iron && material != Material.anvil && material != Material.rock ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
    }
}
