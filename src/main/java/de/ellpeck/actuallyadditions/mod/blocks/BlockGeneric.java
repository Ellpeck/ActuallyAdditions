/*
 * This file ("BlockGeneric.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class BlockGeneric extends BlockBase{

    public BlockGeneric(String name){
        this(name, Material.ROCK, SoundType.STONE, 1.5F, 10.0F, "pickaxe", 0);
    }

    public BlockGeneric(String name, Material material, SoundType sound, float hardness, float resistance, String harvestTool, int harvestLevel){
        super(material, name);
        this.setHarvestLevel(harvestTool, harvestLevel);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setSoundType(sound);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.COMMON;
    }
}
