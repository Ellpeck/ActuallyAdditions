package de.ellpeck.actuallyadditions.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.lens.Lenses;
import net.minecraft.block.BlockState;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AtomicReconstructorBlockRecipeFactory extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AtomicReconstructorBlockRecipe> {
    
    public static final AtomicReconstructorBlockRecipeFactory INSTANCE = IRecipeSerializer.register("actuallyadditions:atomic_reconstructor_block", new AtomicReconstructorBlockRecipeFactory());
    
    @Nonnull
    @Override
    public AtomicReconstructorBlockRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json){
        Lens lens = Lenses.LENS_CONVERSION.get();
        BlockState input;
        BlockState output;
        int energyConsumption = 1000; // default atomic reconstructor energy usage is 1000
        
        if(json.has("lens") && json.get("lens").isJsonObject()){
            ResourceLocation lensId = new ResourceLocation(json.get("lens").getAsString());
            if(ActuallyAdditions.LENS_REGISTRY.containsKey(lensId)){
                lens = ActuallyAdditions.LENS_REGISTRY.getValue(lensId);
            } else {
                throw new JsonSyntaxException(String.format("Lens type is given, but no lens could be found with id '%s'!", lensId));
            }
        }
        
        if(json.has("input")){
            input = BlockState.deserialize(new Dynamic<>(JsonOps.INSTANCE, json.get("input")));
        } else {
            throw new JsonSyntaxException("Input is not given for the recipe!");
        }
    
        if(json.has("output")){
            output = BlockState.deserialize(new Dynamic<>(JsonOps.INSTANCE, json.get("input")));
        } else {
            throw new JsonSyntaxException("Output is not given for the recipe!");
        }
        
        if(json.has("energy") && json.get("energy").isJsonPrimitive()){
            energyConsumption = json.get("energy").getAsInt();
        }
        
        return new AtomicReconstructorBlockRecipe(recipeId, lens, input, output, energyConsumption);
    }
    
    @Nullable
    @Override
    public AtomicReconstructorBlockRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer){
        ResourceLocation lensId = buffer.readResourceLocation();
        CompoundNBT inputNBT = buffer.readCompoundTag();
        CompoundNBT outputNBT = buffer.readCompoundTag();
        int energyConsumption = buffer.readVarInt();
    
        Lens lens;
        if(ActuallyAdditions.LENS_REGISTRY.containsKey(lensId)){
            lens = ActuallyAdditions.LENS_REGISTRY.getValue(lensId);
        } else {
            System.out.println(String.format("Lens is not possible to get while reading packet! '%s'", lensId));
            return null;
        }
    
        BlockState input = BlockState.deserialize(new Dynamic<>(NBTDynamicOps.INSTANCE, inputNBT));
        BlockState output = BlockState.deserialize(new Dynamic<>(NBTDynamicOps.INSTANCE, outputNBT));
    
        return new AtomicReconstructorBlockRecipe(recipeId, lens, input, output, energyConsumption);
    }
    
    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull AtomicReconstructorBlockRecipe recipe){
        buffer.writeResourceLocation(recipe.getId());
        
        INBT inputNBT = BlockState.serialize(NBTDynamicOps.INSTANCE, recipe.getInput()).getValue();
        buffer.writeCompoundTag((CompoundNBT) inputNBT); // if it isn't a compound than something real big is wrong and a crash should be the best way to handle it
    
        INBT outputNBT = BlockState.serialize(NBTDynamicOps.INSTANCE, recipe.getOutput()).getValue();
        buffer.writeCompoundTag((CompoundNBT) outputNBT);
    
        buffer.writeVarInt(recipe.getEnergyConsumption());
    }
    
}
