package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.misc.BannerHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BannerPatternTagsGenerator extends BannerPatternTagsProvider {
	public BannerPatternTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper fileHelper) {
		super(output, completableFuture, ActuallyAdditions.MODID, fileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {
		this.tag(ActuallyTags.BannerPatterns.PATTERN_DRILL).add(BannerHelper.DRILL.getKey());
		this.tag(ActuallyTags.BannerPatterns.PATTERN_LEAF_BLO).add(BannerHelper.LEAF_BLO.getKey());
		this.tag(ActuallyTags.BannerPatterns.PATTERN_PHAN_CON).add(BannerHelper.PHAN_CON.getKey());
		this.tag(ActuallyTags.BannerPatterns.PATTERN_BOOK).add(BannerHelper.BOOK.getKey());
	}
}
