package de.ellpeck.actuallyadditions.common.items;

import java.util.List;

import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.base.ItemBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ItemPhantomConnector extends ItemBase {

    public ItemPhantomConnector(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    public static World getStoredWorld(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null) { return DimensionManager.getWorld(tag.getInteger("WorldOfTileStored")); }
        return null;
    }

    public static BlockPos getStoredPosition(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null) {
            int x = tag.getInteger("XCoordOfTileStored");
            int y = tag.getInteger("YCoordOfTileStored");
            int z = tag.getInteger("ZCoordOfTileStored");
            if (!(x == 0 && y == 0 && z == 0)) { return new BlockPos(x, y, z); }
        }
        return null;
    }

    public static void clearStorage(ItemStack stack, String... keys) {
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            for (String key : keys) {
                compound.removeTag(key);
            }
        }
    }

    public static void storeConnection(ItemStack stack, int x, int y, int z, World world) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
        }

        tag.setInteger("XCoordOfTileStored", x);
        tag.setInteger("YCoordOfTileStored", y);
        tag.setInteger("ZCoordOfTileStored", z);
        tag.setInteger("WorldOfTileStored", world.provider.getDimension());

        stack.setTagCompound(tag);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing par7, float par8, float par9, float par10) {
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
