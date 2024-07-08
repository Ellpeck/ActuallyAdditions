package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class ActuallyDamageTypes {
	public static final ResourceKey<DamageType> ATOMIC_RECONSTRUCTOR = register("atomicreconstructor");

	private static ResourceKey<DamageType> register(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, ActuallyAdditions.modLoc(name));
	}
}
