package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.util.IName;
import net.minecraft.item.EnumRarity;

public enum TheMiscItems implements IName{

    PAPER_CONE("PaperCone", EnumRarity.common),
    MASHED_FOOD("MashedFood", EnumRarity.uncommon),
    KNIFE_BLADE("KnifeBlade", EnumRarity.common),
    KNIFE_HANDLE("KnifeHandle", EnumRarity.common),
    DOUGH("Dough", EnumRarity.common),
    QUARTZ("BlackQuartz", EnumRarity.epic);

    public final String name;
    public final EnumRarity rarity;

    TheMiscItems(String name, EnumRarity rarity){
        this.name = name;
        this.rarity = rarity;
    }

    @Override
    public String getName(){
        return this.name;
    }
}