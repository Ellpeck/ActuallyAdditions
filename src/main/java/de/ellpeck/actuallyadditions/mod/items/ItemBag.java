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
        super(ActuallyItems.defaultProps().stacksTo(1));
        this.isVoid = isVoid;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        if (!this.isVoid) {
            TileEntity tile = context.getLevel().getBlockEntity(context.getClickedPos());
            if (tile != null) {
                if (!context.getLevel().isClientSide) {
                    ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerBag.getSlotAmount(this.isVoid));

                    boolean changed = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, context.getClickedFace())
                        .map(cap -> {
                            boolean localChanged = false;
                            DrillItem.loadSlotsFromNBT(inv, stack);

                            for (int j = 0; j < inv.getSlots(); j++) {
                                ItemStack invStack = inv.getStackInSlot(j);
                                if (StackUtil.isValid(invStack)) {
                                    for (int i = 0; i < cap.getSlots(); i++) {
                                        ItemStack remain = cap.insertItem(i, invStack, false);
                                        if (!ItemStack.matches(remain, invStack)) {
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
                        DrillItem.writeSlotsToNBT(inv, stack);
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide && hand == Hand.MAIN_HAND) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) -> new ContainerBag(windowId, playerInventory, playerEntity.getItemInHand(hand), this.isVoid), StringTextComponent.EMPTY));
            //            player.openGui(ActuallyAdditions.INSTANCE, (this.isVoid
            //                ? GuiTypes.VOID_BAG
            //                : GuiTypes.BAG).ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return ActionResult.pass(player.getItemInHand(hand));
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
