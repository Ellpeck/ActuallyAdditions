package ellpeck.someprettytechystuff.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettytechystuff.creative.CreativeTab;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemFoods extends ItemFood{

    public enum TheFoods{

        /*Name of every single one:
        * "itemFoodNAMEHERE
        * Every food in a 16x16 .png file with that name
        *                v this is said name*/
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
        private IIcon theIcon;

        private TheFoods(String name, int healAmount, float saturation, boolean getsDrunken, int useDuration){
            this.name = name;
            this.getsDrunken = getsDrunken;
            this.healAmount = healAmount;
            this.saturation = saturation;
            this.useDuration = useDuration;
        }
    }

    private final TheFoods[] allFoods = TheFoods.values();

    public ItemFoods(){
        super(0, 0.0F, false);
        this.setUnlocalizedName("itemFood");
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTab.instance);
        this.setAlwaysEdible();
    }

    public int func_150905_g(ItemStack stack){
        return allFoods[stack.getItemDamage()].healAmount;
    }

    public float func_150906_h(ItemStack stack){
        return allFoods[stack.getItemDamage()].saturation;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for(TheFoods theFood : allFoods){
            theFood.theIcon = iconReg.registerIcon(Util.MOD_ID + ":" + this.getUnlocalizedName().substring(5) + theFood.name);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1){
        return allFoods[par1].theIcon;
    }

    public EnumAction getItemUseAction(ItemStack stack){
        return allFoods[stack.getItemDamage()].getsDrunken ? EnumAction.drink : EnumAction.eat;
    }

    public int getMaxItemUseDuration(ItemStack stack){
        return allFoods[stack.getItemDamage()].useDuration;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for (int j = 0; j < allFoods.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + allFoods[stack.getItemDamage()].name;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        if(Util.isShiftPressed()){
            list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName(stack).substring(5) + ".desc"));
            list.add(StatCollector.translateToLocal("tooltip.hunger.desc") + ": " + allFoods[stack.getItemDamage()].healAmount);
            list.add(StatCollector.translateToLocal("tooltip.saturation.desc") + ": " + allFoods[stack.getItemDamage()].saturation);
        }
        else list.add(Util.shiftForInfo());
    }
}