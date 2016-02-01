/*
 * This file ("BlockFurnaceSolar.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceSolar;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFurnaceSolar extends BlockContainerBase{

    public BlockFurnaceSolar(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
        this.setBlockBounds(0F, 0F, 0F, 1F, 6F/16F, 1F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityFurnaceSolar();
    }

    @Override
    public boolean isFullCube(){
        return false;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
    }
}
