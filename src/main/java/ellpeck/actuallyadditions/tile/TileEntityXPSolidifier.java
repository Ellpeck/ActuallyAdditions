package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemSpecialDrop;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.network.gui.IButtonReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileEntityXPSolidifier extends TileEntityInventoryBase implements IButtonReactor{

    public TileEntityXPSolidifier(){
        super(12, "xpSolidifier");
    }

    public int getFirstSlot(int itemsNeeded){
        for(int i = 0; i < this.slots.length; i++){
            if(this.slots[i] == null || this.slots[i].stackSize <= this.slots[i].getMaxStackSize()-itemsNeeded){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean canUpdate(){
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return false;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return true;
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(!player.worldObj.isRemote){
            if(buttonID == 0 && player.experienceTotal >= ItemSpecialDrop.SOLID_XP_AMOUNT){
                int slot = this.getFirstSlot(1);
                if(slot >= 0){
                    if(this.slots[slot] != null){
                        this.slots[slot].stackSize++;
                    }
                    else this.slots[slot] = new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal());
                    player.addExperience(ItemSpecialDrop.SOLID_XP_AMOUNT);
                }
            }
            else if(buttonID == 1 && player.experienceTotal >= 64*ItemSpecialDrop.SOLID_XP_AMOUNT){
                int slot = this.getFirstSlot(64);
                if(slot >= 0){
                    this.slots[slot] = new ItemStack(InitItems.itemSpecialDrop, 64, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal());
                    player.addExperience(64*ItemSpecialDrop.SOLID_XP_AMOUNT);
                }
            }
        }
    }
}
