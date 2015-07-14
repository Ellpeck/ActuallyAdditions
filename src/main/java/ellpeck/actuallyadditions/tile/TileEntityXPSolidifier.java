package ellpeck.actuallyadditions.tile;


import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemSpecialDrop;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.network.gui.IButtonReactor;
import ellpeck.actuallyadditions.network.sync.IPacketSyncerToClient;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityXPSolidifier extends TileEntityInventoryBase implements IButtonReactor, IPacketSyncerToClient{

    public short amount;
    private short lastAmount;

    public TileEntityXPSolidifier(){
        super(1, "xpSolidifier");
    }

    @Override
    public void updateEntity(){
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

            if(this.lastAmount != this.amount){
                this.lastAmount = this.amount;
                this.sendUpdate();
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setShort("Amount", this.amount);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
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

    private int[] buttonAmounts = new int[]{1, 5, 10, 20, 30, 40, 50, 64, -999};

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID < this.buttonAmounts.length){
            if(this.buttonAmounts[buttonID] != -999){
                if(this.amount < Short.MAX_VALUE-this.buttonAmounts[buttonID] && this.getPlayerXP(player) >= ItemSpecialDrop.SOLID_XP_AMOUNT*this.buttonAmounts[buttonID]){
                    this.addPlayerXP(player, -(ItemSpecialDrop.SOLID_XP_AMOUNT*this.buttonAmounts[buttonID]));
                    this.amount += this.buttonAmounts[buttonID];
                }
            }
            else{
                int xp = this.getPlayerXP(player)/ItemSpecialDrop.SOLID_XP_AMOUNT;
                if(this.amount < Short.MAX_VALUE-xp){
                    this.addPlayerXP(player, -(xp*ItemSpecialDrop.SOLID_XP_AMOUNT));
                    this.amount += xp;
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

    @Override
    public int[] getValues(){
        return new int[]{this.amount};
    }

    @Override
    public void setValues(int[] values){
        this.amount = (short)values[0];
    }

    @Override
    public void sendUpdate(){
        PacketSyncerToClient.sendPacket(this);
    }
}
