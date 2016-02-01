/*
 * This file ("TileEntityPhantomItemface.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityPhantomItemface extends TileEntityPhantomface{

    public TileEntityPhantomItemface(){
        super("phantomface");
        this.type = BlockPhantom.Type.FACE;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side){
        if(this.isBoundThingInRange()){
            if(this.getSided() != null){
                return this.getSided().getSlotsForFace(side);
            }
            else{
                int[] theInt = new int[this.getSizeInventory()];
                for(int i = 0; i < theInt.length; i++){
                    theInt[i] = i;
                }
                return theInt;
            }
        }
        return new int[0];
    }

    @Override
    public int getInventoryStackLimit(){
        return this.isBoundThingInRange() ? this.getInventory().getInventoryStackLimit() : 0;
    }

    @Override
    public void clear(){
        if(this.isBoundThingInRange()){
            this.getInventory().clear();
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack){
        if(this.isBoundThingInRange()){
            this.getInventory().setInventorySlotContents(i, stack);
        }
        this.markDirty();
    }

    @Override
    public int getSizeInventory(){
        return this.isBoundThingInRange() ? this.getInventory().getSizeInventory() : 0;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        return this.isBoundThingInRange() ? this.getInventory().getStackInSlot(i) : null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        return this.isBoundThingInRange() ? this.getInventory().decrStackSize(i, j) : null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index){
        if(this.isBoundThingInRange()){
            return this.getInventory().removeStackFromSlot(index);
        }
        return null;
    }

    @Override
    public String getName(){
        return this.name;
    }

    public ISidedInventory getSided(){
        return this.getInventory() instanceof ISidedInventory ? (ISidedInventory)this.getInventory() : null;
    }

    public IInventory getInventory(){
        if(this.boundPosition != null){
            TileEntity tile = worldObj.getTileEntity(boundPosition);
            if(tile instanceof IInventory){
                return (IInventory)tile;
            }
        }
        return null;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(this.isBoundThingInRange()){
            TileEntity tile = worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                return tile.getCapability(capability, facing);
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return this.isBoundThingInRange() && this.getInventory().isItemValidForSlot(i, stack);
    }

    @Override
    public boolean isBoundThingInRange(){
        return super.isBoundThingInRange() && worldObj.getTileEntity(boundPosition) instanceof IInventory;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isBoundThingInRange() && (this.getSided() == null || this.getSided().canInsertItem(slot, stack, side));
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return this.isBoundThingInRange() && (this.getSided() == null || this.getSided().canExtractItem(slot, stack, side));
    }
}
