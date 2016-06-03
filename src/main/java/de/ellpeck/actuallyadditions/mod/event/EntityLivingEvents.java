/*
 * This file ("EntityLivingEvent.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemWingsOfTheBats;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.misc.WorldData;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.playerdata.PlayerServerData;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class EntityLivingEvents{

    @SubscribeEvent
    public void livingUpdateEvent(LivingUpdateEvent event){
        //Ocelots dropping Hair Balls
        if(event.getEntityLiving() != null){
            if(event.getEntityLiving().worldObj != null && !event.getEntityLiving().worldObj.isRemote){
                if((event.getEntityLiving() instanceof EntityOcelot && ((EntityOcelot)event.getEntityLiving()).isTamed()) || (event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().getUniqueID().equals(/*KittyVanCat*/ UUID.fromString("681d4e20-10ef-40c9-a0a5-ba2f1995ef44")))){
                    if(ConfigBoolValues.DO_CAT_DROPS.isEnabled()){
                        if(Util.RANDOM.nextInt(5000)+1 == 1){
                            EntityItem item = new EntityItem(event.getEntityLiving().worldObj, event.getEntityLiving().posX+0.5, event.getEntityLiving().posY+0.5, event.getEntityLiving().posZ+0.5, new ItemStack(InitItems.itemHairyBall));
                            event.getEntityLiving().worldObj.spawnEntityInWorld(item);
                        }
                    }
                }
            }

            //Wings allowing Flight
            this.doWingStuff(event.getEntityLiving());
        }
    }

    @SubscribeEvent
    public void livingDeathEvent(LivingDeathEvent event){
        if(event.getEntityLiving().worldObj != null && !event.getEntityLiving().worldObj.isRemote && event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            NBTTagCompound data = PlayerServerData.getDataFromPlayer(player);

            NBTTagList deaths = data.getTagList("Deaths", 10);
            while(deaths.tagCount() >= 5){
                deaths.removeTag(0);
            }

            NBTTagCompound death = new NBTTagCompound();
            death.setDouble("X", player.posX);
            death.setDouble("Y", player.posY);
            death.setDouble("Z", player.posZ);
            deaths.appendTag(death);

            data.setTag("Deaths", deaths);

            //player.addChatComponentMessage(new TextComponentTranslation("info."+ModUtil.MOD_ID+".deathRecorded"));
            WorldData.markDirty(event.getEntityLiving().getEntityWorld());
        }
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        if(event.getWorld() != null){
            if(ConfigBoolValues.WATER_BOWL.isEnabled()){
                if(event.getItemStack() != null && event.getItemStack().getItem() == Items.BOWL){
                    RayTraceResult trace = WorldUtil.getNearestBlockWithDefaultReachDistance(event.getWorld(), event.getEntityPlayer(), true, false, false);
                    ActionResult<ItemStack> result = ForgeEventFactory.onBucketUse(event.getEntityPlayer(), event.getWorld(), event.getItemStack(), trace);
                    if(result == null && trace != null && trace.getBlockPos() != null){
                        if(event.getEntityPlayer().canPlayerEdit(trace.getBlockPos().offset(trace.sideHit), trace.sideHit, event.getItemStack())){
                            IBlockState state = event.getWorld().getBlockState(trace.getBlockPos());
                            Material material = state.getMaterial();

                            if(material == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0){
                                event.getEntityPlayer().playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);

                                if(!event.getWorld().isRemote){
                                    event.getWorld().setBlockState(trace.getBlockPos(), Blocks.AIR.getDefaultState(), 11);
                                    event.getItemStack().stackSize--;

                                    ItemStack bowl = new ItemStack(InitItems.itemWaterBowl);
                                    if(!event.getEntityPlayer().inventory.addItemStackToInventory(bowl.copy())){
                                        EntityItem entityItem = new EntityItem(event.getWorld(), event.getEntityPlayer().posX, event.getEntityPlayer().posY, event.getEntityPlayer().posZ, bowl.copy());
                                        entityItem.setPickupDelay(0);
                                        event.getWorld().spawnEntityInWorld(entityItem);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event){
        if(event.getEntityLiving().worldObj != null && !event.getEntityLiving().worldObj.isRemote && event.getSource().getEntity() instanceof EntityPlayer){
            //Drop Solidified XP
            if(event.getEntityLiving() instanceof EntityCreature){
                if(Util.RANDOM.nextInt(10) <= event.getLootingLevel()*2){
                    event.getEntityLiving().entityDropItem(new ItemStack(InitItems.itemSolidifiedExperience, Util.RANDOM.nextInt(2+event.getLootingLevel())+1), 0);
                }
            }

            //Drop Cobwebs from Spiders
            if(ConfigBoolValues.DO_SPIDER_DROPS.isEnabled() && event.getEntityLiving() instanceof EntitySpider){
                if(Util.RANDOM.nextInt(20) <= event.getLootingLevel()*2){
                    event.getEntityLiving().entityDropItem(new ItemStack(Blocks.WEB, Util.RANDOM.nextInt(2+event.getLootingLevel())+1), 0);
                }
            }

            //Drop Wings from Bats
            if(ConfigBoolValues.DO_BAT_DROPS.isEnabled() && event.getEntityLiving() instanceof EntityBat){
                if(Util.RANDOM.nextInt(15) <= event.getLootingLevel()*2){
                    event.getEntityLiving().entityDropItem(new ItemStack(InitItems.itemMisc, Util.RANDOM.nextInt(2+event.getLootingLevel())+1, TheMiscItems.BAT_WING.ordinal()), 0);
                }
            }
        }
    }

    /**
     * Makes players be able to fly if they have Wings Of The Bats equipped
     * (Partially excerpted from Botania's Wing System by Vazkii (as I had fiddled around with the system and couldn't make it work) with permission, thanks!)
     */
    private void doWingStuff(EntityLivingBase living){
        if(living instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)living;
            boolean wingsEquipped = ItemWingsOfTheBats.getWingItem(player) != null;

            //If Player isn't (really) winged
            if(!ItemWingsOfTheBats.isPlayerWinged(player)){
                if(wingsEquipped){
                    //Make the Player actually winged
                    ItemWingsOfTheBats.addWingsToPlayer(player);
                }
            }
            //If Player is (or should be) winged
            else{
                if(wingsEquipped){
                    //Allow the Player to fly when he has Wings equipped
                    player.capabilities.allowFlying = true;
                }
                else{
                    //Make the Player not winged
                    ItemWingsOfTheBats.removeWingsFromPlayer(player);
                    //Reset Player's Values
                    if(!player.capabilities.isCreativeMode){
                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                        //Enables Fall Damage again (Automatically gets disabled for some reason)
                        player.capabilities.disableDamage = false;
                    }
                }
            }
        }
    }
}
