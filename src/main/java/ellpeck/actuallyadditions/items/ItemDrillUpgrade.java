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
        SPEED,
        SPEED_II,
        SPEED_III,
        SILK_TOUCH, //Done
        FORTUNE, //Done
        FORTUNE_II, //Done
        THREE_BY_THREE, //Done
        FIVE_BY_FIVE, //Done
        VEIN,
        PLACER
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
