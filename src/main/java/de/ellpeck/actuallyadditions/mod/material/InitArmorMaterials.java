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

        //TODO Fix armor textures
        armorMaterialEmerald = EnumHelper.addArmorMaterial("armorMaterialEmerald", ModUtil.MOD_ID_LOWER+":armorEmerald", 50, new int[]{5, 9, 8, 5}, 15);
        armorMaterialObsidian = EnumHelper.addArmorMaterial("armorMaterialObsidian", ModUtil.MOD_ID_LOWER+":armorObsidian", 120, new int[]{3, 4, 3, 1}, 10);
        armorMaterialQuartz = EnumHelper.addArmorMaterial("armorMaterialQuartz", ModUtil.MOD_ID_LOWER+":armorQuartz", 20, new int[]{3, 6, 5, 3}, 8);

        armorMaterialCrystalRed = EnumHelper.addArmorMaterial("armorMaterialCrystalRed", ModUtil.MOD_ID_LOWER+":armorCrystalRed", 18, new int[]{3, 7, 6, 3}, 9);
        armorMaterialCrystalBlue = EnumHelper.addArmorMaterial("armorMaterialCrystalBlue", ModUtil.MOD_ID_LOWER+":armorCrystalBlue", 18, new int[]{3, 7, 6, 3}, 10);
        armorMaterialCrystalLightBlue = EnumHelper.addArmorMaterial("armorMaterialCrystalLightBlue", ModUtil.MOD_ID_LOWER+":armorCrystalLightBlue", 35, new int[]{7, 9, 7, 5}, 12);
        armorMaterialCrystalBlack = EnumHelper.addArmorMaterial("armorMaterialCrystalBlack", ModUtil.MOD_ID_LOWER+":armorCrystalBlack", 12, new int[]{1, 4, 3, 1}, 13);
        armorMaterialCrystalGreen = EnumHelper.addArmorMaterial("armorMaterialCrystalGreen", ModUtil.MOD_ID_LOWER+":armorCrystalGreen", 60, new int[]{7, 10, 9, 6}, 18);
        armorMaterialCrystalWhite = EnumHelper.addArmorMaterial("armorMaterialCrystalWhite", ModUtil.MOD_ID_LOWER+":armorCrystalWhite", 18, new int[]{4, 7, 6, 4}, 11);
    }
}
