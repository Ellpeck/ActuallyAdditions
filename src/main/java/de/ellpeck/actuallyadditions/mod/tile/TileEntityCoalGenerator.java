/*
 * This file ("TileEntityCoalGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCoalGenerator extends TileEntityInventoryBase implements ISharingEnergyProvider {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(60000, 0, 80);
    public int maxBurnTime;
    public int currentBurnTime;
    private int lastEnergy;
    private int lastBurnTime;
    private int lastCurrentBurnTime;
    private int lastCompare;
    private ItemStack curStack = ItemStack.EMPTY;
    private int curBurn = -1;

    public TileEntityCoalGenerator() {
        super(1, "coalGenerator");
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getBurningScaled(int i) {
        return this.currentBurnTime * i / this.maxBurnTime;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.setInteger("BurnTime", this.currentBurnTime);
            compound.setInteger("MaxBurnTime", this.maxBurnTime);
        }
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.currentBurnTime = compound.getInteger("BurnTime");
            this.maxBurnTime = compound.getInteger("MaxBurnTime");
        }
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            boolean flag = this.currentBurnTime > 0;

            if (this.currentBurnTime > 0) {
                this.currentBurnTime--;
                int produce = ConfigIntValues.COAL_GENERATOR_CF_PRODUCTION.getValue();
                if (produce > 0) {
                    this.storage.addEnergyRaw(produce);
                }
            }

            ItemStack stack = this.inv.getStackInSlot(0);
            if (!stack.isEmpty() && stack != this.curStack) {
                this.curStack = stack;
                this.curBurn = TileEntityFurnace.getItemBurnTime(stack);
            } else if (stack.isEmpty()) {
                this.curStack = ItemStack.EMPTY;
                this.curBurn = -1;
            }

            if (!this.isRedstonePowered && this.currentBurnTime <= 0 && this.curBurn > 0 && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
                this.maxBurnTime = this.curBurn;
                this.currentBurnTime = this.curBurn;
                this.inv.setStackInSlot(0, StackUtil.shrinkForContainer(stack, 1));
            }

            if (flag != this.currentBurnTime > 0 || this.lastCompare != this.getComparatorStrength()) {
                this.lastCompare = this.getComparatorStrength();
                this.markDirty();
            }

            if ((this.storage.getEnergyStored() != this.lastEnergy || this.currentBurnTime != this.lastCurrentBurnTime || this.lastBurnTime != this.maxBurnTime) && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastCurrentBurnTime = this.currentBurnTime;
                this.lastBurnTime = this.currentBurnTime;
            }
        }
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.storage.getEnergyStored() / (float) this.storage.getMaxEnergyStored() * 15F;
        return (int) calc;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> TileEntityFurnace.getItemBurnTime(stack) > 0;
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> {
            if (!automation) return true;
            return TileEntityFurnace.getItemBurnTime(this.inv.getStackInSlot(0)) <= 0;
        };
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
}
