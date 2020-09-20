package de.ellpeck.actuallyadditions.common.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMachineIngredientFactory extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CoffeeMachineIngredient> {
    
    public static final CoffeeMachineIngredientFactory INSTANCE = IRecipeSerializer.register("actuallyadditions:coffee_machine", new CoffeeMachineIngredientFactory());
    
    @Override
    public CoffeeMachineIngredient read(ResourceLocation recipeId, JsonObject json){
        Ingredient input = null;
        EffectInstance[] effects = new EffectInstance[0];
        int maxAmplifier = 0;
        
        if(json.has("input")){
            input = Ingredient.deserialize(json.get("input"));
        }
        
        if(json.has("effects")){
            if(json.get("effects").isJsonArray()){
                List<EffectInstance> effectInstances = new ArrayList<>();
                for(JsonElement element : json.get("effects").getAsJsonArray()){
                    if(element.isJsonObject()){
                        try{
                            CompoundNBT tagFromJson = JsonToNBT.getTagFromJson(element.toString());
                            effectInstances.add(EffectInstance.read(tagFromJson));
                        } catch(CommandSyntaxException e){
                            throw new JsonSyntaxException("Can't deserialize Potion Effect from json for recipe!", e);
                        }
                    }
                }
                if(!effectInstances.isEmpty()){
                    effects = effectInstances.toArray(new EffectInstance[0]);
                } else {
                    throw new JsonSyntaxException("No Potion effects assigned for recipe!");
                }
            }
        }
        
        if(json.has("max_amplifier") && json.get("max_amplifier").isJsonPrimitive()){
            maxAmplifier = json.get("max_amplifier").getAsInt();
        }
        
        if(input != null && effects.length > 0){
            return new CoffeeMachineIngredient(recipeId, input, maxAmplifier, effects);
        } else {
            return null;
        }
    }
    
    @Nullable
    @Override
    public CoffeeMachineIngredient read(ResourceLocation recipeId, PacketBuffer buffer){
        Ingredient input = Ingredient.read(buffer);
        CompoundNBT nbt = buffer.readCompoundTag();
        int maxAmplifier = buffer.readVarInt();
    
        List<EffectInstance> effectsList = new ArrayList<>();
        ListNBT effectsNBTList = nbt.getList("effectsList", Constants.NBT.TAG_COMPOUND);
        for(INBT inbt : effectsNBTList){
            EffectInstance effect = EffectInstance.read((CompoundNBT) inbt);
            effectsList.add(effect);
        }
    
        return new CoffeeMachineIngredient(recipeId, input, maxAmplifier, effectsList.toArray(new EffectInstance[0]));
    }
    
    @Override
    public void write(PacketBuffer buffer, CoffeeMachineIngredient recipe){
        recipe.getInput().write(buffer);
        
        ListNBT effectsList = new ListNBT();
        for(EffectInstance effect : recipe.getEffects()){
            CompoundNBT effectTag = new CompoundNBT();
            effect.write(effectTag);
            effectsList.add(effectTag);
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("effectsList", effectsList);
        buffer.writeCompoundTag(nbt);
        
        buffer.writeVarInt(recipe.getMaxAmplifier());
    }
}
