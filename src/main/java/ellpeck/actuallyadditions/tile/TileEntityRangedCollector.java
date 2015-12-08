/*
 * This file ("TileEntityRangedCollector.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.network.gui.IButtonReactor;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;

public class TileEntityRangedCollector extends TileEntityInventoryBase implements IButtonReactor{

    public static final int WHITELIST_START = 6;
    public static final int RANGE = 6;
    public boolean isWhitelist = true;
    private boolean lastWhitelist;

    public TileEntityRangedCollector(){
        super(18, "rangedCollector");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!this.isRedstonePowered){
                ArrayList<EntityItem> items = (ArrayList<EntityItem>)this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(this.xCoord-RANGE, this.yCoord-RANGE, this.zCoord-RANGE, this.xCoord+RANGE, this.yCoord+RANGE, this.zCoord+RANGE));
                if(!items.isEmpty()){
                    for(EntityItem item : items){
                        if(!item.isDead && item.getEntityItem() != null){
                            ItemStack toAdd = item.getEntityItem().copy();
                            if(this.checkFilter(toAdd)){
                                ArrayList<ItemStack> checkList = new ArrayList<ItemStack>();
                                checkList.add(toAdd);
                                if(WorldUtil.addToInventory(this, 0, WHITELIST_START, checkList, false)){
                                    WorldUtil.addToInventory(this, 0, WHITELIST_START, checkList, true);
                                    item.setDead();
                                }
                            }
                        }
                    }
                }
            }

            if(this.isWhitelist != this.lastWhitelist && this.sendUpdateWithInterval()){
                this.lastWhitelist = this.isWhitelist;
            }
        }
    }

    private boolean checkFilter(ItemStack stack){
        int slotStop = WHITELIST_START+12;

        for(int i = WHITELIST_START; i < slotStop; i++){
            if(this.slots[i] != null && this.slots[i].isItemEqual(stack)){
                return this.isWhitelist;
            }
        }
        return !this.isWhitelist;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setBoolean("Whitelist", this.isWhitelist);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.isWhitelist = compound.getBoolean("Whitelist");
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return false;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot < WHITELIST_START;
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        this.isWhitelist = !this.isWhitelist;
    }
}
