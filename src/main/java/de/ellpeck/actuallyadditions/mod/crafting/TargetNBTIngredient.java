package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Stream;

public class TargetNBTIngredient extends Ingredient {
    public static final Codec<TargetNBTIngredient> CODEC = Ingredient.VANILLA_CODEC.xmap(TargetNBTIngredient::new, TargetNBTIngredient::new); //Wrapped in value sub-object
    /*    public static final Codec<TargetNBTIngredient> CODEC =
                RecordCodecBuilder.create(builder -> builder.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf( "item").forGetter(TargetNBTIngredient::getItem)
                ).apply(builder, TargetNBTIngredient::new));*/
    public TargetNBTIngredient(Stream<? extends Value> itemLists) {
        super(itemLists, ActuallyRecipes.Ingredients.TARGET_NBT);
    }
    public TargetNBTIngredient(Ingredient ingredient) {
        super(Arrays.stream(ingredient.values), ActuallyRecipes.Ingredients.TARGET_NBT);
    }

    @Override
    public boolean test(@Nullable ItemStack pStack) {
        return super.test(pStack);
    }

    public static TargetNBTIngredient of(ItemLike itemProvider) {
        return new TargetNBTIngredient(Stream.of(new ItemValue(new ItemStack(itemProvider))));
    }
    public static TargetNBTIngredient of(ItemStack itemStack) {
        return new TargetNBTIngredient(Stream.of(new ItemValue(itemStack)));
    }
    public static TargetNBTIngredient of(TagKey tag) {
        return new TargetNBTIngredient(Stream.of(new TagValue(tag)));
    }
}