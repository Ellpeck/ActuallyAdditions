package de.ellpeck.actuallyadditions.mod.event;

import java.util.Locale;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBag;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemBag;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CommonEvents {

    @SubscribeEvent
    public void onBlockBreakEvent(BlockEvent.HarvestDropsEvent event) {
        IBlockState state = event.getState();
        if (state != null && state.getBlock() == Blocks.MOB_SPAWNER) {
            event.getDrops().add(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.SPAWNER_SHARD.ordinal()));
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        if (event.isCanceled() || event.getResult() == Event.Result.ALLOW) { return; }

        EntityPlayer player = event.getEntityPlayer();
        EntityItem item = event.getItem();
        if (item != null && !item.isDead) {
            ItemStack stack = item.getItem();
            if (StackUtil.isValid(stack)) {
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                    if (i != player.inventory.currentItem) {

                        ItemStack invStack = player.inventory.getStackInSlot(i);
                        if (StackUtil.isValid(invStack) && invStack.getItem() instanceof ItemBag && invStack.hasTagCompound()) {
                            if (invStack.getTagCompound().getBoolean("AutoInsert")) {
                                boolean changed = false;

                                boolean isVoid = ((ItemBag) invStack.getItem()).isVoid;
                                ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerBag.getSlotAmount(isVoid));
                                ItemDrill.loadSlotsFromNBT(inv, invStack);

                                FilterSettings filter = new FilterSettings(4, false, false, false, false, 0, 0);
                                filter.readFromNBT(invStack.getTagCompound(), "Filter");
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
    /*public static void checkAchievements(ItemStack gotten, EntityPlayer player, InitAchievements.Type type){
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
        if (event.getEntityLiving().world != null && !event.getEntityLiving().world.isRemote && event.getSource().getTrueSource() instanceof EntityPlayer) {
            //Drop Cobwebs from Spiders
            if (ConfigBoolValues.DO_SPIDER_DROPS.isEnabled() && event.getEntityLiving() instanceof EntitySpider) {
                if (event.getEntityLiving().world.rand.nextInt(20) <= event.getLootingLevel() * 2) {
                    event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(Blocks.WEB, event.getEntityLiving().world.rand.nextInt(2 + event.getLootingLevel()) + 1)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onLogInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.world.isRemote && event.player instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            PacketHandlerHelper.syncPlayerData(player, true);
            ActuallyAdditions.LOGGER.info("Sending Player Data to player " + player.getName() + " with UUID " + player.getUniqueID() + ".");
        }
    }

    @SubscribeEvent
    public void onCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
        //checkAchievements(event.crafting, event.player, InitAchievements.Type.CRAFTING);

        if (ConfigBoolValues.GIVE_BOOKLET_ON_FIRST_CRAFT.isEnabled()) {
            if (!event.player.world.isRemote && StackUtil.isValid(event.crafting) && event.crafting.getItem() != InitItems.itemBooklet) {

                String name = event.crafting.getItem().getRegistryName().toString();
                if (name != null && name.toLowerCase(Locale.ROOT).contains(ActuallyAdditions.MODID)) {
                    PlayerData.PlayerSave save = PlayerData.getDataFromPlayer(event.player);
                    if (save != null && !save.bookGottenAlready) {
                        save.bookGottenAlready = true;
                        WorldData.get(event.player.getEntityWorld()).markDirty();

                        EntityItem entityItem = new EntityItem(event.player.world, event.player.posX, event.player.posY, event.player.posZ, new ItemStack(InitItems.itemBooklet));
                        entityItem.setPickupDelay(0);
                        event.player.world.spawnEntity(entityItem);
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
