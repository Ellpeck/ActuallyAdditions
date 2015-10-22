/*
 * This file ("CraftEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import ellpeck.actuallyadditions.achievement.InitAchievements;
import ellpeck.actuallyadditions.achievement.TheAchievements;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.misc.WorldData;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.playerdata.PersistentServerData;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CraftEvent{

    @SubscribeEvent
    public void onCraftedEvent(PlayerEvent.ItemCraftedEvent event){
        checkAchievements(event.crafting, event.player, InitAchievements.CRAFTING_ACH);

        if(ConfigBoolValues.GIVE_BOOKLET_ON_FIRST_CRAFT.isEnabled()){
            if(!event.player.worldObj.isRemote && event.crafting.getItem() != InitItems.itemLexicon && (event.crafting.getItem() instanceof IActAddItemOrBlock || Block.getBlockFromItem(event.crafting.getItem()) instanceof IActAddItemOrBlock)){
                NBTTagCompound compound = PersistentServerData.getDataFromPlayer(event.player);
                if(compound != null && !compound.getBoolean("BookGottenAlready")){
                    compound.setBoolean("BookGottenAlready", true);
                    WorldData.makeDirty();

                    EntityItem entityItem = new EntityItem(event.player.worldObj, event.player.posX, event.player.posY, event.player.posZ, new ItemStack(InitItems.itemLexicon));
                    entityItem.delayBeforeCanPickup = 0;
                    event.player.worldObj.spawnEntityInWorld(entityItem);
                }
            }
        }
    }

    public static void checkAchievements(ItemStack gotten, EntityPlayer player, int type){
        for(int i = 0; i < TheAchievements.values().length; i++){
            TheAchievements ach = TheAchievements.values()[i];
            if(ach.type == type){
                if(gotten != null && ach.ach.theItemStack != null && gotten.getItem() == ach.ach.theItemStack.getItem()){
                    if(gotten.getItemDamage() == ach.ach.theItemStack.getItemDamage()){
                        player.addStat(ach.ach, 1);
                    }
                }
            }
        }
    }
}
