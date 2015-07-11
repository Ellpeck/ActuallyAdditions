package ellpeck.actuallyadditions.material;

import ellpeck.actuallyadditions.config.values.ConfigFloatValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class InitToolMaterials{

    public static ToolMaterial toolMaterialEmerald;
    public static ToolMaterial toolMaterialObsidian;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Tool Materials...");

        toolMaterialEmerald = EnumHelper.addToolMaterial("toolMaterialEmerald", ConfigIntValues.EMERALD_HARVEST_LEVEL.getValue(), ConfigIntValues.EMERALD_USES.getValue(), ConfigFloatValues.EMERALD_SPEED.getValue(), ConfigFloatValues.EMERALD_MAX_DAMAGE.getValue(), ConfigIntValues.EMERALD_ENCHANTABILITY.getValue());
        toolMaterialObsidian = EnumHelper.addToolMaterial("toolMaterialObsidian", ConfigIntValues.OBSIDIAN_HARVEST_LEVEL.getValue(), ConfigIntValues.OBSIDIAN_USES.getValue(), ConfigFloatValues.OBSIDIAN_SPEED.getValue(), ConfigFloatValues.OBSIDIAN_MAX_DAMAGE.getValue(), ConfigIntValues.OBSIDIAN_ENCHANTABILITY.getValue());

    }

}
