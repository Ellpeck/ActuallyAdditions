/*
 * This file ("TheJams.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.item.EnumRarity;

public enum TheJams implements INameableItem{

    CU_BA_RA("CuBaRa", 6, 0.1F, EnumRarity.rare, 5, 12, 12595273),
    GRA_KI_BA("GraKiBa", 6, 0.1F, EnumRarity.rare, 16, 13, 5492820),
    PL_AP_LE("PlApLe", 6, 0.1F, EnumRarity.rare, 15, 3, 13226009),
    CH_AP_CI("ChApCi", 6, 0.1F, EnumRarity.rare, 10, 1, 13189222),
    HO_ME_KI("HoMeKi", 6, 0.1F, EnumRarity.rare, 10, 14, 2031360),
    PI_CO("PiCo", 6, 0.1F, EnumRarity.rare, 9, 1, 16056203);

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

    @Override
    public String getName(){
        return this.name;
    }
}