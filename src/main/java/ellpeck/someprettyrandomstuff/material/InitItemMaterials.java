package ellpeck.someprettyrandomstuff.material;

import ellpeck.someprettyrandomstuff.config.ConfigValues;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class InitItemMaterials{

    public static ToolMaterial toolMaterialEmerald;
    public static ToolMaterial toolMaterialObsidian;

    public static void init(){
        Util.logInfo("Initializing Materials...");

        toolMaterialEmerald = EnumHelper.addToolMaterial("toolMaterialEmerald", ConfigValues.toolEmeraldHarvestLevel, ConfigValues.toolEmeraldMaxUses, ConfigValues.toolEmeraldEfficiency, ConfigValues.toolEmeraldDamage, ConfigValues.toolEmeraldEnchantability);
        toolMaterialObsidian = EnumHelper.addToolMaterial("toolMaterialObsidian", ConfigValues.toolObsidianHarvestLevel, ConfigValues.toolObsidianMaxUses, ConfigValues.toolObsidianEfficiency, ConfigValues.toolObsidianDamage, ConfigValues.toolObsidianEnchantability);

    }

}
