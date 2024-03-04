package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NoAdvRecipeOutput implements RecipeOutput {
    private final RecipeOutput inner;
    public NoAdvRecipeOutput(RecipeOutput output) {
        inner = output;
    }

    @Nonnull
    @Override
    public Advancement.Builder advancement() {
        return inner.advancement();
    }

    @Override
    public void accept(@Nonnull ResourceLocation resourceLocation, @Nonnull Recipe<?> recipe, @Nullable AdvancementHolder advancementHolder, @Nonnull ICondition... iConditions) {
        inner.accept(resourceLocation, recipe, null, iConditions);
    }
}
