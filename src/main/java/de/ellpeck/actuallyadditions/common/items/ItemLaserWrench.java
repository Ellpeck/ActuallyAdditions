package de.ellpeck.actuallyadditions.common.items;

import java.util.List;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.base.ItemBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.client.util.ITooltipFlag;
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

public class ItemLaserWrench extends ItemBase {

    public ItemLaserWrench(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing par7, float par8, float par9, float par10) {
        ItemStack stack = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityLaserRelay) {
            TileEntityLaserRelay relay = (TileEntityLaserRelay) tile;
            if (!world.isRemote) {
                if (ItemPhantomConnector.getStoredPosition(stack) == null) {
                    ItemPhantomConnector.storeConnection(stack, pos.getX(), pos.getY(), pos.getZ(), world);
                    player.sendStatusMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".laser.stored.desc"), true);
                } else {
                    BlockPos savedPos = ItemPhantomConnector.getStoredPosition(stack);
                    if (savedPos != null) {
                        TileEntity savedTile = world.getTileEntity(savedPos);
                        if (savedTile instanceof TileEntityLaserRelay) {
                            int distanceSq = (int) savedPos.distanceSq(pos);
                            TileEntityLaserRelay savedRelay = (TileEntityLaserRelay) savedTile;

                            int lowestRange = Math.min(relay.getMaxRange(), savedRelay.getMaxRange());
                            int range = lowestRange * lowestRange;
                            if (ItemPhantomConnector.getStoredWorld(stack) == world && savedRelay.type == relay.type && distanceSq <= range && ActuallyAdditionsAPI.connectionHandler.addConnection(savedPos, pos, relay.type, world, false, true)) {
                                ItemPhantomConnector.clearStorage(stack, "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored");

                                ((TileEntityLaserRelay) savedTile).sendUpdate();
                                relay.sendUpdate();

                                player.sendStatusMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".laser.connected.desc"), true);

                                return EnumActionResult.SUCCESS;
                            }
                        }

                        player.sendMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".laser.cantConnect.desc"));
                        ItemPhantomConnector.clearStorage(stack, "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored");
                    }
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, World playerIn, List<String> list, ITooltipFlag advanced) {
        BlockPos coords = ItemPhantomConnector.getStoredPosition(stack);
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
