/*
 * This file ("TileEntityXPSolidifier.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;


import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemSpecialDrop;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.network.gui.IButtonReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityXPSolidifier extends TileEntityInventoryBase implements IButtonReactor{

    public short amount;
    private short lastAmount;
    private int[] buttonAmounts = new int[]{1, 5, 10, 20, 30, 40, 50, 64, -999};

    public TileEntityXPSolidifier(){
        super(1, "xpSolidifier");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(this.amount > 0){
                if(this.slots[0] == null){
                    int toSet = this.amount > 64 ? 64 : this.amount;
                    this.slots[0] = new ItemStack(InitItems.itemSpecialDrop, toSet, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal());
                    this.amount -= toSet;
                }
                else if(this.slots[0].stackSize < 64){
                    int needed = 64-this.slots[0].stackSize;
                    int toAdd = this.amount > needed ? needed : this.amount;
                    this.slots[0].stackSize += toAdd;
                    this.amount -= toAdd;
                }
            }

            if(this.lastAmount != this.amount && this.trySendUpdate()){
                this.lastAmount = this.amount;
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setShort("Amount", this.amount);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.amount = compound.getShort("Amount");
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
        if(buttonID < this.buttonAmounts.length){
            if(this.getPlayerXP(player) > 0){
                int xp = this.buttonAmounts[buttonID] == -999 ? this.getPlayerXP(player)/ItemSpecialDrop.SOLID_XP_AMOUNT : this.buttonAmounts[buttonID];
                if(this.amount < Short.MAX_VALUE-xp && this.getPlayerXP(player) >= ItemSpecialDrop.SOLID_XP_AMOUNT*xp){
                    this.addPlayerXP(player, -(ItemSpecialDrop.SOLID_XP_AMOUNT*xp));
                    if(!worldObj.isRemote){
                        this.amount += xp;
                    }
                }
            }
        }
    }

    /**
     * Gets the Player's XP
     * (Excerpted from OpenBlocks' XP system with permission, thanks guys!)
     *
     * @param player The Player
     * @return The XP
     */
    private int getPlayerXP(EntityPlayer player){
        return (int)(this.getExperienceForLevel(player.experienceLevel)+(player.experience*player.xpBarCap()));
    }

    /**
     * Adds (or removes, if negative) a certain amount of XP from a player
     * (Excerpted from OpenBlocks' XP system with permission, thanks guys!)
     *
     * @param player The Player
     * @param amount The Amount
     */
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

    /**
     * Gets the amount of experience a certain level contains
     * (Excerpted from OpenBlocks' XP system with permission, thanks guys!)
     *
     * @param level The Level in question
     * @return The total XP the level has
     */
    private int getExperienceForLevel(int level){
        if(level > 0){
            if(level > 0 && level < 16){
                return level*17;
            }
            else if(level > 15 && level < 31){
                return (int)(1.5*Math.pow(level, 2)-29.5*level+360);
            }
            else{
                return (int)(3.5*Math.pow(level, 2)-151.5*level+2220);
            }
        }
        return 0;
    }
}
