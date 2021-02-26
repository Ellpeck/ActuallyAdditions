/*
 * This file ("ServerProxy.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.proxy;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ActuallyAdditions.LOGGER.info("PreInitializing ServerProxy...");
    }

    @Override
    public void init(FMLInitializationEvent event) {
        ActuallyAdditions.LOGGER.info("Initializing ServerProxy...");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        ActuallyAdditions.LOGGER.info("PostInitializing ServerProxy...");
    }

    @Override
    public void addRenderRegister(ItemStack stack, ResourceLocation location, String variant) {

    }

    @Override
    public void addColoredItem(Item item) {

    }

    @Override
    public void addColoredBlock(Block block) {

    }

    @Override
    public PlayerEntity getCurrentPlayer() {
        return null;
    }
}
