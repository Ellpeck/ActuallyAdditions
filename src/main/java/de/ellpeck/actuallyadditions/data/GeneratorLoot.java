package de.ellpeck.actuallyadditions.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GeneratorLoot extends LootTableProvider {
    public GeneratorLoot(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(Blocks::new, LootParameterSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.validateLootTable(validationtracker, name, table));
    }

    private static class Blocks extends BlockLootTables {
        @Override
        protected void addTables() {
            this.registerDropSelfLootTable(ActuallyBlocks.BATTERY_BOX.get());
            this.registerDropSelfLootTable(ActuallyBlocks.HOPPING_ITEM_INTERFACE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.FARMER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BIO_REACTOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.EMPOWERER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.TINY_TORCH.get());
            this.registerDropSelfLootTable(ActuallyBlocks.SHOCK_SUPPRESSOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.DISPLAY_STAND.get());
            this.registerDropSelfLootTable(ActuallyBlocks.PLAYER_INTERFACE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ITEM_INTERFACE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.FIREWORK_BOX.get());
            this.registerDropSelfLootTable(ActuallyBlocks.MINER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ENERGIZER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ENERVATOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAVA_FACTORY_CONTROLLER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CANOLA_PRESS.get());
            this.registerDropSelfLootTable(ActuallyBlocks.PHANTOMFACE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.PHANTOM_PLACER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.PHANTOM_LIQUIFACE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.PHANTOM_ENERGYFACE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.PHANTOM_REDSTONEFACE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.PHANTOM_BREAKER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.COAL_GENERATOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.OIL_GENERATOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.FERMENTING_BARREL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.FEEDER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRUSHER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRUSHER_DOUBLE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.POWERED_FURNACE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.DOUBLE_POWERED_FURNACE.get());
            //this.registerDropSelfLootTable(ActuallyBlocks.ESD.get());
            //this.registerDropSelfLootTable(ActuallyBlocks.ESD_ADVANCED.get());
            this.registerDropSelfLootTable(ActuallyBlocks.HEAT_COLLECTOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.GREENHOUSE_GLASS.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BREAKER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.PLACER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.DROPPER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.FLUID_PLACER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.FLUID_COLLECTOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.COFFEE_MACHINE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.PHANTOM_BOOSTER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.RANGED_COLLECTOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.DIRECTIONAL_BREAKER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LEAF_GENERATOR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.XP_SOLIDIFIER.get());

            this.registerDropSelfLootTable(ActuallyBlocks.ENERGY_LASER_RELAY.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ENERGY_LASER_RELAY_ADVANCED.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ENERGY_LASER_RELAY_EXTREME.get());
            this.registerDropSelfLootTable(ActuallyBlocks.FLUIDS_LASER_RELAY.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ITEM_LASER_RELAY.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ADVANCED_ITEM_LASER_RELAY.get());
            this.registerDropSelfLootTable(ActuallyBlocks.GREEN_BLOCK.get());
            this.registerDropSelfLootTable(ActuallyBlocks.WHITE_BLOCK.get());
            this.registerDropSelfLootTable(ActuallyBlocks.GREEN_STAIRS.get());
            this.registerDropSelfLootTable(ActuallyBlocks.WHITE_STAIRS.get());
            this.registerDropSelfLootTable(ActuallyBlocks.GREEN_SLAB.get());
            this.registerDropSelfLootTable(ActuallyBlocks.WHITE_SLAB.get());
            this.registerDropSelfLootTable(ActuallyBlocks.GREEN_WALL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.WHITE_WALL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_QUARTZ.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_QUARTZ_SMOOTH.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_QUARTZ_CHISELED.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_QUARTZ_PILLAR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_QUARTZ_BRICK.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_QUARTZ_WALL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_SMOOTH_QUARTZ_WALL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_CHISELED_QUARTZ_WALL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_PILLAR_QUARTZ_WALL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_BRICK_QUARTZ_WALL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_QUARTZ_STAIR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_SMOOTH_QUARTZ_STAIR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_CHISELED_QUARTZ_STAIR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_PILLAR_QUARTZ_STAIR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_BRICK_QUARTZ_STAIR.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_QUARTZ_SLAB.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_SMOOTH_QUARTZ_SLAB.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_CHISELED_QUARTZ_SLAB.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_PILLAR_QUARTZ_SLAB.get());
            this.registerDropSelfLootTable(ActuallyBlocks.BLACK_BRICK_QUARTZ_SLAB.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_WHITE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_ORANGE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_MAGENTA.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_YELLOW.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_LIME.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_PINK.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_GRAY.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_CYAN.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_PURPLE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_BLUE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_BROWN.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_GREEN.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_RED.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_BLACK.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAMP_CONTROLLER.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ENDERPEARL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CHARCOAL.get());
            this.registerDropSelfLootTable(ActuallyBlocks.ENDER_CASING.get());
            this.registerDropSelfLootTable(ActuallyBlocks.IRON_CASING.get());
            //this.registerDropSelfLootTable(ActuallyBlocks.IRON_CASING_SNOW.get());
            this.registerDropSelfLootTable(ActuallyBlocks.LAVA_FACTORY_CASE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.WOOD_CASING.get());

            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_ENORI.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_RESTONIA.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_PALIS.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_DIAMATINE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_VOID.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_EMERADIC.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_EMPOWERED_ENORI.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_EMPOWERED_RESTONIA.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_EMPOWERED_PALIS.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_EMPOWERED_DIAMATINE.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_EMPOWERED_VOID.get());
            this.registerDropSelfLootTable(ActuallyBlocks.CRYSTAL_EMPOWERED_EMERADIC.get());

            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_RESTONIA, ActuallyItems.RED_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_PALIS, ActuallyItems.BLUE_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_DIAMATINE, ActuallyItems.LIGHT_BLUE_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_VOID, ActuallyItems.BLACK_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_EMERADIC, ActuallyItems.GREEN_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_ENORI, ActuallyItems.WHITE_CRYSTAL_SHARD);

            this.registerLootTable(ActuallyBlocks.ORE_BLACK_QUARTZ.get(), ore -> droppingItemWithFortune(ore, ActuallyItems.BLACK_QUARTZ.get()));
        }

        // This isn't quite right :cry: fortune doesn't change it
        private void registerCrystal(RegistryObject<Block> crystalCluster, RegistryObject<Item> crystalShard) {
            this.registerLootTable(crystalCluster.get(), (crystal) ->
                    droppingWithSilkTouch(crystal,
                            withExplosionDecay(crystal, ItemLootEntry.builder(crystalShard.get())
                                    .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(2f, 8f)))
                            )
                    )
            );
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            final Set<Block> ignoreForNow = ImmutableSet.of(
                    ActuallyBlocks.RICE.get(), ActuallyBlocks.CANOLA.get(), ActuallyBlocks.FLAX.get(), ActuallyBlocks.COFFEE.get()
            );

            return ActuallyBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(e -> !ignoreForNow.contains(e)).collect(Collectors.toList());
        }
    }
}
