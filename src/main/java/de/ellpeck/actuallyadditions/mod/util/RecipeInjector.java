package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;
@SuppressWarnings("unchecked")
public class RecipeInjector<T extends Recipe<?>> implements RecipeOutput {
    private final RecipeOutput inner;
    private final Function<T, ? extends T> constructor;
    public RecipeInjector(RecipeOutput output, Function<T, ? extends T> constructorIn) {
        inner = output;
        this.constructor = constructorIn;
    }

    @Nonnull
    @Override
    public Advancement.Builder advancement() {
        return inner.advancement();
    }

    @Override
    public void accept(@Nonnull ResourceLocation resourceLocation, @Nonnull Recipe<?> recipe, @Nullable AdvancementHolder advancementHolder, @Nonnull ICondition... iConditions) {
        inner.accept(resourceLocation, constructor.apply((T) recipe), advancementHolder, iConditions);
    }
}
