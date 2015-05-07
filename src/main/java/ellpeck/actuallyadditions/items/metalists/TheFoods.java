package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public enum TheFoods implements INameableItem{

    CHEESE("Cheese", 1, 0.5F, false, 3, EnumRarity.common, "foodCheese"),
    PUMPKIN_STEW("PumpkinStew", 10, 1F, true, 30, EnumRarity.common, "foodPumpkinStew"),
    CARROT_JUICE("CarrotJuice", 6, 0.6F, true, 20, EnumRarity.common, "foodCarrotJuice"),
    FISH_N_CHIPS("FishNChips", 20, 5F, false, 40, EnumRarity.uncommon, "foodFishNChips"),
    FRENCH_FRIES("FrenchFries", 16, 4F, false, 32, EnumRarity.common, "foodFrenchFries"),
    FRENCH_FRY("FrenchFry", 3, 0.5F, false, 3, EnumRarity.common, "foodFrenchFry"),
    SPAGHETTI("Spaghetti", 18, 3F, false, 38, EnumRarity.common, "foodSpaghetti"),
    NOODLE("Noodle", 1, 0.5F, false, 3, EnumRarity.common, "foodNoodle"),
    CHOCOLATE_CAKE("ChocolateCake", 16, 2F, false, 45, EnumRarity.uncommon, "foodChocolateCake"),
    CHOCOLATE("Chocolate", 5, 1F, false, 15, EnumRarity.common, "foodChocolate"),
    TOAST("Toast", 3, 0.4F, false, 25, EnumRarity.common, "foodToast"),
    SUBMARINE_SANDWICH("SubmarineSandwich", 10, 8F, false, 40, EnumRarity.uncommon, "foodSubmarineSandwich"),
    BIG_COOKIE("BigCookie", 6, 1F, false, 20, EnumRarity.uncommon, "foodBigCookie"),
    HAMBURGER("Hamburger", 14, 6F, false, 40, EnumRarity.common, "foodHamburger"),
    PIZZA("Pizza", 20, 10F, false, 45, EnumRarity.uncommon, "foodPizza"),
    BAGUETTE("Baguette", 7, 2F, false, 25, EnumRarity.common, "foodBaguette");

    public static void setReturnItems(){
        SPAGHETTI.returnItem = new ItemStack(Items.bowl);
        PUMPKIN_STEW.returnItem = new ItemStack(Items.bowl);
        CARROT_JUICE.returnItem = new ItemStack(Items.glass_bottle);
        FRENCH_FRIES.returnItem = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal());
        FISH_N_CHIPS.returnItem = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal());
    }

    public final String name;
    public final String oredictName;
    public final int healAmount;
    public final float saturation;
    public final boolean getsDrunken;
    public final int useDuration;
    public ItemStack returnItem;
    public final EnumRarity rarity;

    TheFoods(String name, int healAmount, float saturation, boolean getsDrunken, int useDuration, EnumRarity rarity, String oredictName){
        this.name = name;
        this.getsDrunken = getsDrunken;
        this.healAmount = healAmount;
        this.saturation = saturation;
        this.useDuration = useDuration;
        this.rarity = rarity;
        this.oredictName = oredictName;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getOredictName(){
        return this.oredictName;
    }
}