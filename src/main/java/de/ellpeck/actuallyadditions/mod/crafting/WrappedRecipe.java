package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class WrappedRecipe implements IFinishedRecipe {
    IFinishedRecipe inner;
    IRecipeSerializer<?> serializerOverride;

    public WrappedRecipe(IFinishedRecipe innerIn) {
        inner = innerIn;
    }

    public WrappedRecipe(IFinishedRecipe innerIn, IRecipeSerializer<?> serializerOverrideIn) {
        inner = innerIn;
        serializerOverride = serializerOverrideIn;
    }

    public static Consumer<IFinishedRecipe> Inject(Consumer<IFinishedRecipe> consumer, IRecipeSerializer<?> serializer) {
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
    public IRecipeSerializer<?> getType () {
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
