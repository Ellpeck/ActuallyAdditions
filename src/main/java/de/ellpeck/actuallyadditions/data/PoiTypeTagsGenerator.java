package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.gen.village.ActuallyPOITypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PoiTypeTagsGenerator extends PoiTypeTagsProvider {
	public PoiTypeTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper fileHelper) {
		super(output, completableFuture, ActuallyAdditions.MODID, fileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {
		this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
				.add(
						ActuallyPOITypes.ENGINEER.getKey()
				);
	}
}
