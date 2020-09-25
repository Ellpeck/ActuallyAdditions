package de.ellpeck.actuallyadditions.common.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BallOfFurRecipeFactory extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BallOfFurRecipe> {
    
    public static final BallOfFurRecipeFactory INSTANCE = IRecipeSerializer.register("actuallyadditions:ball_of_fur", new BallOfFurRecipeFactory());
    
    @Nonnull
    @Override
    public BallOfFurRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json){
        ItemStack stack;
        int chance;
        
        if(json.has("stack")){
            JsonElement stackJsonElement = json.get("stack");
            if(stackJsonElement.isJsonObject()){
                stack = ShapedRecipe.deserializeItem(stackJsonElement.getAsJsonObject());
            } else {
                throw new JsonParseException("BallOfFurRecipe stack has to be a json object!");
            }
        } else {
            throw new JsonParseException("BallOfFurRecipe has no json stack object!");
        }
        
        if(json.has("chance")){
            JsonElement chanceJsonElement = json.get("chance");
            if(chanceJsonElement.isJsonPrimitive()){
                chance = chanceJsonElement.getAsInt();
            } else {
                throw new JsonParseException("BallOfFurRecipe chance is not a json integer!");
            }
        } else {
            throw new JsonParseException("BallOfFurRecipe has no chance integer!");
        }
        
        return new BallOfFurRecipe(recipeId, stack, chance);
    }
    
    @Nullable
    @Override
    public BallOfFurRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer){
        ItemStack stack = buffer.readItemStack();
        int chance = buffer.readInt();
        return new BallOfFurRecipe(recipeId, stack, chance);
    }
    
    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull BallOfFurRecipe recipe){
        buffer.writeItemStack(recipe.getReturnItem());
        buffer.writeInt(recipe.getChance());
    }
    
}
