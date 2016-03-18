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


import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.ArrayList;
import java.util.List;

public class TileEntityFishingNet extends TileEntityBase{

    public int timeUntilNextDrop;

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

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!this.isRedstonePowered){
                if(PosUtil.getMaterial(PosUtil.offset(this.pos, 0, -1, 0), this.worldObj) == Material.water){
                    if(this.timeUntilNextDrop > 0){
                        this.timeUntilNextDrop--;
                        if(timeUntilNextDrop <= 0){
                            LootContext.Builder builder = new LootContext.Builder((WorldServer)this.worldObj);
                            List<ItemStack> fishables = this.worldObj.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(Util.RANDOM, builder.build());
                            for(ItemStack fishable : fishables){
                                TileEntity tile = worldObj.getTileEntity(PosUtil.offset(pos, 0, 1, 0));
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
                    }
                    else{
                        int time = 15000;
                        this.timeUntilNextDrop = time+Util.RANDOM.nextInt(time/2);
                    }
                }
            }
        }
    }
}
