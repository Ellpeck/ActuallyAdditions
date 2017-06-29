/*
 * This file ("TileEntityFishingNet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
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
        if(!this.world.isRemote){
            if(!this.isRedstonePowered){
                if(this.world.getBlockState(this.pos.down()).getMaterial() == Material.WATER){
                    if(this.timeUntilNextDrop > 0){
                        this.timeUntilNextDrop--;
                        if(this.timeUntilNextDrop <= 0){
                            LootContext.Builder builder = new LootContext.Builder((WorldServer)this.world);
                            List<ItemStack> fishables = this.world.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(this.world.rand, builder.build());
                            for(ItemStack fishable : fishables){
                                ItemStack leftover = this.storeInContainer(fishable);
                                if(StackUtil.isValid(leftover)){
                                    EntityItem item = new EntityItem(this.world, this.pos.getX()+0.5, this.pos.getY()+0.5, this.pos.getZ()+0.5, leftover.copy());
                                    item.lifespan = 2000;
                                    this.world.spawnEntity(item);
                                }
                            }
                        }
                    }
                    else{
                        int time = 15000;
                        this.timeUntilNextDrop = time+this.world.rand.nextInt(time/2);
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

                            if(!StackUtil.isValid(stack)){
                                return StackUtil.getNull();
                            }
                        }
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart(){
        return true;
    }
}
