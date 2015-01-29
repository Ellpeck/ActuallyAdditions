package ellpeck.someprettytechystuff.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettytechystuff.creative.CreativeTab;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemMisc extends Item{

    private final String[] items = new String[]{"MashedFood", "RefinedIron", "RefinedRedstone", "CompressedIron", "Steel"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons = new IIcon[items.length];

    public ItemMisc(){
        this.setHasSubtypes(true);
        this.setUnlocalizedName("itemMisc");
        this.setCreativeTab(CreativeTab.instance);
    }

    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + items[stack.getItemDamage()];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for(int i = 0; i < items.length; i++){
            icons[i] = iconReg.registerIcon(Util.MOD_ID + ":" + this.getUnlocalizedName().substring(5) + this.items[i]);
        }
    }

    public IIcon getIconFromDamage(int i){
        return icons[i];
    }

    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (int i = 0; i < items.length; i++){
            list.add(new ItemStack(this, 1, i));
        }
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName(stack).substring(5) + ".desc"));
        else list.add(Util.shiftForInfo());
    }
}
