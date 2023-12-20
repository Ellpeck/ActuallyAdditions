package de.ellpeck.actuallyadditions.api;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public final class ActuallyTags {
    public static final void init() {
        // lol
        Items.touch();
    }
    public static class Items {
        public static void touch() {
            // load the stupid tags
        }
        public static final ITag.INamedTag<Item> DRILLS = tag("drills");
        public static final ITag.INamedTag<Item> COFFEE_BEANS = tag("coffee_beans");
        public static final ITag.INamedTag<Item> TINY_COALS = tag("tiny_coals");
        public static final ITag.INamedTag<Item> HOLDS_ITEMS = ItemTags.createOptional(new ResourceLocation("forge", "holds_items"));
        public static final ITag.INamedTag<Item> CRYSTALS = tag("crystals");

        private static ITag.INamedTag<Item> tag(String name) {
            return ItemTags.bind(String.format("%s:%s", ActuallyAdditions.MODID, name));
        }
    }
}
