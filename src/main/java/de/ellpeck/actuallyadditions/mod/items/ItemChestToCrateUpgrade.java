/*
 * This file ("ItemChestToCrateUpgrade.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemChestToCrateUpgrade extends ItemBase{

    public ItemChestToCrateUpgrade(String name){
        super(name);
    }


    @Override
    public EnumActionResult onItemUse(ItemStack heldStack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float par8, float par9, float par10){
        if(player.isSneaking()){
            TileEntity tileHit = world.getTileEntity(pos);
            Block block = world.getBlockState(pos).getBlock();
            if(block instanceof BlockChest && tileHit instanceof TileEntityChest){
                if(!world.isRemote){
                    TileEntityChest chest = (TileEntityChest)tileHit;

                    //Copy Slots
                    ItemStack[] stacks = new ItemStack[chest.getSizeInventory()];
                    for(int i = 0; i < stacks.length; i++){
                        ItemStack aStack = chest.getStackInSlot(i);
                        if(aStack != null){
                            stacks[i] = aStack.copy();
                            chest.setInventorySlotContents(i, null);
                        }
                    }

                    //Set New Block
                    if(!ConfigBoolValues.LESS_BLOCK_BREAKING_EFFECTS.isEnabled()){
                        world.playEvent(2001, pos, Block.getStateId(world.getBlockState(pos)));
                    }
                    world.setBlockState(pos, InitBlocks.blockGiantChest.getDefaultState(), 2);

                    //Copy Items into new Chest
                    TileEntity newTileHit = world.getTileEntity(pos);
                    if(newTileHit instanceof TileEntityGiantChest){
                        TileEntityGiantChest newChest = (TileEntityGiantChest)newTileHit;
                        for(int i = 0; i < stacks.length; i++){
                            if(stacks[i] != null){
                                if(newChest.getSizeInventory() > i){
                                    newChest.setInventorySlotContents(i, stacks[i].copy());
                                }
                            }
                        }
                    }

                    if(!player.capabilities.isCreativeMode){
                        heldStack.stackSize--;
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }

        return super.onItemUse(heldStack, player, world, pos, hand, facing, par8, par9, par10);
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }
}
