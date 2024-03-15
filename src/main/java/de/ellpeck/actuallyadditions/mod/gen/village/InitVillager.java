/*
 * This file ("InitVillager.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.village;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

public final class InitVillager {
	public static void setupTrades(VillagerTradesEvent event) {
		if (event.getType() == ActuallyVillagers.ENGINEER.get()) {
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

			trades.put(1, List.of(new BasicTradeList(new BasicTradeList.PriceRange(1, 2), new ItemStack(ActuallyBlocks.BLACK_QUARTZ_ORE.get()), new BasicTradeList.PriceRange(2, 3)), new BasicTradeList(new BasicTradeList.PriceRange(1, 2), new ItemStack(ActuallyItems.BLACK_QUARTZ.get()), new BasicTradeList.PriceRange(6, 8)), new BasicTradeList(new BasicTradeList.PriceRange(1, 3), new ItemStack(ActuallyItems.LASER_WRENCH.get()), new BasicTradeList.PriceRange(1, 1))));
			trades.put(2, List.of(new BasicTradeList(new ItemStack(ActuallyItems.COFFEE_BEANS.get()), new BasicTradeList.PriceRange(20, 30), new BasicTradeList.PriceRange(1, 2)), new BasicTradeList(new BasicTradeList.PriceRange(3, 5), new ItemStack(ActuallyBlocks.ITEM_INTERFACE.get()), new BasicTradeList.PriceRange(1, 1)), new BasicTradeList(new BasicTradeList.PriceRange(10, 20), new ItemStack(ActuallyBlocks.LASER_RELAY.get()), new BasicTradeList.PriceRange(1, 2))));
			trades.put(3, List.of(new BasicTradeList(new ItemStack(ActuallyBlocks.TINY_TORCH.get()), new BasicTradeList.PriceRange(30, 40), new BasicTradeList.PriceRange(1, 2)), new BasicTradeList(new BasicTradeList.PriceRange(1, 2), new ItemStack(ActuallyBlocks.WOOD_CASING.get()), new BasicTradeList.PriceRange(1, 2))));
			trades.put(4, List.of(new BasicTradeList(new BasicTradeList.PriceRange(3, 5), new ItemStack(ActuallyBlocks.IRON_CASING.get()), new BasicTradeList.PriceRange(1, 2)), new BasicTradeList(new ItemStack(ActuallyBlocks.EMPOWERER.get()), new BasicTradeList.PriceRange(1, 1), new BasicTradeList.PriceRange(15, 20)), new BasicTradeList(new BasicTradeList.PriceRange(30, 40), new ItemStack(ActuallyBlocks.LASER_RELAY_EXTREME.get()), new BasicTradeList.PriceRange(1, 1))));
		}
	}
}
