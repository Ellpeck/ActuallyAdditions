package ellpeck.someprettyrandomstuff.items.metalists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.items.InitItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public enum TheFoods{

    CHEESE("Cheese", 1, 0.1F, false, 3, null),
    PUMPKIN_STEW("PumpkinStew", 10, 0.4F, true, 30, new ItemStack(Items.bowl)),
    CARROT_JUICE("CarrotJuice", 6, 0.2F, true, 20, new ItemStack(Items.glass_bottle)),
    FISH_N_CHIPS("FishNChips", 20, 1F, false, 40, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal())),
    FRENCH_FRIES("FrenchFries", 16, 0.7F, false, 32, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal())),
    FRENCH_FRY("FrenchFry", 1, 0.01F, false, 3, null),
    SPAGHETTI("Spaghetti", 18, 0.8F, false, 38, new ItemStack(Items.bowl)),
    NOODLE("Noodle", 1, 0.01F, false, 3, null),
    CHOCOLATE_CAKE("ChocolateCake", 16, 0.45F, false, 45, null),
    CHOCOLATE("Chocolate", 5, 0.05F, false, 15, null),
    TOAST("Toast", 7, 0.4F, false, 25, null),
    SUBMARINE_SANDWICH("SubmarineSandwich", 10, 0.7F, false, 40, null),
    BIG_COOKIE("BigCookie", 6, 0.1F, false, 20, null),
    HAMBURGER("Hamburger", 14, 0.9F, false, 40, null),
    PIZZA("Pizza", 20, 1F, false, 45, null),
    BAGUETTE("Baguette", 7, 0.2F, false, 25, null);

    public final String name;
    public final int healAmount;
    public final float saturation;
    public final boolean getsDrunken;
    public final int useDuration;
    public final ItemStack returnItem;
    @SideOnly(Side.CLIENT)
    public IIcon theIcon;

    private TheFoods(String name, int healAmount, float saturation, boolean getsDrunken, int useDuration, ItemStack returnItem){
        this.name = name;
        this.getsDrunken = getsDrunken;
        this.healAmount = healAmount;
        this.saturation = saturation;
        this.useDuration = useDuration;
        this.returnItem = returnItem;
    }
}