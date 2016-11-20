/*
 * This file ("ItemFilter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFilter;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.List;

public class ItemFilter extends ItemBase{

    public ItemFilter(String name){
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        if(!world.isRemote){
            player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.FILTER.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
        NonNullList<ItemStack> slots = StackUtil.createSlots(ContainerFilter.SLOT_AMOUNT);
        ItemDrill.loadSlotsFromNBT(slots, stack);
        if(slots != null && slots.size() > 0){
            for(ItemStack slot : slots){
                if(StackUtil.isValid(slot)){
                    tooltip.add(slot.getItem().getItemStackDisplayName(slot));
                }
            }
        }
    }
}
