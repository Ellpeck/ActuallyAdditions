/*
 * This file ("TheJams.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import net.minecraft.item.EnumRarity;

public enum TheJams{

    CU_BA_RA("cu_ba_ra", 6, 0.1F, EnumRarity.RARE, 5, 12, 12595273),
    GRA_KI_BA("gra_ki_ba", 6, 0.1F, EnumRarity.RARE, 16, 13, 5492820),
    PL_AP_LE("pl_ap_le", 6, 0.1F, EnumRarity.RARE, 15, 3, 13226009),
    CH_AP_CI("ch_ap_ci", 6, 0.1F, EnumRarity.RARE, 10, 1, 13189222),
    HO_ME_KI("ho_me_ki", 6, 0.1F, EnumRarity.RARE, 10, 14, 2031360),
    PI_CO("pi_co", 6, 0.1F, EnumRarity.RARE, 9, 1, 16056203),
    HO_ME_CO("ho_me_co", 6, 0.1F, EnumRarity.RARE, 10, 13, 10462208);

    public final String name;
    public final int healAmount;
    public final float saturation;
    public final EnumRarity rarity;
    public final int firstEffectToGet;
    public final int secondEffectToGet;
    public final int color;

    TheJams(String name, int healAmount, float saturation, EnumRarity rarity, int firstEffectID, int secondEffectID, int color){
        this.name = name;
        this.healAmount = healAmount;
        this.saturation = saturation;
        this.rarity = rarity;
        this.firstEffectToGet = firstEffectID;
        this.secondEffectToGet = secondEffectID;
        this.color = color;
    }
}