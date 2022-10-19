package de.ellpeck.actuallyadditions.data;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
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

public class LootTableGenerator extends LootTableProvider {
    public LootTableGenerator(DataGenerator p_i50789_1_) {
        super(p_i50789_1_);
    }
    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(Blocks::new, LootParameterSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.validate(validationtracker, name, table));
    }

    public static class Blocks extends BlockLootTables {
        @Override
        protected void addTables() {
            CopyNbt.Builder copyEnergy = CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Energy", "BlockEntityTag.Energy");
            CopyNbt.Builder copyPulseMode = CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("IsPulseMode", "BlockEntityTag.IsPulseMode");

            //Special Drops
            dropNBT(ActuallyBlocks.ATOMIC_RECONSTRUCTOR, $ -> $.apply(copyEnergy).apply(copyPulseMode));
            dropKeepEnergy(ActuallyBlocks.DISPLAY_STAND);
            dropKeepEnergy(ActuallyBlocks.COAL_GENERATOR);
            dropKeepEnergy(ActuallyBlocks.OIL_GENERATOR);
            dropKeepEnergy(ActuallyBlocks.CRUSHER);
            dropKeepEnergy(ActuallyBlocks.CRUSHER_DOUBLE);
            dropKeepEnergy(ActuallyBlocks.POWERED_FURNACE);

            this.dropSelf(ActuallyBlocks.BATTERY_BOX.get());
            this.dropSelf(ActuallyBlocks.ITEM_INTERFACE_HOPPING.get());
            this.dropSelf(ActuallyBlocks.FARMER.get());
            this.dropSelf(ActuallyBlocks.BIOREACTOR.get());
            this.dropSelf(ActuallyBlocks.EMPOWERER.get());
            this.dropSelf(ActuallyBlocks.TINY_TORCH.get());
            this.dropSelf(ActuallyBlocks.SHOCK_SUPPRESSOR.get());
            this.dropSelf(ActuallyBlocks.PLAYER_INTERFACE.get());
            this.dropSelf(ActuallyBlocks.ITEM_INTERFACE.get());
            this.dropSelf(ActuallyBlocks.FIREWORK_BOX.get());
            this.dropSelf(ActuallyBlocks.VERTICAL_DIGGER.get());
            this.dropSelf(ActuallyBlocks.ENERGIZER.get());
            this.dropSelf(ActuallyBlocks.ENERVATOR.get());
            this.dropSelf(ActuallyBlocks.LAVA_FACTORY_CONTROLLER.get());
            this.dropSelf(ActuallyBlocks.CANOLA_PRESS.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_ITEMFACE.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_PLACER.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_LIQUIFACE.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_ENERGYFACE.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_REDSTONEFACE.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_BREAKER.get());
            this.dropSelf(ActuallyBlocks.FERMENTING_BARREL.get());
            this.dropSelf(ActuallyBlocks.FEEDER.get());
            this.dropSelf(ActuallyBlocks.HEAT_COLLECTOR.get());
            this.dropSelf(ActuallyBlocks.GREENHOUSE_GLASS.get());
            this.dropSelf(ActuallyBlocks.BREAKER.get());
            this.dropSelf(ActuallyBlocks.PLACER.get());
            this.dropSelf(ActuallyBlocks.DROPPER.get());
            this.dropSelf(ActuallyBlocks.FLUID_PLACER.get());
            this.dropSelf(ActuallyBlocks.FLUID_COLLECTOR.get());
            this.dropSelf(ActuallyBlocks.COFFEE_MACHINE.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_BOOSTER.get());
            this.dropSelf(ActuallyBlocks.RANGED_COLLECTOR.get());
            this.dropSelf(ActuallyBlocks.LONG_RANGE_BREAKER.get());
            this.dropSelf(ActuallyBlocks.LEAF_GENERATOR.get());
            this.dropSelf(ActuallyBlocks.XP_SOLIDIFIER.get());
            this.dropSelf(ActuallyBlocks.LASER_RELAY.get());
            this.dropSelf(ActuallyBlocks.LASER_RELAY_ADVANCED.get());
            this.dropSelf(ActuallyBlocks.LASER_RELAY_EXTREME.get());
            this.dropSelf(ActuallyBlocks.LASER_RELAY_FLUIDS.get());
            this.dropSelf(ActuallyBlocks.LASER_RELAY_ITEM.get());
            this.dropSelf(ActuallyBlocks.LASER_RELAY_ITEM_ADVANCED.get());
            this.dropSelf(ActuallyBlocks.ETHETIC_GREEN_BLOCK.get());
            this.dropSelf(ActuallyBlocks.ETHETIC_WHITE_BLOCK.get());
            this.dropSelf(ActuallyBlocks.ETHETIC_GREEN_STAIRS.get());
            this.dropSelf(ActuallyBlocks.ETHETIC_WHITE_STAIRS.get());
            this.dropSelf(ActuallyBlocks.ETHETIC_GREEN_SLAB.get());
            this.dropSelf(ActuallyBlocks.ETHETIC_WHITE_SLAB.get());
            this.dropSelf(ActuallyBlocks.ETHETIC_GREEN_WALL.get());
            this.dropSelf(ActuallyBlocks.ETHETIC_WHITE_WALL.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ.get());
            this.dropSelf(ActuallyBlocks.SMOOTH_BLACK_QUARTZ.get());
            this.dropSelf(ActuallyBlocks.CHISELED_BLACK_QUARTZ.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_PILLAR.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_BRICK.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_WALL.get());
            this.dropSelf(ActuallyBlocks.SMOOTH_BLACK_QUARTZ_WALL.get());
            this.dropSelf(ActuallyBlocks.CHISELED_BLACK_QUARTZ_WALL.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_PILLAR_WALL.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_BRICK_WALL.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_STAIR.get());
            this.dropSelf(ActuallyBlocks.SMOOTH_BLACK_QUARTZ_STAIR.get());
            this.dropSelf(ActuallyBlocks.CHISELED_BLACK_QUARTZ_STAIR.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_PILLAR_STAIR.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_BRICK_STAIR.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_SLAB.get());
            this.dropSelf(ActuallyBlocks.SMOOTH_BLACK_QUARTZ_SLAB.get());
            this.dropSelf(ActuallyBlocks.CHISELED_BLACK_QUARTZ_SLAB.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_PILLAR_SLAB.get());
            this.dropSelf(ActuallyBlocks.BLACK_QUARTZ_BRICK_SLAB.get());
            this.dropSelf(ActuallyBlocks.LAMP_WHITE.get());
            this.dropSelf(ActuallyBlocks.LAMP_ORANGE.get());
            this.dropSelf(ActuallyBlocks.LAMP_MAGENTA.get());
            this.dropSelf(ActuallyBlocks.LAMP_LIGHT_BLUE.get());
            this.dropSelf(ActuallyBlocks.LAMP_YELLOW.get());
            this.dropSelf(ActuallyBlocks.LAMP_LIME.get());
            this.dropSelf(ActuallyBlocks.LAMP_PINK.get());
            this.dropSelf(ActuallyBlocks.LAMP_GRAY.get());
            this.dropSelf(ActuallyBlocks.LAMP_LIGHT_GRAY.get());
            this.dropSelf(ActuallyBlocks.LAMP_CYAN.get());
            this.dropSelf(ActuallyBlocks.LAMP_PURPLE.get());
            this.dropSelf(ActuallyBlocks.LAMP_BLUE.get());
            this.dropSelf(ActuallyBlocks.LAMP_BROWN.get());
            this.dropSelf(ActuallyBlocks.LAMP_GREEN.get());
            this.dropSelf(ActuallyBlocks.LAMP_RED.get());
            this.dropSelf(ActuallyBlocks.LAMP_BLACK.get());
            this.dropSelf(ActuallyBlocks.LAMP_CONTROLLER.get());
            this.dropSelf(ActuallyBlocks.ENDER_CASING.get());
            this.dropSelf(ActuallyBlocks.IRON_CASING.get());
            //this.dropSelf(ActuallyBlocks.IRON_CASING_SNOW.get());
            this.dropSelf(ActuallyBlocks.LAVA_FACTORY_CASING.get());
            this.dropSelf(ActuallyBlocks.WOOD_CASING.get());

            this.dropSelf(ActuallyBlocks.ENORI_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.RESTONIA_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.PALIS_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.DIAMATINE_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.VOID_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.EMERADIC_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.EMPOWERED_ENORI_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.EMPOWERED_RESTONIA_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.EMPOWERED_PALIS_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.EMPOWERED_VOID_CRYSTAL.get());
            this.dropSelf(ActuallyBlocks.EMPOWERED_EMERADIC_CRYSTAL.get());

/*            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_RESTONIA, ActuallyItems.RED_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_PALIS, ActuallyItems.BLUE_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_DIAMATINE, ActuallyItems.LIGHT_BLUE_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_VOID, ActuallyItems.BLACK_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_EMERADIC, ActuallyItems.GREEN_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.CRYSTAL_CLUSTER_ENORI, ActuallyItems.WHITE_CRYSTAL_SHARD);*/

            //TODO temp
            dropSelf(ActuallyBlocks.ENORI_CRYSTAL_CLUSTER.get());
            dropSelf(ActuallyBlocks.RESTONIA_CRYSTAL_CLUSTER.get());
            dropSelf(ActuallyBlocks.PALIS_CRYSTAL_CLUSTER.get());
            dropSelf(ActuallyBlocks.DIAMATINE_CRYSTAL_CLUSTER.get());
            dropSelf(ActuallyBlocks.VOID_CRYSTAL_CLUSTER.get());
            dropSelf(ActuallyBlocks.EMERADIC_CRYSTAL_CLUSTER.get());

            //TODO temp
            dropSelf(ActuallyBlocks.BLACK_QUARTZ_ORE.get());

            //this.add(ActuallyBlocks.BLACK_QUARTZ_ORE.get(), ore -> droppingItemWithFortune(ore, ActuallyItems.BLACK_QUARTZ.get()));

            addCrop(ActuallyBlocks.CANOLA, ActuallyItems.CANOLA, ActuallyItems.CANOLA_SEEDS);
            addCrop(ActuallyBlocks.RICE, ActuallyItems.RICE, ActuallyItems.RICE_SEEDS);
            addCrop(ActuallyBlocks.FLAX, () -> Items.STRING, ActuallyItems.FLAX_SEEDS);
            addCrop(ActuallyBlocks.COFFEE, ActuallyItems.COFFEE_BEANS, ActuallyItems.COFFEE_SEEDS);
        }

        private void addCrop(Supplier<Block> block, Supplier<Item> item, Supplier<Item> seed) {
            add(block.get(), createCropDrops(block.get(), item.get(), seed.get(),
                BlockStateProperty.hasBlockStateProperties(block.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropsBlock.AGE, 7))));

        }

        private void dropNBT(Supplier<Block> blockSupplier, Consumer<LootPool.Builder> lootFunctionProvider) {
            LootPool.Builder lootpool = LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(blockSupplier.get()));

            lootFunctionProvider.accept(lootpool);

            add(blockSupplier.get(), LootTable.lootTable().withPool(applyExplosionCondition(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get(), lootpool)));
        }
        private void dropKeepEnergy(Supplier<Block> blockSupplier) {
            dropNBT(blockSupplier, $ -> $.apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Energy", "BlockEntityTag.Energy")));
        }

/*        // This isn't quite right :cry: fortune doesn't change it
        private void registerCrystal(RegistryObject<Block> crystalCluster, RegistryObject<Item> crystalShard) {
            this.registerLootTable(crystalCluster.get(), (crystal) ->
                droppingWithSilkTouch(crystal,
                    withExplosionDecay(crystal, ItemLootEntry.builder(crystalShard.get())
                        .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))
                        .acceptFunction(SetCount.builder(RandomValueRange.of(2f, 8f)))
                    )
                )
            );
        }*/

        @Override
        protected Iterable<Block> getKnownBlocks() {
            final Set<Block> ignoreForNow = ImmutableSet.of(
                InitFluids.CANOLA_OIL.getBlock(),
                InitFluids.REFINED_CANOLA_OIL.getBlock(),
                InitFluids.CRYSTALLIZED_OIL.getBlock(),
                InitFluids.EMPOWERED_OIL.getBlock()
            );

            return ActuallyBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(e -> !ignoreForNow.contains(e)).collect(Collectors.toList());
        }
    }
}
