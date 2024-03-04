//package de.ellpeck.actuallyadditions.mod.crafting;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.tags.TagKey;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.level.ItemLike;
//import net.neoforged.neoforge.common.crafting.IIngredientSerializer;
//
//import javax.annotation.Nonnull;
//import java.util.stream.Stream;
//
//public class TargetNBTIngredient extends Ingredient { TODO: FLANKS PLEASE :D
//    public TargetNBTIngredient(Stream<? extends Value> itemLists) {
//        super(itemLists);
//    }
//
//    @Override
//    @Nonnull
//    public IIngredientSerializer<? extends Ingredient> getSerializer() {
//        return SERIALIZER;
//    }
//
//    public static TargetNBTIngredient of(ItemLike itemProvider) {
//        return new TargetNBTIngredient(Stream.of(new ItemValue(new ItemStack(itemProvider))));
//    }
//    public static TargetNBTIngredient of(ItemStack itemStack) {
//        return new TargetNBTIngredient(Stream.of(new ItemValue(itemStack)));
//    }
//    @Nonnull
//    public static TargetNBTIngredient of(@Nonnull TagKey tag) {
//        return new TargetNBTIngredient(Stream.of(new TagValue(tag)));
//    }
//
//
//
//    @Override
//    @Nonnull
//    public JsonElement toJson() {
//        JsonObject tmp = super.toJson().getAsJsonObject();
//        tmp.addProperty("type", Serializer.NAME.toString());
//        return tmp;
//    }
//
//
//    public static Serializer SERIALIZER = new Serializer();
//    public static class Serializer implements IIngredientSerializer<TargetNBTIngredient> {
//        public static ResourceLocation NAME = new ResourceLocation(ActuallyAdditions.MODID, "nbt_target");
//
//        @Override
//        @Nonnull
//        public TargetNBTIngredient parse(FriendlyByteBuf buffer) {
//            return new TargetNBTIngredient(Stream.generate(() -> new ItemValue(buffer.readItem())).limit(buffer.readVarInt()));
//        }
//
//        @Override
//        @Nonnull
//        public TargetNBTIngredient parse(@Nonnull JsonObject json) {
//            return new TargetNBTIngredient(Stream.of(Ingredient.valueFromJson(json)));
//        }
//
//        @Override
//        public void write(FriendlyByteBuf buffer, TargetNBTIngredient ingredient) {
//            ItemStack[] items = ingredient.getItems();
//            buffer.writeVarInt(items.length);
//
//            for (ItemStack stack : items)
//                buffer.writeItem(stack);
//        }
//    }
//}
