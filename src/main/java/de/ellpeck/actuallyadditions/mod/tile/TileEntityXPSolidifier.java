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
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityXPSolidifier extends TileEntityInventoryBase implements IButtonReactor, MenuProvider {

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

    public TileEntityXPSolidifier(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.XP_SOLIDIFIER.getTileEntityType(), pos, state, 2);
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

    public static int getPlayerXP(Player player) {
        return (int) (getExperienceForLevel(player.experienceLevel) + player.experienceProgress * player.getXpNeededForNextLevel());
    }

    public static void addPlayerXP(Player player, int amount) {
        int experience = Math.max(0, getPlayerXP(player) + amount);
        player.totalExperience = experience;
        player.experienceLevel = getLevelForExperience(experience);
        int expForLevel = getExperienceForLevel(player.experienceLevel);
        player.experienceProgress = (float) (experience - expForLevel) / (float) player.getXpNeededForNextLevel();
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        compound.putInt("Amount", this.amount);
        compound.putInt("SinglePointAmount", this.singlePointAmount);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.amount = compound.getInt("Amount");
        this.singlePointAmount = compound.getInt("SinglePointAmount");
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityXPSolidifier tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityXPSolidifier tile) {
            tile.serverTick();

            if (tile.amount > 0) {
                ItemStack stack = tile.inv.getStackInSlot(0);
                if (stack.isEmpty()) {
                    int toSet = Math.min(tile.amount, 64);
                    tile.inv.setStackInSlot(0, new ItemStack(ActuallyItems.SOLIDIFIED_EXPERIENCE.get(), toSet));
                    tile.amount -= toSet;
                    tile.setChanged();
                } else if (stack.getCount() < 64) {
                    int needed = 64 - stack.getCount();
                    int toAdd = Math.min(needed, tile.amount);
                    stack.grow(toAdd);
                    tile.amount -= toAdd;
                    tile.setChanged();
                }
            }

            if (!tile.isRedstonePowered) {
                int range = 5;
                List<ExperienceOrb> orbs = level.getEntitiesOfClass(ExperienceOrb.class, new AABB(pos.getX() - range, pos.getY() - range, pos.getZ() - range, pos.getX() + 1 + range, pos.getY() + 1 + range, pos.getZ() + 1 + range));
                if (orbs != null && !orbs.isEmpty()) {
                    for (ExperienceOrb orb : orbs) {
                        if (orb != null && orb.isAlive() && !orb.getPersistentData().getBoolean(ActuallyAdditions.MODID + "FromSolidified")) {
                            tile.singlePointAmount += orb.getValue();
                            orb.discard();

                            if (tile.singlePointAmount >= ItemSolidifiedExperience.SOLID_XP_AMOUNT) {
                                tile.amount += tile.singlePointAmount / ItemSolidifiedExperience.SOLID_XP_AMOUNT;
                                tile.singlePointAmount = 0;
                                tile.setChanged();
                            }
                        }
                    }
                }
            }

            ItemStack stack = tile.inv.getStackInSlot(1);
            if (StackUtil.isValid(stack) && stack.getItem() instanceof ItemSolidifiedExperience) {
                int remainingSpace = Mth.clamp(Integer.MAX_VALUE - tile.amount, 0, stack.getCount());
                if (stack.getCount() >= remainingSpace && remainingSpace != 0) {
                    tile.amount += remainingSpace;
                    stack.shrink(remainingSpace);
                    tile.setChanged();
                }
            }

            if (tile.lastAmount != tile.amount && tile.sendUpdateWithInterval()) {
                tile.lastAmount = tile.amount;
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
    public void onButtonPressed(int buttonID, Player player) {
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
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.experienceSolidifier");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerXPSolidifier(windowId, playerInventory, this);
    }
}
