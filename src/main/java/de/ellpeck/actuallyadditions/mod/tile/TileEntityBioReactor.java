/*
 * This file ("TileEntityBioReactor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class TileEntityBioReactor extends TileEntityInventoryBase implements ISharingEnergyProvider {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(200000, 0, 800);

    public int burnTime;
    public int maxBurnTime;
    public int producePerTick;

    private int lastBurnTime;
    private int lastProducePerTick;

    public TileEntityBioReactor() {
        super(8, "bioReactor");
    }

    public static boolean isValidItem(ItemStack stack) {
        if (StackUtil.isValid(stack)) {
            Item item = stack.getItem();
            if (isValid(item)) {
                return true;
            } else if (item instanceof ItemBlock) {
                return isValid(Block.getBlockFromItem(item));
            }
        }
        return false;
    }

    private static boolean isValid(Object o) {
        return o instanceof IPlantable || o instanceof IGrowable || o instanceof ItemFood;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (this.burnTime <= 0) {
            List<Item> types = null;

            if (!this.isRedstonePowered && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
                for (int i = 0; i < this.inv.getSlots(); i++) {
                    ItemStack stack = this.inv.getStackInSlot(i);
                    if (StackUtil.isValid(stack)) {
                        Item item = stack.getItem();
                        if (isValidItem(stack) && (types == null || !types.contains(item))) {
                            if (types == null) {
                                types = new ArrayList<>();
                            }
                            types.add(item);

                            this.inv.setStackInSlot(i, StackUtil.shrink(stack, 1));
                        }
                    }
                }

                this.markDirty();
            }

            if (types != null && !types.isEmpty()) {
                int amount = types.size();
                this.producePerTick = (int) Math.pow(amount * 2, 2);

                this.maxBurnTime = 200 - (int) Math.pow(1.8, amount);
                this.burnTime = this.maxBurnTime;
            } else {
                this.burnTime = 0;
                this.maxBurnTime = 0;
                this.producePerTick = 0;
            }
        } else {
            this.burnTime--;
            this.storage.receiveEnergyInternal(this.producePerTick, false);
        }

        if ((this.lastBurnTime != this.burnTime || this.lastProducePerTick != this.producePerTick) && this.sendUpdateWithInterval()) {
            this.lastBurnTime = this.burnTime;
            this.lastProducePerTick = this.producePerTick;
        }
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.storage.writeToNBT(compound);
        compound.setInteger("BurnTime", this.burnTime);
        compound.setInteger("MaxBurnTime", this.maxBurnTime);
        compound.setInteger("ProducePerTick", this.producePerTick);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.storage.readFromNBT(compound);
        this.burnTime = compound.getInteger("BurnTime");
        this.maxBurnTime = compound.getInteger("MaxBurnTime");
        this.producePerTick = compound.getInteger("ProducePerTick");
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> isValidItem(stack);
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation;
    }

    @Override
    public int getEnergyToSplitShare() {
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy() {
        return true;
    }

    @Override
    public EnumFacing[] getEnergyShareSides() {
        return EnumFacing.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile) {
        return true;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.storage.getEnergyStored() / (float) this.storage.getMaxEnergyStored() * 15F;
        return (int) calc;
    }
}
