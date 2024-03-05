package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoffeeIngredientRecipe implements Recipe<Container> {
	public static final String NAME = "coffee_ingredient";

	protected final Ingredient ingredient;
	protected final NonNullList<EffectInstance> instances; //Just a record used to populate the effects list
	protected final List<MobEffectInstance> effects;
	protected final int maxAmplifier;
	protected final String extraText;

	public CoffeeIngredientRecipe(Ingredient ingredient, NonNullList<EffectInstance> effectInstances, int maxAmplifier, String extraText) {
		this.ingredient = ingredient;
		this.maxAmplifier = maxAmplifier;

		this.instances = effectInstances;
		List<MobEffectInstance> instances = new ArrayList<>();
		for (EffectInstance instance : effectInstances) {
			MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(instance.effect());
			if (effect == null) break;
			instances.add(new MobEffectInstance(effect, instance.duration, instance.amplifier));
		}
		this.effects = instances;
		this.extraText = extraText;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public List<MobEffectInstance> getEffects() {
		return effects;
	}

	public int getMaxAmplifier() {
		return maxAmplifier;
	}

	public String getExtraText() {
		return extraText;
	}

	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	public boolean matches(ItemStack itemStack) {
		return ingredient.test(itemStack);
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return getResultItem(registryAccess);
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return false;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ActuallyRecipes.COFFEE_INGREDIENT_RECIPE.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ActuallyRecipes.Types.COFFEE_INGREDIENT.get();
	}

	public boolean effect(ItemStack stack) {
		return ActuallyAdditionsAPI.methodHandler.addRecipeEffectToStack(stack, this);
	}

	public static Optional<RecipeHolder<CoffeeIngredientRecipe>> getIngredientForStack(ItemStack ingredient) {
		return ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS.stream().filter(recipe -> recipe.value().matches(ingredient)).findFirst();
	}

	public record EffectInstance(ResourceLocation effect, int duration, int amplifier) { //Simplified record for the effect instance
		public static final EffectInstance EMPTY = new EffectInstance(new ResourceLocation("darkness"), 0, 0);
		public static final Codec<EffectInstance> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ResourceLocation.CODEC.fieldOf("effect").forGetter(effect -> effect.effect),
								Codec.INT.fieldOf("duration").forGetter(effect -> effect.duration),
								Codec.INT.fieldOf("amplifier").forGetter(effect -> effect.amplifier)
						)
						.apply(instance, EffectInstance::new)
		);

		public EffectInstance(MobEffectInstance effect) {
			this(BuiltInRegistries.MOB_EFFECT.getKey(effect.getEffect()), effect.getDuration(), effect.getAmplifier());
		}

		public static EffectInstance fromNetwork(FriendlyByteBuf pBuffer) {
			ResourceLocation effect = pBuffer.readResourceLocation();
			int duration = pBuffer.readVarInt();
			int amplifier = pBuffer.readVarInt();
			return new EffectInstance(effect, duration, amplifier);
		}

		public void toNetwork(FriendlyByteBuf pBuffer) {
			pBuffer.writeResourceLocation(effect);
			pBuffer.writeVarInt(duration);
			pBuffer.writeVarInt(amplifier);
		}
	}

	public static class Serializer implements RecipeSerializer<CoffeeIngredientRecipe> {
		private static final Codec<CoffeeIngredientRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
								EffectInstance.CODEC
										.listOf()
										.optionalFieldOf("effects", new ArrayList<>())
										.flatXmap(
												list -> {
													for (EffectInstance effect : list) {
														if (!BuiltInRegistries.MOB_EFFECT.containsKey(effect.effect)) {
															return DataResult.error(() -> "Unknown effect: %s".formatted(effect.effect));
														}
													}
													return DataResult.success(NonNullList.of(EffectInstance.EMPTY, list.toArray(EffectInstance[]::new)));
												},
												DataResult::success
										)
										.forGetter(recipe -> recipe.instances),
								Codec.INT.fieldOf("maxAmplifier").forGetter(recipe -> recipe.maxAmplifier),
								Codec.STRING.optionalFieldOf("extraText", "").forGetter(recipe -> recipe.extraText)
						)
						.apply(instance, CoffeeIngredientRecipe::new)
		);

		@Override
		public Codec<CoffeeIngredientRecipe> codec() {
			return CODEC;
		}

		@Nullable
		@Override
		public CoffeeIngredientRecipe fromNetwork(@Nonnull FriendlyByteBuf pBuffer) {
			Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
			int i = pBuffer.readVarInt();
			NonNullList<EffectInstance> list = NonNullList.withSize(i, EffectInstance.EMPTY);
			for (int j = 0; j < list.size(); ++j) {
				list.set(j, EffectInstance.fromNetwork(pBuffer));
			}
			int maxAmplifier = pBuffer.readInt();
			String extraText = pBuffer.readUtf(32767);
			return new CoffeeIngredientRecipe(ingredient, list, maxAmplifier, extraText);
		}

		@Override
		public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, CoffeeIngredientRecipe pRecipe) {
			pRecipe.ingredient.toNetwork(pBuffer);
			pBuffer.writeVarInt(pRecipe.instances.size());
			for (EffectInstance effect : pRecipe.instances) {
				effect.toNetwork(pBuffer);
			}
			pBuffer.writeInt(pRecipe.maxAmplifier);
			pBuffer.writeUtf(pRecipe.extraText);
		}
	}
}
