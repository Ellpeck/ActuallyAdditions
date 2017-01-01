/*
 * This file ("ThePotionRings.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public enum ThePotionRings{

    SPEED(MobEffects.SPEED.getName(), 8171462, MobEffects.SPEED, 0, 1, 10, false, EnumRarity.UNCOMMON, new ItemStack(Items.SUGAR)),
    //Slowness
    HASTE(MobEffects.HASTE.getName(), 14270531, MobEffects.HASTE, 0, 1, 10, false, EnumRarity.EPIC, new ItemStack(Items.REPEATER)),
    //Mining Fatigue
    STRENGTH(MobEffects.STRENGTH.getName(), 9643043, MobEffects.STRENGTH, 0, 1, 10, false, EnumRarity.RARE, new ItemStack(Items.BLAZE_POWDER)),
    //Health (Not Happening)
    //Damage
    JUMP_BOOST(MobEffects.JUMP_BOOST.getName(), 7889559, MobEffects.JUMP_BOOST, 0, 1, 10, false, EnumRarity.RARE, new ItemStack(Blocks.PISTON)),
    //Nausea
    REGEN(MobEffects.REGENERATION.getName(), 13458603, MobEffects.REGENERATION, 0, 1, 50, true, EnumRarity.RARE, new ItemStack(Items.GHAST_TEAR)),
    RESISTANCE(MobEffects.RESISTANCE.getName(), 10044730, MobEffects.RESISTANCE, 0, 1, 10, false, EnumRarity.EPIC, new ItemStack(Items.SLIME_BALL)),
    FIRE_RESISTANCE(MobEffects.FIRE_RESISTANCE.getName(), 14981690, MobEffects.FIRE_RESISTANCE, 0, 0, 10, false, EnumRarity.UNCOMMON, new ItemStack(Items.MAGMA_CREAM)),
    WATER_BREATHING(MobEffects.WATER_BREATHING.getName(), 3035801, MobEffects.WATER_BREATHING, 0, 0, 10, false, EnumRarity.RARE, new ItemStack(Items.FISH, 1, 3)),
    INVISIBILITY(MobEffects.INVISIBILITY.getName(), 8356754, MobEffects.INVISIBILITY, 0, 0, 10, false, EnumRarity.EPIC, new ItemStack(Items.FERMENTED_SPIDER_EYE)),
    //Blindness
    NIGHT_VISION(MobEffects.NIGHT_VISION.getName(), 2039713, MobEffects.NIGHT_VISION, 0, 0, 300, false, EnumRarity.RARE, new ItemStack(Items.GOLDEN_CARROT));
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