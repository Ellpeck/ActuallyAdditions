package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.DynamicBucketModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

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


        // Blocks
        ActuallyBlocks.BLOCKS.getEntries().stream().filter(b -> !b.get().getRegistryName().getPath().contains("oil")).forEach(this::registerBlockModel);

        withExistingParent(InitFluids.CANOLA_OIL.getBucket().getRegistryName().getPath(), "forge:item/bucket")
            .customLoader((builder, template) -> DynamicBucketModelBuilder.begin(builder, template).fluid(InitFluids.CANOLA_OIL.get()));
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
