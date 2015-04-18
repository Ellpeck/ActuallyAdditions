package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.item.EnumRarity;

public enum TheJams implements INameableItem{

    CU_BA_RA("CuBaRa", 20, 5F, EnumRarity.rare, "jamCuBaRa", 5, 12, 12595273),
    GRA_KI_BA("GraKiBa", 20, 5F, EnumRarity.rare, "jamGraKiBa", 16, 13, 5492820),
    PL_AP_LE("PlApLe", 20, 5F, EnumRarity.rare, "jamPlApLe", 15, 3, 13226009),
    CH_AP_CI("ChApCi", 20, 5F, EnumRarity.rare, "jamChApCi", 10, 1, 13189222),
    HO_ME_KI("HoMeKi", 20, 5F, EnumRarity.rare, "jamHoMeKi", 10, 14, 2031360),
    PI_CO("PiCo", 20, 5F, EnumRarity.rare, "jamPiCo", 9, 1, 16056203);

    public final String name;
    public final String oredictName;
    public final int healAmount;
    public final float saturation;
    public final EnumRarity rarity;
    public final int firstEffectToGet;
    public final int secondEffectToGet;
    public final int color;

    TheJams(String name, int healAmount, float saturation, EnumRarity rarity, String oredictName, int firstEffectID, int secondEffectID, int color){
        this.name = name;
        this.healAmount = healAmount;
        this.saturation = saturation;
        this.rarity = rarity;
        this.oredictName = oredictName;
        this.firstEffectToGet = firstEffectID;
        this.secondEffectToGet = secondEffectID;
        this.color = color;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getOredictName(){
        return this.oredictName;
    }
}