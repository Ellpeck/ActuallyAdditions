/*
 * This file ("TileEntityItemViewerHopping.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerCustom;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class TileEntityItemViewerHopping extends TileEntityItemViewer{

    private IItemHandler handlerToPullFrom;
    private IItemHandler handlerToPushTo;

    public TileEntityItemViewerHopping(){
        super("itemViewerHopping");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.world.isRemote && this.world.getTotalWorldTime()%10 == 0){
            if(this.handlerToPullFrom != null){
                outer : for(int i = 0; i < this.handlerToPullFrom.getSlots(); i++){
                    if(StackUtil.isValid(this.handlerToPullFrom.getStackInSlot(i))){
                        for(int j = 0; j < this.itemHandler.getSlots(); j++){
                            if(WorldUtil.doItemInteraction(i, j, this.handlerToPullFrom, this.itemHandler, 4)){
                                break outer;
                            }
                        }
                    }
                }
            }

            if(this.handlerToPushTo != null){
                outer : for(int i = 0; i < this.itemHandler.getSlots(); i++){
                    if(StackUtil.isValid(this.itemHandler.getStackInSlot(i))){
                        for(int j = 0; j < this.handlerToPushTo.getSlots(); j++){
                            if(WorldUtil.doItemInteraction(i, j, this.itemHandler, this.handlerToPushTo, 4)){
                                break outer;
                            }
                        }
                    }
                }
            }

            if(this.world.getTotalWorldTime()%20 == 0){
                List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.pos.getX(), this.pos.getY()+0.5, this.pos.getZ(), this.pos.getX()+1, this.pos.getY()+2, this.pos.getZ()+1));
                if(items != null && !items.isEmpty()){
                    for(EntityItem item : items){
                        if(item != null && !item.isDead){
                            for(int i = 0; i < this.itemHandler.getSlots(); i++){
                                ItemStack left = this.itemHandler.insertItem(i, item.getEntityItem(), false);
                                item.setEntityItemStack(left);

                                if(!StackUtil.isValid(left)){
                                    item.setDead();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void saveDataOnChangeOrWorldStart(){
        super.saveDataOnChangeOrWorldStart();

        TileEntity from = this.world.getTileEntity(this.pos.offset(EnumFacing.UP));
        if(from != null && !(from instanceof TileEntityItemViewer) && from.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)){
            this.handlerToPullFrom = from.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        }

        IBlockState state = this.world.getBlockState(this.pos);
        EnumFacing facing = state.getValue(BlockHopper.FACING);

        TileEntity to = this.world.getTileEntity(this.pos.offset(facing));
        if(to != null && !(to instanceof TileEntityItemViewer) && to.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())){
            this.handlerToPushTo = to.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
        }
    }
}
