/*
 * This file ("TileEntityFishingNet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class TileEntityFishingNet extends TileEntityBase{

    public int timeUntilNextDrop;

    public TileEntityFishingNet(){
        super("fishingNet");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("TimeUntilNextDrop", this.timeUntilNextDrop);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.timeUntilNextDrop = compound.getInteger("TimeUntilNextDrop");
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(!this.isRedstonePowered){
                if(this.worldObj.getBlockState(this.pos.down()).getMaterial() == Material.WATER){
                    if(this.timeUntilNextDrop > 0){
                        this.timeUntilNextDrop--;
                        if(this.timeUntilNextDrop <= 0){
                            LootContext.Builder builder = new LootContext.Builder((WorldServer)this.worldObj);
                            List<ItemStack> fishables = this.worldObj.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(Util.RANDOM, builder.build());
                            for(ItemStack fishable : fishables){
                                ItemStack leftover = this.storeInContainer(fishable);
                                if(leftover != null){
                                    EntityItem item = new EntityItem(this.worldObj, this.pos.getX()+0.5, this.pos.getY()+0.5, this.pos.getZ()+0.5, leftover.copy());
                                    item.lifespan = 2000;
                                    this.worldObj.spawnEntityInWorld(item);
                                }
                            }
                        }
                    }
                    else{
                        int time = 150;
                        this.timeUntilNextDrop = time+Util.RANDOM.nextInt(time/2);
                    }
                }
            }
        }
    }

    private ItemStack storeInContainer(ItemStack stack){
        for(EnumFacing side : EnumFacing.values()){
            TileEntity tile = this.tilesAround[side.ordinal()];
            if(tile != null){
                if(tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite())){
                    IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
                    if(cap != null){
                        for(int i = 0; i < cap.getSlots(); i++){
                            stack = cap.insertItem(i, stack, false);

                            if(stack == null || stack.stackSize <= 0){
                                return null;
                            }
                        }
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public boolean shouldSaveHandlersAround(){
        return true;
    }
}
