package de.ellpeck.actuallyadditions.api;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public final class ActuallyTags {
    public static class Items {
        public static final ITag.INamedTag<Item> DRILLS = tag("drills");
        public static final ITag.INamedTag<Item> COFFEE_BEANS = tag("coffee_beans");
        public static final ITag.INamedTag<Item> TINY_COALS = tag("tiny_coals");

        private static ITag.INamedTag<Item> tag(String name) {
            return ItemTags.makeWrapperTag(String.format("%s:%s", ActuallyAdditions.MODID, name));
        }
    }
}
