package ellpeck.thingycraft.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.thingycraft.ThingyCraft;
import ellpeck.thingycraft.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemGem extends Item {

    public static final IIcon[] textures = new IIcon[Util.gemTypes.length];

    public ItemGem(){
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabBrewing);
        this.setUnlocalizedName("itemGem");
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(Util.isShiftPressed()){
            for(int i = 0; i < Util.gemTypes.length; i++){
                if(this.getDamage(stack) == i) list.add(StatCollector.translateToLocal("tooltip.gem" + Util.gemTypes[i] + ".desc"));
            }
        }
        else{
            list.add(Util.shiftForInfo());
        }
    }

    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + Util.gemTypes[stack.getItemDamage()];
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list){
        for (int i = 0; i < Util.gemTypes.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1){
        return textures[par1];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for (int i = 0; i < Util.gemTypes.length; i++) {
            textures[i] = iconReg.registerIcon(ThingyCraft.MOD_ID + ":" + this.getUnlocalizedName().substring(5) + Util.gemTypes[i]);
        }
    }

}
