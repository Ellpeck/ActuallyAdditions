package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.network.PacketTileEntityFeeder;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;
import java.util.Random;

public class TileEntityFeeder extends TileEntityInventoryBase{

    public int reach = ConfigIntValues.FEEDER_REACH.getValue();
    public int timerGoal = ConfigIntValues.FEEDER_TIME.getValue();
    public int animalThreshold = ConfigIntValues.FEEDER_THRESHOLD.getValue();

    public int currentTimer;
    public int currentAnimalAmount;

    public TileEntityFeeder(){
        super(1, "feeder");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            boolean theFlag = this.currentTimer > 0;
            List<EntityAnimal> animals = worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getBoundingBox(this.xCoord - reach, this.yCoord - reach, this.zCoord - reach, this.xCoord + reach, this.yCoord + reach, this.zCoord + reach));
            if(animals != null){
                this.currentAnimalAmount = animals.size();
                if(this.currentAnimalAmount >= 2){
                    if(this.currentAnimalAmount < this.animalThreshold){
                        if(this.currentTimer >= this.timerGoal){
                            this.currentTimer = 0;
                            if(this.slots[0] != null){
                                EntityAnimal randomAnimal = animals.get(new Random().nextInt(this.currentAnimalAmount));
                                if(!randomAnimal.isInLove() && randomAnimal.getGrowingAge() == 0 && randomAnimal.isBreedingItem(this.slots[0])){

                                    PacketHandler.theNetwork.sendToAllAround(new PacketTileEntityFeeder(this, randomAnimal.getEntityId()), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 80));
                                    this.feedAnimal(randomAnimal);

                                    this.slots[0].stackSize--;
                                    if(this.slots[0].stackSize == 0)
                                        this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
                                }
                            }
                        }
                        else this.currentTimer++;
                    }
                    else this.currentTimer = 0;
                }
                else this.currentTimer = 0;
            }

            if(theFlag != this.currentTimer > 0){
                this.markDirty();
            }
        }
    }

    public void feedAnimal(EntityAnimal animal){
        animal.func_146082_f(null);
        Random rand = new Random();
        for(int i = 0; i < 7; i++){
            double d = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle("heart", (animal.posX + (double)(rand.nextFloat() * animal.width * 2.0F)) - animal.width, animal.posY + 0.5D + (double)(rand.nextFloat() * animal.height), (animal.posZ + (double)(rand.nextFloat() * animal.width * 2.0F)) - animal.width, d, d1, d2);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getCurrentTimerToScale(int i){
        return this.currentTimer * i / this.timerGoal;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("Timer", this.currentTimer);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.currentTimer = compound.getInteger("Timer");
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
