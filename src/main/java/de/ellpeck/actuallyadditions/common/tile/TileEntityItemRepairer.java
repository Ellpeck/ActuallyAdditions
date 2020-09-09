package de.ellpeck.actuallyadditions.common.tile;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityItemRepairer extends TileEntityInventoryBase {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int ENERGY_USE = 2500;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 6000, 0);
    public int nextRepairTick;
    private int lastEnergy;

    public TileEntityItemRepairer() {
        super(2, "repairer");
    }

    public static boolean canBeRepaired(ItemStack stack) {
        if (StackUtil.isValid(stack)) {
            Item item = stack.getItem();
            if (item != null) {
                if (item.isRepairable() && item.getMaxDamage(stack) > 0) {
                    return true;
                } else {
                    String reg = item.getRegistryName().toString();
                    if (reg != null) {
                        for (String strg : ConfigStringListValues.REPAIRER_EXTRA_WHITELIST.getValue()) {
                            if (reg.equals(strg)) { return true; }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.setInteger("NextRepairTick", this.nextRepairTick);
        }
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.nextRepairTick = compound.getInteger("NextRepairTick");
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
                if (input.getItemDamage() <= 0) {
                    this.inv.setStackInSlot(SLOT_OUTPUT, input.copy());
                    this.inv.setStackInSlot(SLOT_INPUT, StackUtil.getEmpty());
                    this.nextRepairTick = 0;
                } else {
                    if (this.storage.getEnergyStored() >= ENERGY_USE) {
                        this.nextRepairTick++;
                        this.storage.extractEnergyInternal(ENERGY_USE, false);
                        if (this.nextRepairTick >= 4) {
                            this.nextRepairTick = 0;
                            input.setItemDamage(input.getItemDamage() - 1);

                            if (input.hasTagCompound()) {
                                //TiCon un-break tools
                                if ("tconstruct".equalsIgnoreCase(input.getItem().getRegistryName().getNamespace())) {
                                    NBTTagCompound stats = input.getTagCompound().getCompoundTag("Stats");
                                    stats.removeTag("Broken");
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

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    public int getItemDamageToScale(int i) {
        if (StackUtil.isValid(this.inv.getStackInSlot(SLOT_INPUT))) { return (this.inv.getStackInSlot(SLOT_INPUT).getMaxDamage() - this.inv.getStackInSlot(SLOT_INPUT).getItemDamage()) * i / this.inv.getStackInSlot(SLOT_INPUT).getMaxDamage(); }
        return 0;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
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
                if (split.length == 1) BLACKLIST.add(Pair.of(item, 0));
                else if (split.length == 2) {
                    BLACKLIST.add(Pair.of(item, Integer.parseInt(split[1])));
                }
            }
        }
        return BLACKLIST.contains(Pair.of(stack.getItem(), stack.getMetadata()));
    }
}
