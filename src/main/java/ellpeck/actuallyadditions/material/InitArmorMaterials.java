package ellpeck.actuallyadditions.material;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class InitArmorMaterials{

    public static ItemArmor.ArmorMaterial armorMaterialScuba;
    public static ItemArmor.ArmorMaterial armorMaterialEmerald;
    public static ItemArmor.ArmorMaterial armorMaterialObsidian;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Armor Materials...");

        armorMaterialScuba = EnumHelper.addArmorMaterial("armorMaterialScuba", ConfigIntValues.SCUBA_DURABILITY.getValue(), new int[]{ConfigIntValues.SCUBA_HEAD_DAMAGE.getValue(), ConfigIntValues.SCUBA_CHEST_DAMAGE.getValue(), ConfigIntValues.SCUBA_LEGS_DAMAGE.getValue(), ConfigIntValues.SCUBA_BOOTS_DAMAGE.getValue()}, ConfigIntValues.SCUBA_ENCHANTABILITY.getValue());
    }
}
