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
import de.ellpeck.actuallyadditions.mod.blocks.BlockLaserRelay;
import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.DrillItem;
import de.ellpeck.actuallyadditions.mod.items.ItemDrillUpgrade;
import de.ellpeck.actuallyadditions.mod.items.ItemTag;
import de.ellpeck.actuallyadditions.mod.items.Sack;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.sack.SackManager;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
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
            event.setUseItem(TriState.FALSE);
            event.setUseBlock(TriState.TRUE);
//            event.setUseItem(Event.Result.DENY);
//            event.setUseBlock(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public void onBlockBreaking(BlockEvent.BreakEvent event) { //Workaround, cant sneak right click a block with an item normally.
        final Player player = event.getPlayer();
        final LevelAccessor level = event.getLevel();
        final BlockPos pos = event.getPos();
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof DrillItem drillItem) {
            boolean toReturn = false;
            int use = drillItem.getEnergyUsePerBlock(stack);
            if (drillItem.getEnergyStored(stack) >= use) {
                //Enchants the Drill depending on the Upgrades it has
                if (drillItem.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SILK_TOUCH)) {
                    ItemUtil.addEnchantment(stack, level.holderOrThrow(Enchantments.SILK_TOUCH), 1, level.registryAccess());
                }
                else {
                    if (drillItem.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE)) {
                        ItemUtil.addEnchantment(stack, level.holderOrThrow(Enchantments.FORTUNE),
                                drillItem.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE_II) ? 3 : 1,
                                level.registryAccess());
                    }
                }
                //Block hit
                HitResult ray = player.pick(Util.getReachDistance(player), 1f, false);
                if (ray instanceof BlockHitResult trace) {
                    //Breaks the Blocks
                    if (!player.isShiftKeyDown() && drillItem.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)) {
                        if (drillItem.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)) {
                            toReturn = drillItem.breakBlocks(stack, 2, player.level(), pos, trace.getDirection(), player);
                        }
                        else {
                            toReturn = drillItem.breakBlocks(stack, 1, player.level(), pos, trace.getDirection(), player);
                        }
                    }
                    else {
                        toReturn = drillItem.breakBlocks(stack, 0, player.level(), pos, trace.getDirection(), player);
                    }

                    //Removes Enchantments added above
                    ItemUtil.removeEnchantment(stack, level.holderOrThrow(Enchantments.SILK_TOUCH), level.registryAccess());
                    ItemUtil.removeEnchantment(stack, level.holderOrThrow(Enchantments.FORTUNE), level.registryAccess());
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemPickup(ItemEntityPickupEvent.Pre event) {
        Player player = event.getPlayer();
        ItemEntity item = event.getItemEntity();
        if (item != null && item.isAlive()) {
            ItemStack stack = item.getItem();
            if (StackUtil.isValid(stack)) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    if (i != player.getInventory().selected) {

                        ItemStack invStack = player.getInventory().getItem(i);
                        if (StackUtil.isValid(invStack) && (invStack.getItem() instanceof Sack)) {
                            boolean changed = false;
                            boolean isVoid = ((Sack) invStack.getItem()).isVoid;

                            FilterSettings filter = new FilterSettings(4, false, false, false, false);
                            filter.readFromNBT(player.registryAccess(), new CompoundTag(), "Filter"); //TODO: IMPORTANT, FIX FILTER READ!

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
                                event.setCanPickup(TriState.FALSE);
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
                if (event.getEntity().level().random.nextInt(20) <= /*event.getLootingLevel() * 2*/ 1) {//TODO: Looting Level is gone from LivingDropsEvent
                    event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(Blocks.COBWEB, event.getEntity().level().random.nextInt(2 + 1/*event.getLootingLevel()*/) + 1)));
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
    public void onPickupEvent(ItemEntityPickupEvent.Pre event) {
        //checkAchievements(event.getItem().getItem(), event.getEntityPlayer(), InitAchievements.Type.PICK_UP);
    }
    @SubscribeEvent
    public void onAnvilEvent(AnvilUpdateEvent event) {
        if (!event.getLeft().isEmpty() && event.getLeft().getItem() instanceof ItemTag && event.getName() != null && !event.getName().isEmpty()) {
            event.setCost(0);
            ResourceLocation tagLocation = ResourceLocation.tryParse(event.getName());
            if (tagLocation != null) {
                TagKey<Item> tagKey = TagKey.create(Registries.ITEM, tagLocation);
                var tag = BuiltInRegistries.ITEM.getTag(tagKey);
                if (tag.isPresent()) {
                    ItemStack stack = event.getLeft().copy();
                    stack.set(ActuallyComponents.ITEM_TAG, tagLocation);
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
