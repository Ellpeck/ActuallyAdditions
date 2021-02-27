/*
 * This file ("ItemPhantomConnector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPhantomConnector extends ItemBase {

    public ItemPhantomConnector(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    public static World getStoredWorld(ItemStack stack) {
        CompoundNBT tag = stack.getTagCompound();
        if (tag != null) {
            return DimensionManager.getWorld(tag.getInteger("WorldOfTileStored"));
        }
        return null;
    }

    public static BlockPos getStoredPosition(ItemStack stack) {
        CompoundNBT tag = stack.getTagCompound();
        if (tag != null) {
            int x = tag.getInteger("XCoordOfTileStored");
            int y = tag.getInteger("YCoordOfTileStored");
            int z = tag.getInteger("ZCoordOfTileStored");
            if (!(x == 0 && y == 0 && z == 0)) {
                return new BlockPos(x, y, z);
            }
        }
        return null;
    }

    public static void clearStorage(ItemStack stack, String... keys) {
        if (stack.hasTagCompound()) {
            CompoundNBT compound = stack.getTagCompound();
            for (String key : keys) {
                compound.removeTag(key);
            }
        }
    }

    public static void storeConnection(ItemStack stack, int x, int y, int z, World world) {
        CompoundNBT tag = stack.getTagCompound();
        if (tag == null) {
            tag = new CompoundNBT();
        }

        tag.setInteger("XCoordOfTileStored", x);
        tag.setInteger("YCoordOfTileStored", y);
        tag.setInteger("ZCoordOfTileStored", z);
        tag.setInteger("WorldOfTileStored", world.provider.getDimension());

        stack.setTagCompound(tag);
    }

    @Override
    public EnumActionResult onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction par7, float par8, float par9, float par10) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            //Passing Data to Phantoms
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null) {
                //Passing to Phantom
                if (tile instanceof IPhantomTile) {
                    BlockPos stored = getStoredPosition(stack);
                    if (stored != null && getStoredWorld(stack) == world) {
                        ((IPhantomTile) tile).setBoundPosition(stored);
                        if (tile instanceof TileEntityBase) {
                            ((TileEntityBase) tile).sendUpdate();
                        }
                        clearStorage(stack, "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored");
                        player.sendStatusMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".phantom.connected.desc"), true);
                        return EnumActionResult.SUCCESS;
                    }
                    return EnumActionResult.FAIL;
                }
            }
            //Storing Connections
            storeConnection(stack, pos.getX(), pos.getY(), pos.getZ(), world);
            player.sendStatusMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".phantom.stored.desc"), true);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> list, ITooltipFlag advanced) {
        BlockPos coords = getStoredPosition(stack);
        if (coords != null) {
            list.add(StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".boundTo.desc") + ":");
            list.add("X: " + coords.getX());
            list.add("Y: " + coords.getY());
            list.add("Z: " + coords.getZ());
            list.add(TextFormatting.ITALIC + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".clearStorage.desc"));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
