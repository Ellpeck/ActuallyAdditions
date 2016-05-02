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
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(this.storage.getEnergyStored() >= ENERGY_USE && this.isMultiblock() == HAS_AIR){
                this.currentWorkTime++;
                if(this.currentWorkTime >= 200){
                    this.currentWorkTime = 0;
                    PosUtil.setBlock(PosUtil.offset(this.pos, 0, 1, 0), this.worldObj, Blocks.LAVA, 0, 2);
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

    public int isMultiblock(){
        BlockPos thisPos = this.pos;
        BlockPos[] positions = new BlockPos[]{
                PosUtil.offset(thisPos, 1, 1, 0),
                PosUtil.offset(thisPos, -1, 1, 0),
                PosUtil.offset(thisPos, 0, 1, 1),
                PosUtil.offset(thisPos, 0, 1, -1)
        };

        if(WorldUtil.hasBlocksInPlacesGiven(positions, InitBlocks.blockMisc, TheMiscBlocks.LAVA_FACTORY_CASE.ordinal(), this.worldObj)){
            BlockPos pos = PosUtil.offset(thisPos, 0, 1, 0);
            if(PosUtil.getBlock(pos, this.worldObj) == Blocks.LAVA || PosUtil.getBlock(pos, this.worldObj) == Blocks.FLOWING_LAVA){
                return HAS_LAVA;
            }
            if(PosUtil.getBlock(pos, this.worldObj) == null || this.worldObj.isAirBlock(pos)){
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
