package de.ellpeck.actuallyadditions.mod.patchouli;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import vazkii.patchouli.client.book.ClientBookRegistry;

public class PatchouliPages {
	public static void init() {
		ClientBookRegistry.INSTANCE.pageTypes.put(ActuallyAdditions.modLoc("reconstructor"), PageReconstructor.class);
	}
}
