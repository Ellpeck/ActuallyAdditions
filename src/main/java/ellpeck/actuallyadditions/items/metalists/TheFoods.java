package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.IName;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public enum TheFoods implements IName{

    CHEESE("Cheese", 1, 0.1F, false, 3, EnumRarity.common),
    PUMPKIN_STEW("PumpkinStew", 10, 0.4F, true, 30, EnumRarity.common),
    CARROT_JUICE("CarrotJuice", 6, 0.2F, true, 20, EnumRarity.common),
    FISH_N_CHIPS("FishNChips", 20, 1F, false, 40, EnumRarity.uncommon),
    FRENCH_FRIES("FrenchFries", 16, 0.7F, false, 32, EnumRarity.common),
    FRENCH_FRY("FrenchFry", 1, 0.01F, false, 3, EnumRarity.common),
    SPAGHETTI("Spaghetti", 18, 0.8F, false, 38, EnumRarity.common),
    NOODLE("Noodle", 1, 0.01F, false, 3, EnumRarity.common),
    CHOCOLATE_CAKE("ChocolateCake", 16, 0.45F, false, 45, EnumRarity.uncommon),
    CHOCOLATE("Chocolate", 5, 0.05F, false, 15, EnumRarity.common),
    TOAST("Toast", 7, 0.4F, false, 25, EnumRarity.common),
    SUBMARINE_SANDWICH("SubmarineSandwich", 10, 0.7F, false, 40, EnumRarity.uncommon),
    BIG_COOKIE("BigCookie", 6, 0.1F, false, 20, EnumRarity.uncommon),
    HAMBURGER("Hamburger", 14, 0.9F, false, 40, EnumRarity.common),
    PIZZA("Pizza", 20, 1F, false, 45, EnumRarity.uncommon),
    BAGUETTE("Baguette", 7, 0.2F, false, 25, EnumRarity.common);

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

    private TheFoods(String name, int healAmount, float saturation, boolean getsDrunken, int useDuration, EnumRarity rarity){
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