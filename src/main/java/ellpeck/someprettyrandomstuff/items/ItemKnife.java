package ellpeck.someprettyrandomstuff.items;

import ellpeck.someprettyrandomstuff.items.tools.ItemSwordSPTS;

public class ItemKnife extends ItemSwordSPTS{

    public ItemKnife(){
        super(ToolMaterial.STONE, "itemKnife");
        this.setContainerItem(this);
    }
}
