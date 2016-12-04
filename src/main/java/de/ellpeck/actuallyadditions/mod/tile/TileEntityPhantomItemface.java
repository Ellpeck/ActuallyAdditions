/*
 * This file ("TileEntityPhantomItemface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityPhantomItemface extends TileEntityPhantomface{

    public TileEntityPhantomItemface(){
        super("phantomface");
        this.type = BlockPhantom.Type.FACE;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return this.isBoundThingInRange();
    }

    @Override
    public boolean isBoundThingInRange(){
        if(super.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.getBoundPosition());
            if(tile != null){
                for(EnumFacing facing : EnumFacing.values()){
                    if(tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return this.isBoundThingInRange();
    }
}