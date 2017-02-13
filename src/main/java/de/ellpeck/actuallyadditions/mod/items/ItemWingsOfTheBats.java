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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemWingsOfTheBats extends ItemBase{

    public static final String THE_BAT_BAT = "the bat bat";
    public static final int MAX_FLY_TIME = 800;

    public ItemWingsOfTheBats(String name){
        super(name);
        this.setMaxStackSize(1);

        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Checks if the Player has Wings in its Inventory
     *
     * @param player The Player
     * @return The Wings
     */
    public static ItemStack getWingItem(EntityPlayer player){
        for(int i = 0; i < player.inventory.getSizeInventory(); i++){
            if(StackUtil.isValid(player.inventory.getStackInSlot(i)) && player.inventory.getStackInSlot(i).getItem() instanceof ItemWingsOfTheBats){
                return player.inventory.getStackInSlot(i);
            }
        }
        return StackUtil.getNull();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack){
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        EntityPlayer player = ActuallyAdditions.proxy.getCurrentPlayer();
        if(player != null){
            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
            if(data != null){
                double diff = MAX_FLY_TIME-data.batWingsFlyTime;
                return 1-(diff/MAX_FLY_TIME);
            }
        }
        return super.getDurabilityForDisplay(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack){
        EntityPlayer player = ActuallyAdditions.proxy.getCurrentPlayer();
        if(player != null){
            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
            if(data != null){
                int curr = data.batWingsFlyTime;
                return MathHelper.hsvToRGB(Math.max(0.0F, 1-(float)curr/MAX_FLY_TIME)/3.0F, 1.0F, 1.0F);
            }
        }
        return super.getRGBDurabilityForDisplay(stack);
    }

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event){
        Entity source = event.getSource().getEntity();

        if(event.getEntityLiving().world != null && !event.getEntityLiving().world.isRemote && source instanceof EntityPlayer){
            //Drop Wings from Bats
            if(ConfigBoolValues.DO_BAT_DROPS.isEnabled() && event.getEntityLiving() instanceof EntityBat){
                int looting = event.getLootingLevel();

                Iterable<ItemStack> equip = source.getHeldEquipment();
                for(ItemStack stack : equip){
                    if(StackUtil.isValid(stack) && ItemWingsOfTheBats.THE_BAT_BAT.equalsIgnoreCase(stack.getDisplayName()) && stack.getItem() instanceof ItemSword){
                        looting += 3;
                        break;
                    }
                }

                if(event.getEntityLiving().world.rand.nextInt(15) <= looting*2){
                    event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(InitItems.itemMisc, event.getEntityLiving().world.rand.nextInt(2+looting)+1, TheMiscItems.BAT_WING.ordinal())));
                }
            }
        }
    }

    @SubscribeEvent
    public void livingUpdateEvent(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();

            if(!player.capabilities.isCreativeMode){
                PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);

                if(!player.world.isRemote){
                    boolean tryDeduct = false;
                    boolean shouldSend = false;

                    boolean wingsEquipped = StackUtil.isValid(ItemWingsOfTheBats.getWingItem(player));
                    if(!data.hasBatWings){
                        if(data.batWingsFlyTime <= 0){
                            if(wingsEquipped){
                                data.hasBatWings = true;
                                shouldSend = true;
                            }
                        }
                        else{
                            tryDeduct = true;
                        }
                    }
                    else{
                        if(wingsEquipped && data.batWingsFlyTime < MAX_FLY_TIME){
                            player.capabilities.allowFlying = true;

                            if(player.capabilities.isFlying){
                                data.batWingsFlyTime++;

                                if(player.world.getTotalWorldTime()%10 == 0){
                                    shouldSend = true;
                                }
                            }

                            tryDeduct = true;
                        }
                        else{
                            data.hasBatWings = false;
                            data.shouldDisableBatWings = true;
                            shouldSend = true;

                            player.capabilities.allowFlying = false;
                            player.capabilities.isFlying = false;
                            player.capabilities.disableDamage = false;
                        }
                    }

                    if(tryDeduct && data.batWingsFlyTime > 0){
                        int deductTime = 0;

                        if(!player.capabilities.isFlying){
                            deductTime = 2;
                        }
                        else{
                            BlockPos pos = new BlockPos(player.posX, player.posY+player.height, player.posZ);
                            IBlockState state = player.world.getBlockState(pos);
                            if(state != null && state.isSideSolid(player.world, pos, EnumFacing.DOWN)){
                                deductTime = 10;
                            }
                        }

                        if(deductTime > 0){
                            data.batWingsFlyTime = Math.max(0, data.batWingsFlyTime-deductTime);

                            if(player.world.getTotalWorldTime()%10 == 0){
                                shouldSend = true;
                            }
                        }
                    }

                    if(shouldSend){
                        PacketHandlerHelper.syncPlayerData(player, false);
                        data.shouldDisableBatWings = false; //was set only temporarily to send it
                    }
                }
                else{
                    if(data.hasBatWings){
                        player.capabilities.allowFlying = true;
                    }
                    else if(data.shouldDisableBatWings){ //so that other modded flying won't be disabled
                        data.shouldDisableBatWings = false;

                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                        player.capabilities.disableDamage = false;
                    }
                }
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
