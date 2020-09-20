package de.ellpeck.actuallyadditions.common.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.lens.Lenses;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AtomicReconstructorItemRecipeFactory extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AtomicReconstructorItemRecipe> {
    
    public static final AtomicReconstructorItemRecipeFactory INSTANCE = IRecipeSerializer.register("actuallyadditions:atomic_reconstructor_item", new AtomicReconstructorItemRecipeFactory());
    
    @Nonnull
    @Override
    public AtomicReconstructorItemRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json){
        Lens lens = Lenses.LENS_CONVERSION.get();
        Ingredient input;
        ItemStack output;
        int energyConsumption = 1000; // default atomic reconstructor energy usage is 1000
        
        if(json.has("lens") && json.get("lens").isJsonObject()){
            ResourceLocation lensId = new ResourceLocation(json.get("lens").getAsString());
            if(ActuallyAdditionsAPI.LENS_REGISTRY.containsKey(lensId)){
                lens = ActuallyAdditionsAPI.LENS_REGISTRY.getValue(lensId);
            } else {
                throw new JsonSyntaxException(String.format("Lens type is given, but no lens could be found with id '%s'!", lensId));
            }
        }
        
        if(json.has("input")){
            input = Ingredient.deserialize(json.get("input"));
        } else {
            throw new JsonSyntaxException("Input is not given for the recipe!");
        }
    
        if(json.has("output") && json.get("output").isJsonObject()){
            output = ShapedRecipe.deserializeItem(json.get("output").getAsJsonObject());
        } else {
            throw new JsonSyntaxException("Output is not given for the recipe!");
        }
        
        if(json.has("energy") && json.get("energy").isJsonPrimitive()){
            energyConsumption = json.get("energy").getAsInt();
        }
        
        return new AtomicReconstructorItemRecipe(recipeId, lens, input, output, energyConsumption);
    }
    
    @Nullable
    @Override
    public AtomicReconstructorItemRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer){
        ResourceLocation lensId = buffer.readResourceLocation();
        Ingredient input = Ingredient.read(buffer);
        ItemStack output = buffer.readItemStack();
        int energyConsumption = buffer.readVarInt();
        
        Lens lens;
        if(ActuallyAdditionsAPI.LENS_REGISTRY.containsKey(lensId)){
            lens = ActuallyAdditionsAPI.LENS_REGISTRY.getValue(lensId);
        } else {
            System.out.println(String.format("Lens is not possible to get while reading packet! '%s'", lensId));
            return null;
        }
        return new AtomicReconstructorItemRecipe(recipeId, lens, input, output, energyConsumption);
    }
    
    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull AtomicReconstructorItemRecipe recipe){
        buffer.writeResourceLocation(recipe.getId());
        recipe.getInput().write(buffer);
        buffer.writeItemStack(recipe.getOutput());
        buffer.writeVarInt(recipe.getEnergyConsumption());
    }
    
}
