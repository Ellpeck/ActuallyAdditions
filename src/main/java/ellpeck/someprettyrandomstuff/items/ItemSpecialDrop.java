package ellpeck.someprettyrandomstuff.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.items.metalists.TheSpecialDrops;
import ellpeck.someprettyrandomstuff.util.IName;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemSpecialDrop extends Item implements IName{

    public static final TheSpecialDrops[] allDrops = TheSpecialDrops.values();
    public IIcon[] textures = new IIcon[allDrops.length];

    public ItemSpecialDrop(){
        this.setUnlocalizedName(Util.setUnlocalizedName(this));
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTab.instance);
    }

    @Override
    public String getName(){
        return "itemSpecial";
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return allDrops[stack.getItemDamage()].rarity;
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allDrops.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + allDrops[stack.getItemDamage()].name;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + Util.MOD_ID_LOWER + "." + this.getName() + allDrops[stack.getItemDamage()].getName() + ".desc"));
        else list.add(Util.shiftForInfo());
    }

    @Override
    public IIcon getIconFromDamage(int par1){
        return textures[par1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + this.getName() + allDrops[i].getName());
        }
    }
}
