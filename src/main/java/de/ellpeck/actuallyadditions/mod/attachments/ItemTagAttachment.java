package de.ellpeck.actuallyadditions.mod.attachments;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ItemTagAttachment implements INBTSerializable<CompoundTag> {
    private TagKey<Item> tag;


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        if (tag != null) {
            nbt.putString("tag", tag.location().toString());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(@Nonnull CompoundTag nbt) {
        if (nbt.contains("tag")) {
            tag = TagKey.create(Registries.ITEM, new ResourceLocation(nbt.getString("tag")));
        }
    }

    public Optional<TagKey<Item>> getTag() {
        return Optional.ofNullable(tag);
    }

    public void setTag(@Nullable TagKey<Item> tag) {
        this.tag = tag;
    }
}
