package de.ellpeck.actuallyadditions.common.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class RecipeFactoryBase<T extends IDummyRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
    
    protected Ingredient readIngredient(JsonObject json, String key){
        if(json.has(key)){
            return Ingredient.deserialize(json.get(key));
        } else {
            throw new JsonSyntaxException(String.format("Json element does not contain any element with the key: '%s'", key));
        }
    }
    
    protected Ingredient readIngredient(JsonObject json, String key, Ingredient alternative){
        try{
            return this.readIngredient(json, key);
        } catch(JsonSyntaxException e){
            return alternative;
        }
    }
    
    protected ItemStack readItemStack(JsonObject json, String key){
        if(json.has(key)){
            if(json.get(key).isJsonObject()){
                return ShapedRecipe.deserializeItem(json.get(key).getAsJsonObject());
            } else if(json.get(key).isJsonPrimitive()){
                ResourceLocation itemKey = new ResourceLocation(json.get(key).getAsString());
                if(ForgeRegistries.ITEMS.containsKey(itemKey)){
                    return new ItemStack(ForgeRegistries.ITEMS.getValue(itemKey));
                } else {
                    throw new JsonSyntaxException(String.format("Item with the key: '%s' is not registered!", key));
                }
            } else {
                throw new JsonSyntaxException(String.format("Json element with the key: '%s' is neither a object nor a string!", key));
            }
        } else {
            throw new JsonSyntaxException(String.format("Json element does not contain any element with the key: '%s'!", key));
        }
    }
    
    protected ItemStack readItemStack(JsonObject json, String key, ItemStack alternative){
        try{
            return this.readItemStack(json, key);
        } catch(JsonSyntaxException e){
            return alternative;
        }
    }
    
    protected int readInt(JsonObject json, String key){
        if(json.has(key)){
            if(json.get(key).isJsonPrimitive()){
                try{
                    return json.get(key).getAsJsonPrimitive().getAsInt();
                } catch(NumberFormatException e){
                    throw new JsonSyntaxException(String.format("Json element '%s' is not a valid integer!", key), e);
                }
            } else {
                throw new JsonSyntaxException(String.format("Json element '%s' is not a valid integer!", key));
            }
        } else {
            throw new JsonSyntaxException(String.format("Json element '%s' does not exist!", key));
        }
    }
    
    protected int readInt(JsonObject json, String key, int alternative){
        try{
            return this.readInt(json, key);
        } catch(Exception e){
            return alternative;
        }
    }
    
}