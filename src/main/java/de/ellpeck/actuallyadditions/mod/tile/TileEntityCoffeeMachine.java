/*
 * This file ("TileEntityCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
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
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCoffeeMachine extends TileEntityInventoryBase implements IButtonReactor, ISharingFluidHandler{

    public static final int SLOT_COFFEE_BEANS = 0;
    public static final int SLOT_INPUT = 1;
    public static final int SLOT_OUTPUT = 2;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(ConfigIntValues.COFFEE_MAKER_ENERGY_CAPACITY.getValue(), ConfigIntValues.COFFEE_MAKER_ENERGY_RECEIVE.getValue(), 0);
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
        return this.coffeeCacheAmount*i/ConfigIntValues.COFFEE_MAKER_COFFEE_STORAGE.getValue();
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
        return this.brewTime*i/ConfigIntValues.COFFEE_MAKER_PROCESSING_DURATION.getValue();
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        compound.setInteger("Cache", this.coffeeCacheAmount);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("Time", this.brewTime);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        this.coffeeCacheAmount = compound.getInteger("Cache");
        if(type != NBTType.SAVE_BLOCK){
            this.brewTime = compound.getInteger("Time");
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
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
        if(StackUtil.isValid(this.slots.getStackInSlot(SLOT_COFFEE_BEANS)) && this.slots.getStackInSlot(SLOT_COFFEE_BEANS).getItem() == InitItems.itemCoffeeBean){
            int toAdd = 2;
            if(toAdd <= ConfigIntValues.COFFEE_MAKER_COFFEE_STORAGE.getValue()-this.coffeeCacheAmount){
                this.slots.setStackInSlot(SLOT_COFFEE_BEANS, StackUtil.addStackSize(this.slots.getStackInSlot(SLOT_COFFEE_BEANS), -1));
                this.coffeeCacheAmount += toAdd;
            }
        }
    }

    public void brew(){
        if(!this.world.isRemote){
            int energyUse = ConfigIntValues.COFFEE_MAKER_ENERGY_USE.getValue();
            int waterUse = ConfigIntValues.COFFEE_MAKER_WATER_USE.getValue();
            int coffeeUse = ConfigIntValues.COFFEE_MAKER_COFFEE_USE.getValue();
            int processingDuration = ConfigIntValues.COFFEE_MAKER_PROCESSING_DURATION.getValue();
            if(StackUtil.isValid(this.slots.getStackInSlot(SLOT_INPUT)) && this.slots.getStackInSlot(SLOT_INPUT).getItem() == InitItems.itemMisc && this.slots.getStackInSlot(SLOT_INPUT).getItemDamage() == TheMiscItems.CUP.ordinal() && !StackUtil.isValid(this.slots.getStackInSlot(SLOT_OUTPUT)) && this.coffeeCacheAmount >= coffeeUse && this.tank.getFluid() != null && this.tank.getFluid().getFluid() == FluidRegistry.WATER && this.tank.getFluidAmount() >= waterUse){
                if(this.storage.getEnergyStored() >= energyUse){
                    if(this.brewTime%30 == 0){
                        this.world.playSound(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), SoundHandler.coffeeMachine, SoundCategory.BLOCKS, 0.35F, 1.0F);
                    }

                    this.brewTime++;
                    this.storage.extractEnergyInternal(energyUse, false);
                    if(this.brewTime >= processingDuration){
                        this.brewTime = 0;
                        ItemStack output = new ItemStack(InitItems.itemCoffee);
                        for(int i = 3; i < this.slots.getSlots(); i++){
                            if(StackUtil.isValid(this.slots.getStackInSlot(i))){
                                CoffeeIngredient ingredient = ItemCoffee.getIngredientFromStack(this.slots.getStackInSlot(i));
                                if(ingredient != null){
                                    if(ingredient.effect(output)){
                                        this.slots.setStackInSlot(i, StackUtil.addStackSize(this.slots.getStackInSlot(i), -1, true));
                                    }
                                }
                            }
                        }
                        this.slots.setStackInSlot(SLOT_OUTPUT, output.copy());
                        this.slots.setStackInSlot(SLOT_INPUT, StackUtil.addStackSize(this.slots.getStackInSlot(SLOT_INPUT), -1));
                        this.coffeeCacheAmount -= coffeeUse;
                        this.tank.drainInternal(waterUse, true);
                    }
                }
            }
            else{
                this.brewTime = 0;
            }
        }
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return slot == SLOT_OUTPUT || (slot >= 3 && slot < this.slots.getSlots() && ItemCoffee.getIngredientFromStack(stack) == null);
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == 0 && this.brewTime <= 0){
            this.brew();
        }
    }

    @Override
    public FluidTank getFluidHandler(EnumFacing facing){
        return this.tank;
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

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
