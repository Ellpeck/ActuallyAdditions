package ellpeck.actuallyadditions.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotFilter extends Slot{

    public SlotFilter(IInventory inventory, int id, int x, int y){
        super(inventory, id, x, y);
    }

    @Override
    public int getSlotStackLimit(){
        return 1;
    }
}
