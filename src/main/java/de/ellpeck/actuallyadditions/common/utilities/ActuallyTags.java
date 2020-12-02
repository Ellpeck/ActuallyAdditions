package de.ellpeck.actuallyadditions.common.utilities;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public final class ActuallyTags {
    public static class Items {
        public static final ITag.INamedTag<Item> DRILLS = tag("drills");

        private static ITag.INamedTag<Item> tag(String name) {
            return ItemTags.makeWrapperTag(String.format("%s:%s", ActuallyAdditions.MOD_ID, name));
        }
    }
}
