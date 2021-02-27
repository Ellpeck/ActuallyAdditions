/*
 * This file ("TileEntityItemRepairer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class TileEntityItemRepairer extends TileEntityInventoryBase {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int ENERGY_USE = 2500;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 6000, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);

    public int nextRepairTick;
    private int lastEnergy;

    public TileEntityItemRepairer() {
        super(ActuallyTiles.ITEMREPAIRER_TILE.get(), 2);
    }

    public static boolean canBeRepaired(ItemStack stack) {
        if (StackUtil.isValid(stack)) {
            Item item = stack.getItem();
            if (item != null) {
                if (item.isRepairable(stack) && item.getMaxDamage(stack) > 0) {
                    return true;
                } else {
                    String reg = item.getRegistryName().toString();
                    if (reg != null) {
                        for (String strg : ConfigStringListValues.REPAIRER_EXTRA_WHITELIST.getValue()) {
                            if (reg.equals(strg)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("NextRepairTick", this.nextRepairTick);
        }
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.nextRepairTick = compound.getInt("NextRepairTick");
        }
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            ItemStack input = this.inv.getStackInSlot(SLOT_INPUT);
            if (!StackUtil.isValid(this.inv.getStackInSlot(SLOT_OUTPUT)) && canBeRepaired(input)) {
                if (input.getDamage() <= 0) {
                    this.inv.setStackInSlot(SLOT_OUTPUT, input.copy());
                    this.inv.setStackInSlot(SLOT_INPUT, StackUtil.getEmpty());
                    this.nextRepairTick = 0;
                } else {
                    if (this.storage.getEnergyStored() >= ENERGY_USE) {
                        this.nextRepairTick++;
                        this.storage.extractEnergyInternal(ENERGY_USE, false);
                        if (this.nextRepairTick >= 4) {
                            this.nextRepairTick = 0;
                            input.setDamage(input.getDamage() - 1);

                            // TODO: [port] validate this is still needed
                            if (input.hasTag()) {
                                //TiCon un-break tools
                                if ("tconstruct".equalsIgnoreCase(input.getItem().getRegistryName().getNamespace())) {
                                    CompoundNBT stats = input.getOrCreateTag().getCompound("Stats");
                                    stats.remove("Broken");
                                }
                            }
                        }
                    }
                }
            } else {
                this.nextRepairTick = 0;
            }

            if (this.lastEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    public int getItemDamageToScale(int i) {
        if (StackUtil.isValid(this.inv.getStackInSlot(SLOT_INPUT))) {
            return (this.inv.getStackInSlot(SLOT_INPUT).getMaxDamage() - this.inv.getStackInSlot(SLOT_INPUT).getDamage()) * i / this.inv.getStackInSlot(SLOT_INPUT).getMaxDamage();
        }
        return 0;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !isBlacklisted(stack) && (!automation || slot == SLOT_INPUT);
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT;
    }

    private static final List<Pair<Item, Integer>> BLACKLIST = new ArrayList<>();

    private static boolean runOnce = false;

    // TODO: [port] fix this.
    public static boolean isBlacklisted(ItemStack stack) {
        if (!runOnce) {
            runOnce = true;
            for (String s : ConfigStringListValues.REPAIR_BLACKLIST.getValue()) {
                String[] split = s.split("@");
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(split[0]));
                if (item == null) {
                    ActuallyAdditions.LOGGER.error("Invalid item in repair blacklist: " + s);
                    continue;
                }
                if (split.length == 1) {
                    BLACKLIST.add(Pair.of(item, 0));
                } else if (split.length == 2) {
                    BLACKLIST.add(Pair.of(item, Integer.parseInt(split[1])));
                }
            }
        }
        return false; //BLACKLIST.contains(stack.getItem());
    }
}
