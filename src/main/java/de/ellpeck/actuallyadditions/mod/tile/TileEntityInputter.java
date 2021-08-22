/*
 * This file ("TileEntityInputter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerInputter;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.network.gui.INumberReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityInputter extends TileEntityInventoryBase implements IButtonReactor, INumberReactor, INamedContainerProvider {

    public static final int OKAY_BUTTON_ID = 133;
    private final SlotlessableItemHandlerWrapper wrapper = new SlotlessableItemHandlerWrapper(this.lazyInv, null);
    public int sideToPut = -1;
    public int slotToPutStart;
    public int slotToPutEnd;
    public Map<Direction, SlotlessableItemHandlerWrapper> placeToPut = new ConcurrentHashMap<>();
    public int sideToPull = -1;
    public int slotToPullStart;
    public int slotToPullEnd;
    public Map<Direction, SlotlessableItemHandlerWrapper> placeToPull = new ConcurrentHashMap<>();
    public boolean isAdvanced;
    public FilterSettings leftFilter = new FilterSettings(12, true, true, false, false, 0, -1000);
    public FilterSettings rightFilter = new FilterSettings(12, true, true, false, false, 0, -2000);
    private int lastPutSide;
    private int lastPutStart;
    private int lastPutEnd;
    private int lastPullSide;
    private int lastPullStart;
    private int lastPullEnd;

    public TileEntityInputter(TileEntityType<?> type, int slots) {
        super(type, slots);
    }

    public TileEntityInputter() {
        super(ActuallyTiles.INPUTTER_TILE.get(), 1);
        this.isAdvanced = false;
    }

    @Override
    public void onNumberReceived(double number, int textID, PlayerEntity player) {
        int text = (int) number;

        if (text != -1) {
            if (textID == 0) {
                this.slotToPutStart = Math.max(text, 0);
            }
            if (textID == 1) {
                this.slotToPutEnd = Math.max(text, 0);
            }

            if (textID == 2) {
                this.slotToPullStart = Math.max(text, 0);
            }
            if (textID == 3) {
                this.slotToPullEnd = Math.max(text, 0);
            }
        }
        this.setChanged();
    }

    private boolean newPulling() {
        for (Direction side : this.placeToPull.keySet()) {
            WorldUtil.doItemInteraction(this.placeToPull.get(side), this.wrapper, Integer.MAX_VALUE, this.slotToPullStart, this.slotToPullEnd, 0, 1, !this.isAdvanced
                ? null
                : this.leftFilter);

            if (this.placeToPull instanceof TileEntityItemInterface) {
                break;
            }
        }
        return false;
    }

    private boolean newPutting() {
        if (!this.isAdvanced || this.rightFilter.check(this.inv.getStackInSlot(0))) {
            for (Direction side : this.placeToPut.keySet()) {
                WorldUtil.doItemInteraction(this.wrapper, this.placeToPut.get(side), Integer.MAX_VALUE, 0, 1, this.slotToPutStart, this.slotToPutEnd, null);

                if (this.placeToPut instanceof TileEntityItemInterface) {
                    break;
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return true;
    }

    /**
     * Sets all of the relevant variables
     */
    @Override
    public void saveDataOnChangeOrWorldStart() {
        this.placeToPull.clear();
        this.placeToPut.clear();

        if (this.sideToPull != -1) {
            Direction side = WorldUtil.getDirectionBySidesInOrder(this.sideToPull);
            BlockPos offset = this.worldPosition.relative(side);

            if (this.level.hasChunkAt(offset)) {
                TileEntity tile = this.level.getBlockEntity(offset);

                if (tile != null) {
                    for (Direction facing : Direction.values()) {
                        LazyOptional<IItemHandler> normal;
                        if (tile instanceof AbstractFurnaceTileEntity) {
                            normal = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                        } else {
                            normal = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                        }


                        // TODO: [port] add support for this back eventually.
                        Object slotless = null;
                        //                        if (ActuallyAdditions.commonCapsLoaded) {
                        //                            if (tile.hasCapability(SlotlessItemHandlerConfig.CAPABILITY, facing)) {
                        //                                slotless = tile.getCapability(SlotlessItemHandlerConfig.CAPABILITY, facing);
                        //                            }
                        //                        }

                        this.placeToPull.put(facing.getOpposite(), new SlotlessableItemHandlerWrapper(normal, slotless));
                    }

                    if (this.slotToPullEnd <= 0) {
                        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                            .ifPresent(cap -> this.slotToPullEnd = cap.getSlots());
                    }
                }
            }
        }

        if (this.sideToPut != -1) {
            Direction side = WorldUtil.getDirectionBySidesInOrder(this.sideToPut);
            BlockPos offset = this.worldPosition.relative(side);

            if (this.level.hasChunkAt(offset)) {
                TileEntity tile = this.level.getBlockEntity(offset);

                if (tile != null) {
                    for (Direction facing : Direction.values()) {
                        LazyOptional<IItemHandler> normal = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);

                        // TODO: [port] add support for this back eventually.
                        Object slotless = null;
                        //                        if (ActuallyAdditions.commonCapsLoaded) {
                        //                            if (tile.hasCapability(SlotlessItemHandlerConfig.CAPABILITY, facing)) {
                        //                                slotless = tile.getCapability(SlotlessItemHandlerConfig.CAPABILITY, facing);
                        //                            }
                        //                        }

                        this.placeToPut.put(facing.getOpposite(), new SlotlessableItemHandlerWrapper(normal, slotless));
                    }

                    if (this.slotToPutEnd <= 0) {
                        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                            .ifPresent(cap -> this.slotToPutEnd = cap.getSlots());
                    }
                }
            }
        }
    }

    @Override
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        this.leftFilter.onButtonPressed(buttonID);
        this.rightFilter.onButtonPressed(buttonID);

        //Reset the Slots
        if (buttonID == 0 || buttonID == 1) {
            this.slotToPutStart = 0;
            this.slotToPutEnd = 0;
        }
        if (buttonID == 2 || buttonID == 3) {
            this.slotToPullStart = 0;
            this.slotToPullEnd = 0;
        }

        if (buttonID == 0) {
            this.sideToPut++;
        }
        if (buttonID == 1) {
            this.sideToPut--;
        }

        if (buttonID == 2) {
            this.sideToPull++;
        }
        if (buttonID == 3) {
            this.sideToPull--;
        }

        if (this.sideToPut >= 6) {
            this.sideToPut = -1;
        } else if (this.sideToPut < -1) {
            this.sideToPut = 5;
        } else if (this.sideToPull >= 6) {
            this.sideToPull = -1;
        } else if (this.sideToPull < -1) {
            this.sideToPull = 5;
        }

        this.setChanged();
        this.saveDataOnChangeOrWorldStart();
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("SideToPut", this.sideToPut);
            compound.putInt("SlotToPut", this.slotToPutStart);
            compound.putInt("SlotToPutEnd", this.slotToPutEnd);
            compound.putInt("SideToPull", this.sideToPull);
            compound.putInt("SlotToPull", this.slotToPullStart);
            compound.putInt("SlotToPullEnd", this.slotToPullEnd);
        }

        this.leftFilter.writeToNBT(compound, "LeftFilter");
        this.rightFilter.writeToNBT(compound, "RightFilter");
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.sideToPut = compound.getInt("SideToPut");
            this.slotToPutStart = compound.getInt("SlotToPut");
            this.slotToPutEnd = compound.getInt("SlotToPutEnd");
            this.sideToPull = compound.getInt("SideToPull");
            this.slotToPullStart = compound.getInt("SlotToPull");
            this.slotToPullEnd = compound.getInt("SlotToPullEnd");
        }

        this.leftFilter.readFromNBT(compound, "LeftFilter");
        this.rightFilter.readFromNBT(compound, "RightFilter");

        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {

            //Is Block not powered by Redstone?
            if (!this.isRedstonePowered) {
                if (this.ticksElapsed % 30 == 0) {
                    if (!(this.sideToPull == this.sideToPut && this.slotToPullStart == this.slotToPutStart && this.slotToPullEnd == this.slotToPutEnd)) {
                        if (!StackUtil.isValid(this.inv.getStackInSlot(0)) && this.sideToPull != -1 && this.placeToPull != null) {
                            this.newPulling();
                        }

                        if (StackUtil.isValid(this.inv.getStackInSlot(0)) && this.sideToPut != -1 && this.placeToPut != null) {
                            this.newPutting();
                        }
                    }
                }
            }

            //Update the Client
            if ((this.sideToPut != this.lastPutSide || this.sideToPull != this.lastPullSide || this.slotToPullStart != this.lastPullStart || this.slotToPullEnd != this.lastPullEnd || this.slotToPutStart != this.lastPutStart || this.slotToPutEnd != this.lastPutEnd || this.leftFilter.needsUpdateSend() || this.rightFilter.needsUpdateSend()) && this.sendUpdateWithInterval()) {
                this.lastPutSide = this.sideToPut;
                this.lastPullSide = this.sideToPull;
                this.lastPullStart = this.slotToPullStart;
                this.lastPullEnd = this.slotToPullEnd;
                this.lastPutStart = this.slotToPutStart;
                this.lastPutEnd = this.slotToPutEnd;
                this.leftFilter.updateLasts();
                this.rightFilter.updateLasts();
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || slot == 0;
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot == 0;
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerInputter(windowId, playerInventory, this);
    }
}
