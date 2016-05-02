/*
 * This file ("TileEntityHeatCollector.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class TileEntityHeatCollector extends TileEntityBase implements IEnergyProvider, IEnergySaver, IEnergyDisplay{

    public static final int ENERGY_PRODUCE = 40;
    public static final int BLOCKS_NEEDED = 4;
    public EnergyStorage storage = new EnergyStorage(30000);
    private int oldEnergy;

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.writeSyncableNBT(compound, isForSync);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.readSyncableNBT(compound, isForSync);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            ArrayList<Integer> blocksAround = new ArrayList<Integer>();
            if(ENERGY_PRODUCE <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored()){
                for(int i = 1; i <= 5; i++){
                    BlockPos coords = WorldUtil.getCoordsFromSide(WorldUtil.getDirectionBySidesInOrder(i), this.pos, 0);
                    Block block = PosUtil.getBlock(coords, this.worldObj);
                    if(block != null && block.getMaterial(this.worldObj.getBlockState(coords)) == Material.LAVA && PosUtil.getMetadata(coords, this.worldObj) == 0){
                        blocksAround.add(i);
                    }
                }

                if(blocksAround.size() >= BLOCKS_NEEDED){
                    this.storage.receiveEnergy(ENERGY_PRODUCE, false);
                    this.markDirty();

                    if(Util.RANDOM.nextInt(10000) == 0){
                        int randomSide = blocksAround.get(Util.RANDOM.nextInt(blocksAround.size()));
                        WorldUtil.breakBlockAtSide(WorldUtil.getDirectionBySidesInOrder(randomSide), this.worldObj, this.pos);
                    }
                }
            }

            if(this.storage.getEnergyStored() > 0){
                WorldUtil.pushEnergy(this.worldObj, this.pos, EnumFacing.UP, this.storage);
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate){
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return from == EnumFacing.UP;
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
