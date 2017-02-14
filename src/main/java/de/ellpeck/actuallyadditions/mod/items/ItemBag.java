/*
 * This file ("ItemBag.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBag;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler.GuiTypes;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerCustom;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class ItemBag extends ItemBase{

    private final boolean isVoid;

    public ItemBag(String name, boolean isVoid){
        super(name);
        this.isVoid = isVoid;
        this.setMaxStackSize(1);

        if(!this.isVoid){ //So that the event stuff only runs once because this class is initialized twice
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
        ItemStackHandlerCustom inv = new ItemStackHandlerCustom(ContainerBag.getSlotAmount(this.isVoid));
        ItemDrill.loadSlotsFromNBT(inv, stack);

        int slotsTotal = inv.getSlots();
        int slotsFilled = 0;

        for(int i = 0; i < inv.getSlots(); i++){
            if(StackUtil.isValid(inv.getStackInSlot(i))){
                slotsFilled++;
            }
        }
        tooltip.add(TextFormatting.ITALIC.toString()+String.format("%d/%d %s", slotsFilled, slotsTotal, StringUtil.localize("item."+ModUtil.MOD_ID+".item_bag.storage")));
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event){
        if(event.isCanceled() || event.getResult() == Event.Result.ALLOW){
            return;
        }

        EntityPlayer player = event.getEntityPlayer();
        EntityItem item = event.getItem();
        if(item != null && !item.isDead){
            ItemStack stack = item.getEntityItem();
            if(StackUtil.isValid(stack)){
                for(int i = 0; i < player.inventory.getSizeInventory(); i++){
                    if(i != player.inventory.currentItem){

                        ItemStack invStack = player.inventory.getStackInSlot(i);
                        if(StackUtil.isValid(invStack) && invStack.getItem() instanceof ItemBag && invStack.hasTagCompound()){
                            if(invStack.getTagCompound().getBoolean("AutoInsert")){
                                boolean changed = false;

                                boolean isVoid = ((ItemBag)invStack.getItem()).isVoid;
                                ItemStackHandlerCustom inv = new ItemStackHandlerCustom(ContainerBag.getSlotAmount(isVoid));
                                ItemDrill.loadSlotsFromNBT(inv, invStack);

                                FilterSettings filter = new FilterSettings(4, false, false, false, false, 0, 0);
                                filter.readFromNBT(invStack.getTagCompound(), "Filter");
                                if(filter.check(stack)){
                                    if(isVoid){
                                        stack = StackUtil.setStackSize(stack, 0);
                                        changed = true;
                                    }
                                    else{
                                        for(int j = 0; j < inv.getSlots(); j++){
                                            ItemStack bagStack = inv.getStackInSlot(j);
                                            if(StackUtil.isValid(bagStack)){
                                                if(ItemUtil.canBeStacked(bagStack, stack)){
                                                    int maxTransfer = Math.min(StackUtil.getStackSize(stack), stack.getMaxStackSize()-StackUtil.getStackSize(bagStack));
                                                    if(maxTransfer > 0){
                                                        inv.setStackInSlot(j, StackUtil.addStackSize(bagStack, maxTransfer));
                                                        stack = StackUtil.addStackSize(stack, -maxTransfer);
                                                        changed = true;
                                                    }
                                                }
                                            }
                                            else{
                                                inv.setStackInSlot(j, stack.copy());
                                                stack = StackUtil.setStackSize(stack, 0);
                                                changed = true;
                                            }

                                            if(!StackUtil.isValid(stack)){
                                                break;
                                            }
                                        }
                                    }
                                }

                                if(changed){
                                    if(!isVoid){
                                        ItemDrill.writeSlotsToNBT(inv, invStack);
                                    }
                                    event.setResult(Event.Result.ALLOW);
                                }
                            }
                        }
                    }

                    if(!StackUtil.isValid(stack)){
                        break;
                    }
                }
            }

            item.setEntityItemStack(stack);
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        ItemStack stack = playerIn.getHeldItem(hand);
        if(!this.isVoid){
            TileEntity tile = worldIn.getTileEntity(pos);
            if(tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)){
                if(!worldIn.isRemote){
                    IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                    if(handler != null){
                        boolean changed = false;

                        ItemStackHandlerCustom inv = new ItemStackHandlerCustom(ContainerBag.getSlotAmount(this.isVoid));
                        ItemDrill.loadSlotsFromNBT(inv, stack);

                        for(int j = 0; j < inv.getSlots(); j++){
                            ItemStack invStack = inv.getStackInSlot(j);
                            if(StackUtil.isValid(invStack)){
                                for(int i = 0; i < handler.getSlots(); i++){
                                    ItemStack remain = handler.insertItem(i, invStack, false);
                                    if(!ItemStack.areItemStacksEqual(remain, invStack)){
                                        inv.setStackInSlot(j, StackUtil.validateCopy(remain));
                                        changed = true;

                                        if(!StackUtil.isValid(remain)){
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if(changed){
                            ItemDrill.writeSlotsToNBT(inv, stack);
                        }
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        if(!world.isRemote){
            player.openGui(ActuallyAdditions.instance, (this.isVoid ? GuiTypes.VOID_BAG : GuiTypes.BAG).ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.isVoid ? EnumRarity.RARE : EnumRarity.UNCOMMON;
    }
}
