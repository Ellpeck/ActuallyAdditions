/*
 * This file ("TileEntityFishingNet.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.FishingHooks;

import java.util.ArrayList;

public class TileEntityFishingNet extends TileEntityBase{

    public int timeUntilNextDrop;

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!this.isRedstonePowered){
                Position pos = Position.fromTileEntity(this);
                if(pos.getOffsetPosition(0, -1, 0).getMaterial(worldObj) == Material.water){
                    if(this.timeUntilNextDrop > 0){
                        this.timeUntilNextDrop--;
                        if(timeUntilNextDrop <= 0){
                            ItemStack fishable = FishingHooks.getRandomFishable(Util.RANDOM, Util.RANDOM.nextFloat());
                            TileEntity tile = pos.getOffsetPosition(0, 1, 0).getTileEntity(worldObj);
                            if(tile != null && tile instanceof IInventory){
                                ArrayList<ItemStack> list = new ArrayList<ItemStack>();
                                list.add(fishable);
                                WorldUtil.addToInventory((IInventory)tile, list, EnumFacing.DOWN, true, false);
                            }
                            else{
                                EntityItem item = new EntityItem(worldObj, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, fishable);
                                item.lifespan = 2000;
                                worldObj.spawnEntityInWorld(item);
                            }
                        }
                    }
                    else{
                        int time = 15000;
                        this.timeUntilNextDrop = time+Util.RANDOM.nextInt(time/2);
                    }
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("TimeUntilNextDrop", this.timeUntilNextDrop);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.timeUntilNextDrop = compound.getInteger("TimeUntilNextDrop");
    }
}
