/*
 * This file ("TileEntityGiantChest.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;


import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityGiantChest extends TileEntityInventoryBase implements IButtonReactor{

    public TileEntityGiantChest(int slotAmount, String name){
        super(slotAmount, name);
    }

    public TileEntityGiantChest(){
        this(9*13, "giantChest");
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return true;
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        GuiHandler.GuiTypes type;

        if(buttonID == 0){
            type = GuiHandler.GuiTypes.GIANT_CHEST;
        }
        else if(buttonID == 1){
            type = GuiHandler.GuiTypes.GIANT_CHEST_PAGE_2;
        }
        else{
            type = GuiHandler.GuiTypes.GIANT_CHEST_PAGE_3;
        }

        player.openGui(ActuallyAdditions.instance, type.ordinal(), this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ());
    }
}
