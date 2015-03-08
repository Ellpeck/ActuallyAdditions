package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.util.IName;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.EnumRarity;

public enum TheDusts implements IName{

    IRON("Iron", 7826534, EnumRarity.common),
    GOLD("Gold", 14335744, EnumRarity.uncommon),
    DIAMOND("Diamond", 292003, EnumRarity.rare),
    EMERALD("Emerald", 4319527, EnumRarity.epic),
    LAPIS("Lapis", 1849791, EnumRarity.uncommon),
    QUARTZ("Quartz", Util.DECIMAL_COLOR_WHITE, EnumRarity.uncommon),
    COAL("Coal", 0, EnumRarity.uncommon),
    QUARTZ_BLACK("QuartzBlack", 18, EnumRarity.rare);

    public final String name;
    public final int color;
    public final EnumRarity rarity;

    private TheDusts(String name, int color, EnumRarity rarity){
        this.name = name;
        this.color = color;
        this.rarity = rarity;
    }

    @Override
    public String getName(){
        return this.name;
    }
}