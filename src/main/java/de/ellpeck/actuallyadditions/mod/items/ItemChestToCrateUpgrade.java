/*
 * This file ("ItemChestToCrateUpgrade.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ItemChestToCrateUpgrade extends ItemBase{

    private final Class start;
    private final IBlockState end;

    public ItemChestToCrateUpgrade(String name, Class start, IBlockState end){
        super(name);
        this.start = start;
        this.end = end;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float par8, float par9, float par10){
        ItemStack heldStack = player.getHeldItem(hand);
        if(player.isSneaking()){
            TileEntity tileHit = world.getTileEntity(pos);
            if(tileHit != null && tileHit.getClass() == this.start){
                if(!world.isRemote){

                    //Copy Slots
                    IItemHandlerModifiable chest = null;
                    if(tileHit instanceof IInventory){
                        chest = new InvWrapper((IInventory)tileHit);
                    }
                    else if(tileHit instanceof TileEntityInventoryBase){
                        chest = ((TileEntityInventoryBase)tileHit).slots;
                    }

                    if(chest != null){
                        ItemStack[] stacks = new ItemStack[chest.getSlots()];
                        for(int i = 0; i < stacks.length; i++){
                            ItemStack aStack = chest.getStackInSlot(i);
                            if(StackUtil.isValid(aStack)){
                                stacks[i] = aStack.copy();
                                chest.setStackInSlot(i, StackUtil.getNull());
                            }
                        }

                        //Set New Block
                        world.playEvent(2001, pos, Block.getStateId(world.getBlockState(pos)));
                        world.setBlockState(pos, this.end, 2);

                        //Copy Items into new Chest
                        TileEntity newTileHit = world.getTileEntity(pos);
                        if(newTileHit instanceof TileEntityInventoryBase){
                            IItemHandlerModifiable newChest = ((TileEntityInventoryBase)newTileHit).slots;

                            for(int i = 0; i < stacks.length; i++){
                                if(StackUtil.isValid(stacks[i])){
                                    if(newChest.getSlots() > i){
                                        newChest.setStackInSlot(i, stacks[i].copy());
                                    }
                                }
                            }
                        }

                        if(!player.capabilities.isCreativeMode){
                            player.setHeldItem(hand, StackUtil.addStackSize(heldStack, -1));
                        }
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }

        return super.onItemUse(player, world, pos, hand, facing, par8, par9, par10);
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }
}
