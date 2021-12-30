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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPhantomConnector extends ItemBase {

    public ItemPhantomConnector() {
        super(ActuallyItems.defaultNonStacking());
    }

    public static RegistryKey<World> getStoredWorld(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("WorldOfTileStored")) {
            return null;
        }

        return RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(tag.getString("WorldOfTileStored")));
    }

    public static BlockPos getStoredPosition(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        int x = tag.getInt("XCoordOfTileStored");
        int y = tag.getInt("YCoordOfTileStored");
        int z = tag.getInt("ZCoordOfTileStored");
        if (!(x == 0 && y == 0 && z == 0)) {
            return new BlockPos(x, y, z);
        }

        return null;
    }

    public static void clearStorage(ItemStack stack, String... keys) {
        CompoundNBT compound = stack.getOrCreateTag();
        for (String key : keys) {
            compound.remove(key);
        }
    }

    public static void storeConnection(ItemStack stack, int x, int y, int z, World world) {
        CompoundNBT tag = stack.getOrCreateTag();

        tag.putInt("XCoordOfTileStored", x);
        tag.putInt("YCoordOfTileStored", y);
        tag.putInt("ZCoordOfTileStored", z);
        tag.putString("WorldOfTileStored", world.dimension().location().toString());
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        if (!context.getLevel().isClientSide) {
            //Passing Data to Phantoms
            BlockPos pos = context.getClickedPos();
            TileEntity tile = context.getLevel().getBlockEntity(pos);
            if (tile != null) {
                //Passing to Phantom
                if (tile instanceof IPhantomTile) {
                    BlockPos stored = getStoredPosition(stack);
                    if (stored != null && getStoredWorld(stack) == context.getLevel().dimension()) {
                        ((IPhantomTile) tile).setBoundPosition(stored);
                        if (tile instanceof TileEntityBase) {
                            ((TileEntityBase) tile).sendUpdate();
                        }
                        clearStorage(stack, "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored");
                        context.getPlayer().displayClientMessage(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".phantom.connected.desc"), true);
                        return ActionResultType.SUCCESS;
                    }
                    return ActionResultType.FAIL;
                }
            }
            //Storing Connections
            storeConnection(stack, pos.getX(), pos.getY(), pos.getZ(), context.getLevel());
            context.getPlayer().displayClientMessage(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".phantom.stored.desc"), true);
        }
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        return new CompoundNBT();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World playerIn, List<ITextComponent> list, ITooltipFlag advanced) {
        BlockPos coords = getStoredPosition(stack);
        if (coords != null) {
            list.add(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".boundTo.desc").append(":"));
            list.add(new StringTextComponent("X: " + coords.getX()));
            list.add(new StringTextComponent("Y: " + coords.getY()));
            list.add(new StringTextComponent("Z: " + coords.getZ()));
            list.add(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".clearStorage.desc").withStyle(TextFormatting.ITALIC));
        }
    }
}
