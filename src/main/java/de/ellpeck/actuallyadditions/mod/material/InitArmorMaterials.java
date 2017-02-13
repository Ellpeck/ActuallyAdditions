/*
 * This file ("InitArmorMaterials.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.material;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Locale;

public final class InitArmorMaterials{

    public static ArmorMaterial armorMaterialEmerald;
    public static ArmorMaterial armorMaterialObsidian;
    public static ArmorMaterial armorMaterialQuartz;

    public static ArmorMaterial armorMaterialCrystalRed;
    public static ArmorMaterial armorMaterialCrystalBlue;
    public static ArmorMaterial armorMaterialCrystalLightBlue;
    public static ArmorMaterial armorMaterialCrystalBlack;
    public static ArmorMaterial armorMaterialCrystalGreen;
    public static ArmorMaterial armorMaterialCrystalWhite;

    public static ArmorMaterial armorMaterialGoggles;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Armor Materials...");

        armorMaterialEmerald = addArmorMaterial("armorMaterialEmerald", ModUtil.MOD_ID+":armor_emerald", 50, new int[]{5, 8, 9, 4}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialObsidian = addArmorMaterial("armorMaterialObsidian", ModUtil.MOD_ID+":armor_obsidian", 120, new int[]{1, 3, 4, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialQuartz = addArmorMaterial("armorMaterialQuartz", ModUtil.MOD_ID+":armor_quartz", 20, new int[]{3, 5, 6, 3}, 8, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);

        armorMaterialCrystalRed = addArmorMaterial("armorMaterialCrystalRed", ModUtil.MOD_ID+":armor_crystal_red", 18, new int[]{3, 6, 7, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalBlue = addArmorMaterial("armorMaterialCrystalBlue", ModUtil.MOD_ID+":armor_crystal_blue", 18, new int[]{3, 6, 7, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalLightBlue = addArmorMaterial("armorMaterialCrystalLightBlue", ModUtil.MOD_ID+":armor_crystal_light_blue", 35, new int[]{4, 7, 8, 4}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalBlack = addArmorMaterial("armorMaterialCrystalBlack", ModUtil.MOD_ID+":armor_crystal_black", 12, new int[]{1, 3, 4, 1}, 13, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalGreen = addArmorMaterial("armorMaterialCrystalGreen", ModUtil.MOD_ID+":armor_crystal_green", 60, new int[]{6, 9, 9, 4}, 18, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        armorMaterialCrystalWhite = addArmorMaterial("armorMaterialCrystalWhite", ModUtil.MOD_ID+":armor_crystal_white", 18, new int[]{3, 6, 6, 3}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);

        armorMaterialGoggles = addArmorMaterial("armorMaterialGoggles", ModUtil.MOD_ID+":armor_goggles", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
    }

    private static ArmorMaterial addArmorMaterial(String name, String textureName, int durability, int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip){
        return EnumHelper.addArmorMaterial((ModUtil.MOD_ID+"_"+name).toUpperCase(Locale.ROOT), textureName, durability, reductionAmounts, enchantability, soundOnEquip, 0F);
    }
}
