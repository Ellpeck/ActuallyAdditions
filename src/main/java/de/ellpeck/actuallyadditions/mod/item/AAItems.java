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

    @ObjectHolder(ActuallyAdditions.MODID + ":black_quartz")
    public static Item black_quartz = new Item(new Properties().group(ActuallyAdditions.itemGroup)).setRegistryName("black_quartz");
    
	@SubscribeEvent
	public static void register(Register<Item> e) {
        e.getRegistry().register(black_quartz);
	}

}
