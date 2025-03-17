package de.ellpeck.actuallyadditions.mod.jei;

import de.ellpeck.actuallyadditions.mod.util.CapHelper;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EnergyInterpreter implements ISubtypeInterpreter<ItemStack> {
	public static final EnergyInterpreter INSTANCE = new EnergyInterpreter();

	private EnergyInterpreter() {

	}

	@Override
	@Nullable
	public Object getSubtypeData(@NotNull ItemStack ingredient, @NotNull UidContext context) {
		IEnergyStorage energyStorage = CapHelper.getEnergyStorage(ingredient).orElse(null);
		if (energyStorage != null) {
			return energyStorage.getEnergyStored();
		}
		return null;
	}

	@Override
	@NotNull
	public String getLegacyStringSubtypeInfo(@NotNull ItemStack ingredient, @NotNull UidContext context) {
		return CapHelper.getEnergyStorage(ingredient).flatMap(storage -> {
			if (storage.getEnergyStored() == storage.getMaxEnergyStored()) {
				return Optional.of("charged");
			} else {
				return Optional.of("uncharged");
			}
		}).orElse("uncharged");
	}
}
