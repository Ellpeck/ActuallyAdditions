package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.util.IName;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.EnumRarity;

public enum TheSpecialDrops implements IName{

    SOLIDIFIED_EXPERIENCE("SolidifiedExperience", 40, 3, EntityCreature.class, EnumRarity.uncommon, ConfigValues.enableExperienceDrop),
    BLOOD_FRAGMENT("BloodFragment", 15, 1, EntityCreature.class, EnumRarity.uncommon, ConfigValues.enableBloodDrop),
    HEART_PART("HeartPart", 5, 1, EntityCreature.class, EnumRarity.rare, ConfigValues.enableHeartDrop),
    UNKNOWN_SUBSTANCE("UnknownSubstance", 3, 1, EntitySkeleton.class, EnumRarity.epic, ConfigValues.enableSubstanceDrop),
    PEARL_SHARD("PearlShard", 20, 3, EntityEnderman.class, EnumRarity.epic, ConfigValues.enablePearlShardDrop),
    EMERALD_SHARD("EmeraldShard", 15, 1, EntityCreeper.class, EnumRarity.rare, ConfigValues.enableEmeraldShardDrop);

    public final String name;
    public final int chance;
    public final int maxAmount;
    public final Class<? extends EntityCreature> dropFrom;
    public final boolean canDrop;
    public final EnumRarity rarity;

    TheSpecialDrops(String name, int chance, int maxAmount, Class<? extends EntityCreature> dropFrom, EnumRarity rarity, boolean canDrop){
        this.name = name;
        this.chance = chance;
        this.rarity = rarity;
        this.maxAmount = maxAmount;
        this.dropFrom = dropFrom;
        this.canDrop = canDrop;
    }

    @Override
    public String getName(){
        return this.name;
    }
}