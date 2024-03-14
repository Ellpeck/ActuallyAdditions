package de.ellpeck.actuallyadditions.data.patchouli.builder;

import net.minecraft.resources.ResourceLocation;
import xyz.brassgoggledcoders.patchouliprovider.EntryBuilder;
import xyz.brassgoggledcoders.patchouliprovider.page.RecipePageBuilder;

public class ReconstructorPageBuilder extends RecipePageBuilder<ReconstructorPageBuilder> {
	public ReconstructorPageBuilder(ResourceLocation recipe, EntryBuilder entryBuilder) {
		super("actuallyadditions:reconstructor", recipe, entryBuilder);
	}
}
