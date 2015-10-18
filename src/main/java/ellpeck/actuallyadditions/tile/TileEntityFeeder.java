/*
 * This file ("TileEntityFeeder.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;
import java.util.Random;

public class TileEntityFeeder extends TileEntityInventoryBase{

    public int currentTimer;
    public int currentAnimalAmount;
    private int lastAnimalAmount;
    private int lastTimer;

    public TileEntityFeeder(){
        super(1, "feeder");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            boolean theFlag = this.currentTimer > 0;
            List<EntityAnimal> animals = worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getBoundingBox(this.xCoord-ConfigIntValues.FEEDER_REACH.getValue(), this.yCoord-ConfigIntValues.FEEDER_REACH.getValue(), this.zCoord-ConfigIntValues.FEEDER_REACH.getValue(), this.xCoord+ConfigIntValues.FEEDER_REACH.getValue(), this.yCoord+ConfigIntValues.FEEDER_REACH.getValue(), this.zCoord+ConfigIntValues.FEEDER_REACH.getValue()));
            if(animals != null){
                this.currentAnimalAmount = animals.size();
                if(this.currentAnimalAmount >= 2){
                    if(this.currentAnimalAmount < ConfigIntValues.FEEDER_THRESHOLD.getValue()){
                        if(this.currentTimer >= ConfigIntValues.FEEDER_TIME.getValue()){
                            this.currentTimer = 0;
                            if(this.slots[0] != null){
                                EntityAnimal randomAnimal = animals.get(new Random().nextInt(this.currentAnimalAmount));
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

            if(this.lastAnimalAmount != this.currentAnimalAmount || this.lastTimer != this.currentTimer){
                this.lastAnimalAmount = this.currentAnimalAmount;
                this.lastTimer = this.currentTimer;
                this.sendUpdate();
            }
        }
    }

    public void feedAnimal(EntityAnimal animal){
        animal.func_146082_f(null);
        Random rand = new Random();
        for(int i = 0; i < 7; i++){
            double d = rand.nextGaussian()*0.02D;
            double d1 = rand.nextGaussian()*0.02D;
            double d2 = rand.nextGaussian()*0.02D;
            worldObj.spawnParticle("heart", (animal.posX+(double)(rand.nextFloat()*animal.width*2.0F))-animal.width, animal.posY+0.5D+(double)(rand.nextFloat()*animal.height), (animal.posZ+(double)(rand.nextFloat()*animal.width*2.0F))-animal.width, d, d1, d2);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getCurrentTimerToScale(int i){
        return this.currentTimer*i/ConfigIntValues.FEEDER_TIME.getValue();
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTimer = compound.getInteger("Timer");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("Timer", this.currentTimer);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return true;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return false;
    }
}
