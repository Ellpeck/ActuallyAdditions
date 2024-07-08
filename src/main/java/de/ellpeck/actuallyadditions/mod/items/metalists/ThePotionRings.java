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

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Blocks;

@Deprecated
public enum ThePotionRings {

    SPEED(
        8171462,
        MobEffects.MOVEMENT_SPEED,
        0,
        1,
        10,
        false,
        Rarity.UNCOMMON,
        new ItemStack(Items.SUGAR)
    ),
    HASTE(
        14270531,
        MobEffects.DIG_SPEED,
        0,
        1,
        10,
        false,
        Rarity.EPIC,
        new ItemStack(Items.REPEATER)
    ),
    STRENGTH(
        9643043,
        MobEffects.DAMAGE_BOOST,
        0,
        1,
        10,
        false,
        Rarity.RARE,
        new ItemStack(Items.BLAZE_POWDER)
    ),
    JUMP_BOOST(
        7889559,
        MobEffects.JUMP,
        0,
        1,
        10,
        false,
        Rarity.RARE,
        new ItemStack(Blocks.PISTON)
    ),
    REGEN(
        13458603,
        MobEffects.REGENERATION,
        0,
        1,
        50,
        true,
        Rarity.RARE,
        new ItemStack(Items.GHAST_TEAR)
    ),
    RESISTANCE(
        10044730,
        MobEffects.DAMAGE_RESISTANCE,
        0,
        1,
        10,
        false,
        Rarity.EPIC,
        new ItemStack(Items.SLIME_BALL)
    ),
    FIRE_RESISTANCE(
        14981690,
        MobEffects.FIRE_RESISTANCE,
        0,
        0,
        10,
        false,
        Rarity.UNCOMMON,
        new ItemStack(Items.MAGMA_CREAM)
    ),
    WATER_BREATHING(
        3035801,
        MobEffects.WATER_BREATHING,
        0,
        0,
        10,
        false,
        Rarity.RARE,
        new ItemStack(Items.TROPICAL_FISH)
    ),
    INVISIBILITY(
        8356754,
        MobEffects.INVISIBILITY,
        0,
        0,
        10,
        false,
        Rarity.EPIC,
        new ItemStack(Items.FERMENTED_SPIDER_EYE)
    ),
    NIGHT_VISION(
        2039713,
        MobEffects.NIGHT_VISION,
        0,
        0,
        300,
        false,
        Rarity.RARE,
        new ItemStack(Items.GOLDEN_CARROT)
    );

    public final String name;
    public final int color;
    public final Rarity rarity;
    public final Holder<MobEffect> effect;
    public final int normalAmplifier;
    public final int advancedAmplifier;
    public final int activeTime;
    public final boolean needsWaitBeforeActivating;
    public final ItemStack craftingItem;

    ThePotionRings(int color, Holder<MobEffect> effect, int normalAmplifier, int advancedAmplifier, int activeTime, boolean needsWaitBeforeActivating, Rarity rarity, ItemStack craftingItem) {
        this.name = effect.value().getDisplayName().getString();
        this.color = color;
        this.rarity = rarity;
        this.effect = effect;
        this.normalAmplifier = normalAmplifier;
        this.advancedAmplifier = advancedAmplifier;
        this.activeTime = activeTime;
        this.needsWaitBeforeActivating = needsWaitBeforeActivating;
        this.craftingItem = craftingItem;
    }
}
