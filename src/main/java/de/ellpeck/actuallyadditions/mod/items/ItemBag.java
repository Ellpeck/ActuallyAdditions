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

import de.ellpeck.actuallyadditions.mod.inventory.ContainerBag;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class ItemBag extends ItemBase {
    public final boolean isVoid;

    public ItemBag(boolean isVoid) {
        super(ActuallyItems.defaultProps().maxStackSize(1));
        this.isVoid = isVoid;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getPlayer().getHeldItem(context.getHand());
        if (!this.isVoid) {
            TileEntity tile = context.getWorld().getTileEntity(context.getPos());
            if (tile != null) {
                if (!context.getWorld().isRemote) {
                    ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerBag.getSlotAmount(this.isVoid));

                    boolean changed = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, context.getFace())
                        .map(cap -> {
                            boolean localChanged = false;
                            ItemDrill.loadSlotsFromNBT(inv, stack);

                            for (int j = 0; j < inv.getSlots(); j++) {
                                ItemStack invStack = inv.getStackInSlot(j);
                                if (StackUtil.isValid(invStack)) {
                                    for (int i = 0; i < cap.getSlots(); i++) {
                                        ItemStack remain = cap.insertItem(i, invStack, false);
                                        if (!ItemStack.areItemStacksEqual(remain, invStack)) {
                                            inv.setStackInSlot(j, remain.copy());
                                            localChanged = true;
                                            if (!StackUtil.isValid(remain)) {
                                                break;
                                            }
                                            invStack = remain;
                                        }
                                    }
                                }
                            }

                            return localChanged;
                        }).orElse(false);

                    if (changed) {
                        ItemDrill.writeSlotsToNBT(inv, stack);
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (!world.isRemote && hand == Hand.MAIN_HAND) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) -> new ContainerBag(windowId, playerInventory, playerEntity.getHeldItem(hand), this.isVoid), StringTextComponent.EMPTY));
            //            player.openGui(ActuallyAdditions.INSTANCE, (this.isVoid
            //                ? GuiTypes.VOID_BAG
            //                : GuiTypes.BAG).ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return ActionResult.resultPass(player.getHeldItem(hand));
    }

    // TODO: [port] confirm this is correct
    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        return new CompoundNBT();
    }

    //    @Override
    //    public CompoundNBT getNBTShareTag(ItemStack stack) {
    //        return null;
    //    }
}
