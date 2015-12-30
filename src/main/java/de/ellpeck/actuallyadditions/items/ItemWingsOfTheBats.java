/*
 * This file ("ItemWingsOfTheBats.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.items.base.ItemBase;
import de.ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.ArrayList;

public class ItemWingsOfTheBats extends ItemBase{

    /**
     * A List containing all of the Players that can currently fly
     * Used so that Flight from other Mods' Items doesn't get broken when
     * these Wings aren't worn
     * <p>
     * Saves Remote Players separately to make de-synced Event Calling
     * not bug out Capabilities when taking off the Wings
     * <p>
     * (Partially excerpted from Botania's Wing System by Vazkii (as I had fiddled around with the system and couldn't make it work) with permission, thanks!)
     */
    public static ArrayList<String> wingedPlayers = new ArrayList<String>();

    public ItemWingsOfTheBats(String name){
        super(name);
        this.setMaxStackSize(1);
    }

    /**
     * Checks if the Player is winged
     *
     * @param player The Player
     * @return Winged?
     */
    public static boolean isPlayerWinged(EntityPlayer player){
        return wingedPlayers.contains(player.getUniqueID()+(player.worldObj.isRemote ? "-Remote" : ""));
    }

    /**
     * Same as above, but Remote Checking is done automatically
     */
    public static void removeWingsFromPlayer(EntityPlayer player){
        removeWingsFromPlayer(player, player.worldObj.isRemote);
    }

    /**
     * Removes the Player from the List of Players that have Wings
     *
     * @param player      The Player
     * @param worldRemote If the World the Player is in is remote
     */
    public static void removeWingsFromPlayer(EntityPlayer player, boolean worldRemote){
        wingedPlayers.remove(player.getUniqueID()+(worldRemote ? "-Remote" : ""));
    }

    /**
     * Adds the Player to the List of Players that have Wings
     *
     * @param player The Player
     */
    public static void addWingsToPlayer(EntityPlayer player){
        wingedPlayers.add(player.getUniqueID()+(player.worldObj.isRemote ? "-Remote" : ""));
    }

    /**
     * Checks if the Player has Wings in its Inventory
     *
     * @param player The Player
     * @return The Wings
     */
    public static ItemStack getWingItem(EntityPlayer player){
        for(int i = 0; i < player.inventory.getSizeInventory(); i++){
            if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemWingsOfTheBats){
                return player.inventory.getStackInSlot(i);
            }
        }
        return null;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }
}
