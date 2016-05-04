package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
        if(tile.getEnergy() >= energyUse && hitBlock != null && !PosUtil.getBlock(hitBlock, tile.getWorldObject()).isAir(hitState, tile.getWorldObject(), hitBlock)){
            int range = 2;
            ArrayList<EntityItem> items = (ArrayList<EntityItem>)tile.getWorldObject().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(hitBlock.getX()-range, hitBlock.getY()-range, hitBlock.getZ()-range, hitBlock.getX()+range, hitBlock.getY()+range, hitBlock.getZ()+range));
            for(EntityItem item : items){
                ItemStack stack = item.getEntityItem();
                if(!item.isDead && stack != null){
                    if(!stack.hasTagCompound() || !stack.getTagCompound().getBoolean(ModUtil.MOD_ID+"DisruptedAlready")){
                        ItemStack newStack = null;

                        while(newStack == null || newStack.getItem() == null){
                            if(Util.RANDOM.nextBoolean()){
                                newStack = new ItemStack(Item.REGISTRY.getRandomObject(Util.RANDOM));
                            }
                            else{
                                newStack = new ItemStack(Block.REGISTRY.getRandomObject(Util.RANDOM));
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
