/*
 * This file ("TheFoods.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

@Deprecated
public enum TheFoods {

    CHEESE("cheese", 1, 0.05F, false, 3, Rarity.COMMON),
    PUMPKIN_STEW("pumpkin_stew", 6, 0.3F, true, 30, Rarity.COMMON),
    CARROT_JUICE("carrot_juice", 4, 0.2F, true, 20, Rarity.COMMON),
    FISH_N_CHIPS("fish_n_chips", 14, 0.65F, false, 40, Rarity.UNCOMMON),
    FRENCH_FRIES("french_fries", 10, 0.6F, false, 32, Rarity.COMMON),
    FRENCH_FRY("french_fry", 2, 0.025F, false, 3, Rarity.COMMON),
    SPAGHETTI("spaghetti", 7, 0.4F, false, 38, Rarity.COMMON),
    NOODLE("noodle", 1, 0.01F, false, 3, Rarity.COMMON),
    CHOCOLATE_CAKE("chocolate_cake", 16, 0.8F, false, 45, Rarity.UNCOMMON),
    CHOCOLATE("chocolate", 3, 0.3F, false, 15, Rarity.COMMON),
    TOAST("toast", 3, 0.08F, false, 25, Rarity.COMMON),
    SUBMARINE_SANDWICH("submarine_sandwich", 9, 0.4F, false, 40, Rarity.UNCOMMON),
    BIG_COOKIE("big_cookie", 4, 0.25F, false, 20, Rarity.UNCOMMON),
    HAMBURGER("hamburger", 13, 0.65F, false, 40, Rarity.COMMON),
    PIZZA("pizza", 16, 0.8F, false, 45, Rarity.UNCOMMON),
    BAGUETTE("baguette", 6, 0.5F, false, 25, Rarity.COMMON),
    RICE("rice", 2, 0.05F, false, 10, Rarity.UNCOMMON),
    RICE_BREAD("rice_bread", 6, 0.5F, false, 25, Rarity.UNCOMMON),
    DOUGHNUT("doughnut", 2, 0.1F, false, 10, Rarity.EPIC),
    CHOCOLATE_TOAST("chocolate_toast", 5, 0.2F, false, 40, Rarity.RARE),
    BACON("bacon", 4, 0.1F, false, 30, Rarity.COMMON);

    public final String name;
    public final int healAmount;
    public final float saturation;
    public final boolean getsDrunken;
    public final int useDuration;
    public final Rarity rarity;
    public ItemStack returnItem = ItemStack.EMPTY;

    TheFoods(String name, int healAmount, float saturation, boolean getsDrunken, int useDuration, Rarity rarity) {
        this.name = name;
        this.getsDrunken = getsDrunken;
        this.healAmount = healAmount;
        this.saturation = saturation;
        this.useDuration = useDuration;
        this.rarity = rarity;
    }

    public static void setReturnItems() {
        SPAGHETTI.returnItem = new ItemStack(Items.BOWL);
        PUMPKIN_STEW.returnItem = new ItemStack(Items.BOWL);
        CARROT_JUICE.returnItem = new ItemStack(Items.GLASS_BOTTLE);
    }
}
