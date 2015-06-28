package ellpeck.actuallyadditions.tile;


import net.minecraft.item.ItemStack;

public class TileEntityGiantChest extends TileEntityInventoryBase{

    public TileEntityGiantChest(){
        super(9*13, "giantChest");
    }

    @Override
    public boolean canUpdate(){
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return true;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return true;
    }
}
