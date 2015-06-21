package ellpeck.actuallyadditions.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotImmovable extends Slot{

    public SlotImmovable(IInventory inventory, int id, int x, int y){
        super(inventory, id, x, y);
    }

    @Override
    public boolean canTakeStack(EntityPlayer player){
        return false;
    }

    @Override
    public boolean isItemValid(ItemStack stack){
        return false;
    }

}
