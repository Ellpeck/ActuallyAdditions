package ellpeck.someprettyrandomstuff.blocks.metalists;

import ellpeck.someprettyrandomstuff.util.IName;

public enum TheMiscBlocks implements IName{

    QUARTZ_PILLAR("BlackQuartzPillar"),
    QUARTZ_CHISELED("BlackQuartzChiseled"),
    QUARTZ("BlackQuartz"),
    ORE_QUARTZ("OreBlackQuartz");

    public final String name;

    private TheMiscBlocks(String name){
        this.name = name;
    }

    @Override
    public String getName(){
        return this.name;
    }
}