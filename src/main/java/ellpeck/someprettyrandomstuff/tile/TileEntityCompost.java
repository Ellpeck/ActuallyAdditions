package ellpeck.someprettyrandomstuff.tile;

import ellpeck.someprettyrandomstuff.config.ConfigValues;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.ItemFertilizer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCompost extends TileEntityInventoryBase{

    public final int amountNeededToConvert = ConfigValues.tileEntityCompostAmountNeededToConvert;
    public final int conversionTimeNeeded = ConfigValues.tileEntityCompostConversionTimeNeeded;

    public int conversionTime;

    public TileEntityCompost(){
        super(1, "tileEntityCompost");
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(this.slots[0] != null && !(this.slots[0].getItem() instanceof ItemFertilizer) && this.slots[0].stackSize >= this.amountNeededToConvert){
                this.conversionTime++;
                if(this.conversionTime >= this.conversionTimeNeeded){
                    this.slots[0] = new ItemStack(InitItems.itemFertilizer, this.amountNeededToConvert);
                    this.conversionTime = 0;
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("ConversionTime", this.conversionTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.conversionTime = compound.getInteger("ConversionTime");
    }
}
