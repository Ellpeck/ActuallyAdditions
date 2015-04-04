package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.item.EnumRarity;

public enum TheDusts implements INameableItem{

    IRON("Iron", 7826534, EnumRarity.common, "dustIron"),
    GOLD("Gold", 14335744, EnumRarity.uncommon, "dustGold"),
    DIAMOND("Diamond", 292003, EnumRarity.rare, "dustDiamond"),
    EMERALD("Emerald", 4319527, EnumRarity.epic, "dustEmerald"),
    LAPIS("Lapis", 1849791, EnumRarity.uncommon, "dustLapis"),
    QUARTZ("Quartz", StringUtil.DECIMAL_COLOR_WHITE, EnumRarity.uncommon, "dustQuartz"),
    COAL("Coal", 0, EnumRarity.uncommon, "dustCoal"),
    QUARTZ_BLACK("QuartzBlack", 18, EnumRarity.rare, "dustQuartzBlack");

    public final String name;
    public final String oredictName;
    public final int color;
    public final EnumRarity rarity;

    TheDusts(String name, int color, EnumRarity rarity, String oredictName){
        this.name = name;
        this.color = color;
        this.rarity = rarity;
        this.oredictName = oredictName;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getOredictName(){
        return this.oredictName;
    }
}