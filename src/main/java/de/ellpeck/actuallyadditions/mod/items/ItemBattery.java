/*
 * This file ("ItemBattery.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import cofh.api.energy.IEnergyContainerItem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemBattery extends ItemEnergy{

    public ItemBattery(String name, int capacity, int transfer){
        super(capacity, transfer, name);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public boolean hasEffect(ItemStack stack){
        return this.isDischargeMode(stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
        if(!world.isRemote && entity instanceof EntityPlayer && this.isDischargeMode(stack)){
            EntityPlayer player = (EntityPlayer)entity;
            for(int i = 0; i < player.inventory.getSizeInventory(); i++){
                ItemStack slot = player.inventory.getStackInSlot(i);
                if(StackUtil.isValid(slot)){
                    int received = 0;

                    Item item = slot.getItem();
                    if(item instanceof IEnergyContainerItem){
                        received = ((IEnergyContainerItem)item).receiveEnergy(slot, this.getEnergyStored(stack), false);
                    }
                    else if(ActuallyAdditions.teslaLoaded && slot.hasCapability(TeslaUtil.teslaConsumer, null)){
                        ITeslaConsumer cap = slot.getCapability(TeslaUtil.teslaConsumer, null);
                        if(cap != null){
                            received = (int)cap.givePower(this.getEnergyStored(stack), false);
                        }
                    }

                    if(received > 0){
                        this.extractEnergy(stack, received, false);
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player, EnumHand hand){
        if(!worldIn.isRemote && player.isSneaking()){
            boolean isDischarge = this.isDischargeMode(stack);
            this.setDischargeMode(stack, !isDischarge);

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        return super.onItemRightClick(stack, worldIn, player, hand);
    }

    private boolean isDischargeMode(ItemStack stack){
        return stack.hasTagCompound() && stack.getTagCompound().getBoolean("DischargeMode");
    }

    private void setDischargeMode(ItemStack stack, boolean mode){
        if(!stack.hasTagCompound()){
            stack.setTagCompound(new NBTTagCompound());
        }

        stack.getTagCompound().setBoolean("DischargeMode", mode);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        super.addInformation(stack, player, list, bool);
        list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID+".battery."+(this.isDischargeMode(stack) ? "discharge" : "noDischarge")));
        list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID+".battery.changeMode"));
    }
}
