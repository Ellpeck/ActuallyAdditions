/*
 * This file ("TileEntityPhantomItemface.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.blocks.BlockPhantom;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPhantomItemface extends TileEntityPhantomface{

    public TileEntityPhantomItemface(){
        super("phantomface");
        this.type = BlockPhantom.Type.FACE;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side){
        if(this.isBoundThingInRange()){
            if(this.getSided() != null){
                return this.getSided().getAccessibleSlotsFromSide(side);
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

    public ISidedInventory getSided(){
        return this.getInventory() instanceof ISidedInventory ? (ISidedInventory)this.getInventory() : null;
    }

    @Override
    public int getInventoryStackLimit(){
        return this.isBoundThingInRange() ? this.getInventory().getInventoryStackLimit() : 0;
    }

    @Override
    public boolean isBoundThingInRange(){
        return super.isBoundThingInRange() && this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IInventory;
    }

    public IInventory getInventory(){
        if(this.boundPosition != null && this.boundPosition.getWorld() != null){
            TileEntity tile = boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
            if(tile instanceof IInventory){
                return (IInventory)tile;
            }
        }
        return null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isBoundThingInRange() && (this.getSided() == null || this.getSided().canInsertItem(slot, stack, side));
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return this.isBoundThingInRange() && (this.getSided() == null || this.getSided().canExtractItem(slot, stack, side));
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return this.isBoundThingInRange() && this.getInventory().isItemValidForSlot(i, stack);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i){
        return this.isBoundThingInRange() ? this.getInventory().getStackInSlotOnClosing(i) : null;
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
    public String getInventoryName(){
        return this.name;
    }

}
