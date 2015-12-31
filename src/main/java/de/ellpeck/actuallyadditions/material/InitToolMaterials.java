/*
 * This file ("InitToolMaterials.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.material;

import de.ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class InitToolMaterials{

    public static ToolMaterial toolMaterialEmerald;
    public static ToolMaterial toolMaterialObsidian;
    public static ToolMaterial toolMaterialQuartz;

    public static ToolMaterial toolMaterialCrystalRed;
    public static ToolMaterial toolMaterialCrystalBlue;
    public static ToolMaterial toolMaterialCrystalLightBlue;
    public static ToolMaterial toolMaterialCrystalBlack;
    public static ToolMaterial toolMaterialCrystalGreen;
    public static ToolMaterial toolMaterialCrystalWhite;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Tool Materials...");

        toolMaterialEmerald = EnumHelper.addToolMaterial("toolMaterialEmerald", 3, 2000, 9.0F, 5.0F, 15);
        toolMaterialObsidian = EnumHelper.addToolMaterial("toolMaterialObsidian", 3, 8000, 4.0F, 2.0F, 15);
        toolMaterialQuartz = EnumHelper.addToolMaterial("toolMaterialQuartz", 2, 280, 6.5F, 2.0F, 10);

        toolMaterialCrystalRed = EnumHelper.addToolMaterial("toolMaterialCrystalRed", 2, 300, 7.0F, 2.25F, 12);
        toolMaterialCrystalBlue = EnumHelper.addToolMaterial("toolMaterialCrystalBlue", 2, 300, 7.0F, 2.25F, 12);
        toolMaterialCrystalLightBlue = EnumHelper.addToolMaterial("toolMaterialCrystalLightBlue", 3, 1600, 9.0F, 4.0F, 14);
        toolMaterialCrystalBlack = EnumHelper.addToolMaterial("toolMaterialCrystalBlack", 2, 280, 6.0F, 2.0F, 8);
        toolMaterialCrystalGreen = EnumHelper.addToolMaterial("toolMaterialCrystalGreen", 4, 2200, 9.5F, 5.5F, 18);
        toolMaterialCrystalWhite = EnumHelper.addToolMaterial("toolMaterialCrystalWhite", 2, 280, 6.25F, 2.5F, 15);

    }

}
