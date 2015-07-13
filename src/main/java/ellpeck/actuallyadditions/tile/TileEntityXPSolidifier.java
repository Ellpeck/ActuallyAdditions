package ellpeck.actuallyadditions.tile;


import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemSpecialDrop;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.network.gui.IButtonReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileEntityXPSolidifier extends TileEntityInventoryBase implements IButtonReactor{

    public TileEntityXPSolidifier(){
        super(6, "xpSolidifier");
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

    private int getFirstAvailSlot(ItemStack stack){
        for(int i = 0; i < this.slots.length; i++){
            if(this.slots[i] == null || (this.slots[i].isItemEqual(stack) && this.slots[i].stackSize+stack.stackSize <= this.slots[i].getMaxStackSize())) return i;
        }
        return -1;
    }

    private int[] buttonAmounts = new int[]{1, 5, 10, 20, 30, 40, 50, 64, 128};

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID < buttonAmounts.length){
            for(int i = 0; i < buttonAmounts[buttonID]; i++){
                int slot = this.getFirstAvailSlot(new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal()));
                if(slot >= 0 && this.getPlayerXP(player) >= ItemSpecialDrop.SOLID_XP_AMOUNT){
                    this.addPlayerXP(player, -ItemSpecialDrop.SOLID_XP_AMOUNT);

                    if(this.slots[slot] == null) this.slots[slot] = new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal());
                    else this.slots[slot].stackSize++;
                }
            }
        }
    }

    private int getPlayerXP(EntityPlayer player){
        return (int)(this.getExperienceForLevel(player.experienceLevel)+(player.experience*player.xpBarCap()));
    }

    private void addPlayerXP(EntityPlayer player, int amount){
        int experience = getPlayerXP(player)+amount;
        player.experienceTotal = experience;

        int level = 0;
        while(getExperienceForLevel(level) <= experience){
            level++;
        }
        player.experienceLevel = level-1;

        int expForLevel = this.getExperienceForLevel(player.experienceLevel);
        player.experience = (float)(experience-expForLevel)/(float)player.xpBarCap();
    }

    private int getExperienceForLevel(int level){
        if(level != 0){
            if(level > 0 && level < 16) return level*17;
            else if(level > 15 && level < 31) return (int)(1.5*Math.pow(level, 2)-29.5*level+360);
            else return (int)(3.5*Math.pow(level, 2)-151.5*level+2220);
        }
        return 0;
    }
}
