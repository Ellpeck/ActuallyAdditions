/*
 * This file ("TileEntityDirectionalBreaker.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class TileEntityDirectionalBreaker extends TileEntityInventoryBase implements IEnergyReceiver, IEnergySaver, IRedstoneToggle{

    public static final int RANGE = 8;
    public static final int ENERGY_USE = 5;
    public EnergyStorage storage = new EnergyStorage(10000);
    private int lastEnergy;
    private int currentTime;
    private boolean activateOnceWithSignal;

    public TileEntityDirectionalBreaker(){
        super(9, "directionalBreaker");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!this.isRedstonePowered && !this.activateOnceWithSignal){
                if(this.storage.getEnergyStored() >= ENERGY_USE*RANGE){
                    if(this.currentTime > 0){
                        this.currentTime--;
                        if(this.currentTime <= 0){
                            this.doWork();
                        }
                    }
                    else{
                        this.currentTime = 15;
                    }
                }
            }

            if(this.storage.getEnergyStored() != this.lastEnergy && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
            }
        }
    }

    private void doWork(){
        EnumFacing sideToManipulate = WorldUtil.getDirectionByPistonRotation(PosUtil.getMetadata(this.pos, worldObj));

        for(int i = 0; i < RANGE; i++){
            BlockPos coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, pos, i);
            if(coordsBlock != null){
                Block blockToBreak = PosUtil.getBlock(coordsBlock, worldObj);
                if(blockToBreak != null && !(blockToBreak instanceof BlockAir) && blockToBreak.getBlockHardness(worldObj, pos) > -1.0F){
                    ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                    int meta = PosUtil.getMetadata(coordsBlock, worldObj);
                    drops.addAll(blockToBreak.getDrops(worldObj, coordsBlock, worldObj.getBlockState(coordsBlock), 0));

                    if(WorldUtil.addToInventory(this, drops, false, true)){
                        worldObj.playAuxSFX(2001, this.getPos(), Block.getIdFromBlock(blockToBreak)+(meta << 12));
                        WorldUtil.breakBlockAtSide(sideToManipulate, worldObj, this.getPos(), i);
                        WorldUtil.addToInventory(this, drops, true, true);
                        this.storage.extractEnergy(ENERGY_USE, false);
                        this.markDirty();
                    }
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        this.storage.writeToNBT(compound);
        compound.setInteger("CurrentTime", this.currentTime);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.storage.readFromNBT(compound);
        this.currentTime = compound.getInteger("CurrentTime");
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return true;
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
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
    public void toggle(boolean to){
        this.activateOnceWithSignal = to;
    }

    @Override
    public boolean isPulseMode(){
        return this.activateOnceWithSignal;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }
}
