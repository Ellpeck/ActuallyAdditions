/*
 * This file ("TileEntityToolTable.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import net.minecraft.item.ItemStack;

public class TileEntityToolTable extends TileEntityInventoryBase{

    public static final int SLOT_OUTPUT = 6;
    public static final int INPUT_SLOT_AMOUNT = 6;

    public TileEntityToolTable(){
        super(7, "toolTable");
    }

    @Override
    public boolean canUpdate(){
        return false;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return false;
    }
}
