package ellpeck.someprettyrandomstuff.items.metalists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public enum TheMiscItems{

    PAPER_CONE("PaperCone"),
    MASHED_FOOD("MashedFood"),
    REFINED_IRON("RefinedIron"),
    REFINED_REDSTONE("RefinedRedstone"),
    COMPRESSED_IRON("CompressedIron"),
    STEEL("Steel"),
    DOUGH("Dough");

    public final String name;
    @SideOnly(Side.CLIENT)
    public IIcon theIcon;

    private TheMiscItems(String name){
        this.name = name;
    }
}