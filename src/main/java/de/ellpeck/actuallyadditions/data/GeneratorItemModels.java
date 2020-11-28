package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import de.ellpeck.actuallyadditions.common.items.ToolSet;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.Collection;
import java.util.function.Supplier;

public class GeneratorItemModels extends ItemModelProvider {
    public GeneratorItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ActuallyAdditions.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        simpleItem(ActuallyItems.BOOKLET); // will require complex I think

        // All items?
        ActuallyItems.SIMPLE_ITEMS.forEach(this::simpleItem);

        // Toolsets
        ActuallyItems.ALL_TOOL_SETS.stream()
                .map(ToolSet::getItemGroup)
                .flatMap(Collection::stream)
                .forEach(item -> simpleItem(() -> item));


        // Blocks
        ActuallyBlocks.BLOCKS.getEntries().forEach(this::registerBlockModel);
    }

    private void registerBlockModel(RegistryObject<Block> block) {
        String path = block.get().getRegistryName().getPath();
        if (block.get() instanceof WallBlock) {
            String name = path;
            path = "block/" + path.replace("wall_", "");
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

    @Override
    public String getName() {
        return "Item Models";
    }
}
