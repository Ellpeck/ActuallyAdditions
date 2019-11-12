package de.ellpeck.actuallyadditions.mod.item;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = ActuallyAdditions.MODID, bus = Bus.MOD)
@ObjectHolder(ActuallyAdditions.MODID)
public class AAItems {

	public static final Item BLACK_QUARTZ = null;

	@SubscribeEvent
	public static void register(Register<Item> e) {
		//Formatter::off
		e.getRegistry().registerAll(
        	new Item(new Properties().group(ActuallyAdditions.GROUP)).setRegistryName("black_quartz")
		);
		//Formatter::on
	}

}
