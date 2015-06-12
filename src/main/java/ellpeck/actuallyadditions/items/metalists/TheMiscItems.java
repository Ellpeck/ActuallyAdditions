package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.item.EnumRarity;

public enum TheMiscItems implements INameableItem{

    PAPER_CONE("PaperCone", EnumRarity.common, "itemPaperCone"),
    MASHED_FOOD("MashedFood", EnumRarity.uncommon, "itemMashedFood"),
    KNIFE_BLADE("KnifeBlade", EnumRarity.common, "itemKnifeBlade"),
    KNIFE_HANDLE("KnifeHandle", EnumRarity.common, "itemKnifeHandle"),
    DOUGH("Dough", EnumRarity.common, "itemDough"),
    QUARTZ("BlackQuartz", EnumRarity.epic, "gemQuartzBlack"),
    RING("Ring", EnumRarity.uncommon, "itemRing"),
    COIL("Coil", EnumRarity.common, "itemCoilBasic"),
    COIL_ADVANCED("CoilAdvanced", EnumRarity.uncommon, "itemCoilAdvanced"),
    RICE_DOUGH("RiceDough", EnumRarity.uncommon, "itemRiceDough"),
    TINY_COAL("TinyCoal", EnumRarity.common, "itemTinyCoal"),
    TINY_CHAR("TinyCharcoal", EnumRarity.common, "itemTinyChar"),
    RICE_SLIME("RiceSlime", EnumRarity.uncommon, "slimeball"),
    CANOLA("Canola", EnumRarity.uncommon, "itemCanola"),
    CUP("Cup", EnumRarity.uncommon, "itemCup");

    public final String name;
    public final String oredictName;
    public final EnumRarity rarity;

    TheMiscItems(String name, EnumRarity rarity, String oredictName){
        this.name = name;
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