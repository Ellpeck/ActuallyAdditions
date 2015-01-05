package ellpeck.someprettytechystuff.booklet;

import net.minecraft.util.StatCollector;

public class Chapter{

    public final int ID;
    public final String name;
    public final int pageAmount;
    public final boolean hasCraftingRecipe;
    public String[] pageTexts;

    public Chapter(int ID, String name, int pageAmount, boolean hasCraftingRecipe){
        this.ID = ID;
        this.name = name + "Chapter";
        this.pageAmount = pageAmount;
        this.hasCraftingRecipe = hasCraftingRecipe;
        this.pageTexts = new String[pageAmount];
        for(int i = 0; i < pageTexts.length; i++){
            this.pageTexts[i] = StatCollector.translateToLocal("infoBook." + this.name + ".page" + i + ".text");
        }
    }
}
