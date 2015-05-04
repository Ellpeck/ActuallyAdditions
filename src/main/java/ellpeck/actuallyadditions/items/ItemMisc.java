package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemMisc extends Item implements INameableItem{

    public static final TheMiscItems[] allMiscItems = TheMiscItems.values();
    public IIcon[] textures = new IIcon[allMiscItems.length];

    public ItemMisc(){
        this.setHasSubtypes(true);
    }

    @Override
    public String getName(){
        return "itemMisc";
    }

    @Override
    public String getOredictName(){
        return "";
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return allMiscItems[stack.getItemDamage()].rarity;
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allMiscItems.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + allMiscItems[stack.getItemDamage()].name;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        ItemUtil.addInformation(this, list, 1, allMiscItems[stack.getItemDamage()].getName());
    }

    @Override
    public IIcon getIconFromDamage(int par1){
        return textures[par1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + allMiscItems[i].getName());
        }
    }
}
