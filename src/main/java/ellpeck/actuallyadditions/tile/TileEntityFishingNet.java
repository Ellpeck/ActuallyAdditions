package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.ConfigValues;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.FishingHooks;

import java.util.Random;

public class TileEntityFishingNet extends TileEntityBase{

    public int timeUntilNextDropToSet = ConfigValues.fishingNetTime;

    public int timeUntilNextDrop;

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(worldObj.getBlock(xCoord, yCoord-1, zCoord).getMaterial() == Material.water){
                Random rand = new Random();
                if(this.timeUntilNextDrop > 0){
                    this.timeUntilNextDrop--;
                    if(timeUntilNextDrop <= 0){
                        worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord+0.5, yCoord+1.8, zCoord+0.5, FishingHooks.getRandomFishable(rand, this.worldObj.rand.nextFloat())));
                    }
                }
                else this.timeUntilNextDrop = this.timeUntilNextDropToSet + rand.nextInt(this.timeUntilNextDropToSet/2);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("TimeUntilNextDrop", this.timeUntilNextDrop);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.timeUntilNextDrop = compound.getInteger("TimeUntilNextDrop");
    }
}
