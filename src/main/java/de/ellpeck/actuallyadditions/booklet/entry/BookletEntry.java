package de.ellpeck.actuallyadditions.booklet.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BookletEntry implements IBookletEntry {

    private final String identifier;
    private final int priority;
    private final List<IBookletChapter> chapters = new ArrayList<>();
    private TextFormatting color;

    public BookletEntry(String identifier) {
        this(identifier, 0);
    }

    public BookletEntry(String identifier, int prio) {
        this.identifier = identifier;
        this.priority = prio;
        ActuallyAdditionsAPI.addBookletEntry(this);

        this.color = TextFormatting.RESET;
    }

    @SideOnly(Side.CLIENT)
    private static boolean fitsFilter(IBookletPage page, String searchBarText) {
        Minecraft mc = Minecraft.getMinecraft();

        List<ItemStack> items = new ArrayList<>();
        page.getItemStacksForPage(items);
        if (!items.isEmpty()) {
            for (ItemStack stack : items) {
                if (StackUtil.isValid(stack)) {
                    List<String> tooltip = stack.getTooltip(mc.player, mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
                    for (String strg : tooltip) {
                        if (strg != null && strg.toLowerCase(Locale.ROOT).contains(searchBarText)) { return true; }
                    }
                }
            }
        }

        List<FluidStack> fluids = new ArrayList<>();
        page.getFluidStacksForPage(fluids);
        if (!fluids.isEmpty()) {
            for (FluidStack stack : fluids) {
                if (stack != null) {
                    String strg = stack.getLocalizedName();
                    if (strg != null && strg.toLowerCase(Locale.ROOT).contains(searchBarText)) { return true; }
                }
            }
        }

        return false;
    }

    @Override
    public List<IBookletChapter> getAllChapters() {
        return this.chapters;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getLocalizedName() {
        return StringUtil.localize("booklet." + ActuallyAdditions.MODID + ".indexEntry." + this.getIdentifier() + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getLocalizedNameWithFormatting() {
        return this.color + this.getLocalizedName();
    }

    @Override
    public void addChapter(IBookletChapter chapter) {
        this.chapters.add(chapter);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public List<IBookletChapter> getChaptersForDisplay(String searchBarText) {
        if (searchBarText != null && !searchBarText.isEmpty()) {
            String search = searchBarText.toLowerCase(Locale.ROOT);

            List<IBookletChapter> fittingChapters = new ArrayList<>();
            for (IBookletChapter chapter : this.getAllChapters()) {
                if (chapter.getLocalizedName().toLowerCase(Locale.ROOT).contains(search)) {
                    fittingChapters.add(chapter);
                } else {
                    for (IBookletPage page : chapter.getAllPages()) {
                        if (fitsFilter(page, search)) {
                            fittingChapters.add(chapter);
                            break;
                        }
                    }
                }
            }

            return fittingChapters;
        } else {
            return this.getAllChapters();
        }
    }

    @Override
    public int getSortingPriority() {
        return this.priority;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean visibleOnFrontPage() {
        return true;
    }

    public BookletEntry setImportant() {
        this.color = TextFormatting.DARK_GREEN;
        return this;
    }

    public BookletEntry setSpecial() {
        this.color = TextFormatting.DARK_PURPLE;
        return this;
    }

}
