package ellpeck.actuallyadditions.blocks.metalists;

import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.item.EnumRarity;

public enum TheMiscBlocks implements INameableItem{

    QUARTZ_PILLAR("BlackQuartzPillar", EnumRarity.rare, "blockQuartzBlack"),
    QUARTZ_CHISELED("BlackQuartzChiseled", EnumRarity.rare, "blockQuartzBlack"),
    QUARTZ("BlackQuartz", EnumRarity.rare, "blockQuartzBlack"),
    ORE_QUARTZ("OreBlackQuartz", EnumRarity.epic, "oreQuartzBlack"),
    WOOD_CASING("WoodCasing", EnumRarity.common, "blockCasingWood"),
    STONE_CASING("StoneCasing", EnumRarity.uncommon, "blockCasingStone");

    public final String name;
    public final String oredictName;
    public final EnumRarity rarity;

    TheMiscBlocks(String name, EnumRarity rarity, String oredictName){
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