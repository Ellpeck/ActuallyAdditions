/*
 * This file ("InitArmorMaterials.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.material;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class InitArmorMaterials{

    public static ArmorMaterial armorMaterialEmerald;
    public static ArmorMaterial armorMaterialObsidian;
    public static ArmorMaterial armorMaterialQuartz;

    public static ArmorMaterial armorMaterialCrystalRed;
    public static ArmorMaterial armorMaterialCrystalBlue;
    public static ArmorMaterial armorMaterialCrystalLightBlue;
    public static ArmorMaterial armorMaterialCrystalBlack;
    public static ArmorMaterial armorMaterialCrystalGreen;
    public static ArmorMaterial armorMaterialCrystalWhite;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Armor Materials...");

        armorMaterialEmerald = EnumHelper.addArmorMaterial("armorMaterialEmerald", ModUtil.MOD_ID+":armorEmerald", 50, new int[]{5, 9, 8, 5}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialObsidian = EnumHelper.addArmorMaterial("armorMaterialObsidian", ModUtil.MOD_ID+":armorObsidian", 120, new int[]{3, 4, 3, 1}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialQuartz = EnumHelper.addArmorMaterial("armorMaterialQuartz", ModUtil.MOD_ID+":armorQuartz", 20, new int[]{3, 6, 5, 3}, 8, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);

        armorMaterialCrystalRed = EnumHelper.addArmorMaterial("armorMaterialCrystalRed", ModUtil.MOD_ID+":armorCrystalRed", 18, new int[]{3, 7, 6, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalBlue = EnumHelper.addArmorMaterial("armorMaterialCrystalBlue", ModUtil.MOD_ID+":armorCrystalBlue", 18, new int[]{3, 7, 6, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalLightBlue = EnumHelper.addArmorMaterial("armorMaterialCrystalLightBlue", ModUtil.MOD_ID+":armorCrystalLightBlue", 35, new int[]{7, 9, 7, 5}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalBlack = EnumHelper.addArmorMaterial("armorMaterialCrystalBlack", ModUtil.MOD_ID+":armorCrystalBlack", 12, new int[]{1, 4, 3, 1}, 13, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalGreen = EnumHelper.addArmorMaterial("armorMaterialCrystalGreen", ModUtil.MOD_ID+":armorCrystalGreen", 60, new int[]{7, 10, 9, 6}, 18, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalWhite = EnumHelper.addArmorMaterial("armorMaterialCrystalWhite", ModUtil.MOD_ID+":armorCrystalWhite", 18, new int[]{4, 7, 6, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
    }
}
