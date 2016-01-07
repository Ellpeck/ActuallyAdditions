/*
 * This file ("TileEntityLeafGenerator.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.api.tile.IEnergyDisplay;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketParticle;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityLeafGenerator extends TileEntityBase implements IEnergyProvider, IEnergySaver, IEnergyDisplay{

    public static final int RANGE = 7;
    public static final int ENERGY_PRODUCED = 300;
    public EnergyStorage storage = new EnergyStorage(35000);
    private int nextUseCounter;
    private int oldEnergy;

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!this.isRedstonePowered){

                if(this.nextUseCounter >= 5){
                    this.nextUseCounter = 0;

                    if(ENERGY_PRODUCED <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored()){
                        ArrayList<Position> breakPositions = new ArrayList<Position>();

                        for(int reachX = -RANGE; reachX < RANGE+1; reachX++){
                            for(int reachZ = -RANGE; reachZ < RANGE+1; reachZ++){
                                for(int reachY = -RANGE; reachY < RANGE+1; reachY++){
                                    Position pos = new Position(this.pos.getX()+reachX, this.pos.getY()+reachY, this.pos.getZ()+reachZ);
                                    Block block = pos.getBlock(worldObj);
                                    if(block != null && block.isLeaves(this.worldObj, pos)){
                                        breakPositions.add(pos);
                                    }
                                }
                            }
                        }

                        if(!breakPositions.isEmpty()){
                            Collections.shuffle(breakPositions);
                            Position theCoord = breakPositions.get(0);

                            Block theBlock = theCoord.getBlock(worldObj);
                            int meta = theCoord.getMetadata(worldObj);
                            this.worldObj.playAuxSFX(2001, theCoord, Block.getIdFromBlock(theBlock)+(meta << 12));

                            this.worldObj.setBlockToAir(this.getPos());

                            this.storage.receiveEnergy(ENERGY_PRODUCED, false);

                            PacketHandler.theNetwork.sendToAllAround(new PacketParticle(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), theCoord.getX(), theCoord.getY(), theCoord.getZ(), new float[]{62F/255F, 163F/255F, 74F/255F}, 5, 1.0F), new NetworkRegistry.TargetPoint(worldObj.provider.getDimensionId(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 64));
                        }
                    }
                }
                else{
                    this.nextUseCounter++;
                }
            }

            if(this.storage.getEnergyStored() > 0){
                WorldUtil.pushEnergyToAllSides(worldObj, Position.fromTileEntity(this), this.storage);
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
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.storage.readFromNBT(compound);
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
