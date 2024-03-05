package de.ellpeck.actuallyadditions.data.patchouli;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.brassgoggledcoders.patchouliprovider.BookBuilder;
import xyz.brassgoggledcoders.patchouliprovider.CategoryBuilder;
import xyz.brassgoggledcoders.patchouliprovider.PatchouliBookProvider;

import java.util.function.Consumer;

public class PachouliGenerator extends PatchouliBookProvider {

	public PachouliGenerator(PackOutput output) {
		super(output, ActuallyAdditions.MODID, "en_us");
	}

	@Override
	protected void addBooks(Consumer<BookBuilder> consumer) {
		final ItemStack bookStack = ActuallyItems.ITEM_BOOKLET.get().getDefaultInstance();

		BookBuilder bookBuilder = createBookBuilder("booklet",
				prefix("item.actuallyadditions.booklet"), "<i>To be perfectly honest, I never actually realized how much content Actually Additions has before.<r><n> - Ellpeck")
				.setVersion("153")
				.setCreativeTab("actuallyadditions.tab")
				.setCustomBookItem(bookStack)
				.setBookTexture("actuallyadditions:textures/gui/booklet/booklet.png")
				.setShowProgress(false)
				.setUseBlockyFont(false)
				.setI18n(true)
				.setDontGenerateBook(true)
				.addMacro("<imp>", "$(2)")
				.addMacro("<item>", "$(9)")
				.addMacro("<r>", "$()")
				.addMacro("<n>", "$(br)")
				.addMacro("<i>", "$(o)")
				.addMacro("<tifisgrin>", "$(4)$(n)")
				.setUseResourcePack(true);


		//Getting Started
		CategoryBuilder gettingStarted = bookBuilder.addCategory("getting_started", prefix("indexEntry.getting_started"),
				"", bookStack);

		gettingStarted.addEntry("tutorial", prefix("chapter.book_tutorial"), bookStack)
				.addTextPage(prefix("chapter.book_tutorial.text.1")).build()
				.addTextPage(prefix("chapter.book_tutorial.text.2")).build()
				.addTextPage(prefix("chapter.book_tutorial.text.3")).build()

				.addCraftingPage(new ResourceLocation(ActuallyAdditions.MODID, "booklet")).setTitle("").setText("booklet.actuallyadditions.shapeless_recipe").build();
		gettingStarted.addEntry("guide", prefix("chapter.video_guide"), "actuallyadditions:textures/item/youtube.png")
				.addLinkPage("https://www.youtube.com/watch?v=fhjz0Ew56pM", prefix("chapter.video_guide.booty.button"))
				.setText(prefix("chapter.video_guide.booty.text.1")).build()
				.addTextPage(prefix("chapter.video_guide.booty.text.2")).build()
				.addLinkPage("https://www.youtube.com/playlist?list=PLJeFZ64pT89MrTRZYzD_rtHFajPVlt6cF", prefix("chapter.video_guide.booty.button"))
				.setText(prefix("chapter.video_guide.dire.text.1")).build()
				.addTextPage(prefix("chapter.video_guide.dire.text.2")).build();

		gettingStarted.addEntry("intro", prefix("chapter.intro"), bookStack)
				.addTextPage(prefix("chapter.intro.text.1")).build()
				.addTextPage(prefix("chapter.intro.text.2")).build()
				.addTextPage(prefix("chapter.intro.text.3")).build();

		//End of Getting Started


		//Finish book
		bookBuilder.build(consumer);
	}

	private String prefix(String name) {
		return "booklet.actuallyadditions." + name;
	}
}
