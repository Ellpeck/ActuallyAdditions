package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.item.EnumRarity;

public enum TheMiscItems implements INameableItem{

    PAPER_CONE("PaperCone", EnumRarity.common),
    MASHED_FOOD("MashedFood", EnumRarity.uncommon),
    KNIFE_BLADE("KnifeBlade", EnumRarity.common),
    KNIFE_HANDLE("KnifeHandle", EnumRarity.common),
    DOUGH("Dough", EnumRarity.common),
    QUARTZ("BlackQuartz", EnumRarity.epic),
    RING("Ring", EnumRarity.uncommon),
    COIL("Coil", EnumRarity.common),
    COIL_ADVANCED("CoilAdvanced", EnumRarity.uncommon),
    RICE_DOUGH("RiceDough", EnumRarity.uncommon),
    TINY_COAL("TinyCoal", EnumRarity.common),
    TINY_CHAR("TinyCharcoal", EnumRarity.common),
    RICE_SLIME("RiceSlime", EnumRarity.uncommon),
    CANOLA("Canola", EnumRarity.uncommon),
    CUP("Cup", EnumRarity.uncommon);

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