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
	public static final ColoredLampBlock WHITE_LAMP = null;
	public static final ColoredLampBlock ORANGE_LAMP = null;
	public static final ColoredLampBlock MAGENTA_LAMP = null;
	public static final ColoredLampBlock LIGHT_BLUE_LAMP = null;
	public static final ColoredLampBlock YELLOW_LAMP = null;
	public static final ColoredLampBlock LIME_LAMP = null;
	public static final ColoredLampBlock PINK_LAMP = null;
	public static final ColoredLampBlock GRAY_LAMP = null;
	public static final ColoredLampBlock LIGHT_GRAY_LAMP = null;
	public static final ColoredLampBlock CYAN_LAMP = null;
	public static final ColoredLampBlock PURPLE_LAMP = null;
	public static final ColoredLampBlock BLUE_LAMP = null;
	public static final ColoredLampBlock BROWN_LAMP = null;
	public static final ColoredLampBlock GREEN_LAMP = null;
	public static final ColoredLampBlock RED_LAMP = null;
	public static final ColoredLampBlock BLACK_LAMP = null;
	public static final TinyTorchBlock TINY_TORCH = null;

	@SubscribeEvent
	public static void register(Register<Block> e) {
		//Formatter::off
		e.getRegistry().registerAll(
			new Block(Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F)).setRegistryName("black_quartz_ore"),
        	new Block(Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(0.8F)).setRegistryName("black_quartz_block"),
        	new Block(Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(0.8F)).setRegistryName("chiseled_black_quartz_block"),
        	new RotatedPillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(0.8F)).setRegistryName("black_quartz_pillar"),
        	new SlabBlock(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(0.8F)).setRegistryName("black_quartz_slab"),
            new ColoredLampBlock(LampColor.WHITE).setRegistryName("white_lamp"),
            new ColoredLampBlock(LampColor.ORANGE).setRegistryName("orange_lamp"),
            new ColoredLampBlock(LampColor.MAGENTA).setRegistryName("magenta_lamp"),
            new ColoredLampBlock(LampColor.LIGHT_BLUE).setRegistryName("light_blue_lamp"),
            new ColoredLampBlock(LampColor.YELLOW).setRegistryName("yellow_lamp"),
            new ColoredLampBlock(LampColor.LIME).setRegistryName("lime_lamp"),
            new ColoredLampBlock(LampColor.PINK).setRegistryName("pink_lamp"),
            new ColoredLampBlock(LampColor.GRAY).setRegistryName("gray_lamp"),
            new ColoredLampBlock(LampColor.LIGHT_GRAY).setRegistryName("light_gray_lamp"),
            new ColoredLampBlock(LampColor.CYAN).setRegistryName("cyan_lamp"),
            new ColoredLampBlock(LampColor.PURPLE).setRegistryName("purple_lamp"),
            new ColoredLampBlock(LampColor.BLUE).setRegistryName("blue_lamp"),
            new ColoredLampBlock(LampColor.BROWN).setRegistryName("brown_lamp"),
            new ColoredLampBlock(LampColor.GREEN).setRegistryName("green_lamp"),
            new ColoredLampBlock(LampColor.RED).setRegistryName("red_lamp"),
            new ColoredLampBlock(LampColor.BLACK).setRegistryName("black_lamp"),
			new TinyTorchBlock()
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
			new BlockItem(BLACK_QUARTZ_SLAB, new Item.Properties().group(ActuallyAdditions.GROUP)).setRegistryName(BLACK_QUARTZ_SLAB.getRegistryName()),
			new BlockItem(TINY_TORCH, new Item.Properties().group(ActuallyAdditions.GROUP)).setRegistryName(TINY_TORCH.getRegistryName())
        );
		//Formatter::on
		for (Block b : ColoredLampBlock.LAMPS.values()) {
			e.getRegistry().register(new BlockItem(b, new Item.Properties().group(ActuallyAdditions.GROUP)).setRegistryName(b.getRegistryName()));
		}
	}
}