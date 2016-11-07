/*
 * This file ("TileEntityLeafGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileEntityLeafGenerator extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay{

    public static final int RANGE = 7;
    public static final int ENERGY_PRODUCED = 300;
    public final EnergyStorage storage = new EnergyStorage(35000);
    private int nextUseCounter;
    private int oldEnergy;

    public TileEntityLeafGenerator(){
        super("leafGenerator");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(!this.isRedstonePowered){

                if(this.nextUseCounter >= 5){
                    this.nextUseCounter = 0;

                    if(ENERGY_PRODUCED <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored()){
                        List<BlockPos> breakPositions = new ArrayList<BlockPos>();

                        for(int reachX = -RANGE; reachX < RANGE+1; reachX++){
                            for(int reachZ = -RANGE; reachZ < RANGE+1; reachZ++){
                                for(int reachY = -RANGE; reachY < RANGE+1; reachY++){
                                    BlockPos pos = this.pos.add(reachX, reachY, reachZ);
                                    Block block = this.worldObj.getBlockState(pos).getBlock();
                                    if(block != null && block.isLeaves(this.worldObj.getBlockState(pos), this.worldObj, pos)){
                                        breakPositions.add(pos);
                                    }
                                }
                            }
                        }

                        if(!breakPositions.isEmpty()){
                            Collections.shuffle(breakPositions);
                            BlockPos theCoord = breakPositions.get(0);

                            this.worldObj.playEvent(2001, theCoord, Block.getStateId(this.worldObj.getBlockState(theCoord)));

                            this.worldObj.setBlockToAir(theCoord);

                            this.storage.receiveEnergy(ENERGY_PRODUCED, false);

                            AssetUtil.shootParticles(this.worldObj, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), theCoord.getX(), theCoord.getY(), theCoord.getZ(), new float[]{62F/255F, 163F/255F, 74F/255F}, 5, 1.0F, 1F);
                        }
                    }
                }
                else{
                    this.nextUseCounter++;
                }
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.storage.extractEnergy(maxReceive, simulate);
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
        return true;
    }

    @Override
    public EnergyStorage getEnergyStorage(){
        return this.storage;
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }

    @Override
    public int getEnergyToSplitShare(){
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy(){
        return true;
    }

    @Override
    public EnumFacing[] getEnergyShareSides(){
        return EnumFacing.values();
    }
}
