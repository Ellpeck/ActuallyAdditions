/*
 * This file ("IMCHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util.compat;

import de.ellpeck.actuallyadditions.mod.blocks.*;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockPlant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.apache.commons.lang3.ArrayUtils;

public final class IMCHandler{

    private static final Class<? extends Block>[] NO_CARRYING = new Class[]{
            BlockGiantChest.class,
            BlockWildPlant.class,
            BlockPlant.class,
            BlockPhantom.class,
            BlockTinyTorch.class,
            BlockItemViewer.class,
            BlockItemViewerHopping.class,
            BlockLaserRelay.class
    };

    public static void doBlockIMC(Block block){
        boolean allow = !ArrayUtils.contains(NO_CARRYING, block.getClass());
        FMLInterModComms.sendMessage("charset", (allow ? "add" : "remove")+"Carry", block.getRegistryName());
    }

    public static void doItemIMC(Item item){

    }
}
