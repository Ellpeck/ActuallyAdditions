package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.List;

public class ItemWingsOfTheBats extends Item implements INameableItem{

    /**
     * A List containing all of the Players that can currently fly
     * Used so that Flight from other Mods' Items doesn't get broken when
     * these Wings aren't worn
     *
     * Saves Remote Players specially to make Damage Display not bug
     * when taking off the Wings
     * (Because Client and Server LivingUpdateEvent get called kind of irregularly
     * and not both at the same time (That's at least what I worked out when testing :D))
     */
    public static ArrayList<String> wingedPlayers = new ArrayList<>();

    public ItemWingsOfTheBats(){
        this.setMaxStackSize(1);
    }

    /**
     * Checks if the Player is winged
     * @param player The Player
     * @return Winged?
     */
    public static boolean isPlayerWinged(EntityPlayer player){
        return wingedPlayers.contains(player.getUniqueID()+(player.worldObj.isRemote ? "-Remote" : ""));
    }

    /**
     * Removes the Player from the List of Players that have Wings
     * @param player The Player
     * @param worldRemote If the World the Player is in is remote
     */
    public static void removeWingsFromPlayer(EntityPlayer player, boolean worldRemote){
        wingedPlayers.remove(player.getUniqueID()+(worldRemote ? "-Remote" : ""));
    }

    /**
     * Same as above, but Remote Checking is done automatically
     */
    public static void removeWingsFromPlayer(EntityPlayer player){
        removeWingsFromPlayer(player, player.worldObj.isRemote);
    }

    /**
     * Adds the Player to the List of Players that have Wings
     * @param player The Player
     */
    public static void addWingsToPlayer(EntityPlayer player){
        wingedPlayers.add(player.getUniqueID()+(player.worldObj.isRemote ? "-Remote" : ""));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        ItemUtil.addInformation(this, list, 1, "");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public String getName(){
        return "itemWingsOfTheBats";
    }

    /**
     * Checks if the Player has Wings in its Inventory
     *
     * @param player The Player
     * @return If the Player has Wings
     */
    public static boolean hasWingItem(EntityPlayer player){
        for(int i = 0; i < player.inventory.getSizeInventory(); i++){
            if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemWingsOfTheBats){
                return true;
            }
        }
        return false;
    }
}
