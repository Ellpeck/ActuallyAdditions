package de.ellpeck.actuallyadditions.mod.block;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = ActuallyAdditions.MODID, bus = Bus.MOD)
@ObjectHolder(ActuallyAdditions.MODID)
public class AABlocks {

	public static final Block BLACK_QUARTZ_ORE = null;
	public static final Block BLACK_QUARTZ_BLOCK = null;
	public static final Block CHISELED_BLACK_QUARTZ_BLOCK = null;
	public static final Block BLACK_QUARTZ_PILLAR = null;
	public static final Block BLACK_QUARTZ_SLAB = null;

	@SubscribeEvent
	public static void register(Register<Block> e) {
		//Formatter::off
		e.getRegistry().registerAll(
			new Block(Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F)).setRegistryName("black_quartz_ore"),
        	new Block(Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(0.8F)).setRegistryName("black_quartz_block"),
        	new Block(Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(0.8F)).setRegistryName("chiseled_black_quartz_block"),
        	new RotatedPillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(0.8F)).setRegistryName("black_quartz_pillar"),
        	new SlabBlock(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(0.8F)).setRegistryName("black_quartz_slab")
		); 
		//Formatter::on
	}

	@SubscribeEvent
	public static void registerItemBlocks(Register<Item> e) {
		//Formatter::off	
		e.getRegistry().registerAll(
			new BlockItem(BLACK_QUARTZ_ORE, new Item.Properties().group(ActuallyAdditions.GROUP)).setRegistryName(BLACK_QUARTZ_ORE.getRegistryName()),
			new BlockItem(BLACK_QUARTZ_BLOCK, new Item.Properties().group(ActuallyAdditions.GROUP)).setRegistryName(BLACK_QUARTZ_BLOCK.getRegistryName()),
			new BlockItem(CHISELED_BLACK_QUARTZ_BLOCK, new Item.Properties().group(ActuallyAdditions.GROUP)).setRegistryName(CHISELED_BLACK_QUARTZ_BLOCK.getRegistryName()),
			new BlockItem(BLACK_QUARTZ_PILLAR, new Item.Properties().group(ActuallyAdditions.GROUP)).setRegistryName(BLACK_QUARTZ_PILLAR.getRegistryName()),
			new BlockItem(BLACK_QUARTZ_SLAB, new Item.Properties().group(ActuallyAdditions.GROUP)).setRegistryName(BLACK_QUARTZ_SLAB.getRegistryName())
		);
		//Formatter::on
	}
}