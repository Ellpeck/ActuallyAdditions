/*
 * This file ("TileEntityFeeder.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class TileEntityFeeder extends TileEntityInventoryBase{

    public static final int THRESHOLD = 30;
    private static final int TIME = 100;
    public int currentTimer;
    public int currentAnimalAmount;
    private int lastAnimalAmount;
    private int lastTimer;

    public TileEntityFeeder(){
        super(1, "feeder");
    }

    @SideOnly(Side.CLIENT)
    public int getCurrentTimerToScale(int i){
        return this.currentTimer*i/TIME;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            boolean theFlag = this.currentTimer > 0;
            int range = 5;
            List<EntityAnimal> animals = worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.fromBounds(this.pos.getX()-range, this.pos.getY()-range, this.pos.getZ()-range, this.pos.getX()+range, this.pos.getY()+range, this.pos.getZ()+range));
            if(animals != null){
                this.currentAnimalAmount = animals.size();
                if(this.currentAnimalAmount >= 2){
                    if(this.currentAnimalAmount < THRESHOLD){
                        if(this.currentTimer >= TIME){
                            this.currentTimer = 0;
                            if(this.slots[0] != null){
                                EntityAnimal randomAnimal = animals.get(Util.RANDOM.nextInt(this.currentAnimalAmount));
                                if(!randomAnimal.isInLove() && randomAnimal.getGrowingAge() == 0 && randomAnimal.isBreedingItem(this.slots[0])){

                                    this.feedAnimal(randomAnimal);

                                    this.slots[0].stackSize--;
                                    if(this.slots[0].stackSize == 0){
                                        this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
                                    }
                                }
                            }
                        }
                        else{
                            this.currentTimer++;
                        }
                    }
                    else{
                        this.currentTimer = 0;
                    }
                }
                else{
                    this.currentTimer = 0;
                }
            }

            if(theFlag != this.currentTimer > 0){
                this.markDirty();
            }

            if((this.lastAnimalAmount != this.currentAnimalAmount || this.lastTimer != this.currentTimer) && this.sendUpdateWithInterval()){
                this.lastAnimalAmount = this.currentAnimalAmount;
                this.lastTimer = this.currentTimer;
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("Timer", this.currentTimer);
        if(sync){
            compound.setInteger("Animals", this.currentAnimalAmount);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTimer = compound.getInteger("Timer");
        if(sync){
            this.currentAnimalAmount = compound.getInteger("Animals");
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return true;
    }

    public void feedAnimal(EntityAnimal animal){
        animal.setInLove(null);
        for(int i = 0; i < 7; i++){
            double d = Util.RANDOM.nextGaussian()*0.02D;
            double d1 = Util.RANDOM.nextGaussian()*0.02D;
            double d2 = Util.RANDOM.nextGaussian()*0.02D;
            worldObj.spawnParticle(EnumParticleTypes.HEART, (animal.posX+(double)(Util.RANDOM.nextFloat()*animal.width*2.0F))-animal.width, animal.posY+0.5D+(double)(Util.RANDOM.nextFloat()*animal.height), (animal.posZ+(double)(Util.RANDOM.nextFloat()*animal.width*2.0F))-animal.width, d, d1, d2);
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return false;
    }
}
