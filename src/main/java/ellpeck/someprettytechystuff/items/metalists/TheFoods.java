package ellpeck.someprettytechystuff.items.metalists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public enum TheFoods{

    PUMPKIN_STEW("PumpkinStew", 10, 0.4F, true, 30),
    CARROT_JUICE("CarrotJuice", 6, 0.2F, true, 20),
    FISH_N_CHIPS("FishNChips", 20, 1F, false, 40),
    FRENCH_FRIES("FrenchFries", 16, 0.7F, false, 32),
    FRENCH_FRY("FrenchFry", 1, 0.01F, false, 3),
    SPAGHETTI("Spaghetti", 18, 0.8F, false, 38),
    NOODLE("Noodle", 1, 0.01F, false, 3),
    CHOCOLATE_CAKE("ChocolateCake", 16, 0.45F, false, 45),
    CHOCOLATE("Chocolate", 5, 0.05F, false, 15),
    TOAST("Toast", 7, 0.4F, false, 25),
    SUBMARINE_SANDWICH("SubmarineSandwich", 10, 0.7F, false, 40),
    BIG_COOKIE("BigCookie", 6, 0.1F, false, 20),
    HAMBURGER("Hamburger", 14, 0.9F, false, 40),
    PIZZA("Pizza", 20, 1F, false, 45),
    BAGUETTE("Baguette", 7, 0.2F, false, 25);

    public final String name;
    public final int healAmount;
    public final float saturation;
    public final boolean getsDrunken;
    public final int useDuration;
    @SideOnly(Side.CLIENT)
    public IIcon theIcon;

    private TheFoods(String name, int healAmount, float saturation, boolean getsDrunken, int useDuration){
        this.name = name;
        this.getsDrunken = getsDrunken;
        this.healAmount = healAmount;
        this.saturation = saturation;
        this.useDuration = useDuration;
    }
}