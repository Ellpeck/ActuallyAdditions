package de.ellpeck.actuallyadditions.common.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class CompostRecipeFactory extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CompostRecipe> {
    
    public static final CompostRecipeFactory INSTANCE = IRecipeSerializer.register("actuallyadditions:compost", new CompostRecipeFactory());
    
    @Override
    public CompostRecipe read(ResourceLocation recipeId, JsonObject json){
        Ingredient input;
        ItemStack output;
        BlockState inputDisplay;
        BlockState outputDisplay;
        
        if(json.has("input")){
            input = Ingredient.deserialize(json.get("input"));
        } else {
            throw new JsonParseException("CompostRecipe needs a input ingredient json element!");
        }
        
        if(json.has("output")){
            JsonElement outputJsonElement = json.get("output");
            if(outputJsonElement.isJsonObject()){
                output = ShapedRecipe.deserializeItem(outputJsonElement.getAsJsonObject());
            } else {
                throw new JsonParseException("CompostRecipe output element is not a json object!");
            }
        } else {
            throw new JsonParseException("CompostRecipe needs a output itemstack json object!");
        }
        
        if(json.has("display")){
            JsonElement displayJsonElement = json.get("display");
            if(displayJsonElement.isJsonObject()){
                JsonObject displayJsonObject = displayJsonElement.getAsJsonObject();
                if(displayJsonObject.has("input")){
                    inputDisplay = BlockState.deserialize(new Dynamic<>(JsonOps.INSTANCE, displayJsonObject.get("input")));
                } else {
                    throw new JsonParseException("CompostRecipe display needs an input json element!");
                }
    
                if(displayJsonObject.has("output")){
                    outputDisplay = BlockState.deserialize(new Dynamic<>(JsonOps.INSTANCE, displayJsonObject.get("output")));
                } else {
                    throw new JsonParseException("CompostRecipe display needs an output json element!");
                }
            } else {
                throw new JsonParseException("CompostRecipe display has to be a json object!");
            }
        } else {
            // no exception but default values
            inputDisplay = Blocks.DIRT.getDefaultState();
            outputDisplay = Blocks.COARSE_DIRT.getDefaultState();
        }
    
        return new CompostRecipe(recipeId, input, output, inputDisplay, outputDisplay);
    }
    
    @Nullable
    @Override
    public CompostRecipe read(ResourceLocation recipeId, PacketBuffer buffer){
        Ingredient input = Ingredient.read(buffer);
        ItemStack output = buffer.readItemStack();
        CompoundNBT inputNBT = buffer.readCompoundTag();
        CompoundNBT outputNBT = buffer.readCompoundTag();
    
        BlockState inputDisplay = BlockState.deserialize(new Dynamic<>(NBTDynamicOps.INSTANCE, inputNBT));
        BlockState outputDisplay = BlockState.deserialize(new Dynamic<>(NBTDynamicOps.INSTANCE, outputNBT));
        
        return new CompostRecipe(recipeId, input, output, inputDisplay, outputDisplay);
    }
    
    @Override
    public void write(PacketBuffer buffer, CompostRecipe recipe){
        recipe.getInput().write(buffer);
        buffer.writeItemStack(recipe.getOutput());
    
        INBT inputNBT = BlockState.serialize(NBTDynamicOps.INSTANCE, recipe.getInputDisplay()).getValue();
        buffer.writeCompoundTag((CompoundNBT) inputNBT); // if it isn't a compound than something real big is wrong and a crash should be the best way to handle it
    
        INBT outputNBT = BlockState.serialize(NBTDynamicOps.INSTANCE, recipe.getOutputDisplay()).getValue();
        buffer.writeCompoundTag((CompoundNBT) outputNBT);
    }
}
