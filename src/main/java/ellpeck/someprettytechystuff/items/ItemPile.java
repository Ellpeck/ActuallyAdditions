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

public class ItemPile extends Item{

    public final String[] pileTypes = new String[]{"Iron", "Gold", "Redstone", "Diamond"};
    public final IIcon[] textures = new IIcon[pileTypes.length];

    public ItemPile(){
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTab.instance);
        this.setUnlocalizedName("itemPile");
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(Util.isShiftPressed()){
            for(int i = 0; i < this.pileTypes.length; i++){
                if(this.getDamage(stack) == i) list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName().substring(5) + this.pileTypes[i] + ".desc"));
            }
        }
        else list.add(Util.shiftForInfo());
    }

    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + this.pileTypes[stack.getItemDamage()];
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list){
        for (int i = 0; i < this.pileTypes.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1){
        return textures[par1];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for (int i = 0; i < this.pileTypes.length; i++) {
            textures[i] = iconReg.registerIcon(Util.MOD_ID + ":" + this.getUnlocalizedName().substring(5) + this.pileTypes[i]);
        }
    }

}