/*
 * This file ("TileEntityHeatCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class TileEntityHeatCollector extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay{

    public static final int ENERGY_PRODUCE = 40;
    public static final int BLOCKS_NEEDED = 4;
    public final EnergyStorage storage = new EnergyStorage(30000, 80);
    private int oldEnergy;

    public TileEntityHeatCollector(){
        super("heatCollector");
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
            ArrayList<Integer> blocksAround = new ArrayList<Integer>();
            if(ENERGY_PRODUCE <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored()){
                for(int i = 1; i <= 5; i++){
                    BlockPos coords = this.pos.offset(WorldUtil.getDirectionBySidesInOrder(i));
                    IBlockState state = this.worldObj.getBlockState(coords);
                    Block block = state.getBlock();
                    if(block != null && block.getMaterial(this.worldObj.getBlockState(coords)) == Material.LAVA && block.getMetaFromState(state) == 0){
                        blocksAround.add(i);
                    }
                }

                if(blocksAround.size() >= BLOCKS_NEEDED){
                    this.storage.receiveEnergy(ENERGY_PRODUCE, false);
                    this.markDirty();

                    if(this.worldObj.rand.nextInt(10000) == 0){
                        int randomSide = blocksAround.get(this.worldObj.rand.nextInt(blocksAround.size()));
                        this.worldObj.setBlockToAir(this.pos.offset(WorldUtil.getDirectionBySidesInOrder(randomSide)));
                    }
                }
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
