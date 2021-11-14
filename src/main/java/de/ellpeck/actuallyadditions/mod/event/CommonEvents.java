/*
 * This file ("CommonEvents.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBag;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemBag;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Locale;

public class CommonEvents {

    @SubscribeEvent
    public void onBlockBreakEvent(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        if (state != null && state.getBlock() == Blocks.SPAWNER) {
            // TODO: [port] add back once we've unflattened
            //            event.getDrops().add(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.SPAWNER_SHARD.ordinal()));
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        if (event.isCanceled() || event.getResult() == Event.Result.ALLOW) {
            return;
        }

        PlayerEntity player = event.getPlayer();
        ItemEntity item = event.getItem();
        if (item != null && item.isAlive()) {
            ItemStack stack = item.getItem();
            if (StackUtil.isValid(stack)) {
                for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                    if (i != player.inventory.selected) {

                        ItemStack invStack = player.inventory.getItem(i);
                        if (StackUtil.isValid(invStack) && invStack.getItem() instanceof ItemBag && invStack.hasTag()) {
                            if (invStack.getOrCreateTag().getBoolean("AutoInsert")) {
                                boolean changed = false;

                                boolean isVoid = ((ItemBag) invStack.getItem()).isVoid;
                                ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerBag.getSlotAmount(isVoid));
                                ItemDrill.loadSlotsFromNBT(inv, invStack);

                                FilterSettings filter = new FilterSettings(4, false, false, false, false, 0, 0);
                                filter.readFromNBT(invStack.getOrCreateTag(), "Filter");
                                if (filter.check(stack)) {
                                    if (isVoid) {
                                        stack.setCount(0);
                                        changed = true;
                                    } else {
                                        for (int j = 0; j < inv.getSlots(); j++) {
                                            ItemStack bagStack = inv.getStackInSlot(j);
                                            if (StackUtil.isValid(bagStack)) {
                                                if (ItemUtil.canBeStacked(bagStack, stack)) {
                                                    int maxTransfer = Math.min(stack.getCount(), stack.getMaxStackSize() - bagStack.getCount());
                                                    if (maxTransfer > 0) {
                                                        inv.setStackInSlot(j, StackUtil.grow(bagStack, maxTransfer));
                                                        stack.shrink(maxTransfer);
                                                        changed = true;
                                                    }
                                                }
                                            } else {
                                                inv.setStackInSlot(j, stack.copy());
                                                stack.setCount(0);
                                                changed = true;
                                            }

                                            if (!StackUtil.isValid(stack)) {
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (changed) {
                                    if (!isVoid) {
                                        ItemDrill.writeSlotsToNBT(inv, invStack);
                                    }
                                    event.setResult(Event.Result.ALLOW);
                                }
                            }
                        }
                    }

                    if (!StackUtil.isValid(stack)) {
                        break;
                    }
                }
            }

            item.setItem(stack);
        }
    }

    //TODO Checking Achievements?
    /*public static void checkAchievements(ItemStack gotten, PlayerEntity player, InitAchievements.Type type){
        if(gotten != null && player != null){
            for(TheAchievements ach : TheAchievements.values()){
                if(ach.type == type){
                    if(ItemUtil.contains(ach.itemsToBeGotten, gotten, true)){
                        ach.get(player);
                    }
                }
            }
        }
    }*/

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event) {
        if (event.getEntityLiving().level != null && !event.getEntityLiving().level.isClientSide && event.getSource().getEntity() instanceof PlayerEntity) {
            //Drop Cobwebs from Spiders
            if (ConfigBoolValues.DO_SPIDER_DROPS.isEnabled() && event.getEntityLiving() instanceof SpiderEntity) {
                if (event.getEntityLiving().level.random.nextInt(20) <= event.getLootingLevel() * 2) {
                    event.getDrops().add(new ItemEntity(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), new ItemStack(Blocks.COBWEB, event.getEntityLiving().level.random.nextInt(2 + event.getLootingLevel()) + 1)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onLogInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().level.isClientSide && event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            PacketHandlerHelper.syncPlayerData(player, true);
            ActuallyAdditions.LOGGER.info("Sending Player Data to player " + player.getName() + " with UUID " + player.getUUID() + ".");
        }
    }

    @SubscribeEvent
    public void onCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
        //checkAchievements(event.crafting, event.player, InitAchievements.Type.CRAFTING);

        if (CommonConfig.OTHER.GIVE_BOOKLET_ON_FIRST_CRAFT.get()) {
            if (!event.getPlayer().level.isClientSide && StackUtil.isValid(event.getCrafting()) && event.getCrafting().getItem() != ActuallyItems.ITEM_BOOKLET.get()) {

                String name = event.getCrafting().getItem().getRegistryName().toString();
                if (name != null && name.toLowerCase(Locale.ROOT).contains(ActuallyAdditions.MODID)) {
                    PlayerData.PlayerSave save = PlayerData.getDataFromPlayer(event.getPlayer());
                    if (save != null && !save.bookGottenAlready) {
                        save.bookGottenAlready = true;
                        WorldData.get(event.getPlayer().getCommandSenderWorld()).setDirty();

                        ItemEntity entityItem = new ItemEntity(event.getPlayer().level, event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(), new ItemStack(ActuallyItems.ITEM_BOOKLET.get()));
                        entityItem.setPickUpDelay(0);
                        event.getPlayer().level.addFreshEntity(entityItem);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSmeltedEvent(PlayerEvent.ItemSmeltedEvent event) {
        //checkAchievements(event.smelting, event.player, InitAchievements.Type.SMELTING);
    }

    @SubscribeEvent
    public void onPickupEvent(EntityItemPickupEvent event) {
        //checkAchievements(event.getItem().getItem(), event.getEntityPlayer(), InitAchievements.Type.PICK_UP);
    }
}
