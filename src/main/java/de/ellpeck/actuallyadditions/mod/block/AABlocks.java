package de.ellpeck.actuallyadditions.mod.block;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = ActuallyAdditions.MODID, bus = Bus.MOD)
@ObjectHolder(ActuallyAdditions.MODID)
public class AABlocks {
    
    @ObjectHolder(ActuallyAdditions.MODID + ":block_black_quartz")
    public static Block BLACK_QUARTZ;

	@SubscribeEvent
	public static void register(Register<Block> e) {
        e.getRegistry().register(new Block(Properties.create(Material.ROCK).hardnessAndResistance(0.8F)).setRegistryName("block_black_quartz")); //Values from the QUARTZ_BLOCK
	}

	@SubscribeEvent
	public static void registerItemBlocks(Register<Item> e) {
        e.getRegistry().register(new BlockItem(BLACK_QUARTZ, new Item.Properties().group(ActuallyAdditions.itemGroup)).setRegistryName(BLACK_QUARTZ.getRegistryName()));
	}
}