package ellpeck.someprettyrandomstuff.tile;

import ellpeck.someprettyrandomstuff.config.ConfigValues;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.ItemFertilizer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.IChatComponent;

public class TileEntityCompost extends TileEntityInventoryBase implements IUpdatePlayerListBox{

    public final int amountNeededToConvert = ConfigValues.tileEntityCompostAmountNeededToConvert;
    public final int conversionTimeNeeded = ConfigValues.tileEntityCompostConversionTimeNeeded;

    public int conversionTime;

    public TileEntityCompost(){
        this.slots = new ItemStack[1];
    }

    public void update(){
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

    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("ConversionTime", this.conversionTime);
    }

    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.conversionTime = compound.getInteger("ConversionTime");
    }

    public void openInventory(EntityPlayer player){

    }

    public void closeInventory(EntityPlayer player){

    }

    public int getField(int id){
        return 0;
    }

    public void setField(int id, int value){

    }

    public int getFieldCount(){
        return 0;
    }

    public void clear(){

    }

    public String getName(){
        return null;
    }

    public boolean hasCustomName(){
        return false;
    }

    public IChatComponent getDisplayName(){
        return null;
    }
}
