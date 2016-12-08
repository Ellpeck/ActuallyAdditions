/*
 * This file ("TileEntityDistributorItem.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;

public class TileEntityDistributorItem extends TileEntityInventoryBase{

    private final Map<EnumFacing, IItemHandler> handlersAround = new HashMap<EnumFacing, IItemHandler>();
    private int putSide;

    public TileEntityDistributorItem(){
        super(1, "distributorItem");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            boolean shouldMarkDirty = false;

            IItemHandler handlerUp = this.handlersAround.get(EnumFacing.UP);
            if(handlerUp != null){
                for(int i = 0; i < handlerUp.getSlots(); i++){

                    ItemStack pullable = handlerUp.extractItem(i, 1, true);
                    if(StackUtil.isValid(pullable) && (!StackUtil.isValid(this.slots[0]) || (ItemUtil.canBeStacked(this.slots[0], pullable) && StackUtil.getStackSize(this.slots[0]) < this.slots[0].getMaxStackSize()))){
                        ItemStack pulled = handlerUp.extractItem(i, 1, false);
                        if(StackUtil.isValid(pulled)){
                            if(!StackUtil.isValid(this.slots[0])){
                                this.slots[0] = pulled.copy();
                            }
                            else{
                                this.slots[0] = StackUtil.addStackSize(this.slots[0], StackUtil.getStackSize(pulled));
                            }
                            shouldMarkDirty = true;
                            break;
                        }
                    }

                    if(StackUtil.isValid(this.slots[0]) && StackUtil.getStackSize(this.slots[0]) >= this.slots[0].getMaxStackSize()){
                        break;
                    }
                }
            }

            if(!this.handlersAround.isEmpty() && (!this.handlersAround.containsKey(EnumFacing.UP) || this.handlersAround.size() >= 2) && StackUtil.isValid(this.slots[0])){
                EnumFacing[] allFacings = EnumFacing.values();
                do{
                    this.putSide++;

                    if(this.putSide >= 6){
                        this.putSide = 0;
                    }
                }
                while(allFacings[this.putSide] == EnumFacing.UP || !this.handlersAround.containsKey(allFacings[this.putSide]));

                EnumFacing putFacing = allFacings[this.putSide];
                IItemHandler handler = this.handlersAround.get(putFacing);
                if(handler != null){
                    int aroundAmount = this.handlersAround.containsKey(EnumFacing.UP) ? this.handlersAround.size()-1 : this.handlersAround.size();
                    int amount = StackUtil.getStackSize(this.slots[0])/aroundAmount;
                    if(amount <= 0){
                        amount = StackUtil.getStackSize(this.slots[0]);
                    }

                    if(amount > 0){
                        ItemStack toInsert = this.slots[0].copy();
                        toInsert = StackUtil.setStackSize(toInsert, amount);

                        for(int i = 0; i < handler.getSlots(); i++){
                            toInsert = handler.insertItem(i, toInsert.copy(), false);

                            if(!StackUtil.isValid(toInsert)){
                                this.slots[0] = StackUtil.addStackSize(this.slots[0], -amount);
                                shouldMarkDirty = true;

                                break;
                            }
                        }
                    }
                }
            }

            if(shouldMarkDirty){
                this.markDirty();
            }
        }
    }

    @Override
    public void saveDataOnChangeOrWorldStart(){
        this.handlersAround.clear();

        for(EnumFacing side : EnumFacing.values()){
            TileEntity tile = this.worldObj.getTileEntity(this.pos.offset(side));
            if(tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite())){
                IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
                if(cap != null){
                    this.handlersAround.put(side, cap);
                }
            }
        }
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public void markDirty(){
        super.markDirty();
        this.sendUpdate();
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart(){
        return true;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction){
        return direction == EnumFacing.UP && this.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return true;
    }
}
