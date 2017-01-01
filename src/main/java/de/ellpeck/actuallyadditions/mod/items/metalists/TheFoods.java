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

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public enum TheFoods{

    CHEESE("cheese", 1, 0.05F, false, 3, EnumRarity.COMMON),
    PUMPKIN_STEW("pumpkin_stew", 6, 0.3F, true, 30, EnumRarity.COMMON),
    CARROT_JUICE("carrot_juice", 4, 0.2F, true, 20, EnumRarity.COMMON),
    FISH_N_CHIPS("fish_n_chips", 14, 0.65F, false, 40, EnumRarity.UNCOMMON),
    FRENCH_FRIES("french_fries", 10, 0.6F, false, 32, EnumRarity.COMMON),
    FRENCH_FRY("french_fry", 2, 0.025F, false, 3, EnumRarity.COMMON),
    SPAGHETTI("spaghetti", 7, 0.4F, false, 38, EnumRarity.COMMON),
    NOODLE("noodle", 1, 0.01F, false, 3, EnumRarity.COMMON),
    CHOCOLATE_CAKE("chocolate_cake", 16, 0.8F, false, 45, EnumRarity.UNCOMMON),
    CHOCOLATE("chocolate", 3, 0.3F, false, 15, EnumRarity.COMMON),
    TOAST("toast", 3, 0.08F, false, 25, EnumRarity.COMMON),
    SUBMARINE_SANDWICH("submarine_sandwich", 9, 0.4F, false, 40, EnumRarity.UNCOMMON),
    BIG_COOKIE("big_cookie", 4, 0.25F, false, 20, EnumRarity.UNCOMMON),
    HAMBURGER("hamburger", 13, 0.65F, false, 40, EnumRarity.COMMON),
    PIZZA("pizza", 16, 0.8F, false, 45, EnumRarity.UNCOMMON),
    BAGUETTE("baguette", 6, 0.5F, false, 25, EnumRarity.COMMON),
    RICE("rice", 2, 0.05F, false, 10, EnumRarity.UNCOMMON),
    RICE_BREAD("rice_bread", 6, 0.5F, false, 25, EnumRarity.UNCOMMON),
    DOUGHNUT("doughnut", 2, 0.1F, false, 10, EnumRarity.EPIC),
    CHOCOLATE_TOAST("chocolate_toast", 5, 0.2F, false, 40, EnumRarity.RARE),
    BACON("bacon", 4, 0.1F, false, 30, EnumRarity.COMMON);

    public final String name;
    public final int healAmount;
    public final float saturation;
    public final boolean getsDrunken;
    public final int useDuration;
    public final EnumRarity rarity;
    public ItemStack returnItem = StackUtil.getNull();

    TheFoods(String name, int healAmount, float saturation, boolean getsDrunken, int useDuration, EnumRarity rarity){
        this.name = name;
        this.getsDrunken = getsDrunken;
        this.healAmount = healAmount;
        this.saturation = saturation;
        this.useDuration = useDuration;
        this.rarity = rarity;
    }

    public static void setReturnItems(){
        SPAGHETTI.returnItem = new ItemStack(Items.BOWL);
        PUMPKIN_STEW.returnItem = new ItemStack(Items.BOWL);
        CARROT_JUICE.returnItem = new ItemStack(Items.GLASS_BOTTLE);
        FRENCH_FRIES.returnItem = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal());
        FISH_N_CHIPS.returnItem = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal());
    }
}