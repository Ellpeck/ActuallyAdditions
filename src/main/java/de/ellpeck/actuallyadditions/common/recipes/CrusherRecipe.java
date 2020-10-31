package de.ellpeck.actuallyadditions.common.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class CrusherRecipe implements IDummyRecipe<CrusherRecipe> {
    
    public static final IRecipeType<CrusherRecipe> RECIPE_TYPE = IRecipeType.register("actuallyadditions:crusher");
    public static final CrusherRecipeFactory FACTORY = IRecipeSerializer.register("actuallyadditions:crusher", new CrusherRecipeFactory());
    
    @Nonnull
    private final ResourceLocation recipeId;
    
    @Nonnull
    private final Ingredient input;
    @Nonnull
    private final ItemStack output;
    @Nonnull
    private final ItemStack secondaryOutput;
    private final int outputChance;
    
    public CrusherRecipe(@Nonnull ResourceLocation recipeId, @Nonnull Ingredient input, @Nonnull ItemStack output, @Nonnull ItemStack secondaryOutput, int outputChance){
        this.recipeId = recipeId;
        this.input = input;
        this.output = output;
        this.secondaryOutput = secondaryOutput;
        this.outputChance = outputChance;
    }
    
    public CrusherRecipe(@Nonnull ResourceLocation recipeId, @Nonnull Ingredient input, @Nonnull ItemStack output){
        this(recipeId, input, output, ItemStack.EMPTY, 0);
    }
    
    @Nonnull
    public Ingredient getInput(){
        return input;
    }
    
    @Nonnull
    public ItemStack getOutput(){
        return output;
    }
    
    @Nonnull
    public ItemStack getSecondaryOutput(){
        return secondaryOutput;
    }
    
    public int getOutputChance(){
        return outputChance;
    }
    
    @Nonnull
    @Override
    public ResourceLocation getId(){
        return this.recipeId;
    }
    
    @Nonnull
    @Override
    public IRecipeSerializer<CrusherRecipe> getSerializer(){
        return FACTORY;
    }
    
    @Nonnull
    @Override
    public IRecipeType<?> getType(){
        return RECIPE_TYPE;
    }
    
    static class CrusherRecipeFactory extends RecipeFactoryBase<CrusherRecipe> {
        
        @Nonnull
        @Override
        public CrusherRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json){
            Ingredient input = this.readIngredient(json, "input");
            ItemStack firstOutput = this.readItemStack(json, "output");
            ItemStack secondOutput = ItemStack.EMPTY;
            int secondaryOutputChance = 0;
            
            if(json.has("secondary")){ // Optional
                JsonElement secondaryElement = json.get("secondary");
                if(secondaryElement.isJsonObject()){
                    JsonObject secondary = secondaryElement.getAsJsonObject();
                    secondOutput = this.readItemStack(secondary, "output", ItemStack.EMPTY);
                    secondaryOutputChance = this.readInt(secondary, "chance", 0);
                } else {
                    throw new JsonSyntaxException("Secondary is not valid!");
                }
            }
    
            return new CrusherRecipe(recipeId, input, firstOutput, secondOutput, secondaryOutputChance);
        }
    
        @Override
        public void write(@Nonnull JsonObject json, @Nonnull CrusherRecipe recipe){
            json.add("input", recipe.getInput().serialize());
            json.add("output", this.writeItemStack(recipe.getOutput()));
            if(!recipe.getSecondaryOutput().isEmpty() && recipe.getOutputChance() > 0){
                JsonObject secondary = new JsonObject();
                secondary.add("output", this.writeItemStack(recipe.getSecondaryOutput()));
                secondary.addProperty("chance", recipe.getOutputChance());
                json.add("secondary", secondary);
            }
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
    
}
