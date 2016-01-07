/*
 * This file ("TileEntityLavaFactoryController.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.api.tile.IEnergyDisplay;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLavaFactoryController extends TileEntityBase implements IEnergyReceiver, IEnergySaver, IEnergyDisplay{

    public static final int NOT_MULTI = 0;
    public static final int HAS_LAVA = 1;
    public static final int HAS_AIR = 2;
    public static final int ENERGY_USE = 150000;
    public EnergyStorage storage = new EnergyStorage(3000000);
    private int currentWorkTime;
    private int oldEnergy;

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(this.storage.getEnergyStored() >= ENERGY_USE && this.isMultiblock() == HAS_AIR){
                this.currentWorkTime++;
                if(this.currentWorkTime >= 200){
                    this.currentWorkTime = 0;
                    Position.fromTileEntity(this).getOffsetPosition(0, 1, 0).setBlock(worldObj, Blocks.lava, 0, 2);
                    this.storage.extractEnergy(ENERGY_USE, false);
                }
            }
            else{
                this.currentWorkTime = 0;
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        this.storage.writeToNBT(compound);
        compound.setInteger("WorkTime", this.currentWorkTime);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.storage.readFromNBT(compound);
        this.currentWorkTime = compound.getInteger("WorkTime");
    }

    public int isMultiblock(){
        Position thisPos = Position.fromTileEntity(this);
        Position[] positions = new Position[]{
                thisPos.getOffsetPosition(1, 1, 0),
                thisPos.getOffsetPosition(-1, 1, 0),
                thisPos.getOffsetPosition(0, 1, 1),
                thisPos.getOffsetPosition(0, 1, -1)
        };

        if(WorldUtil.hasBlocksInPlacesGiven(positions, InitBlocks.blockMisc, TheMiscBlocks.LAVA_FACTORY_CASE.ordinal(), worldObj)){
            Position pos = thisPos.getOffsetPosition(0, 1, 0);
            if(pos.getBlock(worldObj) == Blocks.lava || pos.getBlock(worldObj) == Blocks.flowing_lava){
                return HAS_LAVA;
            }
            if(pos.getBlock(worldObj) == null || worldObj.isAirBlock(pos)){
                return HAS_AIR;
            }
        }
        return NOT_MULTI;
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxExtract, boolean simulate){
        return from != EnumFacing.UP ? this.storage.receiveEnergy(maxExtract, simulate) : 0;
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return from != EnumFacing.UP ? this.storage.getEnergyStored() : 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return from != EnumFacing.UP ? this.storage.getMaxEnergyStored() : 0;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return from != EnumFacing.UP;
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getMaxEnergy(){
        return this.storage.getMaxEnergyStored();
    }
}
