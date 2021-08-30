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

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBioReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityBioReactor extends TileEntityInventoryBase implements INamedContainerProvider, ISharingEnergyProvider {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(200000, 0, 800);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);

    public int burnTime;
    public int maxBurnTime;
    public int producePerTick;

    private int lastBurnTime;
    private int lastProducePerTick;

    public TileEntityBioReactor() {
        super(ActuallyBlocks.BIOREACTOR.getTileEntityType(),  8);
    }

    public static boolean isValidItem(ItemStack stack) {
        if (StackUtil.isValid(stack)) {
            Item item = stack.getItem();
            if (item.isEdible()) {
                return true;
            } else if (item instanceof BlockItem) {
                return isValid(Block.byItem(item));
            }
        }
        return false;
    }

    private static boolean isValid(Object o) {
        return o instanceof IPlantable || o instanceof IGrowable;
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

                this.setChanged();
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
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("MaxBurnTime", this.maxBurnTime);
        compound.putInt("ProducePerTick", this.producePerTick);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.storage.readFromNBT(compound);
        this.burnTime = compound.getInt("BurnTime");
        this.maxBurnTime = compound.getInt("MaxBurnTime");
        this.producePerTick = compound.getInt("ProducePerTick");
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
    public Direction[] getEnergyShareSides() {
        return Direction.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile) {
        return true;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public int getComparatorStrength() {
        float calc = (float) this.storage.getEnergyStored() / (float) this.storage.getMaxEnergyStored() * 15F;
        return (int) calc;
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerBioReactor(windowId, playerInventory, this);
    }
}
