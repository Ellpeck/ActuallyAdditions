package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class WrappedRecipe implements FinishedRecipe {
    FinishedRecipe inner;
    RecipeSerializer<?> serializerOverride;

    public WrappedRecipe(FinishedRecipe innerIn) {
        inner = innerIn;
    }

    public WrappedRecipe(FinishedRecipe innerIn, RecipeSerializer<?> serializerOverrideIn) {
        inner = innerIn;
        serializerOverride = serializerOverrideIn;
    }

    public static Consumer<FinishedRecipe> Inject(Consumer<FinishedRecipe> consumer, RecipeSerializer<?> serializer) {
        return iFinishedRecipe -> consumer.accept(new WrappedRecipe(iFinishedRecipe, serializer));
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        inner.serializeRecipeData(json);
    }

    @Override
    public JsonObject serializeRecipe() {
        JsonObject jsonObject = new JsonObject();

        if (serializerOverride != null)
            jsonObject.addProperty("type", serializerOverride.getRegistryName().toString());
        else
            jsonObject.addProperty("type", inner.getType().getRegistryName().toString());
        serializeRecipeData(jsonObject);
        return jsonObject;
    }

    @Override
    public ResourceLocation getId() {
        return inner.getId();
    }

    @Override
    public RecipeSerializer<?> getType () {
        return serializerOverride != null? serializerOverride:inner.getType();
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return inner.serializeAdvancement();
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return inner.getAdvancementId();
    }
}
