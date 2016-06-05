/*
 * This file ("TileEntityPhantomLiquiface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityPhantomLiquiface extends TileEntityPhantomface{

    public TileEntityPhantomLiquiface(){
        super("liquiface");
        this.type = BlockPhantom.Type.LIQUIFACE;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            if(this.isRedstonePowered && this.isBoundThingInRange()){
                for(EnumFacing side : EnumFacing.values()){
                    WorldUtil.pushFluid(this, side);
                }
            }
        }
    }

    @Override
    public boolean isBoundThingInRange(){
        return super.isBoundThingInRange() && this.worldObj.getTileEntity(this.boundPosition).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    }
}
