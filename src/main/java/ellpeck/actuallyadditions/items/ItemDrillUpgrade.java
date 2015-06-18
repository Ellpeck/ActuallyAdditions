package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemDrillUpgrade extends Item implements INameableItem{

    public enum UpgradeType{
        SPEED(10),
        SPEED_II(20),
        SPEED_III(30),
        SILK_TOUCH(20), //Done
        FORTUNE(40), //Done
        FORTUNE_II(60), //Done
        THREE_BY_THREE(5), //Done
        FIVE_BY_FIVE(5), //Done
        VEIN(30),
        PLACER(0);

        public int extraEnergy;

        UpgradeType(int extraEnergy){
            this.extraEnergy = extraEnergy;
        }
    }

    public UpgradeType type;
    public String unlocalizedName;

    public ItemDrillUpgrade(UpgradeType type, String unlocName){
        this.type = type;
        this.unlocalizedName = unlocName;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public String getName(){
        return this.unlocalizedName;
    }

    @Override
    public String getOredictName(){
        return this.getName();
    }
}
