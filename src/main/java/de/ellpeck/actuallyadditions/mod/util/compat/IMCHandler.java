package de.ellpeck.actuallyadditions.mod.util.compat;

import org.apache.commons.lang3.ArrayUtils;

import de.ellpeck.actuallyadditions.mod.blocks.BlockGiantChest;
import de.ellpeck.actuallyadditions.mod.blocks.BlockItemViewer;
import de.ellpeck.actuallyadditions.mod.blocks.BlockItemViewerHopping;
import de.ellpeck.actuallyadditions.mod.blocks.BlockLaserRelay;
import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.mod.blocks.BlockTinyTorch;
import de.ellpeck.actuallyadditions.mod.blocks.BlockWildPlant;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockPlant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public final class IMCHandler {

    private static final Class<?>[] NO_CARRYING = new Class<?>[] { BlockGiantChest.class, BlockWildPlant.class, BlockPlant.class, BlockPhantom.class, BlockTinyTorch.class, BlockItemViewer.class, BlockItemViewerHopping.class, BlockLaserRelay.class };

    public static void doBlockIMC(Block block) {
        boolean allow = !ArrayUtils.contains(NO_CARRYING, block.getClass());
        FMLInterModComms.sendMessage("charset", (allow ? "add" : "remove") + "Carry", block.getRegistryName());
    }

    public static void doItemIMC(Item item) {

    }
}
