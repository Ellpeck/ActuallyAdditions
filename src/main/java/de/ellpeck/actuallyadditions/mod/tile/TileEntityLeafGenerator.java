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
import cofh.api.energy.IEnergyProvider;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketParticle;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileEntityLeafGenerator extends TileEntityBase implements IEnergyProvider, IEnergySaver, IEnergyDisplay{

    public static final int RANGE = 7;
    public static final int ENERGY_PRODUCED = 300;
    public final EnergyStorage storage = new EnergyStorage(35000);
    private int nextUseCounter;
    private int oldEnergy;

    public TileEntityLeafGenerator(){
        super("leafGenerator");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.storage.readFromNBT(compound);
    }

    @Override
    @SuppressWarnings("unchecked")
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
                                    BlockPos pos = PosUtil.offset(this.pos, reachX, reachY, reachZ);
                                    Block block = PosUtil.getBlock(pos, this.worldObj);
                                    if(block != null && block.isLeaves(this.worldObj.getBlockState(pos), this.worldObj, pos)){
                                        breakPositions.add(pos);
                                    }
                                }
                            }
                        }

                        if(!breakPositions.isEmpty()){
                            Collections.shuffle(breakPositions);
                            BlockPos theCoord = breakPositions.get(0);

                            if(!ConfigBoolValues.LESS_BLOCK_BREAKING_EFFECTS.isEnabled()){
                                this.worldObj.playEvent(2001, theCoord, Block.getStateId(this.worldObj.getBlockState(theCoord)));
                            }

                            this.worldObj.setBlockToAir(theCoord);

                            this.storage.receiveEnergy(ENERGY_PRODUCED, false);

                            if(!ConfigBoolValues.LESS_PARTICLES.isEnabled()){
                                PacketHandler.theNetwork.sendToAllAround(new PacketParticle(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), theCoord.getX(), theCoord.getY(), theCoord.getZ(), new float[]{62F/255F, 163F/255F, 74F/255F}, 5, 1.0F), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 64));
                            }
                        }
                    }
                }
                else{
                    this.nextUseCounter++;
                }
            }

            if(this.storage.getEnergyStored() > 0){
                WorldUtil.pushEnergyToAllSides(this.worldObj, this.pos, this.storage);
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
