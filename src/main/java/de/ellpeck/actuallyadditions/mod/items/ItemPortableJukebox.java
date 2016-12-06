/*
 * This file ("ItemPortableJukebox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketServerToClient;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPortableJukebox extends ItemBase{

    public ItemPortableJukebox(String name){
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){
        return ItemUtil.isEnabled(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        ItemStack stack = player.getHeldItem(hand);

        if(!world.isRemote){
            if(player.isSneaking()){
                String disc = getDisc(stack);
                if(disc != null && !disc.isEmpty()){
                    ItemUtil.changeEnabled(player, hand);

                    if(ItemUtil.isEnabled(stack)){
                        int slot = -1;

                        for(int i = 0; i < player.inventory.getSizeInventory(); i++){
                            if(player.inventory.getStackInSlot(i) == stack){
                                slot = i;
                            }
                        }

                        if(slot >= 0){
                            playSoundToAllAround(player, disc, slot);
                        }
                    }
                }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    private static void playSoundToAllAround(EntityPlayer player, String disc, int slot){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("Disc", disc);
        compound.setUniqueId("PlayerId", player.getUniqueID());
        compound.setInteger("Slot", slot);

        PacketHandler.theNetwork.sendToAllAround(new PacketServerToClient(compound, PacketHandler.SEND_PORTABLE_JUKEBOX_SOUND_HANDLER), new NetworkRegistry.TargetPoint(player.world.provider.getDimension(), player.posX, player.posY, player.posZ, 32));
    }

    public static String getDisc(ItemStack stack){
        if(stack.hasTagCompound()){
            return stack.getTagCompound().getString("Disc");
        }
        return null;
    }

    public static void setDisc(ItemStack stack, String disc){
        boolean hasTag = stack.hasTagCompound();

        if(disc != null){
            if(!hasTag){
                stack.setTagCompound(new NBTTagCompound());
            }
            stack.getTagCompound().setString("Disc", disc);
        }
        else{
            if(hasTag){
                stack.getTagCompound().removeTag("Disc");
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
        String disc = getDisc(stack);

        if(disc != null && !disc.isEmpty()){
            Item item = Item.REGISTRY.getObject(new ResourceLocation(disc));
            if(item instanceof ItemRecord){
                tooltip.add("Disc: "+((ItemRecord)item).getRecordNameLocal());
                return;
            }
        }

        tooltip.add("No Disc");
    }
}
