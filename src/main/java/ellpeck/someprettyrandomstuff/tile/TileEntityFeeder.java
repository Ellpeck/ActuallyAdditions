package ellpeck.someprettyrandomstuff.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.config.ConfigValues;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;
import java.util.Random;

public class TileEntityFeeder extends TileEntityInventoryBase{

    public int reach = ConfigValues.tileEntityFeederReach;
    public int timerGoal = ConfigValues.tileEntityFeederTimeNeeded;
    public int animalThreshold = ConfigValues.tileEntityFeederThreshold;

    public int currentTimer;
    public int currentAnimalAmount;
    //Is 0 or 1, gets sent to the GUI for display
    public int isBred;

    public TileEntityFeeder(){
        super(1, "tileEntityFeeder");
    }

    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            List<EntityAnimal> animals = worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getBoundingBox(this.xCoord - reach, this.yCoord - reach, this.zCoord - reach, this.xCoord + reach, this.yCoord + reach, this.zCoord + reach));
            if(animals != null){
                this.currentAnimalAmount = animals.size();
                if(this.currentAnimalAmount >= 2 && this.slots[0] != null){
                    if(this.currentAnimalAmount < this.animalThreshold){
                        if(this.currentTimer >= this.timerGoal){
                            this.currentTimer = 0;
                            EntityAnimal randomAnimal = animals.get(new Random().nextInt(this.currentAnimalAmount));
                            if(!randomAnimal.isInLove() && randomAnimal.getGrowingAge() == 0 && randomAnimal.isBreedingItem(this.slots[0])){
                                randomAnimal.func_146082_f(null);
                                this.slots[0].stackSize--;
                                if(this.slots[0].stackSize == 0) this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);

                                this.isBred = 1;
                                Random rand = new Random();
                                for(int i = 0; i < 7; i++){
                                    double d = rand.nextGaussian() * 0.02D;
                                    double d1 = rand.nextGaussian() * 0.02D;
                                    double d2 = rand.nextGaussian() * 0.02D;
                                    worldObj.spawnParticle("heart", (randomAnimal.posX + (double)(rand.nextFloat() * randomAnimal.width * 2.0F)) - randomAnimal.width, randomAnimal.posY + 0.5D + (double)(rand.nextFloat() * randomAnimal.height), (randomAnimal.posZ + (double)(rand.nextFloat() * randomAnimal.width * 2.0F)) - randomAnimal.width, d, d1, d2);
                                }
                            }
                        }
                        else{
                            if(this.currentTimer == timerGoal/10) this.isBred = 0;
                            this.currentTimer++;
                        }
                    }
                    else{
                        this.currentTimer = 0;
                        this.isBred = 0;
                    }
                }
                else{
                    this.currentTimer = 0;
                    this.isBred = 0;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getCurrentTimerToScale(int i){
        return this.currentTimer * i / this.timerGoal;
    }

    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("Timer", this.currentTimer);
    }

    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.currentTimer = compound.getInteger("Timer");
    }
}
