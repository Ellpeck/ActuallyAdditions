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
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerCustom;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBag extends ItemBase{

    public final boolean isVoid;

    public ItemBag(String name, boolean isVoid){
        super(name);
        this.isVoid = isVoid;
        this.setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced){
        ItemStackHandlerCustom inv = new ItemStackHandlerCustom(ContainerBag.getSlotAmount(this.isVoid));
        ItemDrill.loadSlotsFromNBT(inv, stack);

        int slotsTotal = inv.getSlots();
        int slotsFilled = 0;

        for(int i = 0; i < inv.getSlots(); i++){
            if(StackUtil.isValid(inv.getStackInSlot(i))){
                slotsFilled++;
            }
        }
        tooltip.add(TextFormatting.ITALIC+String.format("%d/%d %s", slotsFilled, slotsTotal, StringUtil.localize("item."+ActuallyAdditions.MODID+".item_bag.storage")));
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
        if(!world.isRemote && hand == EnumHand.MAIN_HAND){
            player.openGui(ActuallyAdditions.INSTANCE, (this.isVoid ? GuiTypes.VOID_BAG : GuiTypes.BAG).ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.isVoid ? EnumRarity.RARE : EnumRarity.UNCOMMON;
    }
}
