package de.ellpeck.actuallyadditions.data;


import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.misc.DungeonLoot;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LootTableGenerator extends LootTableProvider {
    public LootTableGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Set.of(), List.of(
                new SubProviderEntry(Blocks::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(Dungeon::new, LootContextParamSets.CHEST)
        ), lookupProvider);
    }

    @Override
    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
        super.validate(writableregistry, validationcontext, problemreporter$collector);
    }

    public static class Blocks extends BlockLootSubProvider {

        protected Blocks(HolderLookup.Provider lookupProvider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
        }

        @Override
        protected void generate() {
            CopyComponentsFunction.Builder copyEnergy = CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                    .include(ActuallyComponents.ENERGY_STORAGE.get());
            CopyComponentsFunction.Builder copyPulseMode = CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                    .include(ActuallyComponents.PULSE_MODE.get());
            CopyComponentsFunction.Builder copyFluid_A = CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                    .include(ActuallyComponents.FLUID_A.get());
            CopyComponentsFunction.Builder copyFluid_B = CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                    .include(ActuallyComponents.FLUID_B.get());
            CopyComponentsFunction.Builder copyMiscInt = CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                    .include(ActuallyComponents.MISC_INT.get());

            //Special Drops
            dropComponents(ActuallyBlocks.ATOMIC_RECONSTRUCTOR, $ -> $.apply(copyEnergy).apply(copyPulseMode));
            dropKeepEnergy(ActuallyBlocks.DISPLAY_STAND);
            dropKeepEnergy(ActuallyBlocks.COAL_GENERATOR);
            dropComponents(ActuallyBlocks.OIL_GENERATOR, $ -> $.apply(copyEnergy).apply(copyFluid_A));
            dropKeepEnergy(ActuallyBlocks.LEAF_GENERATOR);
            dropKeepEnergy(ActuallyBlocks.CRUSHER);
            dropKeepEnergy(ActuallyBlocks.CRUSHER_DOUBLE);
            dropKeepEnergy(ActuallyBlocks.POWERED_FURNACE);
            dropComponents(ActuallyBlocks.COFFEE_MACHINE, $ -> $.apply(copyEnergy).apply(copyFluid_A).apply(copyMiscInt));
            dropComponents(ActuallyBlocks.FLUID_COLLECTOR, $ -> $.apply(copyFluid_A).apply(copyPulseMode));
            dropComponents(ActuallyBlocks.FLUID_PLACER, $ -> $.apply(copyFluid_A).apply(copyPulseMode));
            dropKeepPulseMode(ActuallyBlocks.BREAKER);
            dropKeepPulseMode(ActuallyBlocks.PLACER);
            dropKeepPulseMode(ActuallyBlocks.DROPPER);
            dropComponents(ActuallyBlocks.CANOLA_PRESS, $ -> $.apply(copyEnergy).apply(copyFluid_A));
            dropComponents(ActuallyBlocks.FERMENTING_BARREL, $ -> $.apply(copyFluid_A).apply(copyFluid_B));
            dropKeepEnergy(ActuallyBlocks.FARMER);

            this.dropSelf(ActuallyBlocks.BATTERY_BOX.get());
            this.dropSelf(ActuallyBlocks.ITEM_INTERFACE_HOPPING.get());
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
            this.dropSelf(ActuallyBlocks.PHANTOM_ITEMFACE.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_PLACER.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_LIQUIFACE.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_ENERGYFACE.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_REDSTONEFACE.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_BREAKER.get());
            this.dropSelf(ActuallyBlocks.FEEDER.get());
            this.dropSelf(ActuallyBlocks.HEAT_COLLECTOR.get());
            this.dropSelf(ActuallyBlocks.GREENHOUSE_GLASS.get());
            this.dropSelf(ActuallyBlocks.CRATE_SMALL.get());
            this.dropSelf(ActuallyBlocks.PHANTOM_BOOSTER.get());
            this.dropSelf(ActuallyBlocks.RANGED_COLLECTOR.get());
            this.dropSelf(ActuallyBlocks.LONG_RANGE_BREAKER.get());
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

            this.registerCrystal(ActuallyBlocks.ENORI_CRYSTAL_CLUSTER, ActuallyItems.ENORI_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.RESTONIA_CRYSTAL_CLUSTER, ActuallyItems.RESTONIA_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.PALIS_CRYSTAL_CLUSTER, ActuallyItems.PALIS_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.DIAMATINE_CRYSTAL_CLUSTER, ActuallyItems.DIAMATINE_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.VOID_CRYSTAL_CLUSTER, ActuallyItems.VOID_CRYSTAL_SHARD);
            this.registerCrystal(ActuallyBlocks.EMERADIC_CRYSTAL_CLUSTER, ActuallyItems.EMERADIC_CRYSTAL_SHARD);

            add(ActuallyBlocks.BLACK_QUARTZ_ORE.get(), createOreDrop(ActuallyBlocks.BLACK_QUARTZ_ORE.getBlock(), ActuallyItems.BLACK_QUARTZ.get()));

            addCrop(ActuallyBlocks.CANOLA, ActuallyItems.CANOLA, ActuallyItems.CANOLA_SEEDS);
            addCrop(ActuallyBlocks.RICE, ActuallyItems.RICE, ActuallyItems.RICE_SEEDS);
            addCrop(ActuallyBlocks.FLAX, () -> Items.STRING, ActuallyItems.FLAX_SEEDS);
            addCrop(ActuallyBlocks.COFFEE, ActuallyItems.COFFEE_BEANS, ActuallyItems.COFFEE_BEANS);
        }

        private void addCrop(Supplier<? extends Block> block, Supplier<? extends Item> item, Supplier<? extends Item> seed) {
            add(block.get(), createCropDrops(block.get(), item.get(), seed.get(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7))));

        }

        private void dropComponents(Supplier<? extends Block> blockSupplier, Consumer<LootPool.Builder> lootFunctionProvider) {
            LootPool.Builder lootpool = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(blockSupplier.get()));

            lootFunctionProvider.accept(lootpool);

            add(blockSupplier.get(), LootTable.lootTable().withPool(applyExplosionCondition(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get(), lootpool)));
        }
        private void dropKeepPulseMode(Supplier<? extends Block> blockSupplier) {
            dropComponents(blockSupplier, $ -> $.apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                    .include(ActuallyComponents.PULSE_MODE.get())));
        }
        private void dropKeepEnergy(Supplier<? extends Block> blockSupplier) {
            dropComponents(blockSupplier, $ -> $.apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                    .include(ActuallyComponents.ENERGY_STORAGE.get())));
        }

        private void registerCrystal(Supplier<? extends Block> crystalCluster, DeferredItem<? extends Item> crystalShard) {
            this.add(crystalCluster.get(), block ->
                    this.createSingleItemTableWithSilkTouch(block, crystalShard.get(), UniformGenerator.between(2.0F, 8.0F)));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            final Set<Block> ignoreForNow = ImmutableSet.of(
                InitFluids.CANOLA_OIL.getBlock(),
                InitFluids.REFINED_CANOLA_OIL.getBlock(),
                InitFluids.CRYSTALLIZED_OIL.getBlock(),
                InitFluids.EMPOWERED_OIL.getBlock()
            );

            return ActuallyBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).filter(e -> !ignoreForNow.contains(e)).collect(Collectors.toList());
        }
    }

    public static class Dungeon implements LootTableSubProvider {
        public Dungeon(HolderLookup.Provider provider) {

        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput) {
            //                addCrystals = true;

            pOutput.accept(
                    DungeonLoot.ENGINEER_HOUSE,
                    LootTable.lootTable()
                            .withPool(
                                    LootPool.lootPool()
                                            .setRolls(UniformGenerator.between(4.0F, 7.0F))
                                            .add(LootItem.lootTableItem(ActuallyBlocks.WOOD_CASING.getItem()).setWeight(60).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 10.0F))))
                                            .add(LootItem.lootTableItem(ActuallyBlocks.IRON_CASING.getItem()).setWeight(40).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                            .add(LootItem.lootTableItem(ActuallyItems.BLACK_QUARTZ.get()).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                            .add(LootItem.lootTableItem(ActuallyItems.BATS_WING.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                            .add(LootItem.lootTableItem(ActuallyItems.DRILL_CORE.get()).setWeight(5))
                                            .add(TagEntry.expandTag(ActuallyTags.Items.CRYSTALS).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                            .add(TagEntry.expandTag(ActuallyTags.Items.CRYSTALS).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                            )
            );
        }
    }
}
