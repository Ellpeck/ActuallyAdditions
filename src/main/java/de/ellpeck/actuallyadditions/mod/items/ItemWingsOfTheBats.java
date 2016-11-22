/*
 * This file ("ItemWingsOfTheBats.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWingsOfTheBats extends ItemBase{

    private static final int MAX_FLY_TIME = 800;

    public ItemWingsOfTheBats(String name){
        super(name);
        this.setMaxStackSize(1);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack){
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getDurabilityForDisplay(ItemStack stack){
        PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(Minecraft.getMinecraft().thePlayer);
        if(data != null){
            double diff = MAX_FLY_TIME-data.batWingsFlyTime;
            return 1-(diff/MAX_FLY_TIME);
        }
        else{
            return super.getDurabilityForDisplay(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRGBDurabilityForDisplay(ItemStack stack){
        PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(Minecraft.getMinecraft().thePlayer);
        if(data != null){
            int curr = data.batWingsFlyTime;
            return MathHelper.hsvToRGB(Math.max(0.0F, 1-(float)curr/MAX_FLY_TIME)/3.0F, 1.0F, 1.0F);
        }
        else{
            return super.getRGBDurabilityForDisplay(stack);
        }
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

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event){
        if(event.getEntityLiving().worldObj != null && !event.getEntityLiving().worldObj.isRemote && event.getSource().getEntity() instanceof EntityPlayer){
            //Drop Wings from Bats
            if(ConfigBoolValues.DO_BAT_DROPS.isEnabled() && event.getEntityLiving() instanceof EntityBat){
                if(event.getEntityLiving().worldObj.rand.nextInt(15) <= event.getLootingLevel()*2){
                    event.getEntityLiving().entityDropItem(new ItemStack(InitItems.itemMisc, event.getEntityLiving().worldObj.rand.nextInt(2+event.getLootingLevel())+1, TheMiscItems.BAT_WING.ordinal()), 0);
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

                if(!player.worldObj.isRemote){
                    boolean shouldDeduct = false;
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
                            shouldDeduct = true;
                        }
                    }
                    else{
                        if(wingsEquipped && data.batWingsFlyTime < MAX_FLY_TIME){
                            player.capabilities.allowFlying = true;

                            if(player.capabilities.isFlying){
                                data.batWingsFlyTime++;

                                if(player.worldObj.getTotalWorldTime()%10 == 0){
                                    shouldSend = true;
                                }
                            }
                            else{
                                shouldDeduct = true;
                            }
                        }
                        else{
                            data.hasBatWings = false;
                            shouldSend = true;

                            player.capabilities.allowFlying = false;
                            player.capabilities.isFlying = false;
                            player.capabilities.disableDamage = false;
                        }
                    }

                    if(shouldDeduct){
                        if(data.batWingsFlyTime >= 0){
                            data.batWingsFlyTime = Math.max(0, data.batWingsFlyTime-5);
                        }

                        if(player.worldObj.getTotalWorldTime()%10 == 0){
                            shouldSend = true;
                        }
                    }

                    if(shouldSend){
                        PacketHandlerHelper.sendPlayerDataPacket(player, false, true);
                    }
                }
                else{
                    if(data.hasBatWings){
                        player.capabilities.allowFlying = true;
                    }
                    else{
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
