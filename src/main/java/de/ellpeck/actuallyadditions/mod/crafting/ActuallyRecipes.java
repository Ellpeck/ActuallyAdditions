package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ActuallyRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ActuallyAdditions.MODID);

    public static void init(IEventBus bus) {
        RECIPE_TYPES.register(bus);
    }

    public static final RegistryObject<IRecipeSerializer<?>> NBT_COPY_RECIPE = RECIPE_TYPES.register(CopyNBTRecipeShaped.NAME, CopyNBTRecipeShaped.Serializer::new);
}
