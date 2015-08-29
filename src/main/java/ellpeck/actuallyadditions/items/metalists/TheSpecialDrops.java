/*
 * This file ("TheSpecialDrops.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.EnumRarity;

public enum TheSpecialDrops implements INameableItem{

    SOLIDIFIED_EXPERIENCE("SolidifiedExperience", 40, 3, EntityCreature.class, EnumRarity.uncommon, ConfigBoolValues.EXPERIENCE_DROP.isEnabled()),
    BLOOD_FRAGMENT("BloodFragment", 15, 1, EntityCreature.class, EnumRarity.uncommon, ConfigBoolValues.BLOOD_DROP.isEnabled()),
    HEART_PART("HeartPart", 5, 1, EntityCreature.class, EnumRarity.rare, ConfigBoolValues.HEART_DROP.isEnabled()),
    UNKNOWN_SUBSTANCE("UnknownSubstance", 3, 1, EntitySkeleton.class, EnumRarity.epic, ConfigBoolValues.SUBSTANCE_DROP.isEnabled()),
    PEARL_SHARD("PearlShard", 30, 3, EntityEnderman.class, EnumRarity.epic, ConfigBoolValues.PEARL_SHARD_DROP.isEnabled()),
    EMERALD_SHARD("EmeraldShard", 30, 3, EntityCreeper.class, EnumRarity.rare, ConfigBoolValues.EMERALD_SHARD_CROP.isEnabled());

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