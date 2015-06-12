package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemCoffee;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.network.gui.IButtonReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoffeeMachine extends TileEntityInventoryBase implements IButtonReactor, IEnergyReceiver{

    public static final int SLOT_COFFEE_BEANS = 0;
    public static final int SLOT_INPUT = 1;
    public static final int SLOT_OUTPUT = 2;

    public EnergyStorage storage = new EnergyStorage(300000);

    public static int energyUsePerTick = ConfigIntValues.COFFEE_MACHINE_ENERGY_USED.getValue();

    public final int coffeeCacheMaxAmount = 300;
    public final int coffeeCacheAddPerItem = ConfigIntValues.COFFEE_CACHE_ADDED_PER_ITEM.getValue();
    public final int coffeeCacheUsePerItem = ConfigIntValues.COFFEE_CACHE_USED_PER_ITEM.getValue();
    public int coffeeCacheAmount;

    public final int maxBrewTime = ConfigIntValues.COFFEE_MACHINE_TIME_USED.getValue();
    public int brewTime;

    public TileEntityCoffeeMachine(){
        super(11, "coffeeMachine");
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            this.storeCoffee();

            if(this.brewTime > 0){
                this.brew();
            }
        }
    }

    public void storeCoffee(){
        if(this.slots[SLOT_COFFEE_BEANS] != null && this.slots[SLOT_COFFEE_BEANS].getItem() == InitItems.itemCoffeeBean){
            if(this.coffeeCacheAddPerItem <= this.coffeeCacheMaxAmount-this.coffeeCacheAmount){
                this.slots[SLOT_COFFEE_BEANS].stackSize--;
                if(this.slots[SLOT_COFFEE_BEANS].stackSize <= 0) this.slots[SLOT_COFFEE_BEANS] = null;
                this.coffeeCacheAmount += this.coffeeCacheAddPerItem;
            }
        }
    }

    public void brew(){
        if(!worldObj.isRemote){
            if(this.slots[SLOT_INPUT] != null && this.slots[SLOT_INPUT].getItem() == InitItems.itemMisc && this.slots[SLOT_INPUT].getItemDamage() == TheMiscItems.CUP.ordinal() && this.slots[SLOT_OUTPUT] == null && this.storage.getEnergyStored() >= energyUsePerTick && this.coffeeCacheAmount >= this.coffeeCacheUsePerItem){
                this.brewTime++;
                if(this.brewTime >= this.maxBrewTime){
                    this.brewTime = 0;
                    ItemStack output = new ItemStack(InitItems.itemCoffee);
                    while(getFirstAvailIngredient() > 0){
                        int ingr = this.getFirstAvailIngredient();
                        ItemCoffee.Ingredient ingredient = ItemCoffee.getIngredientFromStack(this.slots[ingr]);
                        if(ingredient != null){
                            ingredient.effect(output);
                        }
                        this.slots[ingr].stackSize--;
                        if(this.slots[ingr].stackSize <= 0) this.slots[ingr] = this.slots[ingr].getItem().getContainerItem(this.slots[ingr]);
                    }
                    this.slots[SLOT_OUTPUT] = output.copy();
                    this.slots[SLOT_INPUT].stackSize--;
                    if(this.slots[SLOT_INPUT].stackSize <= 0) this.slots[SLOT_INPUT] = null;
                    this.coffeeCacheAmount -= this.coffeeCacheUsePerItem;
                }
            }
            else this.brewTime = 0;

            if(this.brewTime > 0) this.storage.extractEnergy(energyUsePerTick, false);
        }
    }

    public int getFirstAvailIngredient(){
        for(int i = 3; i < this.slots.length; i++){
            if(this.slots[i] != null && this.slots[i].stackSize == 1 && ItemCoffee.getIngredientFromStack((this.slots[i])) != null){
                return i;
            }
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public int getCoffeeScaled(int i){
        return this.coffeeCacheAmount * i / this.coffeeCacheMaxAmount;
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.getEnergyStored(ForgeDirection.UNKNOWN) * i / this.getMaxEnergyStored(ForgeDirection.UNKNOWN);
    }

    @SideOnly(Side.CLIENT)
    public int getBrewScaled(int i){
        return this.brewTime * i / this.maxBrewTime;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        this.storage.writeToNBT(compound);
        compound.setInteger("Cache", this.coffeeCacheAmount);
        compound.setInteger("Time", this.brewTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.storage.readFromNBT(compound);
        this.coffeeCacheAmount = compound.getInteger("Cache");
        this.brewTime = compound.getInteger("Time");
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == SLOT_COFFEE_BEANS && stack.getItem() == InitItems.itemCoffeeBean) || (i == SLOT_INPUT && stack.getItem() == InitItems.itemMisc && stack.getItemDamage() == TheMiscItems.CUP.ordinal());
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT || (slot >= 3 && ItemCoffee.getIngredientFromStack(stack) == null);
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == 0 && this.brewTime <= 0){
            this.brew();
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }
}
