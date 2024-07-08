//package de.ellpeck.actuallyadditions.data.patchouli;
//
//import de.ellpeck.actuallyadditions.data.patchouli.builder.ReconstructorPageBuilder;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
//import net.minecraft.data.PackOutput;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import xyz.brassgoggledcoders.patchouliprovider.BookBuilder;
//import xyz.brassgoggledcoders.patchouliprovider.CategoryBuilder;
//import xyz.brassgoggledcoders.patchouliprovider.EntryBuilder;
//import xyz.brassgoggledcoders.patchouliprovider.PatchouliBookProvider;
//
//import java.util.function.Consumer;
//
//public class PachouliGenerator extends PatchouliBookProvider {
//
//	public PachouliGenerator(PackOutput output) {
//		super(output, ActuallyAdditions.MODID, "en_us");
//	}
//
//	@Override
//	protected void addBooks(Consumer<BookBuilder> consumer) {
//		final ItemStack bookStack = ActuallyItems.ITEM_BOOKLET.get().getDefaultInstance();
//
//		BookBuilder bookBuilder = createBookBuilder("booklet",
//				prefix("item.actuallyadditions.booklet"), "<i>To be perfectly honest, I never actually realized how much content Actually Additions has before.<r><n> - Ellpeck")
//				.setVersion("153")
//				.setCreativeTab("actuallyadditions.tab")
//				.setCustomBookItem(bookStack)
//				.setBookTexture("actuallyadditions:textures/gui/booklet/booklet.png")
//				.setShowProgress(false)
//				.setUseBlockyFont(false)
//				.setI18n(true)
//				.setDontGenerateBook(true)
//				.addMacro("<imp>", "$(2)")
//				.addMacro("<item>", "$(9)")
//				.addMacro("<r>", "$()")
//				.addMacro("<n>", "$(br)")
//				.addMacro("<i>", "$(o)")
//				.addMacro("<tifisgrin>", "$(4)$(n)")
//				.setUseResourcePack(true);
//
//
//		//Getting Started
//		CategoryBuilder gettingStarted = bookBuilder.addCategory("getting_started", prefix("indexEntry.getting_started"),
//				"", bookStack);
//
//		gettingStarted.addEntry("tutorial", prefix("chapter.book_tutorial"), bookStack)
//				.addTextPage(prefix("chapter.book_tutorial.text.1")).build()
//				.addTextPage(prefix("chapter.book_tutorial.text.2")).build()
//				.addTextPage(prefix("chapter.book_tutorial.text.3")).build()
//				.addCraftingPage(ActuallyAdditions.modLoc("booklet"))
//				.setTitle("").setText("booklet.actuallyadditions.shapeless_recipe").build();
//
//		gettingStarted.addEntry("guide", prefix("chapter.video_guide"), "actuallyadditions:textures/item/youtube.png")
//				.addLinkPage("https://www.youtube.com/watch?v=fhjz0Ew56pM", prefix("chapter.video_guide.booty.button"))
//				.setText(prefix("chapter.video_guide.booty.text.1")).build()
//				.addTextPage(prefix("chapter.video_guide.booty.text.2")).build()
//				.addLinkPage("https://www.youtube.com/playlist?list=PLJeFZ64pT89MrTRZYzD_rtHFajPVlt6cF", prefix("chapter.video_guide.booty.button"))
//				.setText(prefix("chapter.video_guide.dire.text.1")).build()
//				.addTextPage(prefix("chapter.video_guide.dire.text.2")).build();
//
//		gettingStarted.addEntry("intro", prefix("chapter.intro"), bookStack)
//				.addTextPage(prefix("chapter.intro.text.1")).build()
//				.addTextPage(prefix("chapter.intro.text.2")).build()
//				.addTextPage(prefix("chapter.intro.text.3")).build()
//				.addTextPage(prefix("chapter.intro.text.4")).build();
//
//		gettingStarted.addEntry("engineer_house", prefix("chapter.engineer_house"), bookStack)
//				.addTextPage(prefix("chapter.engineer_house.text.1")).build()
//				.addImagePage(ActuallyAdditions.modLoc("textures/gui/booklet/page_engineer_house.png"))
//				.setText("booklet.actuallyadditions.chapter.engineer_house.text.2").build();
//
//		EntryBuilder crystalsBuilder = gettingStarted.addEntry("crystals", prefix("chapter.crystals"), bookStack)
//				.addTextPage(prefix("chapter.crystals.text.1")).build()
//				.addTextPage(prefix("chapter.crystals.text.2")).build()
//				.addTextPage(prefix("chapter.crystals.text.3")).build()
//				.addImagePage(ActuallyAdditions.modLoc("textures/gui/booklet/page_atomic_reconstructor.png")).setBorder(false).build()
//				.addTextPage(prefix("chapter.crystals.text.5")).build()
//				.addCraftingPage(ActuallyAdditions.modLoc("atomic_reconstructor"))
//				.setText(prefix("chapter.crystals.text.6")).build();
//		crystalsBuilder.addPage(new ReconstructorPageBuilder(ActuallyAdditions.modLoc("laser/crystalize_restonia_crystal_block"), crystalsBuilder))
//				.setRecipe2(ActuallyAdditions.modLoc("laser/crystalize_restonia_crystal"))
//				.setText("booklet.actuallyadditions.reconstructor_recipe").build();
//		crystalsBuilder.addPage(new ReconstructorPageBuilder(ActuallyAdditions.modLoc("laser/crystalize_palis_crystal_block"), crystalsBuilder))
//				.setRecipe2(ActuallyAdditions.modLoc("laser/crystalize_palis_crystal"))
//				.setText("booklet.actuallyadditions.reconstructor_recipe").build();
//		crystalsBuilder.addPage(new ReconstructorPageBuilder(ActuallyAdditions.modLoc("laser/crystalize_diamatine_crystal_block"), crystalsBuilder))
//				.setRecipe2(ActuallyAdditions.modLoc("laser/crystalize_diamatine_crystal"))
//				.setText("booklet.actuallyadditions.reconstructor_recipe").build();
//		crystalsBuilder.addPage(new ReconstructorPageBuilder(ActuallyAdditions.modLoc("laser/crystalize_void_crystal_block"), crystalsBuilder))
//				.setRecipe2(ActuallyAdditions.modLoc("laser/crystalize_void_crystal"))
//				.setText("booklet.actuallyadditions.reconstructor_recipe").build();
//		crystalsBuilder.addPage(new ReconstructorPageBuilder(ActuallyAdditions.modLoc("laser/crystalize_emeradic_crystal_block"), crystalsBuilder))
//				.setRecipe2(ActuallyAdditions.modLoc("laser/crystalize_emeradic_crystal"))
//				.setText("booklet.actuallyadditions.reconstructor_recipe").build();
//		crystalsBuilder.addPage(new ReconstructorPageBuilder(ActuallyAdditions.modLoc("laser/crystalize_enori_crystal_block"), crystalsBuilder))
//				.setRecipe2(ActuallyAdditions.modLoc("laser/crystalize_enori_crystal"))
//				.setText("booklet.actuallyadditions.reconstructor_recipe").build();
//		crystalsBuilder.addCraftingPage(ActuallyAdditions.modLoc("decompress/restonia_crystal"))
//				.setRecipe2(ActuallyAdditions.modLoc("compress/restonia_crystal_block")).build()
//				.addCraftingPage(ActuallyAdditions.modLoc("decompress/palis_crystal"))
//				.setRecipe2(ActuallyAdditions.modLoc("compress/palis_crystal_block")).build()
//				.addCraftingPage(ActuallyAdditions.modLoc("decompress/diamatine_crystal"))
//				.setRecipe2(ActuallyAdditions.modLoc("compress/diamatine_crystal_block")).build()
//				.addCraftingPage(ActuallyAdditions.modLoc("decompress/void_crystal"))
//				.setRecipe2(ActuallyAdditions.modLoc("compress/void_crystal_block")).build()
//				.addCraftingPage(ActuallyAdditions.modLoc("decompress/emeradic_crystal"))
//				.setRecipe2(ActuallyAdditions.modLoc("compress/emeradic_crystal_block")).build()
//				.addCraftingPage(ActuallyAdditions.modLoc("decompress/enori_crystal"))
//				.setRecipe2(ActuallyAdditions.modLoc("compress/enori_crystal_block")).build();
//		crystalsBuilder.build();
//
//		//End of Getting Started
//
//
//		//Finish book
//		bookBuilder.build(consumer);
//	}
//
//	private String prefix(String name) {
//		return "booklet.actuallyadditions." + name;
//	}
//}
