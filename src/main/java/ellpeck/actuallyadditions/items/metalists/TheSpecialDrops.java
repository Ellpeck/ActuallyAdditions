package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.EnumRarity;

public enum TheSpecialDrops implements INameableItem{

    SOLIDIFIED_EXPERIENCE("SolidifiedExperience", 40, 3, EntityCreature.class, EnumRarity.uncommon, ConfigValues.enableExperienceDrop, "itemSolidifiedExperience"),
    BLOOD_FRAGMENT("BloodFragment", 15, 1, EntityCreature.class, EnumRarity.uncommon, ConfigValues.enableBloodDrop, "itemBloodFragment"),
    HEART_PART("HeartPart", 5, 1, EntityCreature.class, EnumRarity.rare, ConfigValues.enableHeartDrop, "itemHeartPart"),
    UNKNOWN_SUBSTANCE("UnknownSubstance", 3, 1, EntitySkeleton.class, EnumRarity.epic, ConfigValues.enableSubstanceDrop, "itemUnknownSubstance"),
    PEARL_SHARD("PearlShard", 30, 3, EntityEnderman.class, EnumRarity.epic, ConfigValues.enablePearlShardDrop, "nuggetEnderpearl"),
    EMERALD_SHARD("EmeraldShard", 30, 3, EntityCreeper.class, EnumRarity.rare, ConfigValues.enableEmeraldShardDrop, "nuggetEmerald");

    public final String name;
    public final String oredictName;
    public final int chance;
    public final int maxAmount;
    public final Class<? extends EntityCreature> dropFrom;
    public final boolean canDrop;
    public final EnumRarity rarity;

    TheSpecialDrops(String name, int chance, int maxAmount, Class<? extends EntityCreature> dropFrom, EnumRarity rarity, boolean canDrop, String oredictName){
        this.name = name;
        this.chance = chance;
        this.rarity = rarity;
        this.maxAmount = maxAmount;
        this.dropFrom = dropFrom;
        this.canDrop = canDrop;
        this.oredictName = oredictName;
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