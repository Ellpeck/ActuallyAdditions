/*
 * This file ("ClientProxy.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy {

    private static final List<Item> COLOR_PRODIVIDING_ITEMS_FOR_REGISTERING = new ArrayList<>();
    private static final List<Block> COLOR_PRODIVIDING_BLOCKS_FOR_REGISTERING = new ArrayList<>();


    // TODO: [port] add remaining color
    //    @Override
    //    public void init(FMLInitializationEvent event) {
    //        ActuallyAdditions.LOGGER.info("Initializing ClientProxy...");
    //
    //
    //        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompost.class, new RenderCompost());
    //
    //
    //        for (Item item : COLOR_PRODIVIDING_ITEMS_FOR_REGISTERING) {
    //            if (item instanceof IColorProvidingItem) {
    //                Minecraft.getInstance().getItemColors().registerItemColorHandler(((IColorProvidingItem) item).getItemColor(), item);
    //            }
    //        }
    //
    //        for (Block block : COLOR_PRODIVIDING_BLOCKS_FOR_REGISTERING) {
    //            if (block instanceof IColorProvidingBlock) {
    //                Minecraft.getInstance().getBlockColors().registerBlockColorHandler(((IColorProvidingBlock) block).getBlockColor(), block);
    //            }
    //            if (block instanceof IColorProvidingItem) {
    //                Minecraft.getInstance().getItemColors().registerItemColorHandler(((IColorProvidingItem) block).getItemColor(), block);
    //            }
    //        }
    //
    //        IBlockColor color = (state, world, pos, tint) -> {
    //            if (world != null && pos != null) {
    //                TileEntity tileentity = world.getTileEntity(pos);
    //                if (tileentity instanceof TileEntityCompost && ((TileEntityCompost) tileentity).getCurrentDisplay().getBlock() != state.getBlock()) {
    //                    BlockState iblockstate = ((TileEntityCompost) tileentity).getCurrentDisplay();
    //                    return Minecraft.getInstance().getBlockColors().colorMultiplier(iblockstate, world, pos, tint);
    //                }
    //            }
    //            return -1;
    //        };
    //        Minecraft.getInstance().getBlockColors().registerBlockColorHandler(color, InitBlocks.blockCompost);
    //    }

    //    @Override
    //    public void postInit(FMLPostInitializationEvent event) {
    //        ActuallyAdditions.LOGGER.info("PostInitializing ClientProxy...");
    //    }
    //
    //    @Override
    //    public void addRenderRegister(ItemStack stack, ResourceLocation location, String variant) {
    //        ClientRegistryHandler.MODEL_LOCATIONS_FOR_REGISTERING.put(stack, new ModelResourceLocation(location, variant));
    //    }

    //    @Override
    //    public void addColoredItem(Item item) {
    //        COLOR_PRODIVIDING_ITEMS_FOR_REGISTERING.add(item);
    //    }
    //
    //    @Override
    //    public void addColoredBlock(Block block) {
    //        COLOR_PRODIVIDING_BLOCKS_FOR_REGISTERING.add(block);
    //    }

    @Deprecated
    public PlayerEntity getCurrentPlayer() {
        return Minecraft.getInstance().player;
    }
}
