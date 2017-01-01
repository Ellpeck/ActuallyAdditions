/*
 * This file ("TileEntityPhantomItemface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
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
            TileEntity tile = this.world.getTileEntity(this.getBoundPosition());
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
    protected boolean isCapabilitySupported(Capability<?> capability){
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return this.isBoundThingInRange();
    }
}
