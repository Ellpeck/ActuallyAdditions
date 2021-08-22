/*
 * This file ("TileEntityPhantomLiquiface.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityPhantomLiquiface extends TileEntityPhantomFace implements ISharingFluidHandler {

    public TileEntityPhantomLiquiface() {
        super(ActuallyBlocks.PHANTOM_LIQUIFACE.getTileEntityType());
        this.type = BlockPhantom.Type.LIQUIFACE;
    }

    @Override
    public boolean isBoundThingInRange() {
        if (super.isBoundThingInRange()) {
            TileEntity tile = this.level.getBlockEntity(this.boundPosition);
            if (tile != null && !(tile instanceof TileEntityLaserRelayFluids)) {
                for (Direction facing : Direction.values()) {
                    if (tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing).isPresent()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected boolean isCapabilitySupported(Capability<?> capability) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Override
    public int getMaxFluidAmountToSplitShare() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean doesShareFluid() {
        return true;
    }

    @Override
    public Direction[] getFluidShareSides() {
        return Direction.values();
    }
}
