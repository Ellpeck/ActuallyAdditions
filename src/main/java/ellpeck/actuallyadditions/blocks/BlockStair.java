/*
 * This file ("BlockStair.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class BlockStair extends BlockStairs implements IActAddItemOrBlock{

    private String name;

    public BlockStair(Block block, String name){
        super(block, 0);
        this.name = name;
        this.setLightOpacity(0);
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.common;
    }
}
