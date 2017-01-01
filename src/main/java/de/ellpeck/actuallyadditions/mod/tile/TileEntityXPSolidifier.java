/*
 * This file ("TileEntityXPSolidifier.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;


import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemSolidifiedExperience;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class TileEntityXPSolidifier extends TileEntityInventoryBase implements IButtonReactor{

    private static final int[] XP_MAP = new int[256];

    static{
        for(int i = 0; i < XP_MAP.length; i++){
            XP_MAP[i] = getExperienceForLevelImpl(i);
        }
    }

    private final int[] buttonAmounts = new int[]{1, 5, 10, 20, 30, 40, 50, 64, -999};
    public int amount;
    private int lastAmount;
    private int singlePointAmount;

    public TileEntityXPSolidifier(){
        super(2, "xpSolidifier");
    }

    /*
     * The below methods were excerpted from EnderIO by SleepyTrousers with permission, thanks!
     */

    public static int getExperienceForLevel(int level){
        if(level >= 0 && level < XP_MAP.length){
            return XP_MAP[level];
        }
        if(level >= 21863){
            return Integer.MAX_VALUE;
        }
        return getExperienceForLevelImpl(level);
    }

    private static int getExperienceForLevelImpl(int level){
        int res = 0;
        for(int i = 0; i < level; i++){
            res += getXpBarCapacity(i);
            if(res < 0){
                return Integer.MAX_VALUE;
            }
        }
        return res;
    }

    public static int getXpBarCapacity(int level){
        if(level >= 30){
            return 112+(level-30)*9;
        }
        else if(level >= 15){
            return 37+(level-15)*5;
        }
        return 7+level*2;
    }

    public static int getLevelForExperience(int experience){
        for(int i = 0; i < XP_MAP.length; i++){
            if(XP_MAP[i] > experience){
                return i-1;
            }
        }
        int i = XP_MAP.length;
        while(getExperienceForLevel(i) <= experience){
            i++;
        }
        return i-1;
    }

    public static int getPlayerXP(EntityPlayer player){
        return (int)(getExperienceForLevel(player.experienceLevel)+(player.experience*player.xpBarCap()));
    }

    public static void addPlayerXP(EntityPlayer player, int amount){
        int experience = Math.max(0, getPlayerXP(player)+amount);
        player.experienceTotal = experience;
        player.experienceLevel = getLevelForExperience(experience);
        int expForLevel = getExperienceForLevel(player.experienceLevel);
        player.experience = (float)(experience-expForLevel)/(float)player.xpBarCap();
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        compound.setInteger("Amount", this.amount);
        compound.setInteger("SinglePointAmount", this.singlePointAmount);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.amount = compound.getInteger("Amount");
        this.singlePointAmount = compound.getInteger("SinglePointAmount");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            if(this.amount > 0){
                if(!StackUtil.isValid(this.slots.getStackInSlot(0))){
                    int toSet = this.amount > 64 ? 64 : this.amount;
                    this.slots.setStackInSlot(0, new ItemStack(InitItems.itemSolidifiedExperience, toSet));
                    this.amount -= toSet;
                    this.markDirty();
                }
                else if(StackUtil.getStackSize(this.slots.getStackInSlot(0)) < 64){
                    int needed = 64-StackUtil.getStackSize(this.slots.getStackInSlot(0));
                    int toAdd = this.amount > needed ? needed : this.amount;
                    this.slots.setStackInSlot(0, StackUtil.addStackSize(this.slots.getStackInSlot(0), toAdd));
                    this.amount -= toAdd;
                    this.markDirty();
                }
            }

            if(!this.isRedstonePowered){
                int range = 5;
                List<EntityXPOrb> orbs = this.world.getEntitiesWithinAABB(EntityXPOrb.class, new AxisAlignedBB(this.pos.getX()-range, this.pos.getY()-range, this.pos.getZ()-range, this.pos.getX()+1+range, this.pos.getY()+1+range, this.pos.getZ()+1+range));
                if(orbs != null && !orbs.isEmpty()){
                    for(EntityXPOrb orb : orbs){
                        if(orb != null && !orb.isDead && !orb.getEntityData().getBoolean(ModUtil.MOD_ID+"FromSolidified")){
                            this.singlePointAmount += orb.getXpValue();
                            orb.setDead();

                            if(this.singlePointAmount >= ItemSolidifiedExperience.SOLID_XP_AMOUNT){
                                this.amount += this.singlePointAmount/ItemSolidifiedExperience.SOLID_XP_AMOUNT;
                                this.singlePointAmount = 0;
                                this.markDirty();
                            }
                        }
                    }
                }
            }

            if(StackUtil.isValid(this.slots.getStackInSlot(1)) && this.slots.getStackInSlot(1).getItem() instanceof ItemSolidifiedExperience){
                this.amount += StackUtil.getStackSize(this.slots.getStackInSlot(1));
                this.slots.setStackInSlot(1, StackUtil.getNull());
                this.markDirty();
            }

            if(this.lastAmount != this.amount && this.sendUpdateWithInterval()){
                this.lastAmount = this.amount;
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return true;
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID < this.buttonAmounts.length){
            int playerXP = getPlayerXP(player);
            if(playerXP > 0){
                int xp = this.buttonAmounts[buttonID] == -999 ? playerXP/ItemSolidifiedExperience.SOLID_XP_AMOUNT : this.buttonAmounts[buttonID];
                if(this.amount < Integer.MAX_VALUE-xp && playerXP >= ItemSolidifiedExperience.SOLID_XP_AMOUNT*xp){
                    addPlayerXP(player, -(ItemSolidifiedExperience.SOLID_XP_AMOUNT*xp));
                    if(!this.world.isRemote){
                        this.amount += xp;
                    }
                }
            }
        }
    }
}
