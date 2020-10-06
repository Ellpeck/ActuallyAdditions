package de.ellpeck.actuallyadditions.common.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;

public class CrusherRecipeFactory extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrusherRecipe> {
    
    public static final CrusherRecipeFactory INSTANCE = IRecipeSerializer.register("actuallyadditions:crusher", new CrusherRecipeFactory());
    
    @Nonnull
    @Override
    public CrusherRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json){
        Ingredient input;
        ItemStack firstOutput;
        ItemStack secondOutput = ItemStack.EMPTY;
        int secondaryOutputChance = 0;
        
        if(json.has("input")){
            input = Ingredient.deserialize(json.get("input"));
        } else {
            throw new JsonSyntaxException("Input is not given for the recipe!");
        }
        
        if(json.has("output")){
            JsonElement outputElement = json.get("output");
            if(outputElement.isJsonObject()){
                firstOutput = ShapedRecipe.deserializeItem(outputElement.getAsJsonObject());
            } else {
                throw new JsonSyntaxException("Output is not valid!");
            }
        } else {
            throw new JsonSyntaxException("Output is not given for the recipe!");
        }
        
        if(json.has("secondary")){ // Optional
            JsonElement secondaryElement = json.get("secondary");
            if(secondaryElement.isJsonObject()){
                JsonObject secondary = secondaryElement.getAsJsonObject();
                if(secondary.has("output")){
                    JsonElement outputElement = json.get("output");
                    if(outputElement.isJsonObject()){
                        secondOutput = ShapedRecipe.deserializeItem(outputElement.getAsJsonObject());
                    } else {
                        throw new JsonSyntaxException("Secondary output is not valid!");
                    }
                } else {
                    throw new JsonSyntaxException("Secondary output is not given for the recipe!");
                }
                if(secondary.has("chance")){
                    JsonElement chanceElement = secondary.get("chance");
                    if(chanceElement.isJsonPrimitive()){
                        secondaryOutputChance = chanceElement.getAsInt();
                    } else {
                        throw new JsonSyntaxException("Secondary chance is not valid!");
                    }
                } else {
                    throw new JsonSyntaxException("Secondary chance is not given for the recipe!");
                }
            } else {
                throw new JsonSyntaxException("Secondary is not valid!");
            }
        }
        
        return new CrusherRecipe(recipeId, input, firstOutput, secondOutput, secondaryOutputChance);
    }
    
    @Nonnull
    @Override
    public CrusherRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer){
        Ingredient input = Ingredient.read(buffer);
        ItemStack output = buffer.readItemStack();
        ItemStack secondaryOutput = buffer.readItemStack();
        int chance = buffer.readVarInt();
        
        return new CrusherRecipe(recipeId, input, output, secondaryOutput, chance);
    }
    
    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull CrusherRecipe recipe){
        recipe.getInput().write(buffer);
        buffer.writeItemStack(recipe.getOutput());
        buffer.writeItemStack(recipe.getSecondaryOutput());
        buffer.writeVarInt(recipe.getOutputChance());
    }
}
