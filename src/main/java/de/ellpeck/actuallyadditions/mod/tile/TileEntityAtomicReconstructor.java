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
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityAtomicReconstructor extends TileEntityInventoryBase implements ICustomEnergyReceiver, IEnergyDisplay, IAtomicReconstructor{

    public static final int ENERGY_USE = 1000;
    public final EnergyStorage storage = new EnergyStorage(300000);
    public int counter;
    private int currentTime;
    private int oldEnergy;

    public TileEntityAtomicReconstructor(){
        super(1, "reconstructor");
    }

    public static void shootLaser(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, Lens currentLens){
        world.playSound(null, startX, startY, startZ, SoundHandler.reconstructor, SoundCategory.BLOCKS, 0.35F, 1.0F);
        AssetUtil.spawnLaserWithTimeServer(world, startX, startY, startZ, endX, endY, endZ, currentLens.getColor(), 25, 0, 0.2F, 0.8F);
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("CurrentTime", this.currentTime);
            compound.setInteger("Counter", this.counter);
        }
        this.storage.writeToNBT(compound);
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public EnumFacing getOrientation(){
        IBlockState state = this.worldObj.getBlockState(this.pos);
        return WorldUtil.getDirectionByPistonRotation(state.getBlock().getMetaFromState(state));
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.currentTime = compound.getInteger("CurrentTime");
            this.counter = compound.getInteger("Counter");
        }
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(!this.isRedstonePowered && !this.isPulseMode){
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
            IBlockState state = this.worldObj.getBlockState(this.pos);
            EnumFacing sideToManipulate = WorldUtil.getDirectionByPistonRotation(state.getBlock().getMetaFromState(state));
            //The Lens the Reconstructor currently has installed
            Lens currentLens = this.getLens();
            if(currentLens.canInvoke(this, sideToManipulate, ENERGY_USE)){
                //Extract energy for shooting the laser itself too!
                this.storage.extractEnergy(ENERGY_USE, false);

                int distance = currentLens.getDistance();
                for(int i = 0; i < distance; i++){
                    BlockPos hitBlock = this.pos.offset(sideToManipulate, i+1);

                    if(currentLens.invoke(this.worldObj.getBlockState(hitBlock), hitBlock, this) || i >= distance-1){
                        shootLaser(this.worldObj, this.getX(), this.getY(), this.getZ(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), currentLens);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public Lens getLens(){
        if(StackUtil.isValid(this.slots[0])){
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
        return StackUtil.isValid(stack) && stack.getItem() instanceof ILensItem;
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
    public EnergyStorage getEnergyStorage(){
        return this.storage;
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }

    @Override
    public boolean isRedstoneToggle(){
        return true;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }
}
