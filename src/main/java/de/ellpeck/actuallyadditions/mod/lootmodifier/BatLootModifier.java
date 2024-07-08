package de.ellpeck.actuallyadditions.mod.lootmodifier;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class BatLootModifier extends LootModifier {
	public static final Supplier<MapCodec<BatLootModifier>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, BatLootModifier::new)));

	public BatLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		RandomSource random = context.getRandom();
		if (CommonConfig.Other.DO_BAT_DROPS.get() &&
				context.hasParam(LootContextParams.ATTACKING_ENTITY) &&
				context.hasParam(LootContextParams.DAMAGE_SOURCE) &&
				context.hasParam(LootContextParams.THIS_ENTITY) &&
				context.getParam(LootContextParams.THIS_ENTITY) instanceof Bat) {
			Entity entity = context.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
			int looting = 1;
			if (entity instanceof LivingEntity livingentity) {
				looting = EnchantmentHelper.getEnchantmentLevel(livingentity.level().holderOrThrow(Enchantments.LOOTING), livingentity);
			}
			if (random.nextInt(15) <= looting * 2) {
				generatedLoot.add(new ItemStack(ActuallyItems.BATS_WING.get(), random.nextInt(2 + looting) + 1));
			}
		}
		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return ActuallyLootModifiers.BAT_LOOT.get();
	}
}
