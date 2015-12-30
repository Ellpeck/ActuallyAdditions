/*
 * This file ("InitArmorMaterials.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.material;

import de.ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class InitArmorMaterials{

    public static ItemArmor.ArmorMaterial armorMaterialEmerald;
    public static ItemArmor.ArmorMaterial armorMaterialObsidian;
    public static ItemArmor.ArmorMaterial armorMaterialQuartz;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Armor Materials...");

        armorMaterialEmerald = EnumHelper.addArmorMaterial("armorMaterialEmerald", 1500, new int[]{5, 9, 8, 5}, 15);
        armorMaterialObsidian = EnumHelper.addArmorMaterial("armorMaterialObsidian", 7000, new int[]{3, 4, 3, 1}, 10);
        armorMaterialQuartz = EnumHelper.addArmorMaterial("armorMaterialQuartz", 200, new int[]{3, 6, 5, 3}, 8);
    }
}
