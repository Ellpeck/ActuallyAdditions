/*
 * This file ("TheFoods.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public enum TheFoods implements INameableItem{

    CHEESE("Cheese", 1, 0.5F, false, 3, EnumRarity.common),
    PUMPKIN_STEW("PumpkinStew", 10, 1F, true, 30, EnumRarity.common),
    CARROT_JUICE("CarrotJuice", 6, 0.6F, true, 20, EnumRarity.common),
    FISH_N_CHIPS("FishNChips", 20, 5F, false, 40, EnumRarity.uncommon),
    FRENCH_FRIES("FrenchFries", 16, 4F, false, 32, EnumRarity.common),
    FRENCH_FRY("FrenchFry", 3, 0.5F, false, 3, EnumRarity.common),
    SPAGHETTI("Spaghetti", 18, 3F, false, 38, EnumRarity.common),
    NOODLE("Noodle", 1, 0.5F, false, 3, EnumRarity.common),
    CHOCOLATE_CAKE("ChocolateCake", 16, 2F, false, 45, EnumRarity.uncommon),
    CHOCOLATE("Chocolate", 5, 1F, false, 15, EnumRarity.common),
    TOAST("Toast", 3, 0.4F, false, 25, EnumRarity.common),
    SUBMARINE_SANDWICH("SubmarineSandwich", 10, 8F, false, 40, EnumRarity.uncommon),
    BIG_COOKIE("BigCookie", 6, 1F, false, 20, EnumRarity.uncommon),
    HAMBURGER("Hamburger", 14, 6F, false, 40, EnumRarity.common),
    PIZZA("Pizza", 20, 10F, false, 45, EnumRarity.uncommon),
    BAGUETTE("Baguette", 7, 2F, false, 25, EnumRarity.common),
    RICE("Rice", 2, 1F, false, 10, EnumRarity.uncommon),
    RICE_BREAD("RiceBread", 8, 3F, false, 25, EnumRarity.uncommon),
    DOUGHNUT("Doughnut", 4, 0.5F, false, 10, EnumRarity.epic);

    public static void setReturnItems(){
        SPAGHETTI.returnItem = new ItemStack(Items.bowl);
        PUMPKIN_STEW.returnItem = new ItemStack(Items.bowl);
        CARROT_JUICE.returnItem = new ItemStack(Items.glass_bottle);
        FRENCH_FRIES.returnItem = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal());
        FISH_N_CHIPS.returnItem = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal());
    }

    public final String name;
    public final int healAmount;
    public final float saturation;
    public final boolean getsDrunken;
    public final int useDuration;
    public ItemStack returnItem;
    public final EnumRarity rarity;

    TheFoods(String name, int healAmount, float saturation, boolean getsDrunken, int useDuration, EnumRarity rarity){
        this.name = name;
        this.getsDrunken = getsDrunken;
        this.healAmount = healAmount;
        this.saturation = saturation;
        this.useDuration = useDuration;
        this.rarity = rarity;
    }

    @Override
    public String getName(){
        return this.name;
    }
}