package de.ellpeck.actuallyadditions.data;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.fluids.FluidAA;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.DynamicBucketModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ActuallyAdditions.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        //simpleItem(ActuallyItems.BOOKLET); // will require complex I think

        // All items?
        ActuallyItems.SIMPLE_ITEMS.forEach(this::simpleItem);

        // Toolsets
/*        ActuallyItems.ALL_TOOL_SETS.stream()
            .map(ToolSet::getItemGroup)
            .flatMap(Collection::stream)
            .forEach(item -> simpleItem(() -> item));*/


        Set<Block> ignoreList = ImmutableSet.of(
            InitFluids.CANOLA_OIL.getBlock(),
            InitFluids.REFINED_CANOLA_OIL.getBlock(),
            InitFluids.CRYSTALIZED_OIL.getBlock(),
            InitFluids.EMPOWERED_OIL.getBlock(),
            ActuallyBlocks.CANOLA.get(),
            ActuallyBlocks.RICE.get(),
            ActuallyBlocks.FLAX.get(),
            ActuallyBlocks.COFFEE.get()
        );

        // Blocks
        ActuallyBlocks.BLOCKS.getEntries().stream().filter(b -> !ignoreList.contains(b.get())).forEach(this::registerBlockModel);

        generateBucket(InitFluids.CANOLA_OIL);
        generateBucket(InitFluids.REFINED_CANOLA_OIL);
        generateBucket(InitFluids.CRYSTALIZED_OIL);
        generateBucket(InitFluids.EMPOWERED_OIL);
    }

    private void generateBucket(FluidAA fluidSupplier) {
        withExistingParent(fluidSupplier.getBucket().getRegistryName().getPath(), "forge:item/bucket")
            .customLoader((builder, template) -> DynamicBucketModelBuilder.begin(builder, template).fluid(fluidSupplier.get()));
    }

    private void registerBlockModel(RegistryObject<Block> block) {
        String path = block.get().getRegistryName().getPath();
        if (block.get() instanceof WallBlock) {
            String name = path;
            path = "block/" + path.replace("_wall", "_block");
            withExistingParent(name, new ResourceLocation("block/wall_inventory"))
                .texture("wall", modLoc(path));
            return;
        }
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
    }

    private void simpleItem(Supplier<Item> item) {
        String path = item.get().getRegistryName().getPath();
        singleTexture(path, mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
    }
}
