/*
 * This file ("TileEntityFeeder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
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
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        compound.setInteger("Timer", this.currentTimer);
        if(type == NBTType.SYNC){
            compound.setInteger("Animals", this.currentAnimalAmount);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.currentTimer = compound.getInteger("Timer");
        if(type == NBTType.SYNC){
            this.currentAnimalAmount = compound.getInteger("Animals");
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            boolean theFlag = this.currentTimer > 0;
            int range = 5;
            List<EntityAnimal> animals = this.world.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(this.pos.getX()-range, this.pos.getY()-range, this.pos.getZ()-range, this.pos.getX()+range, this.pos.getY()+range, this.pos.getZ()+range));
            if(animals != null){
                this.currentAnimalAmount = animals.size();
                if(this.currentAnimalAmount >= 2){
                    if(this.currentAnimalAmount < THRESHOLD){
                        if(this.currentTimer >= TIME){
                            this.currentTimer = 0;
                            if(StackUtil.isValid(this.slots.getStackInSlot(0))){
                                EntityAnimal randomAnimal = animals.get(this.world.rand.nextInt(this.currentAnimalAmount));
                                if(!randomAnimal.isInLove() && randomAnimal.getGrowingAge() == 0 && (randomAnimal.isBreedingItem(this.slots.getStackInSlot(0)) || this.canHorseBeFed(randomAnimal))){

                                    this.feedAnimal(randomAnimal);

                                    this.slots.setStackInSlot(0, StackUtil.addStackSize(this.slots.getStackInSlot(0), -1));
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

    private boolean canHorseBeFed(EntityAnimal animal){
        if(animal instanceof EntityHorse){
            EntityHorse horse = (EntityHorse)animal;
            if(horse.isTame()){
                Item item = this.slots.getStackInSlot(0).getItem();
                return item == Items.GOLDEN_APPLE || item == Items.GOLDEN_CARROT;
            }
        }
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return true;
    }

    public void feedAnimal(EntityAnimal animal){
        animal.setInLove(null);
        for(int i = 0; i < 7; i++){
            double d = animal.world.rand.nextGaussian()*0.02D;
            double d1 = animal.world.rand.nextGaussian()*0.02D;
            double d2 = animal.world.rand.nextGaussian()*0.02D;
            this.world.spawnParticle(EnumParticleTypes.HEART, (animal.posX+(double)(animal.world.rand.nextFloat()*animal.width*2.0F))-animal.width, animal.posY+0.5D+(double)(animal.world.rand.nextFloat()*animal.height), (animal.posZ+(double)(animal.world.rand.nextFloat()*animal.width*2.0F))-animal.width, d, d1, d2);
        }
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return false;
    }
}
