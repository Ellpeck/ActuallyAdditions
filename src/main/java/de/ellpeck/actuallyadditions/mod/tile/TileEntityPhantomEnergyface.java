/*
 * This file ("TileEntityPhantomEnergyface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityPhantomEnergyface extends TileEntityPhantomface implements ISharingEnergyProvider {

    public TileEntityPhantomEnergyface() {
        super(ActuallyTiles.PHANTOMENERGYFACE_TILE.get());
        this.type = BlockPhantom.Type.ENERGYFACE;
    }

    @Override
    public boolean isBoundThingInRange() {
        if (super.isBoundThingInRange()) {
            TileEntity tile = this.world.getTileEntity(this.boundPosition);
            if (tile != null && !(tile instanceof TileEntityLaserRelayEnergy)) {
                for (Direction facing : Direction.values()) {
                    if (tile.getCapability(CapabilityEnergy.ENERGY, facing).isPresent()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected boolean isCapabilitySupported(Capability<?> capability) {
        return capability == CapabilityEnergy.ENERGY;
    }

    @Override
    public int getEnergyToSplitShare() {
        return Integer.MAX_VALUE;
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
}
