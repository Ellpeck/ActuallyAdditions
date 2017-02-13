/*
 * This file ("ItemLaserWrench.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemLaserWrench extends ItemBase{

    public ItemLaserWrench(String name){
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing par7, float par8, float par9, float par10){
        ItemStack stack = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityLaserRelay){
            TileEntityLaserRelay relay = (TileEntityLaserRelay)tile;
            if(!world.isRemote){
                if(ItemPhantomConnector.getStoredPosition(stack) == null){
                    ItemPhantomConnector.storeConnection(stack, pos.getX(), pos.getY(), pos.getZ(), world);
                    player.sendMessage(new TextComponentTranslation("tooltip."+ModUtil.MOD_ID+".laser.stored.desc"));
                }
                else{
                    BlockPos savedPos = ItemPhantomConnector.getStoredPosition(stack);
                    if(savedPos != null){
                        TileEntity savedTile = world.getTileEntity(savedPos);
                        if(savedTile instanceof TileEntityLaserRelay){
                            int distanceSq = (int)savedPos.distanceSq(pos);
                            TileEntityLaserRelay savedRelay = (TileEntityLaserRelay)savedTile;

                            int lowestRange = Math.min(relay.getMaxRange(), savedRelay.getMaxRange());
                            int range = lowestRange*lowestRange;
                            if(ItemPhantomConnector.getStoredWorld(stack) == world && savedRelay.type == relay.type && distanceSq <= range && ActuallyAdditionsAPI.connectionHandler.addConnection(savedPos, pos, relay.type, world)){
                                ItemPhantomConnector.clearStorage(stack, "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored");

                                ((TileEntityLaserRelay)savedTile).sendUpdate();
                                relay.sendUpdate();

                                player.sendMessage(new TextComponentTranslation("tooltip."+ModUtil.MOD_ID+".laser.connected.desc"));

                                return EnumActionResult.SUCCESS;
                            }
                        }

                        player.sendMessage(new TextComponentTranslation("tooltip."+ModUtil.MOD_ID+".laser.cantConnect.desc"));
                        ItemPhantomConnector.clearStorage(stack, "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored");
                    }
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean getShareTag(){
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        BlockPos coords = ItemPhantomConnector.getStoredPosition(stack);
        if(coords != null){
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID+".boundTo.desc")+":");
            list.add("X: "+coords.getX());
            list.add("Y: "+coords.getY());
            list.add("Z: "+coords.getZ());
            list.add(TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".clearStorage.desc"));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
