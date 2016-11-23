/*
 * This file ("TileEntityCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCoffeeMachine extends TileEntityInventoryBase implements IButtonReactor, ICustomEnergyReceiver, ISharingFluidHandler{

    public static final int SLOT_COFFEE_BEANS = 0;
    public static final int SLOT_INPUT = 1;
    public static final int SLOT_OUTPUT = 2;
    public static final int CACHE_USE = 35;
    public static final int ENERGY_USED = 150;
    public static final int WATER_USE = 500;
    public static final int COFFEE_CACHE_MAX_AMOUNT = 300;
    private static final int TIME_USED = 500;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 250);
    public final FluidTank tank = new FluidTank(4*Util.BUCKET){
        @Override
        public boolean canDrain(){
            return false;
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid){
            return fluid.getFluid() == FluidRegistry.WATER;
        }
    };
    public int coffeeCacheAmount;
    public int brewTime;
    private int lastEnergy;
    private int lastTank;
    private int lastCoffeeAmount;
    private int lastBrewTime;

    public TileEntityCoffeeMachine(){
        super(11, "coffeeMachine");
    }

    @SideOnly(Side.CLIENT)
    public int getCoffeeScaled(int i){
        return this.coffeeCacheAmount*i/COFFEE_CACHE_MAX_AMOUNT;
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
        return this.brewTime*i/TIME_USED;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("Cache", this.coffeeCacheAmount);
            compound.setInteger("Time", this.brewTime);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        if(type != NBTType.SAVE_BLOCK){
            this.coffeeCacheAmount = compound.getInteger("Cache");
            this.brewTime = compound.getInteger("Time");
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            this.storeCoffee();

            if(this.brewTime > 0 || this.isRedstonePowered){
                this.brew();
            }

            if((this.coffeeCacheAmount != this.lastCoffeeAmount || this.storage.getEnergyStored() != this.lastEnergy || this.tank.getFluidAmount() != this.lastTank || this.brewTime != this.lastBrewTime) && this.sendUpdateWithInterval()){
                this.lastCoffeeAmount = this.coffeeCacheAmount;
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastTank = this.tank.getFluidAmount();
                this.lastBrewTime = this.brewTime;
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i >= 3 && ItemCoffee.getIngredientFromStack(stack) != null) || (i == SLOT_COFFEE_BEANS && stack.getItem() == InitItems.itemCoffeeBean) || (i == SLOT_INPUT && stack.getItem() == InitItems.itemMisc && stack.getItemDamage() == TheMiscItems.CUP.ordinal());
    }

    public void storeCoffee(){
        if(StackUtil.isValid(this.slots.get(SLOT_COFFEE_BEANS)) && this.slots.get(SLOT_COFFEE_BEANS).getItem() == InitItems.itemCoffeeBean){
            int toAdd = 2;
            if(toAdd <= COFFEE_CACHE_MAX_AMOUNT-this.coffeeCacheAmount){
                this.slots.set(SLOT_COFFEE_BEANS, StackUtil.addStackSize(this.slots.get(SLOT_COFFEE_BEANS), -1));
                this.coffeeCacheAmount += toAdd;
            }
        }
    }

    public void brew(){
        if(!this.worldObj.isRemote){
            if(StackUtil.isValid(this.slots.get(SLOT_INPUT)) && this.slots.get(SLOT_INPUT).getItem() == InitItems.itemMisc && this.slots.get(SLOT_INPUT).getItemDamage() == TheMiscItems.CUP.ordinal() && !StackUtil.isValid(this.slots.get(SLOT_OUTPUT)) && this.coffeeCacheAmount >= CACHE_USE && this.tank.getFluid() != null && this.tank.getFluid().getFluid() == FluidRegistry.WATER && this.tank.getFluidAmount() >= WATER_USE){
                if(this.storage.getEnergyStored() >= ENERGY_USED){
                    if(this.brewTime%30 == 0){
                        this.worldObj.playSound(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), SoundHandler.coffeeMachine, SoundCategory.BLOCKS, 0.35F, 1.0F);
                    }

                    this.brewTime++;
                    this.storage.extractEnergyInternal(ENERGY_USED, false);
                    if(this.brewTime >= TIME_USED){
                        this.brewTime = 0;
                        ItemStack output = new ItemStack(InitItems.itemCoffee);
                        for(int i = 3; i < this.slots.size(); i++){
                            if(StackUtil.isValid(this.slots.get(i))){
                                CoffeeIngredient ingredient = ItemCoffee.getIngredientFromStack(this.slots.get(i));
                                if(ingredient != null){
                                    if(ingredient.effect(output)){
                                        this.slots.set(i, StackUtil.addStackSize(this.slots.get(i), -1));
                                    }
                                }
                            }
                        }
                        this.slots.set(SLOT_OUTPUT, output.copy());
                        this.slots.set(SLOT_INPUT, StackUtil.addStackSize(this.slots.get(SLOT_INPUT), -1));
                        this.coffeeCacheAmount -= CACHE_USE;
                        this.tank.drainInternal(WATER_USE, true);
                    }
                }
            }
            else{
                this.brewTime = 0;
            }
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return slot == SLOT_OUTPUT || (slot >= 3 && slot < this.slots.size() && ItemCoffee.getIngredientFromStack(stack) == null);
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == 0 && this.brewTime <= 0){
            this.brew();
        }
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return true;
    }

    @Override
    public FluidTank getFluidHandler(EnumFacing facing){
        return facing != EnumFacing.DOWN ? this.tank : null;
    }

    @Override
    public int getMaxFluidAmountToSplitShare(){
        return 0;
    }

    @Override
    public boolean doesShareFluid(){
        return false;
    }

    @Override
    public EnumFacing[] getFluidShareSides(){
        return null;
    }
}
