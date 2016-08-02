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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemBag extends ItemBase{

    private final boolean isVoid;

    public ItemBag(String name, boolean isVoid){
        super(name);
        this.isVoid = isVoid;
        this.setMaxStackSize(1);

        MinecraftForge.EVENT_BUS.register(this);
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
                                boolean needsSave = false;

                                boolean isVoid = ((ItemBag)invStack.getItem()).isVoid;
                                ItemStack[] inventory = new ItemStack[ContainerBag.getSlotAmount(isVoid)];
                                ItemDrill.loadSlotsFromNBT(inventory, invStack);

                                FilterSettings filter = new FilterSettings(0, 4, false, false, false, 0, 0);
                                filter.readFromNBT(invStack.getTagCompound(), "Filter");
                                if(filter.check(stack, inventory)){
                                    for(int j = 4; j < inventory.length; j++){
                                        ItemStack bagStack = inventory[j];
                                        if(bagStack != null){
                                            if(ItemUtil.canBeStacked(bagStack, stack)){
                                                int maxTransfer = Math.min(stack.stackSize, stack.getMaxStackSize()-bagStack.stackSize);
                                                if(maxTransfer > 0){
                                                    bagStack.stackSize += maxTransfer;
                                                    stack.stackSize -= maxTransfer;
                                                    needsSave = true;
                                                }
                                            }
                                        }
                                        else{
                                            inventory[j] = stack.copy();
                                            stack.stackSize = 0;
                                            needsSave = true;
                                        }

                                        if(stack.stackSize <= 0){
                                            break;
                                        }
                                    }
                                }

                                if(needsSave && !isVoid){ //void doesn't need to save as items are deleted
                                    ItemDrill.writeSlotsToNBT(inventory, invStack);
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
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        if(!world.isRemote){
            player.openGui(ActuallyAdditions.instance, (this.isVoid ? GuiTypes.VOID_BAG : GuiTypes.BAG).ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }
}
