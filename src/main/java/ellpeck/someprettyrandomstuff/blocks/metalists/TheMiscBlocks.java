package ellpeck.someprettyrandomstuff.blocks.metalists;

import ellpeck.someprettyrandomstuff.util.IItemEnum;

public enum TheMiscBlocks implements IItemEnum{

    QUARTZ_PILLAR("BlackQuartzPillar"),
    QUARTZ_CHISELED("BlackQuartzChiseled"),
    QUARTZ("BlackQuartz"),
    ORE_QUARTZ("OreBlackQuartz");

    public final String name;

    private TheMiscBlocks(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}