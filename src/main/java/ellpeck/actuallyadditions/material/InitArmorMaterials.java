/*
 * This file ("InitArmorMaterials.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.material;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class InitArmorMaterials{

    public static ItemArmor.ArmorMaterial armorMaterialEmerald;
    public static ItemArmor.ArmorMaterial armorMaterialObsidian;
    public static ItemArmor.ArmorMaterial armorMaterialQuartz;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Armor Materials...");

        armorMaterialEmerald = EnumHelper.addArmorMaterial("armorMaterialEmerald", ConfigIntValues.EMERALD_ARMOR_DURABILITY.getValue(), new int[]{ConfigIntValues.EMERALD_ARMOR_HEAD_DAMAGE.getValue(), ConfigIntValues.EMERALD_ARMOR_CHEST_DAMAGE.getValue(), ConfigIntValues.EMERALD_ARMOR_LEGS_DAMAGE.getValue(), ConfigIntValues.EMERALD_ARMOR_BOOTS_DAMAGE.getValue()}, ConfigIntValues.EMERALD_ARMOR_ENCHANTABILITY.getValue());
        armorMaterialObsidian = EnumHelper.addArmorMaterial("armorMaterialObsidian", ConfigIntValues.OBSIDIAN_ARMOR_DURABILITY.getValue(), new int[]{ConfigIntValues.OBSIDIAN_ARMOR_HEAD_DAMAGE.getValue(), ConfigIntValues.OBSIDIAN_ARMOR_CHEST_DAMAGE.getValue(), ConfigIntValues.OBSIDIAN_ARMOR_LEGS_DAMAGE.getValue(), ConfigIntValues.OBSIDIAN_ARMOR_BOOTS_DAMAGE.getValue()}, ConfigIntValues.OBSIDIAN_ARMOR_ENCHANTABILITY.getValue());
        armorMaterialQuartz = EnumHelper.addArmorMaterial("armorMaterialQuartz", ConfigIntValues.QUARTZ_ARMOR_DURABILITY.getValue(), new int[]{ConfigIntValues.QUARTZ_ARMOR_HEAD_DAMAGE.getValue(), ConfigIntValues.QUARTZ_ARMOR_CHEST_DAMAGE.getValue(), ConfigIntValues.QUARTZ_ARMOR_LEGS_DAMAGE.getValue(), ConfigIntValues.QUARTZ_ARMOR_BOOTS_DAMAGE.getValue()}, ConfigIntValues.QUARTZ_ARMOR_ENCHANTABILITY.getValue());

    }
}
