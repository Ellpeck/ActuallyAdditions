/*
 * This file ("TileEntityHeatCollector.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

public class TileEntityHeatCollector extends TileEntityBase implements IEnergyProvider{

    public EnergyStorage storage = new EnergyStorage(30000);

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            ArrayList<Integer> blocksAround = new ArrayList<Integer>();
            if(ConfigIntValues.HEAT_COLLECTOR_ENERGY_PRODUCED.getValue() <= this.getMaxEnergyStored(ForgeDirection.UNKNOWN)-this.getEnergyStored(ForgeDirection.UNKNOWN)){
                for(int i = 1; i <= 5; i++){
                    WorldPos coords = WorldUtil.getCoordsFromSide(WorldUtil.getDirectionBySidesInOrder(i), worldObj, xCoord, yCoord, zCoord, 0);
                    if(coords != null){
                        Block block = worldObj.getBlock(coords.getX(), coords.getY(), coords.getZ());
                        if(block != null && block.getMaterial() == Material.lava && worldObj.getBlockMetadata(coords.getX(), coords.getY(), coords.getZ()) == 0){
                            blocksAround.add(i);
                        }
                    }
                }

                if(blocksAround.size() >= ConfigIntValues.HEAT_COLLECTOR_BLOCKS.getValue()){
                    this.storage.receiveEnergy(ConfigIntValues.HEAT_COLLECTOR_ENERGY_PRODUCED.getValue(), false);
                    this.markDirty();

                    Random rand = new Random();
                    if(rand.nextInt(ConfigIntValues.HEAT_COLLECTOR_LAVA_CHANCE.getValue()) == 0){
                        int randomSide = blocksAround.get(rand.nextInt(blocksAround.size()));
                        WorldUtil.breakBlockAtSide(WorldUtil.getDirectionBySidesInOrder(randomSide), worldObj, xCoord, yCoord, zCoord);
                    }
                }
            }

            if(this.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, this.storage);
            }
        }
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return from == ForgeDirection.UP;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        this.storage.readFromNBT(compound);
    }
}
