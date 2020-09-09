package de.ellpeck.actuallyadditions.booklet.chapter;

import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.misc.IDisableableItem;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BookletChapter implements IBookletChapter {

    public final IBookletPage[] pages;
    public final IBookletEntry entry;
    public final ItemStack displayStack;
    private final String identifier;
    private final int priority;
    public TextFormatting color;

    public BookletChapter(String identifier, IBookletEntry entry, ItemStack displayStack, IBookletPage... pages) {
        this(identifier, entry, displayStack, 0, pages);
    }

    public BookletChapter(String identifier, IBookletEntry entry, ItemStack displayStack, int priority, IBookletPage... pages) {
        this.pages = pages;
        this.identifier = identifier;
        this.entry = entry;
        this.displayStack = displayStack;
        if (displayStack.getItem() instanceof IDisableableItem && ((IDisableableItem) displayStack.getItem()).isDisabled()) displayStack = ItemStack.EMPTY;
        this.priority = priority;
        this.color = TextFormatting.RESET;

        this.entry.addChapter(this);
        for (IBookletPage page : this.pages) {
            page.setChapter(this);
        }
    }

    @Override
    public IBookletPage[] getAllPages() {
        return this.pages;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getLocalizedName() {
        return StringUtil.localize("booklet." + ActuallyAdditions.MODID + ".chapter." + this.getIdentifier() + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getLocalizedNameWithFormatting() {
        return this.color + this.getLocalizedName();
    }

    @Override
    public IBookletEntry getEntry() {
        return this.entry;
    }

    @Override
    public ItemStack getDisplayItemStack() {
        return this.displayStack;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public int getPageIndex(IBookletPage page) {
        for (int i = 0; i < this.pages.length; i++) {
            if (this.pages[i] == page) { return i; }
        }
        return -1;
    }

    @Override
    public int getSortingPriority() {
        return this.priority;
    }

    public BookletChapter setImportant() {
        this.color = TextFormatting.DARK_GREEN;
        return this;
    }

    public BookletChapter setSpecial() {
        this.color = TextFormatting.DARK_PURPLE;
        return this;
    }
}
