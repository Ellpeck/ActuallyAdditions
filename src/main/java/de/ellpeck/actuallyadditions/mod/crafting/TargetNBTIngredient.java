package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public class TargetNBTIngredient extends Ingredient {
    public TargetNBTIngredient(Stream<? extends IItemList> itemLists) {
        super(itemLists);
    }

    @Override
    @Nonnull
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return SERIALIZER;
    }

    public static TargetNBTIngredient of(IItemProvider itemProvider) {
        return new TargetNBTIngredient(Stream.of(new SingleItemList(new ItemStack(itemProvider))));
    }
    public static TargetNBTIngredient of(ItemStack itemStack) {
        return new TargetNBTIngredient(Stream.of(new SingleItemList(itemStack)));
    }
    @Nonnull
    public static TargetNBTIngredient of(@Nonnull ITag tag) {
        return new TargetNBTIngredient(Stream.of(new TagList(tag)));
    }



    @Override
    @Nonnull
    public JsonElement toJson() {
        JsonObject tmp = super.toJson().getAsJsonObject();
        tmp.addProperty("type", Serializer.NAME.toString());
        return tmp;
    }


    public static Serializer SERIALIZER = new Serializer();
    public static class Serializer implements IIngredientSerializer<TargetNBTIngredient> {
        public static ResourceLocation NAME = new ResourceLocation(ActuallyAdditions.MODID, "nbt_target");

        @Override
        @Nonnull
        public TargetNBTIngredient parse(PacketBuffer buffer) {
            return new TargetNBTIngredient(Stream.generate(() -> new SingleItemList(buffer.readItem())).limit(buffer.readVarInt()));
        }

        @Override
        @Nonnull
        public TargetNBTIngredient parse(@Nonnull JsonObject json) {
            return new TargetNBTIngredient(Stream.of(Ingredient.valueFromJson(json)));
        }

        @Override
        public void write(PacketBuffer buffer, TargetNBTIngredient ingredient) {
            ItemStack[] items = ingredient.getItems();
            buffer.writeVarInt(items.length);

            for (ItemStack stack : items)
                buffer.writeItem(stack);
        }
    }
}
