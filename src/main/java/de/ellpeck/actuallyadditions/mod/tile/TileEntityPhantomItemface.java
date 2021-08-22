/*
 * This file ("TileEntityPhantomItemface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityPhantomItemface extends TileEntityPhantomFace {

    public TileEntityPhantomItemface() {
        super(ActuallyBlocks.PHANTOM_ITEMFACE.getTileEntityType());
        this.type = BlockPhantom.Type.ITEMFACE;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || this.isBoundThingInRange();
    }

    @Override
    public boolean isBoundThingInRange() {
        if (super.isBoundThingInRange()) {
            TileEntity tile = this.level.getBlockEntity(this.getBoundPosition());
            if (tile != null) {
                for (Direction facing : Direction.values()) {
                    if (tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing).isPresent()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected boolean isCapabilitySupported(Capability<?> capability) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || this.isBoundThingInRange();
    }
}
