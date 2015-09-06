/*
 * This file ("BlockContainerBase.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import ellpeck.actuallyadditions.tile.TileEntityInventoryBase;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockContainerBase extends BlockContainer{

    public BlockContainerBase(Material mat){
        super(mat);
    }

    public void dropInventory(World world, int x, int y, int z){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileEntityInventoryBase){
                TileEntityInventoryBase tileEntity = (TileEntityInventoryBase)tile;
                if(tileEntity.getSizeInventory() > 0){
                    Random rand = new Random();
                    for(int i = 0; i < tileEntity.getSizeInventory(); i++){
                        ItemStack itemStack = tileEntity.getStackInSlot(i);
                        if(itemStack != null && itemStack.stackSize > 0){
                            float dX = rand.nextFloat()*0.8F+0.1F;
                            float dY = rand.nextFloat()*0.8F+0.1F;
                            float dZ = rand.nextFloat()*0.8F+0.1F;
                            EntityItem entityItem = new EntityItem(world, x+dX, y+dY, z+dZ, itemStack.copy());
                            if(itemStack.hasTagCompound()) entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
                            float factor = 0.05F;
                            entityItem.motionX = rand.nextGaussian()*factor;
                            entityItem.motionY = rand.nextGaussian()*factor+0.2F;
                            entityItem.motionZ = rand.nextGaussian()*factor;
                            world.spawnEntityInWorld(entityItem);
                            itemStack.stackSize = 0;
                        }
                        tileEntity.setInventorySlotContents(i, null);
                    }
                }
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride(){
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int meta){
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof IInventory) return Container.calcRedstoneFromInventory((IInventory)tile);
        return 0;
    }
}
