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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ItemLaserWrench extends ItemBase {

    public ItemLaserWrench() {
        super(ActuallyItems.defaultNonStacking());
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        World world = context.getLevel();
        PlayerEntity player = context.getPlayer();

        ItemStack stack = player.getItemInHand(context.getHand());
        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityLaserRelay) {
            TileEntityLaserRelay relay = (TileEntityLaserRelay) tile;
            if (!world.isClientSide) {
                if (ItemPhantomConnector.getStoredPosition(stack) == null) {
                    ItemPhantomConnector.storeConnection(stack, pos.getX(), pos.getY(), pos.getZ(), world);
                    player.displayClientMessage(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".laser.stored.desc"), true);
                } else {
                    BlockPos savedPos = ItemPhantomConnector.getStoredPosition(stack);
                    if (savedPos != null) {
                        TileEntity savedTile = world.getBlockEntity(savedPos);
                        if (savedTile instanceof TileEntityLaserRelay) {
                            int distanceSq = (int) savedPos.distSqr(pos);
                            TileEntityLaserRelay savedRelay = (TileEntityLaserRelay) savedTile;

                            int lowestRange = Math.min(relay.getMaxRange(), savedRelay.getMaxRange());
                            int range = lowestRange * lowestRange;
                            if (ItemPhantomConnector.getStoredWorld(stack) == world.dimension() && savedRelay.type == relay.type && distanceSq <= range && ActuallyAdditionsAPI.connectionHandler.addConnection(savedPos, pos, relay.type, world, false, true)) {
                                ItemPhantomConnector.clearStorage(stack, "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored");

                                ((TileEntityLaserRelay) savedTile).sendUpdate();
                                relay.sendUpdate();

                                player.displayClientMessage(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".laser.connected.desc"), true);

                                return ActionResultType.SUCCESS;
                            }
                        }

                        player.displayClientMessage(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".laser.cantConnect.desc"), false);
                        ItemPhantomConnector.clearStorage(stack, "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored");
                    }
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    //    // TODO: [port] ensure this is correct
    //    @Nullable
    //    @Override
    //    public CompoundNBT getShareTag(ItemStack stack) {
    //        return new CompoundNBT();
    //    }

    @Override
    public void appendHoverText(ItemStack stack, World playerIn, List<ITextComponent> list, ITooltipFlag advanced) {
        BlockPos coords = ItemPhantomConnector.getStoredPosition(stack);
        if (coords != null) {
            list.add(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".boundTo.desc").append(":"));
            list.add(new StringTextComponent("X: " + coords.getX()));
            list.add(new StringTextComponent("Y: " + coords.getY()));
            list.add(new StringTextComponent("Z: " + coords.getZ()));
            list.add(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".clearStorage.desc").withStyle(TextFormatting.ITALIC));
        }
    }
}
