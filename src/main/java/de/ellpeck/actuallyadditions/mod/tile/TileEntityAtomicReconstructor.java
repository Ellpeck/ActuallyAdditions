/*
 * This file ("TileEntityAtomicReconstructor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityAtomicReconstructor extends TileEntityInventoryBase implements IEnergyReceiver, IEnergySaver, IRedstoneToggle, IEnergyDisplay, IAtomicReconstructor{

    public static final int ENERGY_USE = 1000;
    public final EnergyStorage storage = new EnergyStorage(300000);
    public int counter;
    private int currentTime;
    private boolean activateOnceWithSignal;
    private int oldEnergy;

    public TileEntityAtomicReconstructor(){
        super(1, "reconstructor");
    }

    public static void shootLaser(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, Lens currentLens){
        if(!ConfigBoolValues.LESS_SOUND.isEnabled()){
            world.playSound(null, startX, startY, startZ, SoundHandler.reconstructor, SoundCategory.BLOCKS, 0.35F, 1.0F);
        }
        AssetUtil.shootParticles(world, startX, startY, startZ, endX, endY, endZ, currentLens.getColor(), ConfigBoolValues.LESS_PARTICLES.isEnabled() ? 2 : 8, 2F);
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("CurrentTime", this.currentTime);
        compound.setInteger("Counter", this.counter);
        this.storage.writeToNBT(compound);
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTime = compound.getInteger("CurrentTime");
        this.counter = compound.getInteger("Counter");
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(!this.isRedstonePowered && !this.activateOnceWithSignal){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        this.doWork();
                    }
                }
                else{
                    this.currentTime = 100;
                }
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }

    }

    private void doWork(){
        if(this.storage.getEnergyStored() >= ENERGY_USE){
            EnumFacing sideToManipulate = WorldUtil.getDirectionByPistonRotation(PosUtil.getMetadata(this.pos, this.worldObj));
            //Extract energy for shooting the laser itself too!
            this.storage.extractEnergy(ENERGY_USE, false);

            //The Lens the Reconstructor currently has installed
            Lens currentLens = this.getLens();
            int distance = currentLens.getDistance();
            for(int i = 0; i < distance; i++){
                BlockPos hitBlock = WorldUtil.getCoordsFromSide(sideToManipulate, this.pos, i);

                if(currentLens.invoke(this.worldObj.getBlockState(hitBlock), hitBlock, this)){
                    shootLaser(this.worldObj, this.getX(), this.getY(), this.getZ(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), currentLens);
                    break;
                }
                else if(i >= distance-1){
                    shootLaser(this.worldObj, this.getX(), this.getY(), this.getZ(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), currentLens);
                }
            }
        }
    }

    @Override
    public Lens getLens(){
        if(this.slots[0] != null){
            if(this.slots[0].getItem() instanceof ILensItem){
                return ((ILensItem)this.slots[0].getItem()).getLens();
            }
        }
        return this.counter >= 500 ? ActuallyAdditionsAPI.lensDisruption : ActuallyAdditionsAPI.lensDefaultConversion;
    }

    @Override
    public int getX(){
        return this.getPos().getX();
    }

    @Override
    public int getY(){
        return this.getPos().getY();
    }

    @Override
    public int getZ(){
        return this.getPos().getZ();
    }

    @Override
    public World getWorldObject(){
        return this.getWorld();
    }

    @Override
    public void extractEnergy(int amount){
        this.storage.extractEnergy(amount, false);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return stack != null && stack.getItem() instanceof ILensItem;
    }

    @Override
    public void markDirty(){
        super.markDirty();
        this.sendUpdate();
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
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
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
