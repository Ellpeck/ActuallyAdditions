/*
 * This file ("ItemPhantomConnector.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPhantomConnector extends ItemBase{

    public ItemPhantomConnector(String name){
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing par7, float par8, float par9, float par10){
        if(!world.isRemote){
            //Passing Data to Phantoms
            TileEntity tile = world.getTileEntity(pos);
            if(tile != null){
                //Passing to Phantom
                if(tile instanceof IPhantomTile){
                    if(this.checkHasConnection(stack, player, tile) && getStoredWorld(stack) == world){
                        ((IPhantomTile)tile).setBoundPosition(getStoredPosition(stack));
                        if(tile instanceof TileEntityBase){
                            ((TileEntityBase)tile).sendUpdate();
                        }
                        clearStorage(stack);
                        player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connected.desc")));
                        return true;
                    }
                    return false;
                }
            }
            //Storing Connections
            storeConnection(stack, pos.getX(), pos.getY(), pos.getZ(), world);
            player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.stored.desc")));
        }
        return true;
    }

    public boolean checkHasConnection(ItemStack stack, EntityPlayer player, TileEntity tile){
        if(getStoredPosition(stack) != null){
            return true;
        }
        else{
            if(tile instanceof IPhantomTile){
                ((IPhantomTile)tile).setBoundPosition(null);
            }
            player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.unbound.desc")));
            return false;
        }
    }

    public static Position getStoredPosition(ItemStack stack){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null){
            int x = tag.getInteger("XCoordOfTileStored");
            int y = tag.getInteger("YCoordOfTileStored");
            int z = tag.getInteger("ZCoordOfTileStored");
            if(!(x == 0 && y == 0 && z == 0)){
                return new Position(x, y, z);
            }
        }
        return null;
    }

    public static World getStoredWorld(ItemStack stack){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null){
            return DimensionManager.getWorld(tag.getInteger("WorldOfTileStored"));
        }
        return null;
    }

    public static void clearStorage(ItemStack stack){
        stack.setTagCompound(new NBTTagCompound());
    }

    public static void storeConnection(ItemStack stack, int x, int y, int z, World world){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null){
            tag = new NBTTagCompound();
        }

        tag.setInteger("XCoordOfTileStored", x);
        tag.setInteger("YCoordOfTileStored", y);
        tag.setInteger("ZCoordOfTileStored", z);
        tag.setInteger("WorldOfTileStored", world.provider.getDimensionId());

        stack.setTagCompound(tag);
    }

    @Override
    public boolean getShareTag(){
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(getStoredPosition(stack) == null){
            clearStorage(stack);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        Position coords = getStoredPosition(stack);
        if(coords != null){
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".boundTo.desc")+":");
            list.add("X: "+coords.getX());
            list.add("Y: "+coords.getY());
            list.add("Z: "+coords.getZ());
            list.add(EnumChatFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".clearStorage.desc"));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
