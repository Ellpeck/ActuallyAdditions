/*
 * This file ("LensDisruption.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class LensDisruption extends Lens{

    @Override
    public boolean invoke(IBlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile){
        int energyUse = 150000;
        if(tile.getEnergy() >= energyUse && hitBlock != null && !hitState.getBlock().isAir(hitState, tile.getWorldObject(), hitBlock)){
            int range = 2;
            ArrayList<EntityItem> items = (ArrayList<EntityItem>)tile.getWorldObject().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(hitBlock.getX()-range, hitBlock.getY()-range, hitBlock.getZ()-range, hitBlock.getX()+range, hitBlock.getY()+range, hitBlock.getZ()+range));
            for(EntityItem item : items){
                ItemStack stack = item.getEntityItem();
                if(!item.isDead && stack != null){
                    if(!stack.hasTagCompound() || !stack.getTagCompound().getBoolean(ModUtil.MOD_ID+"DisruptedAlready")){
                        ItemStack newStack = null;

                        boolean done = false;
                        while(!done){
                            if(Util.RANDOM.nextBoolean()){
                                newStack = new ItemStack(Item.REGISTRY.getRandomObject(Util.RANDOM));
                            }
                            else{
                                newStack = new ItemStack(Block.REGISTRY.getRandomObject(Util.RANDOM));
                            }

                            if(newStack != null){
                                Item newItem = newStack.getItem();
                                if(newItem != null){
                                    CreativeTabs[] tabs = newItem.getCreativeTabs();
                                    for(CreativeTabs tab : tabs){
                                        if(tab != null){
                                            done = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        newStack.stackSize = stack.stackSize;

                        if(!newStack.hasTagCompound()){
                            newStack.setTagCompound(new NBTTagCompound());
                        }
                        newStack.getTagCompound().setBoolean(ModUtil.MOD_ID+"DisruptedAlready", true);

                        item.setDead();

                        EntityItem newItem = new EntityItem(tile.getWorldObject(), item.posX, item.posY, item.posZ, newStack);
                        tile.getWorldObject().spawnEntityInWorld(newItem);

                        tile.extractEnergy(energyUse);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public float[] getColor(){
        return new float[]{246F/255F, 255F/255F, 183F/255F};
    }

    @Override
    public int getDistance(){
        return 3;
    }
}
