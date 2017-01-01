/*
 * This file ("ItemPickaxeAA.java") is part of the Actually Additions mod for Minecraft.
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

public class ItemPickaxeAA extends ItemToolAA{

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);

    public ItemPickaxeAA(Item.ToolMaterial material, String repairItem, String unlocalizedName, EnumRarity rarity){
        super(1.0F, -2.8F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
        this.setHarvestLevel("pickaxe", material.getHarvestLevel());
    }

    public ItemPickaxeAA(Item.ToolMaterial material, ItemStack repairItem, String unlocalizedName, EnumRarity rarity){
        super(1.0F, -2.8F, material, repairItem, unlocalizedName, rarity, EFFECTIVE_ON);
        this.setHarvestLevel("pickaxe", material.getHarvestLevel());
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn){
        Block block = blockIn.getBlock();

        if(block == Blocks.OBSIDIAN){
            return this.toolMaterial.getHarvestLevel() == 3;
        }
        else if(block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE){
            if(block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK){
                if(block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE){
                    if(block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE){
                        if(block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE){
                            if(block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE){
                                Material material = blockIn.getMaterial();
                                return material == Material.ROCK || (material == Material.IRON || material == Material.ANVIL);
                            }
                            else{
                                return this.toolMaterial.getHarvestLevel() >= 2;
                            }
                        }
                        else{
                            return this.toolMaterial.getHarvestLevel() >= 1;
                        }
                    }
                    else{
                        return this.toolMaterial.getHarvestLevel() >= 1;
                    }
                }
                else{
                    return this.toolMaterial.getHarvestLevel() >= 2;
                }
            }
            else{
                return this.toolMaterial.getHarvestLevel() >= 2;
            }
        }
        else{
            return this.toolMaterial.getHarvestLevel() >= 2;
        }
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state){
        Material material = state.getMaterial();
        return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack){
        return Collections.singleton("pickaxe");
    }
}
