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
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityLeafGenerator extends TileEntityBase implements IEnergyProvider{

    public EnergyStorage storage = new EnergyStorage(35000);

    private int nextUseCounter;

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.storage.readFromNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        this.storage.writeToNBT(compound);
        super.writeToNBT(compound);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){

            if(this.nextUseCounter >= ConfigIntValues.LEAF_GENERATOR_COOLDOWN_TIME.getValue()){
                this.nextUseCounter = 0;

                int energyProducedPerLeaf = ConfigIntValues.LEAF_GENERATOR_ENERGY_PRODUCED.getValue();
                if(energyProducedPerLeaf <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored()){
                    ArrayList<WorldPos> breakPositions = new ArrayList<WorldPos>();

                    int range = ConfigIntValues.LEAF_GENERATOR_RANGE.getValue();
                    for(int reachX = -range; reachX < range+1; reachX++){
                        for(int reachZ = -range; reachZ < range+1; reachZ++){
                            for(int reachY = -range; reachY < range+1; reachY++){
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

                        this.storage.receiveEnergy(energyProducedPerLeaf, false);
                    }
                }
            }
            else{
                this.nextUseCounter++;
            }

            if(this.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, storage);
            }
        }
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
}
