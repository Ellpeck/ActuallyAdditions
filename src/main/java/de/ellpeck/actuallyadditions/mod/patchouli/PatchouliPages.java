package de.ellpeck.actuallyadditions.mod.patchouli;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.ClientBookRegistry;

public class PatchouliPages {
	public static void init() {
		ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(ActuallyAdditions.MODID, "reconstructor"), PageReconstructor.class);
	}
}
