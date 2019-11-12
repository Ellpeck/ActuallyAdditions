/*
 * This file ("ActuallyAdditions.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ellpeck.actuallyadditions.mod.item.AAItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ActuallyAdditions.MODID)
public class ActuallyAdditions {

	public static final String MODID = "actuallyadditions";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final ItemGroup GROUP = new ItemGroup("actuallyadditions") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(AAItems.BLACK_QUARTZ);
		}
	};

	public ActuallyAdditions() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		ctx.getModEventBus().register(this);
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent e) {

	}
}
