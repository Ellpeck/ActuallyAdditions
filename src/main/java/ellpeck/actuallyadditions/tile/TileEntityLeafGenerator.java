/*
 * This file ("TileEntityLeafGenerator.java") is part of the Actually Additions Mod for Minecraft.
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
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.network.PacketParticle;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

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
                        ArrayList<WorldPos> breakPositions = new ArrayList<WorldPos>();

                        for(int reachX = -RANGE; reachX < RANGE+1; reachX++){
                            for(int reachZ = -RANGE; reachZ < RANGE+1; reachZ++){
                                for(int reachY = -RANGE; reachY < RANGE+1; reachY++){
                                    Block block = this.worldObj.getBlock(this.xCoord+reachX, this.yCoord+reachY, this.zCoord+reachZ);
                                    if(block != null && block.isLeaves(this.worldObj, this.xCoord+reachX, this.yCoord+reachY, this.zCoord+reachZ)){
                                        breakPositions.add(new WorldPos(this.worldObj, this.xCoord+reachX, this.yCoord+reachY, this.zCoord+reachZ));
                                    }
                                }
                            }
                        }

                        if(!breakPositions.isEmpty()){
                            Collections.shuffle(breakPositions);
                            WorldPos theCoord = breakPositions.get(0);

                            Block theBlock = this.worldObj.getBlock(theCoord.getX(), theCoord.getY(), theCoord.getZ());
                            int meta = this.worldObj.getBlockMetadata(theCoord.getX(), theCoord.getY(), theCoord.getZ());
                            this.worldObj.playAuxSFX(2001, theCoord.getX(), theCoord.getY(), theCoord.getZ(), Block.getIdFromBlock(theBlock)+(meta << 12));

                            this.worldObj.setBlockToAir(theCoord.getX(), theCoord.getY(), theCoord.getZ());

                            this.storage.receiveEnergy(ENERGY_PRODUCED, false);

                            PacketHandler.theNetwork.sendToAllAround(new PacketParticle(xCoord, yCoord, zCoord, theCoord.getX(), theCoord.getY(), theCoord.getZ(), new float[]{62F/255F, 163F/255F, 74F/255F}, 5, 1.0F), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 64));
                        }
                    }
                }
                else{
                    this.nextUseCounter++;
                }
            }

            if(this.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, storage);
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
    public int extractEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.storage.extractEnergy(maxReceive, simulate);
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
        return true;
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getMaxEnergy(){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }
}
