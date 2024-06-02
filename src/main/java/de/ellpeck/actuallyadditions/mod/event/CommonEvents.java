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
import de.ellpeck.actuallyadditions.mod.attachments.ActuallyAttachments;
import de.ellpeck.actuallyadditions.mod.blocks.BlockLaserRelay;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemTag;
import de.ellpeck.actuallyadditions.mod.items.Sack;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.sack.SackManager;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.EntityItemPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Locale;

public class CommonEvents {
    @SubscribeEvent
    public void onBlockRightClick(PlayerInteractEvent.RightClickBlock event) { //Workaround, cant sneak right click a block with an item normally.
        if (event.getLevel().isClientSide)
            return;
        if( (event.getLevel().getBlockState(event.getHitVec().getBlockPos()).getBlock() instanceof BlockLaserRelay) && (event.getItemStack().is(CommonConfig.Other.relayConfigureItem))) {
            event.setUseItem(Event.Result.DENY);
            event.setUseBlock(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        if (event.isCanceled() || event.getResult() == Event.Result.ALLOW) {
            return;
        }

        Player player = event.getEntity();
        ItemEntity item = event.getItem();
        if (item != null && item.isAlive()) {
            ItemStack stack = item.getItem();
            if (StackUtil.isValid(stack)) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    if (i != player.getInventory().selected) {

                        ItemStack invStack = player.getInventory().getItem(i);
                        if (StackUtil.isValid(invStack) && (invStack.getItem() instanceof Sack) && invStack.hasTag()) {
                            if (invStack.getOrCreateTag().getBoolean("AutoInsert")) {
                                boolean changed = false;
                                boolean isVoid = ((Sack) invStack.getItem()).isVoid;

                                FilterSettings filter = new FilterSettings(4, false, false, false, false);
                                filter.readFromNBT(invStack.getOrCreateTag(), "Filter");

                                if (isVoid) {
                                    if (filter.check(stack)) {
                                        stack.setCount(0);
                                        changed = true;
                                    }
                                }
                                else {
                                    var optHandler = SackManager.get().getHandler(invStack);

                                    if (optHandler.isEmpty())
                                        continue;

                                    ItemStackHandlerAA inv = optHandler.get();

                                    if (filter.check(stack)) {
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

                                                if (stack.isEmpty()) {
                                                    break;
                                                }
                                            }
                                    }
                                }

                                if (changed) {
/*                                    if (!isVoid) {
                                        DrillItem.writeSlotsToNBT(inv, invStack);
                                    }*/
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

    //TODO Checking Achievements? yeet?
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

    //TODO this isnt how this should be done im pretty sure...
    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event) {
        if (event.getEntity().level() != null && !event.getEntity().level().isClientSide && event.getSource().getEntity() instanceof Player) {
            //Drop Cobwebs from Spiders
            if (ConfigBoolValues.DO_SPIDER_DROPS.isEnabled() && event.getEntity() instanceof Spider) {
                if (event.getEntity().level().random.nextInt(20) <= event.getLootingLevel() * 2) {
                    event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(Blocks.COBWEB, event.getEntity().level().random.nextInt(2 + event.getLootingLevel()) + 1)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onLogInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer player) {
	        PacketHandlerHelper.syncPlayerData(player, true);
            ActuallyAdditions.LOGGER.info("Sending Player Data to player " + player.getName() + " with UUID " + player.getUUID() + ".");
        }
    }

    //TODO im pretty sure this can be done with normal advancements...
    @SubscribeEvent
    public void onCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
        //checkAchievements(event.crafting, event.player, InitAchievements.Type.CRAFTING);

        if (CommonConfig.Other.GIVE_BOOKLET_ON_FIRST_CRAFT.get()) {
            if (!event.getEntity().level().isClientSide && StackUtil.isValid(event.getCrafting()) && event.getCrafting().getItem() != ActuallyItems.ITEM_BOOKLET.get()) {

                String name = BuiltInRegistries.ITEM.getKey(event.getCrafting().getItem()).toString();
                if (name != null && name.toLowerCase(Locale.ROOT).contains(ActuallyAdditions.MODID)) {
                    PlayerData.PlayerSave save = PlayerData.getDataFromPlayer(event.getEntity());
                    if (save != null && !save.bookGottenAlready) {
                        save.bookGottenAlready = true;
                        WorldData.get(event.getEntity().getCommandSenderWorld()).setDirty();

                        ItemEntity entityItem = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(ActuallyItems.ITEM_BOOKLET.get()));
                        entityItem.setPickUpDelay(0);
                        event.getEntity().level().addFreshEntity(entityItem);
                    }
                }
            }
        }
    }

    //TODO im pretty sure this can be done with normal advancements...
    @SubscribeEvent
    public void onSmeltedEvent(PlayerEvent.ItemSmeltedEvent event) {
        //checkAchievements(event.smelting, event.player, InitAchievements.Type.SMELTING);
    }

    //TODO im pretty sure this can be done with normal advancements...
    @SubscribeEvent
    public void onPickupEvent(EntityItemPickupEvent event) {
        //checkAchievements(event.getItem().getItem(), event.getEntityPlayer(), InitAchievements.Type.PICK_UP);
    }
    @SubscribeEvent
    public void onAnvilEvent(AnvilUpdateEvent event) {
        if (!event.getLeft().isEmpty() && event.getLeft().getItem() instanceof ItemTag && event.getName() != null && !event.getName().isEmpty()) {
            event.setCost(0);
            if (ResourceLocation.isValidResourceLocation(event.getName())) {
                TagKey<Item> tagKey = TagKey.create(Registries.ITEM, new ResourceLocation(event.getName()));
                var tag = BuiltInRegistries.ITEM.getTag(tagKey);
                if (tag.isPresent()) {
                    ItemStack stack = event.getLeft().copy();
                    stack.getData(ActuallyAttachments.ITEM_TAG).setTag(tagKey);
                    event.setOutput(stack);
                } else {
                    event.setCanceled(true);
                }
            }
            else {
                event.setCanceled(true);
            }
        }
    }
}
