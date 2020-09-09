package de.ellpeck.actuallyadditions.common.material;

import java.util.Locale;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public final class InitArmorMaterials {

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

    public static void init() {
        ActuallyAdditions.LOGGER.info("Initializing Armor Materials...");

        armorMaterialEmerald = addArmorMaterial("armorMaterialEmerald", ActuallyAdditions.MODID + ":armor_emerald", 50, new int[] { 5, 8, 9, 4 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2);
        armorMaterialObsidian = addArmorMaterial("armorMaterialObsidian", ActuallyAdditions.MODID + ":armor_obsidian", 120, new int[] { 1, 3, 4, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
        armorMaterialQuartz = addArmorMaterial("armorMaterialQuartz", ActuallyAdditions.MODID + ":armor_quartz", 20, new int[] { 3, 5, 6, 3 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);

        armorMaterialCrystalRed = addArmorMaterial("armorMaterialCrystalRed", ActuallyAdditions.MODID + ":armor_crystal_red", 18, new int[] { 3, 6, 7, 3 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
        armorMaterialCrystalBlue = addArmorMaterial("armorMaterialCrystalBlue", ActuallyAdditions.MODID + ":armor_crystal_blue", 18, new int[] { 3, 6, 7, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
        armorMaterialCrystalLightBlue = addArmorMaterial("armorMaterialCrystalLightBlue", ActuallyAdditions.MODID + ":armor_crystal_light_blue", 35, new int[] { 4, 7, 8, 4 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 3);
        armorMaterialCrystalBlack = addArmorMaterial("armorMaterialCrystalBlack", ActuallyAdditions.MODID + ":armor_crystal_black", 12, new int[] { 1, 3, 4, 1 }, 13, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
        armorMaterialCrystalGreen = addArmorMaterial("armorMaterialCrystalGreen", ActuallyAdditions.MODID + ":armor_crystal_green", 60, new int[] { 6, 9, 9, 4 }, 18, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 3);
        armorMaterialCrystalWhite = addArmorMaterial("armorMaterialCrystalWhite", ActuallyAdditions.MODID + ":armor_crystal_white", 18, new int[] { 3, 6, 6, 3 }, 11, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);

        armorMaterialGoggles = addArmorMaterial("armorMaterialGoggles", ActuallyAdditions.MODID + ":armor_goggles", 0, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
    }

    private static ArmorMaterial addArmorMaterial(String name, String textureName, int durability, int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip, float toughness) {
        return EnumHelper.addArmorMaterial((ActuallyAdditions.MODID + "_" + name).toUpperCase(Locale.ROOT), textureName, durability, reductionAmounts, enchantability, soundOnEquip, toughness);
    }
}
