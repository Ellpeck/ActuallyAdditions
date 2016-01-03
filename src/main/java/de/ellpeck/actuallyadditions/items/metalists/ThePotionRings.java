/*
 * This file ("ThePotionRings.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.items.metalists;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public enum ThePotionRings{

    SPEED(Potion.moveSpeed.getName(), 8171462, Potion.moveSpeed.getId(), 0, 1, 10, false, EnumRarity.uncommon, new ItemStack(Items.sugar)),
    //Slowness
    HASTE(Potion.digSpeed.getName(), 14270531, Potion.digSpeed.getId(), 0, 1, 10, false, EnumRarity.epic, new ItemStack(Items.repeater)),
    //Mining Fatigue
    STRENGTH(Potion.damageBoost.getName(), 9643043, Potion.damageBoost.getId(), 0, 1, 10, false, EnumRarity.rare, new ItemStack(Items.blaze_powder)),
    //Health (Not Happening)
    //Damage
    JUMP_BOOST(Potion.jump.getName(), 7889559, Potion.jump.getId(), 0, 1, 10, false, EnumRarity.rare, new ItemStack(Blocks.piston)),
    //Nausea
    REGEN(Potion.regeneration.getName(), 13458603, Potion.regeneration.getId(), 0, 1, 50, true, EnumRarity.rare, new ItemStack(Items.ghast_tear)),
    RESISTANCE(Potion.resistance.getName(), 10044730, Potion.resistance.getId(), 0, 1, 10, false, EnumRarity.epic, new ItemStack(Items.slime_ball)),
    FIRE_RESISTANCE(Potion.fireResistance.getName(), 14981690, Potion.fireResistance.getId(), 0, 0, 10, false, EnumRarity.uncommon, new ItemStack(Items.magma_cream)),
    WATER_BREATHING(Potion.waterBreathing.getName(), 3035801, Potion.waterBreathing.getId(), 0, 0, 10, false, EnumRarity.rare, new ItemStack(Items.fish, 1, 3)),
    INVISIBILITY(Potion.invisibility.getName(), 8356754, Potion.invisibility.getId(), 0, 0, 10, false, EnumRarity.epic, new ItemStack(Items.fermented_spider_eye)),
    //Blindness
    NIGHT_VISION(Potion.nightVision.getName(), 2039713, Potion.nightVision.getId(), 0, 0, 300, false, EnumRarity.rare, new ItemStack(Items.golden_carrot));
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

    ThePotionRings(String name, int color, int effectID, int normalAmplifier, int advancedAmplifier, int activeTime, boolean needsWaitBeforeActivating, EnumRarity rarity, ItemStack craftingItem){
        this.name = name;
        this.color = color;
        this.rarity = rarity;
        this.effectID = effectID;
        this.normalAmplifier = normalAmplifier;
        this.advancedAmplifier = advancedAmplifier;
        this.activeTime = activeTime;
        this.needsWaitBeforeActivating = needsWaitBeforeActivating;
        this.craftingItem = craftingItem;
    }
}