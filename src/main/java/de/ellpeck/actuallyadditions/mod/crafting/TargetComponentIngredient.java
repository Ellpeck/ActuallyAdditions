package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public record TargetComponentIngredient(Ingredient ingredient) implements ICustomIngredient {
	public static final MapCodec<TargetComponentIngredient> CODEC = RecordCodecBuilder.mapCodec(
			builder -> builder
					.group(
							Ingredient.CODEC.fieldOf("base").forGetter(TargetComponentIngredient::ingredient)
					)
					.apply(builder, TargetComponentIngredient::new));

	@Nonnull
	@Override
	public Stream<ItemStack> getItems() {
		return Stream.of(ingredient.getItems());
	}

	@Override
	public boolean test(@Nonnull ItemStack stack) {
		return ingredient.test(stack);
	}

	@Override
	public boolean isSimple() {
		return ingredient.isSimple();
	}

	@Nonnull
	@Override
	public IngredientType<?> getType() {
		return ActuallyRecipes.Ingredients.TARGET_NBT.get();
	}

    public static Ingredient of(ItemLike itemProvider) {
        return new TargetComponentIngredient(Ingredient.of(itemProvider)).toVanilla();
    }
    public static Ingredient of(ItemStack itemStack) {
        return new TargetComponentIngredient(Ingredient.of(itemStack)).toVanilla();
    }
    public static Ingredient of(@Nonnull TagKey<Item> tag) {
        return new TargetComponentIngredient(Ingredient.of(tag)).toVanilla();
    }
}