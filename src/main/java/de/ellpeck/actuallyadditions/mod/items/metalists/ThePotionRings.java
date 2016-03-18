/*
 * This file ("ThePotionRings.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public enum ThePotionRings{

    SPEED(MobEffects.moveSpeed.getName(), 8171462, MobEffects.moveSpeed, 0, 1, 10, false, EnumRarity.UNCOMMON, new ItemStack(Items.sugar)),
    //Slowness
    HASTE(MobEffects.digSpeed.getName(), 14270531, MobEffects.digSpeed, 0, 1, 10, false, EnumRarity.EPIC, new ItemStack(Items.repeater)),
    //Mining Fatigue
    STRENGTH(MobEffects.damageBoost.getName(), 9643043, MobEffects.damageBoost, 0, 1, 10, false, EnumRarity.RARE, new ItemStack(Items.blaze_powder)),
    //Health (Not Happening)
    //Damage
    JUMP_BOOST(MobEffects.jump.getName(), 7889559, MobEffects.jump, 0, 1, 10, false, EnumRarity.RARE, new ItemStack(Blocks.piston)),
    //Nausea
    REGEN(MobEffects.regeneration.getName(), 13458603, MobEffects.regeneration, 0, 1, 50, true, EnumRarity.RARE, new ItemStack(Items.ghast_tear)),
    RESISTANCE(MobEffects.resistance.getName(), 10044730, MobEffects.resistance, 0, 1, 10, false, EnumRarity.EPIC, new ItemStack(Items.slime_ball)),
    FIRE_RESISTANCE(MobEffects.fireResistance.getName(), 14981690, MobEffects.fireResistance, 0, 0, 10, false, EnumRarity.UNCOMMON, new ItemStack(Items.magma_cream)),
    WATER_BREATHING(MobEffects.waterBreathing.getName(), 3035801, MobEffects.waterBreathing, 0, 0, 10, false, EnumRarity.RARE, new ItemStack(Items.fish, 1, 3)),
    INVISIBILITY(MobEffects.invisibility.getName(), 8356754, MobEffects.invisibility, 0, 0, 10, false, EnumRarity.EPIC, new ItemStack(Items.fermented_spider_eye)),
    //Blindness
    NIGHT_VISION(MobEffects.nightVision.getName(), 2039713, MobEffects.nightVision, 0, 0, 300, false, EnumRarity.RARE, new ItemStack(Items.golden_carrot));
    //Hunger
    //Weakness
    //Poison
    //Withering
    //Health Boost (Not Happening)
    //Absorption (Not Happening)

    public final String name;
    public final int color;
    public final EnumRarity rarity;
    public final int effectID;
    public final int normalAmplifier;
    public final int advancedAmplifier;
    public final int activeTime;
    public final boolean needsWaitBeforeActivating;
    public final ItemStack craftingItem;

    ThePotionRings(String name, int color, Potion effect, int normalAmplifier, int advancedAmplifier, int activeTime, boolean needsWaitBeforeActivating, EnumRarity rarity, ItemStack craftingItem){
        this.name = name;
        this.color = color;
        this.rarity = rarity;
        this.effectID = Potion.getIdFromPotion(effect);
        this.normalAmplifier = normalAmplifier;
        this.advancedAmplifier = advancedAmplifier;
        this.activeTime = activeTime;
        this.needsWaitBeforeActivating = needsWaitBeforeActivating;
        this.craftingItem = craftingItem;
    }
}