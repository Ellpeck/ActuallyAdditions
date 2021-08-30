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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerXPSolidifier;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemSolidifiedExperience;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.List;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityXPSolidifier extends TileEntityInventoryBase implements IButtonReactor, INamedContainerProvider {

    private static final int[] XP_MAP = new int[256];

    static {
        for (int i = 0; i < XP_MAP.length; i++) {
            XP_MAP[i] = getExperienceForLevelImpl(i);
        }
    }

    private final int[] buttonAmounts = new int[]{1, 5, 10, 20, 30, 40, 50, 64, -999};
    public int amount;
    private int lastAmount;
    private int singlePointAmount;

    public TileEntityXPSolidifier() {
        super(ActuallyBlocks.XP_SOLIDIFIER.getTileEntityType(), 2);
    }

    /*
     * The below methods were excerpted from EnderIO by SleepyTrousers with permission, thanks!
     */

    public static int getExperienceForLevel(int level) {
        if (level >= 0 && level < XP_MAP.length) {
            return XP_MAP[level];
        }
        if (level >= 21863) {
            return Integer.MAX_VALUE;
        }
        return getExperienceForLevelImpl(level);
    }

    private static int getExperienceForLevelImpl(int level) {
        int res = 0;
        for (int i = 0; i < level; i++) {
            res += getXpBarCapacity(i);
            if (res < 0) {
                return Integer.MAX_VALUE;
            }
        }
        return res;
    }

    public static int getXpBarCapacity(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        } else if (level >= 15) {
            return 37 + (level - 15) * 5;
        }
        return 7 + level * 2;
    }

    public static int getLevelForExperience(int experience) {
        for (int i = 0; i < XP_MAP.length; i++) {
            if (XP_MAP[i] > experience) {
                return i - 1;
            }
        }
        int i = XP_MAP.length;
        while (getExperienceForLevel(i) <= experience) {
            i++;
        }
        return i - 1;
    }

    public static int getPlayerXP(PlayerEntity player) {
        return (int) (getExperienceForLevel(player.experienceLevel) + player.experienceProgress * player.getXpNeededForNextLevel());
    }

    public static void addPlayerXP(PlayerEntity player, int amount) {
        int experience = Math.max(0, getPlayerXP(player) + amount);
        player.totalExperience = experience;
        player.experienceLevel = getLevelForExperience(experience);
        int expForLevel = getExperienceForLevel(player.experienceLevel);
        player.experienceProgress = (float) (experience - expForLevel) / (float) player.getXpNeededForNextLevel();
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        compound.putInt("Amount", this.amount);
        compound.putInt("SinglePointAmount", this.singlePointAmount);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.amount = compound.getInt("Amount");
        this.singlePointAmount = compound.getInt("SinglePointAmount");
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (this.amount > 0) {
                ItemStack stack = this.inv.getStackInSlot(0);
                if (stack.isEmpty()) {
                    int toSet = Math.min(this.amount, 64);
                    this.inv.setStackInSlot(0, new ItemStack(ActuallyItems.SOLIDIFIED_EXPERIENCE.get(), toSet));
                    this.amount -= toSet;
                    this.setChanged();
                } else if (stack.getCount() < 64) {
                    int needed = 64 - stack.getCount();
                    int toAdd = Math.min(needed, this.amount);
                    stack.grow(toAdd);
                    this.amount -= toAdd;
                    this.setChanged();
                }
            }

            if (!this.isRedstonePowered) {
                int range = 5;
                List<ExperienceOrbEntity> orbs = this.level.getEntitiesOfClass(ExperienceOrbEntity.class, new AxisAlignedBB(this.worldPosition.getX() - range, this.worldPosition.getY() - range, this.worldPosition.getZ() - range, this.worldPosition.getX() + 1 + range, this.worldPosition.getY() + 1 + range, this.worldPosition.getZ() + 1 + range));
                if (orbs != null && !orbs.isEmpty()) {
                    for (ExperienceOrbEntity orb : orbs) {
                        // TODO: [port] validate the getPersistentData is correct
                        if (orb != null && orb.isAlive() && !orb.getPersistentData().getBoolean(ActuallyAdditions.MODID + "FromSolidified")) {
                            this.singlePointAmount += orb.getValue();
                            orb.remove();

                            if (this.singlePointAmount >= ItemSolidifiedExperience.SOLID_XP_AMOUNT) {
                                this.amount += this.singlePointAmount / ItemSolidifiedExperience.SOLID_XP_AMOUNT;
                                this.singlePointAmount = 0;
                                this.setChanged();
                            }
                        }
                    }
                }
            }

            ItemStack stack = this.inv.getStackInSlot(1);
            if (StackUtil.isValid(stack) && stack.getItem() instanceof ItemSolidifiedExperience) {
                int remainingSpace = MathHelper.clamp(Integer.MAX_VALUE - this.amount, 0, stack.getCount());
                if (stack.getCount() >= remainingSpace && remainingSpace != 0) {
                    this.amount += remainingSpace;
                    stack.shrink(remainingSpace);
                    this.setChanged();
                }
            }

            if (this.lastAmount != this.amount && this.sendUpdateWithInterval()) {
                this.lastAmount = this.amount;
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> slot == 1 && stack.getItem() == ActuallyItems.SOLIDIFIED_EXPERIENCE.get();
    }

    @Override
    public void setChanged() {
        if (this.amount < 0) {
            this.amount = Integer.MAX_VALUE; //don't u go negative on me weird number
        }
        super.setChanged();
    }

    @Override
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        if (buttonID < this.buttonAmounts.length) {
            int playerXP = getPlayerXP(player);
            if (playerXP > 0) {
                int xp = this.buttonAmounts[buttonID] == -999
                    ? playerXP / ItemSolidifiedExperience.SOLID_XP_AMOUNT
                    : this.buttonAmounts[buttonID];
                if (this.amount < Integer.MAX_VALUE - xp && playerXP >= ItemSolidifiedExperience.SOLID_XP_AMOUNT * xp) {
                    addPlayerXP(player, -(ItemSolidifiedExperience.SOLID_XP_AMOUNT * xp));
                    if (!this.level.isClientSide) {
                        this.amount += xp;
                    }
                }
            }
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerXPSolidifier(windowId, playerInventory, this);
    }
}
