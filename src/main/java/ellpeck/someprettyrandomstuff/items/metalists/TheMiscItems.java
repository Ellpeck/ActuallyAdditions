package ellpeck.someprettyrandomstuff.items.metalists;

import ellpeck.someprettyrandomstuff.util.IItemEnum;

public enum TheMiscItems implements IItemEnum{

    PAPER_CONE("PaperCone"),
    MASHED_FOOD("MashedFood"),
    REFINED_IRON("RefinedIron"),
    REFINED_REDSTONE("RefinedRedstone"),
    COMPRESSED_IRON("CompressedIron"),
    STEEL("Steel"),
    KNIFE_BLADE("KnifeBlade"),
    KNIFE_HANDLE("KnifeHandle"),
    DOUGH("Dough");

    public final String name;

    private TheMiscItems(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}