/*
 * This file ("ItemBag.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBag;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler.GuiTypes;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
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
        tooltip.add(TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".previously"+(this.isVoid ? "VoidBag" : "Bag")));
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event){
        EntityPlayer player = event.getEntityPlayer();
        EntityItem item = event.getItem();
        if(item != null && !item.isDead){
            ItemStack stack = item.getEntityItem();
            if(stack != null && stack.getItem() != null){
                for(int i = 0; i < player.inventory.getSizeInventory(); i++){
                    if(i != player.inventory.currentItem){

                        ItemStack invStack = player.inventory.getStackInSlot(i);
                        if(invStack != null && invStack.getItem() instanceof ItemBag && invStack.hasTagCompound()){
                            if(invStack.getTagCompound().getBoolean("AutoInsert")){
                                boolean changed = false;

                                boolean isVoid = ((ItemBag)invStack.getItem()).isVoid;
                                ItemStack[] inventory = new ItemStack[ContainerBag.getSlotAmount(isVoid)];
                                ItemDrill.loadSlotsFromNBT(inventory, invStack);

                                FilterSettings filter = new FilterSettings(0, 4, false, false, false, 0, 0);
                                filter.readFromNBT(invStack.getTagCompound(), "Filter");
                                if(filter.check(stack, inventory)){
                                    if(isVoid){
                                        stack.stackSize = 0;
                                        changed = true;
                                    }
                                    else{
                                        for(int j = 4; j < inventory.length; j++){
                                            ItemStack bagStack = inventory[j];
                                            if(bagStack != null){
                                                if(ItemUtil.canBeStacked(bagStack, stack)){
                                                    int maxTransfer = Math.min(stack.stackSize, stack.getMaxStackSize()-bagStack.stackSize);
                                                    if(maxTransfer > 0){
                                                        bagStack.stackSize += maxTransfer;
                                                        stack.stackSize -= maxTransfer;
                                                        changed = true;
                                                    }
                                                }
                                            }
                                            else{
                                                inventory[j] = stack.copy();
                                                stack.stackSize = 0;
                                                changed = true;
                                            }

                                            if(stack.stackSize <= 0){
                                                break;
                                            }
                                        }
                                    }
                                }

                                if(changed){
                                    if(!isVoid){
                                        ItemDrill.writeSlotsToNBT(inventory, invStack);
                                    }
                                    event.setResult(Event.Result.ALLOW);
                                }
                            }
                        }
                    }

                    if(stack.stackSize <= 0){
                        break;
                    }
                }
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!this.isVoid){
            TileEntity tile = worldIn.getTileEntity(pos);
            if(tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)){
                if(!worldIn.isRemote){
                    IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                    if(handler != null){
                        boolean changed = false;

                        ItemStack[] inventory = new ItemStack[ContainerBag.getSlotAmount(this.isVoid)];
                        ItemDrill.loadSlotsFromNBT(inventory, stack);

                        for(int j = 4; j < inventory.length; j++){
                            ItemStack invStack = inventory[j];
                            if(invStack != null){
                                for(int i = 0; i < handler.getSlots(); i++){
                                    ItemStack remain = handler.insertItem(i, invStack, false);
                                    if(!ItemStack.areItemStacksEqual(remain, invStack)){
                                        inventory[j] = remain == null ? null : remain.copy();
                                        changed = true;

                                        if(remain == null){
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if(changed){
                            ItemDrill.writeSlotsToNBT(inventory, stack);
                        }
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        if(!world.isRemote){
            player.openGui(ActuallyAdditions.instance, (this.isVoid ? GuiTypes.VOID_BAG : GuiTypes.BAG).ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.isVoid ? EnumRarity.RARE : EnumRarity.UNCOMMON;
    }
}
