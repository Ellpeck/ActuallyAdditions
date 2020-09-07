package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityPhantomLiquiface extends TileEntityPhantomface implements ISharingFluidHandler {

    public TileEntityPhantomLiquiface() {
        super("liquiface");
        this.type = BlockPhantom.Type.LIQUIFACE;
    }

    @Override
    public boolean isBoundThingInRange() {
        if (super.isBoundThingInRange()) {
            TileEntity tile = this.world.getTileEntity(this.boundPosition);
            if (tile != null && !(tile instanceof TileEntityLaserRelayFluids)) {
                for (EnumFacing facing : EnumFacing.values()) {
                    if (tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) { return true; }
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
    public EnumFacing[] getFluidShareSides() {
        return EnumFacing.values();
    }
}
