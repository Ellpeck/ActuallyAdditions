/*
 * This file ("TileEntityCoffeeMachine.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

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
import ellpeck.actuallyadditions.network.sync.IPacketSyncerToClient;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityCoffeeMachine extends TileEntityInventoryBase implements IButtonReactor, IEnergyReceiver, IFluidHandler, IPacketSyncerToClient{

    public static final int SLOT_COFFEE_BEANS = 0;
    public static final int SLOT_INPUT = 1;
    public static final int SLOT_OUTPUT = 2;
    public static final int SLOT_WATER_INPUT = 11;
    public static final int SLOT_WATER_OUTPUT = 12;
    public final int waterUsedPerCoffee = 500;
    public final int coffeeCacheMaxAmount = 300;
    public EnergyStorage storage = new EnergyStorage(300000);
    public FluidTank tank = new FluidTank(4*FluidContainerRegistry.BUCKET_VOLUME);
    public int coffeeCacheAmount;
    public int brewTime;
    private int lastEnergy;
    private int lastTank;
    private int lastCoffeeAmount;
    private int lastBrewTime;

    public TileEntityCoffeeMachine(){
        super(13, "coffeeMachine");
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            this.storeCoffee();

            if(this.brewTime > 0 || this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                this.brew();
            }

            if(this.coffeeCacheAmount != this.lastCoffeeAmount || this.storage.getEnergyStored() != this.lastEnergy || this.tank.getFluidAmount() != this.lastTank || this.brewTime != this.lastBrewTime){
                this.lastCoffeeAmount = coffeeCacheAmount;
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastTank = this.tank.getFluidAmount();
                this.lastBrewTime = this.brewTime;
                this.sendUpdate();
            }
        }
    }

    public void storeCoffee(){
        if(this.slots[SLOT_COFFEE_BEANS] != null && this.slots[SLOT_COFFEE_BEANS].getItem() == InitItems.itemCoffeeBean){
            if(ConfigIntValues.COFFEE_CACHE_ADDED_PER_ITEM.getValue() <= this.coffeeCacheMaxAmount-this.coffeeCacheAmount){
                this.slots[SLOT_COFFEE_BEANS].stackSize--;
                if(this.slots[SLOT_COFFEE_BEANS].stackSize <= 0){
                    this.slots[SLOT_COFFEE_BEANS] = null;
                }
                this.coffeeCacheAmount += ConfigIntValues.COFFEE_CACHE_ADDED_PER_ITEM.getValue();
            }
        }

        WorldUtil.emptyBucket(tank, slots, SLOT_WATER_INPUT, SLOT_WATER_OUTPUT, FluidRegistry.WATER);
    }

    public void brew(){
        if(!worldObj.isRemote){
            if(this.slots[SLOT_INPUT] != null && this.slots[SLOT_INPUT].getItem() == InitItems.itemMisc && this.slots[SLOT_INPUT].getItemDamage() == TheMiscItems.CUP.ordinal() && this.slots[SLOT_OUTPUT] == null && this.coffeeCacheAmount >= ConfigIntValues.COFFEE_CACHE_USED_PER_ITEM.getValue() && this.tank.getFluid() != null && this.tank.getFluid().getFluid() == FluidRegistry.WATER && this.tank.getFluidAmount() >= this.waterUsedPerCoffee){
                if(this.storage.getEnergyStored() >= ConfigIntValues.COFFEE_MACHINE_ENERGY_USED.getValue()){
                    this.brewTime++;
                    this.storage.extractEnergy(ConfigIntValues.COFFEE_MACHINE_ENERGY_USED.getValue(), false);
                    if(this.brewTime >= ConfigIntValues.COFFEE_MACHINE_TIME_USED.getValue()){
                        this.brewTime = 0;
                        ItemStack output = new ItemStack(InitItems.itemCoffee);
                        for(int i = 3; i < this.slots.length-2; i++){
                            if(this.slots[i] != null){
                                ItemCoffee.Ingredient ingredient = ItemCoffee.getIngredientFromStack(this.slots[i]);
                                if(ingredient != null){
                                    if(ingredient.effect(output)){
                                        this.slots[i].stackSize--;
                                        if(this.slots[i].stackSize <= 0){
                                            this.slots[i] = this.slots[i].getItem().getContainerItem(this.slots[i]);
                                        }
                                    }
                                }
                            }
                        }
                        this.slots[SLOT_OUTPUT] = output.copy();
                        this.slots[SLOT_INPUT].stackSize--;
                        if(this.slots[SLOT_INPUT].stackSize <= 0){
                            this.slots[SLOT_INPUT] = null;
                        }
                        this.coffeeCacheAmount -= ConfigIntValues.COFFEE_CACHE_USED_PER_ITEM.getValue();
                        this.tank.drain(this.waterUsedPerCoffee, true);
                    }
                }
            }
            else{
                this.brewTime = 0;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getCoffeeScaled(int i){
        return this.coffeeCacheAmount*i/this.coffeeCacheMaxAmount;
    }

    @SideOnly(Side.CLIENT)
    public int getWaterScaled(int i){
        return this.tank.getFluidAmount()*i/this.tank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getBrewScaled(int i){
        return this.brewTime*i/ConfigIntValues.COFFEE_MACHINE_TIME_USED.getValue();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        compound.setInteger("Cache", this.coffeeCacheAmount);
        compound.setInteger("Time", this.brewTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        this.coffeeCacheAmount = compound.getInteger("Cache");
        this.brewTime = compound.getInteger("Time");
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i >= 3 && ItemCoffee.getIngredientFromStack(stack) != null) || (i == SLOT_COFFEE_BEANS && stack.getItem() == InitItems.itemCoffeeBean) || (i == SLOT_INPUT && stack.getItem() == InitItems.itemMisc && stack.getItemDamage() == TheMiscItems.CUP.ordinal());
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT || (slot >= 3 && slot < this.slots.length-2 && ItemCoffee.getIngredientFromStack(stack) == null) || slot == SLOT_WATER_OUTPUT;
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

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
        return resource.getFluid() == FluidRegistry.WATER && from != ForgeDirection.DOWN ? this.tank.fill(resource, doFill) : 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid){
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid){
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from){
        return new FluidTankInfo[]{this.tank.getInfo()};
    }

    @Override
    public int[] getValues(){
        return new int[]{this.tank.getFluidAmount(), this.tank.getFluid() == null ? -1 : this.tank.getFluid().getFluidID(), this.storage.getEnergyStored(), this.brewTime, this.coffeeCacheAmount};
    }

    @Override
    public void setValues(int[] values){
        if(values[1] != -1){
            this.tank.setFluid(new FluidStack(FluidRegistry.getFluid(values[1]), values[0]));
        }
        else{
            this.tank.setFluid(null);
        }
        this.storage.setEnergyStored(values[2]);
        this.brewTime = values[3];
        this.coffeeCacheAmount = values[4];
    }

    @Override
    public void sendUpdate(){
        PacketSyncerToClient.sendPacket(this);
    }
}
