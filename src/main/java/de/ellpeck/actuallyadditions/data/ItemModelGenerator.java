package de.ellpeck.actuallyadditions.data;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.fluids.FluidAA;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.function.Supplier;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ActuallyAdditions.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        simpleTool(ActuallyItems.ITEM_BOOKLET); // will require complex I think
        simpleTool(ActuallyItems.CRATE_KEEPER);

        // All items?
        ActuallyItems.SIMPLE_ITEMS.forEach(this::simpleItem);
        ActuallyItems.TOOLS.forEach(this::simpleTool);

        // Toolsets
/*        ActuallyItems.ALL_TOOL_SETS.stream()
            .map(ToolSet::getItemGroup)
            .flatMap(Collection::stream)
            .forEach(item -> simpleItem(() -> item));*/


        Set<Block> ignoreList = ImmutableSet.of(
            InitFluids.CANOLA_OIL.getBlock(),
            InitFluids.REFINED_CANOLA_OIL.getBlock(),
            InitFluids.CRYSTALLIZED_OIL.getBlock(),
            InitFluids.EMPOWERED_OIL.getBlock(),
            ActuallyBlocks.CANOLA.get(),
            ActuallyBlocks.RICE.get(),
            ActuallyBlocks.FLAX.get(),
            ActuallyBlocks.COFFEE.get(),
            ActuallyBlocks.TINY_TORCH.get()
        );

        // Blocks
        ActuallyBlocks.BLOCKS.getEntries().stream().filter(b -> !ignoreList.contains(b.get())).forEach(this::registerBlockModel);

        generateBucket(InitFluids.CANOLA_OIL);
        generateBucket(InitFluids.REFINED_CANOLA_OIL);
        generateBucket(InitFluids.CRYSTALLIZED_OIL);
        generateBucket(InitFluids.EMPOWERED_OIL);


        String wormpath = ActuallyItems.WORM.getId().getPath();
        singleTexture(wormpath, mcLoc("item/handheld"), "layer0", modLoc("item/" + wormpath))
                .override().predicate(new ResourceLocation(ActuallyAdditions.MODID, "snail"), 1F)
                .model(singleTexture("snail", mcLoc("item/handheld"), "layer0", modLoc("item/snail"))).end();
/*        withExistingParent(wormpath, mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + wormpath))
                .override().predicate(new ResourceLocation(ActuallyAdditions.MODID, "snail"), 1F)
                .model(getBuilder("snail").parent(getExistingFile(mcLoc("item/handheld"))).texture("layer0", "item/snail")).end();*/

        String torchPath = BuiltInRegistries.ITEM.getKey(ActuallyBlocks.TINY_TORCH.getItem()).getPath();
        singleTexture(torchPath, mcLoc("item/generated"), "layer0", modLoc("block/" + torchPath));
    }

    private void generateBucket(FluidAA fluidSupplier) {
        withExistingParent(BuiltInRegistries.ITEM.getKey(fluidSupplier.getBucket()).getPath(), "neoforge:item/bucket")
            .customLoader((builder, template) -> DynamicFluidContainerModelBuilder.begin(builder, template).fluid(fluidSupplier.get()));
    }

    private void registerBlockModel(DeferredHolder<Block, ? extends Block> block) {
        String path = block.getId().getPath();
        if (block.get() instanceof WallBlock) {
            String name = path;
            path = "block/" + path.replace("_wall", "_block");
            withExistingParent(name, new ResourceLocation("block/wall_inventory"))
                .texture("wall", modLoc(path));
            return;
        }
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
    }

    private void simpleTool(Supplier<? extends Item> item) {
        String path = BuiltInRegistries.ITEM.getKey(item.get()).getPath();
        singleTexture(path, mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
    }
    private void simpleItem(Supplier<? extends Item> item) {
        String path = BuiltInRegistries.ITEM.getKey(item.get()).getPath();
        singleTexture(path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
    }
}
