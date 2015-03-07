package ellpeck.actuallyadditions.blocks.metalists;

import ellpeck.actuallyadditions.util.IName;
import net.minecraft.item.EnumRarity;

public enum TheMiscBlocks implements IName{

    QUARTZ_PILLAR("BlackQuartzPillar", EnumRarity.rare),
    QUARTZ_CHISELED("BlackQuartzChiseled", EnumRarity.rare),
    QUARTZ("BlackQuartz", EnumRarity.rare),
    ORE_QUARTZ("OreBlackQuartz", EnumRarity.epic);

    public final String name;
    public final EnumRarity rarity;

    private TheMiscBlocks(String name, EnumRarity rarity){
        this.name = name;
        this.rarity = rarity;
    }

    @Override
    public String getName(){
        return this.name;
    }
}