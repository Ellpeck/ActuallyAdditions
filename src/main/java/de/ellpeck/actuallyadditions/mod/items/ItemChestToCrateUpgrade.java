/*
 * This file ("ItemChestToCrateUpgrade.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChest;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemChestToCrateUpgrade extends ItemBase{

    public ItemChestToCrateUpgrade(String name){
        super(name);
    }

    @Override
    public boolean onItemUse(ItemStack heldStack, EntityPlayer player, World world, BlockPos pos, EnumFacing facing, float par8, float par9, float par10){
        if(player.isSneaking()){
            TileEntity tileHit = world.getTileEntity(pos);
            Block block = PosUtil.getBlock(pos, world);
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
                    if(!ConfigValues.lessBlockBreakingEffects){
                        world.playAuxSFX(2001, pos, Block.getIdFromBlock(block)+(PosUtil.getMetadata(pos, world) << 12));
                    }
                    PosUtil.setBlock(pos, world, InitBlocks.blockGiantChest, 0, 2);

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
                return true;
            }
        }

        return super.onItemUse(heldStack, player, world, pos, facing, par8, par9, par10);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }
}
