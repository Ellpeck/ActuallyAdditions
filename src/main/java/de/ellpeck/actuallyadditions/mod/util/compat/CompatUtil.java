/*
 * This file ("CompatUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util.compat;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class CompatUtil {

	static boolean fb = Loader.isModLoaded("fastbench");

	@SideOnly(Side.CLIENT)
	public static Object getCrafterGuiElement(EntityPlayer player, World world, int x, int y, int z) {
		if (fb) return CompatFastBench.getFastBenchGui(player, world, x, y, z);
		return new GuiCrafting(player.inventory, world, new BlockPos(x, y, z));
	}

	public static Object getCrafterContainerElement(EntityPlayer player, World world, int x, int y, int z) {
		if (fb) return CompatFastBench.getFastBenchContainer(player, world, x, y, z);
		return new ContainerWorkbench(player.inventory, world, new BlockPos(x, y, z)) {
			public boolean canInteractWith(EntityPlayer playerIn) {
				return true;
			}
		};
	}
}
