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
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ItemWingsOfTheBats extends ItemBase {

    public static final String THE_BAT_BAT = "the bat bat";
    public static final int MAX_FLY_TIME = 800;

    public ItemWingsOfTheBats() {
        super(ActuallyItems.defaultProps().stacksTo(1));

        // TODO: Lets move this somewhere global. Don't like event logic in a single place.
        //MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Checks if the Player has Wings in its Inventory
     *
     * @param player The Player
     *
     * @return The Wings
     */
    public static ItemStack getWingItem(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (StackUtil.isValid(player.getInventory().getItem(i)) && player.getInventory().getItem(i).getItem() instanceof ItemWingsOfTheBats) {
                return player.getInventory().getItem(i);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
/*        PlayerEntity player = ClientProxy.getCurrentPlayer();
        if (player != null) {
//            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
            double diff = MAX_FLY_TIME - 1;//data.batWingsFlyTime; // TODO: fix me
            return 1 - diff / MAX_FLY_TIME;
        }*/ //TODO
        return super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
/*        PlayerEntity player = ClientProxy.getCurrentPlayer();
        if (player != null) {
//            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
            int curr = 1;//data.batWingsFlyTime; // TODO: fix me
            return MathHelper.hsvToRgb(Math.max(0.0F, 1 - (float) curr / MAX_FLY_TIME) / 3.0F, 1.0F, 1.0F);
        }*/
        return super.getBarColor(stack);
    }

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event) {
        Entity source = event.getSource().getEntity();

        if (event.getEntity().level() != null && !event.getEntity().level().isClientSide && source instanceof Player player) {
            //Drop Wings from Bats
            if (ConfigBoolValues.DO_BAT_DROPS.isEnabled() && event.getEntity() instanceof Bat) { //TODO: Change to CommonConfig
                int looting = 1; //event.getLootingLevel(); TODO: Looting is gone from LivingDropsEvent

                Iterable<ItemStack> equip = player.getHandSlots();
                for (ItemStack stack : equip) {
                    // Todo: [port] this might not work anymore due to the way things are checked
                    if (StackUtil.isValid(stack) && ItemWingsOfTheBats.THE_BAT_BAT.equalsIgnoreCase(stack.getHoverName().getString()) && stack.getItem() instanceof SwordItem) {
                        looting += 3;
                        break;
                    }
                }

                if (event.getEntity().level().random.nextInt(15) <= looting * 2) {
                    LivingEntity entityLiving = event.getEntity();
                    event.getDrops().add(new ItemEntity(event.getEntity().level(), entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), new ItemStack(ActuallyItems.BATS_WING.get(), event.getEntity().level().random.nextInt(2 + looting) + 1)));
                }
            }
        }
    }

    @SubscribeEvent
    public void livingUpdateEvent(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof Player player) {

	        if (false &&!player.isCreative() && !player.isSpectator()) { //TODO disabled for now.
                PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);

                if (!player.level().isClientSide) {
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
                            player.getAbilities().mayfly = true;

                            if (player.getAbilities().flying) {
                                data.batWingsFlyTime++;

                                if (player.level().getLevelData().getGameTime() % 10 == 0) {
                                    shouldSend = true;
                                }
                            }

                            tryDeduct = true;
                        } else {
                            data.hasBatWings = false;
                            data.shouldDisableBatWings = true;
                            shouldSend = true;

                            player.getAbilities().mayfly = false;
                            player.getAbilities().flying = false;
                            player.getAbilities().invulnerable = false;
                        }
                    }

                    if (tryDeduct && data.batWingsFlyTime > 0) {
                        int deductTime = 0;

                        if (!player.getAbilities().flying) {
                            deductTime = 2;
                        } else {
                            BlockPos pos = BlockPos.containing(player.getX(), player.getY() + player.getBbHeight(), player.getZ());
                            BlockState state = player.level().getBlockState(pos);
                            if (state.isFaceSturdy(player.level(), pos, Direction.DOWN)) {
                                deductTime = 10;
                            }
                        }

                        if (deductTime > 0) {
                            data.batWingsFlyTime = Math.max(0, data.batWingsFlyTime - deductTime);

                            if (player.level().getLevelData().getGameTime() % 10 == 0) {
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
                        player.getAbilities().mayfly = true;
                    } else if (data.shouldDisableBatWings) { //so that other modded flying won't be disabled
                        data.shouldDisableBatWings = false;

                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                        player.getAbilities().invulnerable = false;
                    }
                }
            }
        }
    }
}
