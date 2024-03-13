package de.ellpeck.actuallyadditions.api;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

public final class ActuallyTags {
    public static final void init() {
        // lol
        Items.touch();
        Blocks.touch();
        BannerPatterns.touch();
    }
    public static class Items {
        public static void touch() {
            // load the stupid tags
        }
        public static final TagKey<Item> DRILLS = tag("drills");
        public static final TagKey<Item> COFFEE_BEANS = tag("coffee_beans");
        public static final TagKey<Item> TINY_COALS = tag("tiny_coals");
        public static final TagKey<Item> HOLDS_ITEMS = ItemTags.create(new ResourceLocation("forge", "holds_items"));
        public static final TagKey<Item> CRYSTALS = tag("crystals");
        public static final TagKey<Item> CRYSTAL_BLOCKS = tag("crystal_blocks");
        public static final TagKey<Item> SEEDS_RICE = ItemTags.create(new ResourceLocation("forge", "seeds/rice"));
        public static final TagKey<Item> SEEDS_COFFEE = ItemTags.create(new ResourceLocation("forge", "seeds/coffee"));
        public static final TagKey<Item> SEEDS_CANOLA = ItemTags.create(new ResourceLocation("forge", "seeds/canola"));
        public static final TagKey<Item> SEEDS_FLAX = ItemTags.create(new ResourceLocation("forge", "seeds/flax"));
        public static final TagKey<Item> CROPS_RICE = ItemTags.create(new ResourceLocation("forge", "crops/rice"));
        public static final TagKey<Item> CROPS_COFFEE = ItemTags.create(new ResourceLocation("forge", "crops/coffee"));
        public static final TagKey<Item> CROPS_CANOLA = ItemTags.create(new ResourceLocation("forge", "crops/canola"));
        public static final TagKey<Item> CROPS_FLAX = ItemTags.create(new ResourceLocation("forge", "crops/flax"));
        public static final TagKey<Item> GEMS_BLACK_QUARTZ = ItemTags.create(new ResourceLocation("forge", "gems/black_quartz"));
        public static final TagKey<Item> ORES_BLACK_QUARTZ = ItemTags.create(new ResourceLocation("forge", "ores/black_quartz"));
        public static final TagKey<Item> STORAGE_BLOCKS_BLACK_QUARTZ = ItemTags.create(new ResourceLocation("forge", "storage_blocks/black_quartz"));

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(ActuallyAdditions.MODID, name));
        }
    }

    public static class Blocks {
        public static void touch() {
            // load the stupid tags
        }

        public static final TagKey<Block> MINEABLE_WITH_DRILL = tag("mineable/drill");
        public static final TagKey<Block> MINEABLE_WITH_AIO = tag("mineable/aio");
        public static final TagKey<Block> NEEDS_BLACK_QUARTZ_TOOL = tag("needs_black_quartz_tool");
        public static final TagKey<Block> NEEDS_RESTONIA_TOOL = tag("needs_restonia_tool");
        public static final TagKey<Block> NEEDS_PALIS_TOOL = tag("needs_palis_tool");
        public static final TagKey<Block> NEEDS_DIAMATINE_TOOL = tag("needs_diamatine_tool");
        public static final TagKey<Block> NEEDS_VOID_TOOL = tag("needs_void_tool");
        public static final TagKey<Block> NEEDS_EMERADIC_TOOL = tag("needs_emeradic_tool");
        public static final TagKey<Block> NEEDS_ENORI_TOOL = tag("needs_enori_tool");
        public static final TagKey<Block> ORES_BLACK_QUARTZ = BlockTags.create(new ResourceLocation("forge", "ores/black_quartz"));
        public static final TagKey<Block> STORAGE_BLOCKS_BLACK_QUARTZ = BlockTags.create(new ResourceLocation("forge", "storage_blocks/black_quartz"));


        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(ActuallyAdditions.MODID, name));
        }
    }

    public static class BannerPatterns {
        public static void touch() {
            // load the stupid tags
        }

        public static final TagKey<BannerPattern> PATTERN_DRILL = tag("pattern_item/drill");
        public static final TagKey<BannerPattern> PATTERN_LEAF_BLO = tag("pattern_item/leaf_blower");
        public static final TagKey<BannerPattern> PATTERN_PHAN_CON = tag("pattern_item/phan_con");
        public static final TagKey<BannerPattern> PATTERN_BOOK = tag("pattern_item/book");

        private static TagKey<BannerPattern> tag(String name) {
            return TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(ActuallyAdditions.MODID, name));
        }
    }
}
