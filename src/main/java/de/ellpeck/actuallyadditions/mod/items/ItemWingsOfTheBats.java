/*
 * This file ("ItemWingsOfTheBats.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemWingsOfTheBats extends ItemBase {

    public static final String THE_BAT_BAT = "the bat bat";
    public static final int MAX_FLY_TIME = 800;

    public ItemWingsOfTheBats() {
        super(ActuallyItems.defaultProps().maxStackSize(1));

        // TODO: Lets move this somewhere global. Don't like event logic in a single place.
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Checks if the Player has Wings in its Inventory
     *
     * @param player The Player
     *
     * @return The Wings
     */
    public static ItemStack getWingItem(PlayerEntity player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            if (StackUtil.isValid(player.inventory.getStackInSlot(i)) && player.inventory.getStackInSlot(i).getItem() instanceof ItemWingsOfTheBats) {
                return player.inventory.getStackInSlot(i);
            }
        }
        return StackUtil.getEmpty();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        PlayerEntity player = ClientProxy.getCurrentPlayer();
        if (player != null) {
            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
            double diff = MAX_FLY_TIME - data.batWingsFlyTime;
            return 1 - diff / MAX_FLY_TIME;
        }
        return super.getDurabilityForDisplay(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        PlayerEntity player = ClientProxy.getCurrentPlayer();
        if (player != null) {
            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
            int curr = data.batWingsFlyTime;
            return MathHelper.hsvToRGB(Math.max(0.0F, 1 - (float) curr / MAX_FLY_TIME) / 3.0F, 1.0F, 1.0F);
        }
        return super.getRGBDurabilityForDisplay(stack);
    }

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event) {
        Entity source = event.getSource().getTrueSource();

        if (event.getEntityLiving().world != null && !event.getEntityLiving().world.isRemote && source instanceof PlayerEntity) {
            //Drop Wings from Bats
            if (ConfigBoolValues.DO_BAT_DROPS.isEnabled() && event.getEntityLiving() instanceof BatEntity) {
                int looting = event.getLootingLevel();

                Iterable<ItemStack> equip = source.getHeldEquipment();
                for (ItemStack stack : equip) {
                    // Todo: [port] this might not work anymore due to the way things are checked
                    if (StackUtil.isValid(stack) && ItemWingsOfTheBats.THE_BAT_BAT.equalsIgnoreCase(stack.getDisplayName().getString()) && stack.getItem() instanceof SwordItem) {
                        looting += 3;
                        break;
                    }
                }

                if (event.getEntityLiving().world.rand.nextInt(15) <= looting * 2) {
                    LivingEntity entityLiving = event.getEntityLiving();
                    event.getDrops().add(new ItemEntity(event.getEntityLiving().world, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), new ItemStack(ActuallyItems.BAT_WING.get(), event.getEntityLiving().world.rand.nextInt(2 + looting) + 1)));
                }
            }
        }
    }

    @SubscribeEvent
    public void livingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            if (!player.isCreative() && !player.isSpectator()) {
                PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);

                if (!player.world.isRemote) {
                    boolean tryDeduct = false;
                    boolean shouldSend = false;

                    boolean wingsEquipped = StackUtil.isValid(ItemWingsOfTheBats.getWingItem(player));
                    if (!data.hasBatWings) {
                        if (data.batWingsFlyTime <= 0) {
                            if (wingsEquipped) {
                                data.hasBatWings = true;
                                shouldSend = true;
                            }
                        } else {
                            tryDeduct = true;
                        }
                    } else {
                        if (wingsEquipped && data.batWingsFlyTime < MAX_FLY_TIME) {
                            player.abilities.allowFlying = true;

                            if (player.abilities.isFlying) {
                                data.batWingsFlyTime++;

                                if (player.world.getWorldInfo().getGameTime() % 10 == 0) {
                                    shouldSend = true;
                                }
                            }

                            tryDeduct = true;
                        } else {
                            data.hasBatWings = false;
                            data.shouldDisableBatWings = true;
                            shouldSend = true;

                            player.abilities.allowFlying = false;
                            player.abilities.isFlying = false;
                            player.abilities.disableDamage = false;
                        }
                    }

                    if (tryDeduct && data.batWingsFlyTime > 0) {
                        int deductTime = 0;

                        if (!player.abilities.isFlying) {
                            deductTime = 2;
                        } else {
                            BlockPos pos = new BlockPos(player.getPosX(), player.getPosY() + player.getHeight(), player.getPosZ());
                            BlockState state = player.world.getBlockState(pos);
                            if (state.isSolidSide(player.world, pos, Direction.DOWN)) {
                                deductTime = 10;
                            }
                        }

                        if (deductTime > 0) {
                            data.batWingsFlyTime = Math.max(0, data.batWingsFlyTime - deductTime);

                            if (player.world.getWorldInfo().getGameTime() % 10 == 0) {
                                shouldSend = true;
                            }
                        }
                    }

                    if (shouldSend) {
                        PacketHandlerHelper.syncPlayerData(player, false);
                        data.shouldDisableBatWings = false; //was set only temporarily to send it
                    }
                } else {
                    if (data.hasBatWings) {
                        player.abilities.allowFlying = true;
                    } else if (data.shouldDisableBatWings) { //so that other modded flying won't be disabled
                        data.shouldDisableBatWings = false;

                        player.abilities.allowFlying = false;
                        player.abilities.isFlying = false;
                        player.abilities.disableDamage = false;
                    }
                }
            }
        }
    }
}
